package bg.trivia.model.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Document
@Data
public abstract class Question extends BaseEntity {

    private String question;
    private Map<String, String> options;
    @JsonProperty("correct_answer")
    private String correctAnswer;
    private String difficulty;
    private int lifetime;
    private List<String> topics;
}
