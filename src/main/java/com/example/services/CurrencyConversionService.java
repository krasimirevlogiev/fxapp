package com.example.services;

import com.example.dto.ConversionRequest;
import com.example.dto.ConversionResponse;
import com.example.entity.ConversionTransaction;
import com.example.repository.ConversionTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

@Service
public class CurrencyConversionService {

    @Autowired
    private ExchangeRateService exchangeRateService;  

    @Autowired
    private ConversionTransactionRepository transactionRepository;

    public ConversionResponse convertCurrency(ConversionRequest request) {
        
        Map<String, Object> rateResult = exchangeRateService.getExchangeRate(request.getFrom(), request.getTo());
        if(rateResult.containsKey("error")){
            
            throw new RuntimeException("Error fetching exchange rate: " + rateResult.get("error"));
        }
        BigDecimal rate = (BigDecimal) rateResult.get("rate");

        BigDecimal convertedAmount = request.getAmount().multiply(rate).setScale(6, RoundingMode.HALF_UP);

        ConversionTransaction transaction = new ConversionTransaction(
                request.getFrom().toUpperCase(),
                request.getTo().toUpperCase(),
                request.getAmount(),
                convertedAmount
        );
        transactionRepository.save(transaction);

        return new ConversionResponse(
                transaction.getTransactionId(),
                transaction.getFromCurrency(),
                transaction.getToCurrency(),
                transaction.getOriginalAmount(),
                transaction.getConvertedAmount()
        );
    }
}
