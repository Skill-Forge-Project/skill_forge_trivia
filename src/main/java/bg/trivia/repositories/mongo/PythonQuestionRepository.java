package bg.trivia.repositories.mongo;

import bg.trivia.model.entities.mongo.PythonQuestion;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PythonQuestionRepository extends MongoRepository<PythonQuestion, String> {
}
