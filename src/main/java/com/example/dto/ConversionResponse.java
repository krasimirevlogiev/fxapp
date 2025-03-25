package com.example.dto;

import java.math.BigDecimal;

public class ConversionResponse {
    private String transactionId;
    private String from;
    private String to;
    private BigDecimal originalAmount;
    private BigDecimal convertedAmount;

    public ConversionResponse() {
    }

    public ConversionResponse(String transactionId, String from, String to, BigDecimal originalAmount, BigDecimal convertedAmount) {
        this.transactionId = transactionId;
        this.from = from;
        this.to = to;
        this.originalAmount = originalAmount;
        this.convertedAmount = convertedAmount;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public BigDecimal getOriginalAmount() {
        return originalAmount;
    }

    public void setOriginalAmount(BigDecimal originalAmount) {
        this.originalAmount = originalAmount;
    }

    public BigDecimal getConvertedAmount() {
        return convertedAmount;
    }

    public void setConvertedAmount(BigDecimal convertedAmount) {
        this.convertedAmount = convertedAmount;
    }
}
