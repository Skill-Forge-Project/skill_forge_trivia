package bg.trivia.services;

import bg.trivia.mapper.QuestionMapper;
import bg.trivia.model.dtos.QuestionDTO;
import bg.trivia.model.entities.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {


    private MongoTemplate mongoTemplate;
    private QuestionMapper questionMapper;

    @Autowired
    public QuestionService(MongoTemplate mongoTemplate, QuestionMapper questionMapper) {
        this.mongoTemplate = mongoTemplate;
        this.questionMapper = questionMapper;
    }

    public List<QuestionDTO> get10Questions(String technology, String difficulty) {
        Aggregation agg = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("difficulty").is(difficulty)),
                Aggregation.sample(10)
        );

        AggregationResults<Question> results = mongoTemplate.aggregate(agg, technology +"-questions", Question.class);
        return results.getMappedResults().stream().map(questionMapper::toDTO).toList();
    }
}
