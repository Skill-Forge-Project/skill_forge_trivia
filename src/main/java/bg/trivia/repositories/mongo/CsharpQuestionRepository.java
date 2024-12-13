package bg.trivia.repositories.mongo;

import bg.trivia.model.entities.mongo.CsharpQuestion;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CsharpQuestionRepository extends MongoRepository<CsharpQuestion, String> {
}
