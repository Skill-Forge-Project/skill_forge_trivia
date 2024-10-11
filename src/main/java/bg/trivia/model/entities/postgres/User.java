package bg.trivia.model.entities.postgres;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "user_log")
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_log_seq")
    @SequenceGenerator(name = "user_log_seq", sequenceName = "user_log_SEQ", allocationSize = 1)
    private Long id;

    @Column
    private String userId;

    @Column
    private LocalDate startedAt;

    @Column(columnDefinition = "TEXT")
    private String questions;
}
