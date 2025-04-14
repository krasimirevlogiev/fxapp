package com.example.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
public class ConversionHistoryRequest {
    private UUID transactionId;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    
    private int page = 0;
    private int size = 10;

    public void setTransactionId(String transactionId) {
        if (transactionId != null && !transactionId.isEmpty()) {
            try {
                this.transactionId = UUID.fromString(transactionId);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid UUID format: " + transactionId);
            }
        }
    }
    
}