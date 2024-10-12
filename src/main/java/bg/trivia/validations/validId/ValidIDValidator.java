package bg.trivia.validations.validId;

import bg.trivia.exceptions.InvalidInputException;
import bg.trivia.model.entities.question.Question;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;

/**
 * Validator class that implements the logic for the @ValidID annotation.
 * This validator checks whether the given ID exists in any of the MongoDB collections
 * associated with the Question entity.
 *
 * The class uses MongoTemplate to query all MongoDB collections and validate
 * the existence of the provided ID. If the ID is found, the validation passes.
 *
 * The validation skips system collections (e.g., "system.") and checks user-defined collections.
 *
 * @see bg.trivia.validations.validId.ValidID
 */

@Slf4j
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
        log.error("{} : {}", message, value);
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Question with ID " + value + " not found");
    }
}
