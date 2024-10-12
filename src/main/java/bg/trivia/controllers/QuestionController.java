package bg.trivia.controllers;

import bg.trivia.model.dtos.QuestionDTO;
import bg.trivia.model.dtos.UpdateQuestionDTO;
import bg.trivia.model.dtos.UserRequestDTO;
import bg.trivia.model.views.QuestionVIEW;
import bg.trivia.services.QuestionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trivia")
@Tag(name = "Question Management", description = "APIs for managing questions")
public class QuestionController {

    private final QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @Operation(summary = "Get 10 questions by technology for user ID.", description = "Fetches 10 random questions based on the specified technology (javascript/java/csharp/python) for the provided user id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved questions",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = QuestionVIEW.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid input provided", content = @Content),
            @ApiResponse(responseCode = "429", description = "User has already played for today.", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<QuestionVIEW>> get10Questions(@Valid @RequestBody UserRequestDTO userRequestDTO) throws JsonProcessingException {

        List<QuestionVIEW> questions = questionService.get10Questions(userRequestDTO);
        return ResponseEntity.ok().body(questions);
    }

    @Operation(summary = "Create a new question",
            description = "Creates a question for a specified technology. The request body contains the question details including options, correct answer, difficulty, and related topics.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully created question",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = QuestionDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid input provided. Possible validation errors include missing fields or incorrect values.",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    @PostMapping("/create")
    public ResponseEntity<?> createQuestion(@Valid @RequestBody QuestionDTO questionDTO) {
        questionService.createQuestion(questionDTO);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Update an existing question",
            description = "Updates an existing question based on the provided ID and other details. The request body should contain the question ID, text, options, correct answer, difficulty, lifetime, and related topics.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the question"),
            @ApiResponse(responseCode = "400", description = "Invalid input provided. Possible validation errors include missing fields or incorrect values.",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Question not found with the provided ID",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    @PatchMapping("/update")
    public ResponseEntity<?> updateQuestion(@Parameter(description = "Question details to update, including ID, options, and other details",
            required = true,
            schema = @Schema(implementation = UpdateQuestionDTO.class))
                                                @Valid @RequestBody UpdateQuestionDTO updatequestionDTO) throws JsonProcessingException {
        questionService.updateQuestion(updatequestionDTO);
        return ResponseEntity.ok().build();
    }
}