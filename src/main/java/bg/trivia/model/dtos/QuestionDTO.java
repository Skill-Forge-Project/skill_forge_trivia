package bg.trivia.model.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class QuestionDTO {

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
