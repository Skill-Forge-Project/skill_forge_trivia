package bg.trivia.services;

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

    @Autowired
    public QuestionService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public List<? extends Question> get10Questions(String technology, String difficulty) {
        Aggregation agg = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("difficulty").is(difficulty)),
                Aggregation.sample(10)
        );

        AggregationResults<Question> results = mongoTemplate.aggregate(agg, technology +"-questions", Question.class);
        return results.getMappedResults();
    }
}
