package bg.trivia.utils;

import bg.trivia.entities.JavaQuestion;
import bg.trivia.entities.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetQuestions {

    private static MongoTemplate mongoTemplate;

    @Autowired
    public GetQuestions(MongoTemplate mongoTemplate) {
        GetQuestions.mongoTemplate = mongoTemplate;
    }

    public static List<? extends Question> get10EasyQuestions(String technology, String difficulty) {
        Aggregation agg = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("difficulty").is(difficulty)),
                Aggregation.sample(10)
        );

        AggregationResults<Question> results = mongoTemplate.aggregate(agg, technology +"-questions", Question.class);
        return results.getMappedResults();
    }

}
