package bg.trivia.repositories.postgres;

import bg.trivia.model.entities.postgres.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReportRepository extends JpaRepository<Report, String> {
    Optional<Report> findByQuestion(String question);
}
