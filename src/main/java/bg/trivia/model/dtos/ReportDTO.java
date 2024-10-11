package bg.trivia.model.dtos;

import bg.trivia.validations.validId.ValidID;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReportDTO {

    @Schema(description = "The ID of the question being reported")
    @NotNull(message = "Question ID cannot be null")
    @ValidID
    private String questionId;

    @Schema(description = "Id of the user which report the question.")
    @NotNull(message = "User ID cannot be null")
    @NotEmpty
    private String userId;

    @Schema(description = "Description of the issue with the question", example = "The question contains outdated information.")
    @NotBlank(message = "Reason for reporting cannot be blank")
    private String reason;
}
