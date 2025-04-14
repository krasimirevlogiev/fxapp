package com.example.repository;

import com.example.entity.ConversionTransaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.UUID;

public interface ConversionTransactionRepository extends JpaRepository<ConversionTransaction, UUID> {
    
    Page<ConversionTransaction> findByTransactionId(UUID transactionId, Pageable pageable);
   
    @Query("SELECT ct FROM ConversionTransaction ct WHERE FUNCTION('FORMATDATETIME', ct.conversionTime, 'yyyy-MM-dd') = FUNCTION('FORMATDATETIME', :date, 'yyyy-MM-dd')")
    Page<ConversionTransaction> findByConversionDate(@Param("date") LocalDateTime date, Pageable pageable);
}