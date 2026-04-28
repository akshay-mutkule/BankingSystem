package demo.demo.src.main.java.com.example.demo.repository;

public class AccountRepository {
    
package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.model.Account;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    Account findByAccountNumberAndPin(int accountNumber, String pin);
}
}