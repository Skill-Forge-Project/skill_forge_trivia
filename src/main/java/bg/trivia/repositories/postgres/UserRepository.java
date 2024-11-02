package bg.trivia.repositories.postgres;

import bg.trivia.model.entities.postgres.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("SELECT u FROM User u WHERE u.userId = :userId AND FUNCTION('DATE', u.startedAt) = CURRENT_DATE")
    Optional<User> findUserByUserIdAndStartedAtToday(@Param("userId") String userId);
}