package com.example.controllers;

import com.example.dto.ConversionHistoryResponse;
import com.example.services.ConversionHistoryService;
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
public class ConversionHistoryController {

    @Autowired
    private ConversionHistoryService historyService;

    @GetMapping("/history")
    public ResponseEntity<ConversionHistoryResponse> getHistory(
        @RequestParam(required = false) String transactionId,
        @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size) {
        
        if (transactionId == null && date == null) {
            return ResponseEntity.badRequest().build();
        }
        
        ConversionHistoryResponse response = historyService.getHistory(transactionId, date, page, size);
        return ResponseEntity.ok(response);
    }
}