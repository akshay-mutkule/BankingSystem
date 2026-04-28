package com.example.banking_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.banking_api.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    List<Transaction> findByAccountNumber(int accountNumber);
}
