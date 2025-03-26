package com.example.dto;

import java.util.List;

public class ConversionHistoryResponse {
    private List<ConversionResponse> transactions;
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;

    public ConversionHistoryResponse(List<ConversionResponse> transactions, int page, int size, 
                                    long totalElements, int totalPages) {
        this.transactions = transactions;
        this.page = page;
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }

    public List<ConversionResponse> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<ConversionResponse> transactions) {
        this.transactions = transactions;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}