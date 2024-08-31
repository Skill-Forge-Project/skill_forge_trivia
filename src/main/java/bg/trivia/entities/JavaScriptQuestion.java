package bg.trivia.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode(callSuper = true)
@Document("javascript-questions")
@Data
public class JavaScriptQuestion extends Question{
}
