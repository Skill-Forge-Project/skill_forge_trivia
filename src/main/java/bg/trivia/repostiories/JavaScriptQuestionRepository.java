package bg.trivia.repostiories;

import bg.trivia.model.entities.JavaScriptQuestion;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JavaScriptQuestionRepository extends MongoRepository<JavaScriptQuestion, String> {
}
