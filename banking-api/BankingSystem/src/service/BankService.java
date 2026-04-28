package service;

import model.Account;
import java.util.HashMap;
import java.util.Map;

public class BankService {
    private Map<Integer, Account> accounts = new HashMap<>();

    public void createAccount(int accNo, String name, double balance) {
        if (accounts.containsKey(accNo)) {
            System.out.println("Account already exists!");
            return;
        }
        accounts.put(accNo, new Account(accNo, name, balance));
        System.out.println("Account created successfully!");
    }

    public void deposit(int accNo, double amount) {
        Account acc = accounts.get(accNo);
        if (acc != null) {
            acc.deposit(amount);
            System.out.println("Deposited successfully!");
        } else {
            System.out.println("Account not found!");
        }
    }

    public void withdraw(int accNo, double amount) {
        Account acc = accounts.get(accNo);
        if (acc != null) {
            if (acc.withdraw(amount)) {
                System.out.println("Withdrawal successful!");
            } else {
                System.out.println("Insufficient balance!");
            }
        } else {
            System.out.println("Account not found!");
        }
    }

    public void checkBalance(int accNo) {
        Account acc = accounts.get(accNo);
        if (acc != null) {
            System.out.println("Balance: " + acc.getBalance());
        } else {
            System.out.println("Account not found!");
        }
    }
}