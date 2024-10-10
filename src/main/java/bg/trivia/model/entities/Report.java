package bg.trivia.model.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Document
@Getter
@Setter
@NoArgsConstructor
public class Report extends BaseEntity {

    private Question question;
    private String reason;
    private boolean resolved;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Report report)) return false;
        return Objects.equals(question, report.question);
    }
}
