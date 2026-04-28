package com.example.banking_api.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.banking_api.entity.Transaction;
import com.example.banking_api.model.Account;
import com.example.banking_api.service.AccountService;
@RestController
@RequestMapping("/account")
@CrossOrigin(origins = "*")

public class AccountController {

    @Autowired
    private AccountService service;

    @PostMapping("/create")
    public Account create(@RequestBody Account acc) {
        return service.create(acc);
    }

    @GetMapping("/test")   // ✅ FIXED
    public String test() {
        return "API working";
    }
    @PostMapping("/login")
public Account login(@RequestParam int accNo, @RequestParam String pin) {
    return service.login(accNo, pin);
}
@PostMapping("/deposit")
public Account deposit(@RequestParam int accNo, @RequestParam double amount) {
    return service.deposit(accNo, amount);
}
@GetMapping("/balance")
public double balance(@RequestParam int accNo) {
    double balance = service.getBalance(accNo);
return balance;
}
@PostMapping("/withdraw")
public Account withdraw(@RequestParam int accNo, @RequestParam double amount) {
    return service.withdraw(accNo, amount);
}
@PostMapping("/transfer")
public String transfer(@RequestParam int fromAcc,
                       @RequestParam int toAcc,
                       @RequestParam double amount) {

    return service.transfer(fromAcc, toAcc, amount);
}
@GetMapping("/history")
public List<Transaction> getTransactions(@RequestParam int accNo) {
    return service.getTransactions(accNo);
}
}