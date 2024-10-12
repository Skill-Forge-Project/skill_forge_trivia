package bg.trivia.model.dtos;

import bg.trivia.validations.validId.ValidID;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class UpdateQuestionDTO {

    @JsonProperty("id")
    @NotNull
    @NotEmpty
    @ValidID
    private String id;

    @JsonProperty("question")
    @NotNull(message = "Question can't be null")
    @Size(min = 10, max = 1000)
    private String question;

    @JsonProperty("options")
    @NotNull
    @Size(min = 4)
    private Map<String, String> options;

    @JsonProperty("correct_answer")
    @NotNull
    @Size(max = 1, message = "Correct answer can't be more that one.")
    private String correctAnswer;

    @JsonProperty("difficulty")
    @NotEmpty
    @NotNull
    @Pattern(regexp = "Easy|Medium|Hard", message = "Difficulty must be Easy, Medium, or Hard")
    private String difficulty;

    @JsonProperty("lifetime")
    @Min(30)
    @Max(60)
    private int lifetime;

    @JsonProperty("topics")
    @NotNull
    @NotEmpty
    private List<String> topics;
}
