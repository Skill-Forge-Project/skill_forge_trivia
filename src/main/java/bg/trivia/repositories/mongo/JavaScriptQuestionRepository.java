package bg.trivia.repositories.mongo;

import bg.trivia.model.entities.question.JavaScriptQuestion;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JavaScriptQuestionRepository extends MongoRepository<JavaScriptQuestion, String> {
}
