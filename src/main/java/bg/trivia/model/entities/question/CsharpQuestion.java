package bg.trivia.model.entities.question;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode(callSuper = true)
@Document("csharp-questions")
@Data
public class CsharpQuestion extends Question {
}
