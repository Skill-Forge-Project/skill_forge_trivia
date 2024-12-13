package bg.trivia.repositories.mongo;

import bg.trivia.model.entities.mongo.JavaQuestion;
import bg.trivia.model.entities.mongo.Question;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JavaQuestionRepository extends MongoRepository<JavaQuestion, String> {
    List<Question> findTop10RandomByDifficulty(String difficulty);
}
