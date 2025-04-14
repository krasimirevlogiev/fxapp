package com.example.controllers;

import com.example.dto.ConversionHistoryResponse;
import com.example.dto.ConversionHistoryRequest;
import com.example.services.ConversionHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/history")
@Tag(name = "Conversion History", description = "Conversion History API")
public class ConversionHistoryController {

    private final ConversionHistoryService historyService;

    public ConversionHistoryController(ConversionHistoryService historyService) {
        this.historyService = historyService;
    }

    @GetMapping
    @Operation(
        summary = "Get conversion history",
        description = "Retrieves conversion history filtered by transaction ID or date",
        responses = {
            @ApiResponse(
                responseCode = "200", 
                description = "Successful retrieval of history",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = ConversionHistoryResponse.class))
            ),
            @ApiResponse(responseCode = "400", description = "Missing required parameters"),
            @ApiResponse(responseCode = "404", description = "No transactions found")
        }
    )
    public ResponseEntity<ConversionHistoryResponse> getHistory(
        @Parameter(description = "History search criteria") 
        @ModelAttribute ConversionHistoryRequest request) {
        
        ConversionHistoryResponse response = historyService.getHistory(
            request.getTransactionId(), 
            request.getDate(), 
            request.getPage(), 
            request.getSize()
        );
        
        return ResponseEntity.ok(response);
    }
}