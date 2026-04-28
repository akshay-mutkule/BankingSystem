package com.example.banking_api.service;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.banking_api.entity.Transaction;
import com.example.banking_api.model.Account;
import com.example.banking_api.repository.AccountRepository;
import com.example.banking_api.repository.TransactionRepository;

@Service
public class AccountService {

    @Autowired
    private AccountRepository repo;

    // Create account
    public Account create(Account acc) {
        return repo.save(acc);
    }

    // Login
    public Account login(int accNo, String pin) {
        return repo.findByAccountNumberAndPin(accNo, pin);
    }

    // Deposit
   public Account deposit(int accNo, double amount) {
    Account acc = repo.findById(accNo).orElse(null);

    if (acc != null) {
        acc.setBalance(acc.getBalance() + amount);
        repo.save(acc);

        Transaction t = new Transaction();
        t.setAccountNumber(accNo);
        t.setType("DEPOSIT");
        t.setAmount(amount);
     t.setDate(LocalDateTime.now());

        transactionRepo.save(t);

        return acc;
    }
    return null;
}
 public Account withdraw(int accNo, double amount) {
    Account acc = repo.findById(accNo).orElse(null);

    if (acc != null && acc.getBalance() >= amount) {
        acc.setBalance(acc.getBalance() - amount);
        repo.save(acc);

        Transaction t = new Transaction();
        t.setAccountNumber(accNo);
        t.setType("WITHDRAW");
        t.setAmount(amount);
    t.setDate(LocalDateTime.now());

        transactionRepo.save(t);

        return acc;
    }
    return null;
}
@Transactional
public String transfer(int fromAcc, int toAcc, double amount) {

    Account sender = repo.findById(fromAcc).orElse(null);
    Account receiver = repo.findById(toAcc).orElse(null);

    if (sender == null || receiver == null) return "Account not found";
    if (sender.getBalance() < amount) return "Insufficient balance";

    sender.setBalance(sender.getBalance() - amount);
    receiver.setBalance(receiver.getBalance() + amount);

    repo.save(sender);
    repo.save(receiver);

    // sender transaction
    Transaction t1 = new Transaction();
    t1.setAccountNumber(fromAcc);
    t1.setType("TRANSFER_SENT");
    t1.setAmount(amount);
     t1.setDate(LocalDateTime.now());

    // receiver transaction
    Transaction t2 = new Transaction();
    t2.setAccountNumber(toAcc);
    t2.setType("TRANSFER_RECEIVED");
    t2.setAmount(amount);
    t2.setDate(LocalDateTime.now());

    transactionRepo.save(t1);
    transactionRepo.save(t2);

    return "Transfer successful";
}
public List<Transaction> getHistory(int accNo) {
    return transactionRepo.findByAccountNumber(accNo);
}
public double getBalance(int accNo) {
    Account acc = repo.findById(accNo).orElseThrow();
    return acc.getBalance();
}
private void saveTransaction(int accNo, String type, double amount) {
    Transaction t = new Transaction();
    t.setAccountNumber(accNo);
    t.setType(type);
    t.setAmount(amount);
    t.setDate(java.time.LocalDateTime.now());

    transactionRepo.save(t);
}
public List<Transaction> getTransactions(int accNo) {
    return transactionRepo.findByAccountNumber(accNo);
}
@Autowired
private TransactionRepository transactionRepo;
}
