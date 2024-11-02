package bg.trivia.model.entities.mongo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode(callSuper = true)
@Document("java-questions")
@Data
public class JavaQuestion extends Question {
}
