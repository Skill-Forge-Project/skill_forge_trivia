package bg.trivia.controllers;

import bg.trivia.model.dtos.ReportDTO;
import bg.trivia.model.views.ReportView;
import bg.trivia.services.ReportService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/report")
public class ReportController {

    private final ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }


    @Operation(summary = "Report an issue with a question", description = "Allows users to report a question that has some issue, such as incorrect information.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Report submitted successfully", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid input provided", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PostMapping
    public ResponseEntity<?> reportQuestion(
            @Valid @RequestBody ReportDTO reportDTO) throws JsonProcessingException {
        reportService.reportQuestion(reportDTO);
        return ResponseEntity.ok("Report submitted successfully");
    }

    @Operation(
            summary = "Get all reported issues",
            description = "Fetches all reported issues regarding questions that users have submitted."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reports fetched successfully", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/all")
    public ResponseEntity<List<ReportView>> getAllReports(){
        return ResponseEntity.ok(reportService.getAllReports());
    }

    @Operation(summary = "Get all unresolved reports",
            description = "Fetches a list of all unresolved reports submitted by users. Each report contains details of the question and the associated report status.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Successfully retrieved unresolved reports",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ReportView.class))),
            @ApiResponse(responseCode = "500",
                    description = "Internal server error",
                    content = @Content)
    })
    @GetMapping("/unresolved")
    public ResponseEntity<List<ReportView>> getUnresolvedReports(){
        return ResponseEntity.ok(reportService.getAllUnresolvedReports());
    }
}
