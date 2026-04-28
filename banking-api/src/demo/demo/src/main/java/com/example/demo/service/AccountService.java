package demo.demo.src.main.java.com.example.demo.service;

public class AccountService {
    package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.model.Account;
import com.example.demo.repository.AccountRepository;

@Service
public class AccountService {

    @Autowired
    private AccountRepository repo;

    public Account create(Account acc) {
        return repo.save(acc);
    }

    public Account login(int accNo, String pin) {
        return repo.findByAccountNumberAndPin(accNo, pin);
    }

    public Account deposit(int accNo, double amount) {
        Account acc = repo.findById(accNo).orElse(null);
        if (acc != null) {
            acc.setBalance(acc.getBalance() + amount);
            return repo.save(acc);
        }
        return null;
    }

    public Account getBalance(int accNo) {
        return repo.findById(accNo).orElse(null);
    }
}
}
