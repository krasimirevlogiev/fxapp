package com.example.services;

import com.example.dto.ConversionHistoryResponse;
import com.example.dto.ConversionResponse;
import com.example.entity.ConversionTransaction;
import com.example.repository.ConversionTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;

@Service
public class ConversionHistoryService {

    @Autowired
    private ConversionTransactionRepository transactionRepository;

    public ConversionHistoryResponse getHistory(String transactionId, LocalDate date, int page, int size) {
    if (transactionId == null && date == null) {
        throw new IllegalArgumentException("Either transactionId or date must be provided");
    }

    Pageable pageable = PageRequest.of(page, size, Sort.by("conversionTime").descending());
    Page<ConversionTransaction> transactionsPage;

    System.out.println("Searching with params: transactionId=" + transactionId + ", date=" + date);

    if (transactionId != null) {
        Optional<ConversionTransaction> exactMatch = transactionRepository.findById(transactionId);
        if (exactMatch.isPresent()) {
            System.out.println("Found exact match by ID");
            List<ConversionTransaction> singleResult = List.of(exactMatch.get());
            Page<ConversionTransaction> singlePage = new PageImpl<>(singleResult, pageable, 1);
            List<ConversionResponse> transactions = singlePage.getContent()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
            
            return new ConversionHistoryResponse(
                transactions,
                0,
                1,
                1,
                1
            );
        }
        
        System.out.println("No exact match found, trying page query");
        transactionsPage = transactionRepository.findByTransactionId(transactionId, pageable);
    } else {
        LocalDateTime dateTime = LocalDateTime.of(date, LocalTime.MIN);
        System.out.println("Searching by date: " + dateTime);
        transactionsPage = transactionRepository.findByConversionDate(dateTime, pageable);
    }

    System.out.println("Found " + transactionsPage.getTotalElements() + " transactions");

    List<ConversionResponse> transactions = transactionsPage.getContent()
            .stream()
            .map(this::mapToResponse)
            .collect(Collectors.toList());

    return new ConversionHistoryResponse(
            transactions,
            transactionsPage.getNumber(),
            transactionsPage.getSize(),
            transactionsPage.getTotalElements(),
            transactionsPage.getTotalPages()
    );
}

    private ConversionResponse mapToResponse(ConversionTransaction transaction) {
        return new ConversionResponse(
                transaction.getTransactionId(),
                transaction.getFromCurrency(),
                transaction.getToCurrency(),
                transaction.getOriginalAmount(),
                transaction.getConvertedAmount()
        );
    }
}