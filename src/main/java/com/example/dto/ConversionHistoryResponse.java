package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@NoArgsConstructor
@SuperBuilder
@AllArgsConstructor
public class ConversionHistoryResponse extends PageResponse {
    private List<ConversionResponse> transactions;
    
    public ConversionHistoryResponse(long totalElements, int totalPages, int currentPage, List<ConversionResponse> transactions) {
        super(currentPage, transactions != null ? transactions.size() : 0, totalElements, totalPages);
        this.transactions = transactions;
    }
}