import service.BankService;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        BankService bank = new BankService();

        // Create account
        bank.createAccount(301, "User1", 5000, "1234");

        // Login
        System.out.print("Enter Account Number: ");
        int accNo = sc.nextInt();

        System.out.print("Enter PIN: ");
        String pin = sc.next();

        if (bank.login(accNo, pin)) {

            // Only allowed after login
            bank.deposit(accNo, 1000);
            bank.checkBalance(accNo);
            bank.showTransactions(accNo);

        } else {
            System.out.println("Access denied!");
        }
    }
}