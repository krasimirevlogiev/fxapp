package com.example.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class ConversionTransaction {

    @Id
    private String transactionId;
    private String fromCurrency;
    private String toCurrency;
    private BigDecimal originalAmount;
    private BigDecimal convertedAmount;
    private LocalDateTime conversionTime;

    public ConversionTransaction() {
    }

    public ConversionTransaction(String fromCurrency, String toCurrency, BigDecimal originalAmount, BigDecimal convertedAmount) {
        this.transactionId = UUID.randomUUID().toString();
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
        this.originalAmount = originalAmount;
        this.convertedAmount = convertedAmount;
        this.conversionTime = LocalDateTime.now();
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getFromCurrency() {
        return fromCurrency;
    }

    public void setFromCurrency(String fromCurrency) {
        this.fromCurrency = fromCurrency;
    }

    public String getToCurrency() {
        return toCurrency;
    }

    public void setToCurrency(String toCurrency) {
        this.toCurrency = toCurrency;
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

    public LocalDateTime getConversionTime() {
        return conversionTime;
    }

    public void setConversionTime(LocalDateTime conversionTime) {
        this.conversionTime = conversionTime;
    }
}
