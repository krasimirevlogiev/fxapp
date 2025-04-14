package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConversionResponse {
    private String transactionId;
    private String from;  
    private String to;    
    private BigDecimal originalAmount;
    private BigDecimal convertedAmount;
    private BigDecimal exchangeRate;
    private LocalDateTime timestamp;
    
    public ConversionResponse(String transactionId, String from, String to, 
                           BigDecimal originalAmount, BigDecimal convertedAmount) {
        this.transactionId = transactionId;
        this.from = from;
        this.to = to;
        this.originalAmount = originalAmount;
        this.convertedAmount = convertedAmount;
    }
}