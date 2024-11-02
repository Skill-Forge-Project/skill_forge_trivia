package bg.trivia.model.entities.mongo;

import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode(callSuper = true)
@Document("javascript-questions")
public class JavaScriptQuestion extends Question{
}
