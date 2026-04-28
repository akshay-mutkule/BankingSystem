package com.example.banking_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.banking_api.model.Account;

public interface AccountRepository extends JpaRepository<Account, Integer> {

    // Custom login query
    Account findByAccountNumberAndPin(int accountNumber, String pin);
}
