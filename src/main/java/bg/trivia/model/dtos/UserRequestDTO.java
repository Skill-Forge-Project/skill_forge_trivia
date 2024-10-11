package bg.trivia.model.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDTO {

    @NotNull
    @NotEmpty
    @Pattern(regexp = "USR-\\w+")
    private String userId;

    @Pattern(regexp = "javascript|csharp|java|python", message = "Technology must be java, csharp, javascript, or python")
    private String technology;
}
