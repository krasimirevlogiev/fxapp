package com.example.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "conversion_transaction")
@Getter
@Setter
@NoArgsConstructor
public class ConversionTransaction {

    @Id
    @Column(name = "transaction_id")
    private UUID transactionId;

    @Column(name = "from_currency")
    private String fromCurrency;
    
    @Column(name = "to_currency")
    private String toCurrency;
    
    @Column(name = "original_amount")
    private BigDecimal originalAmount;
    
    @Column(name = "converted_amount")
    private BigDecimal convertedAmount;
    
    @CreationTimestamp
    @Column(name = "conversion_time", updatable = false)
    private LocalDateTime conversionTime;

    public ConversionTransaction(String fromCurrency, String toCurrency, BigDecimal originalAmount, BigDecimal convertedAmount) {
        this.transactionId = UUID.randomUUID();
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
        this.originalAmount = originalAmount;
        this.convertedAmount = convertedAmount;
    }
}