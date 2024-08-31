package bg.trivia.repostiories;

import bg.trivia.entities.PythonQuestion;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PythonQuestionRepository extends MongoRepository<PythonQuestion, String> {
}
