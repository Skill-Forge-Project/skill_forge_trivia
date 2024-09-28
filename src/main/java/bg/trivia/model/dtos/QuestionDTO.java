package bg.trivia.model.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class QuestionDTO {

    private String question;
    private Map<String, String> options;
    @JsonProperty("correct_answer")
    private String correctAnswer;
    private String difficulty;
    private int lifetime;
    private List<String> topics;
}
