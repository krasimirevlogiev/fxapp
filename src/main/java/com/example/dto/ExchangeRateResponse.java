package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeRateResponse {
    private String from;
    private String to;
    private BigDecimal rate;
    private LocalDateTime timestamp;
    private boolean success;
    
    public ExchangeRateResponse(String from, String to, BigDecimal rate) {
        this.from = from;
        this.to = to;
        this.rate = rate;
        this.timestamp = LocalDateTime.now();
        this.success = true;
    }
}