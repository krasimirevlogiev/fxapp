package com.example.services;

import com.example.dto.ConversionHistoryResponse;
import com.example.dto.ConversionResponse;
import com.example.entity.ConversionTransaction;
import com.example.exception.InvalidRequestException;
import com.example.exception.ResourceNotFoundException;
import com.example.mapper.ConversionMapper;
import com.example.repository.ConversionTransactionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ConversionHistoryService {

    private final ConversionTransactionRepository transactionRepository;
    private final ConversionMapper conversionMapper;

    public ConversionHistoryService(ConversionTransactionRepository transactionRepository, 
                                   ConversionMapper conversionMapper) {
        this.transactionRepository = transactionRepository;
        this.conversionMapper = conversionMapper;
    }

    public ConversionHistoryResponse getHistory(UUID transactionId, LocalDate date, int page, int size) {
        if (transactionId == null && date == null) {
            throw new InvalidRequestException("Either transactionId or date parameter is required");
        }
        
        Pageable pageable = PageRequest.of(page, size, Sort.by("conversionTime").descending());
        Page<ConversionTransaction> transactions;
        
        if (transactionId != null) {
            transactions = transactionRepository.findByTransactionId(transactionId, pageable);
        } else {
            LocalDateTime startOfDay = LocalDateTime.of(date, LocalTime.MIN);
            transactions = transactionRepository.findByConversionDate(startOfDay, pageable);
        }
        
        if (transactions.isEmpty()) {
            String errorMsg = transactionId != null 
                ? "No transaction found with ID: " + transactionId
                : "No transactions found for date: " + date;
            throw new ResourceNotFoundException(errorMsg);
        }

        List<ConversionResponse> responseList = transactions.getContent().stream()
                .map(conversionMapper::toResponse)
                .collect(Collectors.toList());

        return new ConversionHistoryResponse(
            transactions.getTotalElements(),
            transactions.getTotalPages(),
            transactions.getNumber(),
            responseList
        );
    }
}