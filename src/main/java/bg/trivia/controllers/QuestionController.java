package bg.trivia.controllers;

import bg.trivia.entities.Question;
import bg.trivia.services.QuestionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/trivia")
public class QuestionController {

    private final QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @Operation(summary = "Get 10 questions by technology and difficulty", description = "Fetches 10 random questions based on the specified technology and difficulty level.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved questions",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Question.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid input provided", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping("/{technology}/{difficulty}")
    public ResponseEntity<?> get10EasyQuestions(
            @Parameter(description = "The technology category (e.g., 'Java', 'Python')", required = true)
            @PathVariable String technology,

            @Parameter(description = "The difficulty level (e.g., 'Easy', 'Medium', 'Hard')", required = true)
            @PathVariable String difficulty) {

        List<? extends Question> questions = questionService.get10Questions(technology, difficulty);
        return ResponseEntity.ok().body(questions);
    }
}
