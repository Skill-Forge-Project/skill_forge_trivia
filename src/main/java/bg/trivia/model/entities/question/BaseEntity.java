package bg.trivia.model.entities.question;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
public abstract class BaseEntity {

    @Id
    private String id;
}
