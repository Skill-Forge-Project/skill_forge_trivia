package bg.trivia.repostiories;

import bg.trivia.model.entities.Question;
import bg.trivia.model.entities.Report;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReportRepository extends MongoRepository<Report, String> {
    Optional<Report> findByQuestion(Question question);
}
