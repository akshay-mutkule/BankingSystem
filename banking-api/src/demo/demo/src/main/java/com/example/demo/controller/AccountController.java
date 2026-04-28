package demo.demo.src.main.java.com.example.demo.controller;

public class AccountController {
    package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.Account;
import com.example.demo.service.AccountService;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService service;

    @PostMapping("/create")
    public Account create(@RequestBody Account acc) {
        return service.create(acc);
    }

    @PostMapping("/login")
    public String login(@RequestParam int accNo, @RequestParam String pin) {
        return (service.login(accNo, pin) != null) ? "Login successful" : "Invalid credentials";
    }

    @PostMapping("/deposit")
    public Account deposit(@RequestParam int accNo, @RequestParam double amount) {
        return service.deposit(accNo, amount);
    }

    @GetMapping("/balance")
    public Account balance(@RequestParam int accNo) {
        return service.getBalance(accNo);
    }
}
}
