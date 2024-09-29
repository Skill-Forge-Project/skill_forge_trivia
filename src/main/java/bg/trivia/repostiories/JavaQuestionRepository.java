package bg.trivia.repostiories;

import bg.trivia.model.entities.JavaQuestion;
import bg.trivia.model.entities.Question;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JavaQuestionRepository extends MongoRepository<JavaQuestion, String> {
    List<Question> findTop10RandomByDifficulty(String difficulty);
}
