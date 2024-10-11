package bg.trivia.validations.validId;

import bg.trivia.model.entities.question.Question;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;
import java.util.Set;

public class ValidIDValidator implements ConstraintValidator<ValidID, String> {

    @Autowired
    private MongoTemplate mongoTemplate;
    private String message;

    @Override
    public void initialize(ValidID constraintAnnotation) {
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        Set<String> collectionNames = mongoTemplate.getCollectionNames();
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(value));

        for (String collectionName : collectionNames) {
            if (collectionName.equals("system.")) {
                continue;
            }

            List<Question> questions = mongoTemplate.find(query, Question.class, collectionName);
            if (!questions.isEmpty()) {
                return true;
            }
        }
        return false;
    }
}
