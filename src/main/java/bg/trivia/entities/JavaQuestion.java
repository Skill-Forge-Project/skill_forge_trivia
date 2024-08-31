package bg.trivia.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode(callSuper = true)
@Document(collation = "java-questions")
@Data
public class JavaQuestions extends Question {
}
