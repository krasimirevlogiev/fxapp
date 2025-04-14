package com.example.services;

import com.example.dto.ConversionRequest;
import com.example.dto.ConversionResponse;
import com.example.dto.ExchangeRateResponse;
import com.example.entity.ConversionTransaction;
import com.example.exception.ExternalServiceException;
import com.example.exception.InvalidRequestException;
import com.example.repository.ConversionTransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class CurrencyConversionService {

    private final ExchangeRateService exchangeRateService;  
    private final ConversionTransactionRepository transactionRepository;

    public CurrencyConversionService(ExchangeRateService exchangeRateService, 
                                   ConversionTransactionRepository transactionRepository) {
        this.exchangeRateService = exchangeRateService;
        this.transactionRepository = transactionRepository;
    }

    @Transactional
    public ConversionResponse convertCurrency(ConversionRequest request) {
        if (request == null || request.getFrom() == null || request.getTo() == null || request.getAmount() == null) {
            throw new InvalidRequestException("Currency codes and amount are required");
        }
        
        if (request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidRequestException("Amount must be greater than zero");
        }
    
        ExchangeRateResponse exchangeRateResponse = exchangeRateService.getExchangeRate(request.getFrom(), request.getTo());
        BigDecimal rate = exchangeRateResponse.getRate();
        BigDecimal convertedAmount = request.getAmount().multiply(rate).setScale(6, RoundingMode.HALF_UP);
    
        ConversionTransaction transaction = new ConversionTransaction(
                request.getFrom().toUpperCase(),
                request.getTo().toUpperCase(),
                request.getAmount(),
                convertedAmount
        );
        
        ConversionTransaction savedTransaction = transactionRepository.saveAndFlush(transaction);
        
        return new ConversionResponse(
                savedTransaction.getTransactionId().toString(),
                savedTransaction.getFromCurrency(),
                savedTransaction.getToCurrency(),
                savedTransaction.getOriginalAmount(),
                savedTransaction.getConvertedAmount(),
                rate,
                savedTransaction.getConversionTime()
        );
    }
}