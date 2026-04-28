package service;

import java.sql.*;
import util.DBConnection;

public class BankService {

   public void createAccount(int accNo, String name, double balance, String pin) {
    try (Connection con = DBConnection.getConnection()) {
        String query = "INSERT INTO accounts VALUES (?, ?, ?, ?)";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setInt(1, accNo);
        ps.setString(2, name);
        ps.setDouble(3, balance);
        ps.setString(4, pin);
        ps.executeUpdate();
        System.out.println("Account created!");
    } catch (Exception e) {
        e.printStackTrace();
    }
}
public boolean login(int accNo, String pin) {
    try (Connection con = DBConnection.getConnection()) {
        String query = "SELECT * FROM accounts WHERE account_number=? AND pin=?";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setInt(1, accNo);
        ps.setString(2, pin);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            System.out.println("Login successful!");
            return true;
        } else {
            System.out.println("Invalid account number or PIN!");
            return false;
        }

    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}public boolean login(int accNo, String pin) {
    try (Connection con = DBConnection.getConnection()) {
        String query = "SELECT * FROM accounts WHERE account_number=? AND pin=?";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setInt(1, accNo);
        ps.setString(2, pin);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            System.out.println("Login successful!");
            return true;
        } else {
            System.out.println("Invalid account number or PIN!");
            return false;
        }

    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}
    public void deposit(int accNo, double amount) {
    try (Connection con = DBConnection.getConnection()) {
        String query = "UPDATE accounts SET balance = balance + ? WHERE account_number=?";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setDouble(1, amount);
        ps.setInt(2, accNo);
        ps.executeUpdate();

        addTransaction(accNo, "DEPOSIT", amount); // 🔥 added

        System.out.println("Deposited!");
    } catch (Exception e) {
        e.printStackTrace();
    }
}

    public void withdraw(int accNo, double amount) {
        try (Connection con = DBConnection.getConnection()) {

            String check = "SELECT balance FROM accounts WHERE account_number=?";
            PreparedStatement ps = con.prepareStatement(check);
            ps.setInt(1, accNo);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                double bal = rs.getDouble("balance");

                if (bal >= amount) {
                    String update = "UPDATE accounts SET balance = balance - ? WHERE account_number=?";
                    PreparedStatement up = con.prepareStatement(update);
                    up.setDouble(1, amount);
                    up.setInt(2, accNo);
                    up.executeUpdate();
                    System.out.println("Withdraw successful!");
                } else {
                    System.out.println("Insufficient balance!");
                }
            } else {
                System.out.println("Account not found!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        addTransaction(accNo, "WITHDRAW", amount); // add after successful withdraw
    }

    public void checkBalance(int accNo) {
        try (Connection con = DBConnection.getConnection()) {
            String query = "SELECT balance FROM accounts WHERE account_number=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, accNo);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                System.out.println("Balance: " + rs.getDouble("balance"));
            } else {
                System.out.println("Account not found!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



private void addTransaction(int accNo, String type, double amount) {
    try (Connection con = DBConnection.getConnection()) {
        String query = "INSERT INTO transactions (account_number, type, amount) VALUES (?, ?, ?)";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setInt(1, accNo);
        ps.setString(2, type);
        ps.setDouble(3, amount);
        ps.executeUpdate();
    } catch (Exception e) {
        e.printStackTrace();
 
    }
}
public void showTransactions(int accNo) {
    try (Connection con = DBConnection.getConnection()) {
        String query = "SELECT * FROM transactions WHERE account_number=?";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setInt(1, accNo);
        ResultSet rs = ps.executeQuery();

        System.out.println("\n--- Transaction History ---");

        while (rs.next()) {
            System.out.println(
                rs.getString("type") + " | " +
                rs.getDouble("amount") + " | " +
                rs.getTimestamp("date")
            );
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
}
public void transfer(int fromAcc, int toAcc, double amount) {
    try (Connection con = DBConnection.getConnection()) {

        con.setAutoCommit(false); // 🔥 important

        // Check balance
        String check = "SELECT balance FROM accounts WHERE account_number=?";
        PreparedStatement ps = con.prepareStatement(check);
        ps.setInt(1, fromAcc);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            double balance = rs.getDouble("balance");

            if (balance >= amount) {

                // Deduct from sender
                String debit = "UPDATE accounts SET balance = balance - ? WHERE account_number=?";
                PreparedStatement ps1 = con.prepareStatement(debit);
                ps1.setDouble(1, amount);
                ps1.setInt(2, fromAcc);
                ps1.executeUpdate();

                // Add to receiver
                String credit = "UPDATE accounts SET balance = balance + ? WHERE account_number=?";
                PreparedStatement ps2 = con.prepareStatement(credit);
                ps2.setDouble(1, amount);
                ps2.setInt(2, toAcc);
                ps2.executeUpdate();

                // Add transaction history
                addTransaction(fromAcc, "TRANSFER_SENT", amount);
                addTransaction(toAcc, "TRANSFER_RECEIVED", amount);

                con.commit();
                System.out.println("Transfer successful!");

            } else {
                System.out.println("Insufficient balance!");
                con.rollback();
            }

        } else {
            System.out.println("Sender account not found!");
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
}}