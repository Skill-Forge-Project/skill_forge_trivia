package bg.trivia.model.entities.mongo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Document
@Getter
@Setter
@NoArgsConstructor
public class Question extends BaseEntity {

    @JsonProperty("question")
    private String question;

    @JsonProperty("options")
    private Map<String, String> options;

    @JsonProperty("correct_answer")
    private String correctAnswer;

    @JsonProperty("difficulty")
    private String difficulty;

    @JsonProperty("lifetime")
    private int lifetime;

    @JsonProperty("topics")
    private List<String> topics;
}
