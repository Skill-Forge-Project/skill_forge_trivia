package bg.trivia.services;

import bg.trivia.exceptions.InvalidInputException;
import bg.trivia.model.dtos.ReportDTO;
import bg.trivia.model.entities.question.Question;
import bg.trivia.repositories.postgres.ReportRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class ReportService {

    private final MongoTemplate mongoTemplate;
    private final ReportRepository reportRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ReportService(MongoTemplate mongoTemplate, ReportRepository reportRepository, ModelMapper modelMapper) {
        this.mongoTemplate = mongoTemplate;
        this.reportRepository = reportRepository;
        this.modelMapper = modelMapper;
    }


    public void reportQuestion(ReportDTO reportDTO) {
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
//                saveReport(questions.get(0));
                exists = true;
                break;
            }
        }
        if (!exists) {
            throw new InvalidInputException("ID " + reportDTO.getQuestionId() + " not found");
        }
    }

//    private void saveReport(Question question) {
//        reportRepository.findByQuestion(question).ifPresentOrElse(existingReport -> {
//            throw new InvalidInputException("Question " + question.getId() + " already exists");
//        }, () -> {
//            Report report = new Report();
//            report.setQuestion(question);
//            report.setResolved(false);
//            reportRepository.save(report);
//        });
//    }
//
//    public List<ReportView> getAllReports() {
//        return reportRepository.findAll()
//                .stream()
//                .map(e -> modelMapper.map(e, ReportView.class))
//                .toList();
//    }
}
