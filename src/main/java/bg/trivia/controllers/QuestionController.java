package bg.trivia.controllers;

import bg.trivia.model.dtos.QuestionDTO;
import bg.trivia.model.dtos.QuestionVIEW;
import bg.trivia.model.entities.Question;
import bg.trivia.services.QuestionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<List<QuestionVIEW>> get10Questions(
            @Parameter(description = "The technology category (e.g., 'java', 'python')", required = true)
            @PathVariable String technology,

            @Parameter(description = "The difficulty level (e.g., 'Easy', 'Medium', 'Hard')", required = true)
            @PathVariable String difficulty) {

        List<QuestionVIEW> questions = questionService.get10Questions(technology, difficulty);
        return ResponseEntity.ok().body(questions);
    }

    @Operation(summary = "Create question in given technology", description = "Create question in given technology.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully created question",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Question.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid input provided", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PostMapping("/create")
    public ResponseEntity<List<QuestionDTO>> createQuestion(@RequestBody QuestionDTO questionDTO) {
        questionService.createQuestion(questionDTO);
        return ResponseEntity.ok().body(questions);
    }
}
