package com.example.controllers;

import com.example.dto.ConversionHistoryResponse;
import com.example.services.ConversionHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api")
@Tag(name = "Conversion History", description = "Conversion History API")
public class ConversionHistoryController {

    @Autowired
    private ConversionHistoryService historyService;

    @GetMapping("/history")
    @Operation(
        summary = "Get conversion history",
        description = "Retrieves conversion history filtered by transaction ID or date",
        responses = {
            @ApiResponse(
                responseCode = "200", 
                description = "Successful retrieval of history",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = ConversionHistoryResponse.class))
            ),
            @ApiResponse(responseCode = "400", description = "Missing required parameters")
        }
    )
    public ResponseEntity<ConversionHistoryResponse> getHistory(
        @Parameter(description = "Transaction ID to filter by") 
        @RequestParam(required = false) String transactionId,
        
        @Parameter(description = "Date to filter by (format: YYYY-MM-DD)") 
        @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
        
        @Parameter(description = "Page number (0-based)") 
        @RequestParam(defaultValue = "0") int page,
        
        @Parameter(description = "Page size") 
        @RequestParam(defaultValue = "10") int size) {
        
        if (transactionId == null && date == null) {
            return ResponseEntity.badRequest().build();
        }
        
        ConversionHistoryResponse response = historyService.getHistory(transactionId, date, page, size);
        return ResponseEntity.ok(response);
    }
}