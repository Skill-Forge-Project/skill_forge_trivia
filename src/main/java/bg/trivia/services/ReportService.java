package bg.trivia.services;

import bg.trivia.exceptions.InvalidInputException;
import bg.trivia.model.dtos.QuestionDTO;
import bg.trivia.model.dtos.ReportDTO;
import bg.trivia.model.entities.postgres.Report;
import bg.trivia.model.entities.question.Question;
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
import java.util.Set;

import static java.rmi.server.LogStream.log;

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


    public void reportQuestion(ReportDTO reportDTO) throws JsonProcessingException {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(reportDTO.getQuestionId()));
        boolean exists = false;

        Set<String> collectionNames = mongoTemplate.getCollectionNames();
        for (String collectionName : collectionNames) {
            if (collectionName.equals("system.")) {
                continue;
            }

            List<Question> questions = mongoTemplate.find(query, Question.class, collectionName);
            if (!questions.isEmpty()) {
                saveReport(questions.get(0), reportDTO);
                exists = true;
                break;
            }
        }
        if (!exists) {
            throw new InvalidInputException("ID " + reportDTO.getQuestionId() + " not found");
        }
    }

    private void saveReport(Question question, ReportDTO reportDTO) throws JsonProcessingException {
        reportRepository.findByQuestion(objectMapper.writeValueAsString(question)).ifPresentOrElse(existingReport -> {
            throw new InvalidInputException("Question " + question.getId() + " already exists");
        }, () -> {
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
        });
    }

    public List<ReportView> getAllReports() {
        return reportRepository.findAll()
                .stream()
                .map(e -> {
                    ReportView reportView = modelMapper.map(e, ReportView.class);

                    if (e.getQuestion() != null) {
                        try {
                            QuestionVIEW questionVIEW = objectMapper.readValue(e.getQuestion(), QuestionVIEW.class);
                            reportView.setQuestion(questionVIEW);
                        } catch (JsonProcessingException jsonException) {
                            log.error("Error while deserializing question: {}", jsonException.getMessage());
                        }
                    }

                    return reportView;
                })
                .toList();
    }
}
