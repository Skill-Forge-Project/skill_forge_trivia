package bg.trivia.repositories.mongo;

import bg.trivia.model.entities.mongo.CommonQuestion;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommonRepository extends MongoRepository<CommonQuestion, String> {
}
