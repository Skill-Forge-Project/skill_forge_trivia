package bg.trivia.services;

import bg.trivia.exceptions.InvalidInputException;
import bg.trivia.model.dtos.ReportDTO;
import bg.trivia.model.entities.postgres.Report;
import bg.trivia.model.entities.mongo.Question;
import bg.trivia.model.views.QuestionVIEW;
import bg.trivia.model.views.ReportView;
import bg.trivia.repositories.postgres.ReportRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class ReportService {

    private final MongoTemplate mongoTemplate;
    private final ReportRepository reportRepository;
    private final ModelMapper modelMapper;
    private final ObjectMapper objectMapper;

    @Autowired
    public ReportService(MongoTemplate mongoTemplate, ReportRepository reportRepository, ModelMapper modelMapper, ObjectMapper objectMapper) {
        this.mongoTemplate = mongoTemplate;
        this.reportRepository = reportRepository;
        this.modelMapper = modelMapper;
        this.objectMapper = objectMapper;
    }


    public void reportQuestion(ReportDTO reportDTO) {
        Query query = new Query(Criteria.where("_id").is(reportDTO.getQuestionId()));
        Optional<Question> questionOpt = findQuestionInCollections(query);

        questionOpt.ifPresentOrElse(
                question -> saveReport(question, reportDTO),
                () -> {
                    throw new InvalidInputException("ID " + reportDTO.getQuestionId() + " not found");
                }
        );
    }

    private Optional<Question> findQuestionInCollections(Query query) {
        Set<String> collectionNames = mongoTemplate.getCollectionNames();
        return collectionNames.stream()
                .filter(name -> !name.startsWith("system."))
                .map(name -> mongoTemplate.find(query, Question.class, name))
                .filter(questions -> !questions.isEmpty())
                .findFirst()
                .map(questions -> questions.get(0));
    }

    private void saveReport(Question question, ReportDTO reportDTO) {
        reportRepository.findByQuestionContains(question.getId())
                .ifPresentOrElse(
                        existingReport -> {
                            throw new InvalidInputException("Question " + question.getId() + " already reported.");
                        },
                        () -> {
                            Report report = new Report();
                            try {
                                report.setQuestion(objectMapper.writeValueAsString(question));
                            } catch (JsonProcessingException e) {
                                throw new RuntimeException(e);
                            }
                            report.setResolved(false);
                            report.setReason(reportDTO.getReason());
                            report.setUserId(reportDTO.getUserId());
                            reportRepository.save(report);
                        }
                );

    }

    public List<ReportView> getAllReports() {
        return mapReportsToViews(reportRepository.findAll());
    }

    public List<ReportView> getAllUnresolvedReports() {
        return mapReportsToViews(reportRepository.findByResolvedIsFalse());
    }

    private List<ReportView> mapReportsToViews(List<Report> reports) {
        return reports.stream()
                .map(report -> {
                    ReportView reportView = modelMapper.map(report, ReportView.class);
                    if (report.getQuestion() != null) {
                        deserializeQuestion(report, reportView);
                    }
                    return reportView;
                })
                .toList();
    }

    private void deserializeQuestion(Report report, ReportView reportView) {
        try {
            QuestionVIEW questionVIEW = objectMapper.readValue(report.getQuestion(), QuestionVIEW.class);
            reportView.setQuestion(questionVIEW);
        } catch (JsonProcessingException e) {
            log.error("Error while deserializing question: {}", e.getMessage());
        }
    }

    public void updateReport(String question) {
        Optional<Report> report = reportRepository.findByQuestionContains(question);
        if (report.isPresent()) {
            report.get().setResolved(true);
            reportRepository.save(report.get());
        }
    }
}