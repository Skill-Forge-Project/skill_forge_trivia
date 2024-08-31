package bg.trivia.repostiories;

import bg.trivia.entities.JavaQuestion;
import bg.trivia.entities.Question;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JavaQuestionRepository extends MongoRepository<JavaQuestion, String> {
    @Query(value = "{ 'difficulty': 'Easy' }", sort = "{ $sample: { size: 10 } }")
    List<Question> findTop10RandomByDifficultyEasy();
}
