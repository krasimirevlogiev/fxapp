package com.example.repository;

import com.example.entity.ConversionTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConversionTransactionRepository extends JpaRepository<ConversionTransaction, String> {
}
