import java.util.Scanner;

import BankingSystem.src.service.BankService;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        BankService bank = new BankService();

        while (true) {
            System.out.println("\n--- Banking System ---");
            System.out.println("1. Create Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Check Balance");
            System.out.println("5. Exit");

            System.out.print("Enter choice: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Account Number: ");
                    int accNo = sc.nextInt();
                    System.out.print("Name: ");
                    String name = sc.next();
                    System.out.print("Initial Balance: ");
                    double bal = sc.nextDouble();
                    bank.createAccount(accNo, name, bal);
                    break;

                case 2:
                    System.out.print("Account Number: ");
                    bank.deposit(sc.nextInt(), sc.nextDouble());
                    break;

                case 3:
                    System.out.print("Account Number: ");
                    bank.withdraw(sc.nextInt(), sc.nextDouble());
                    break;

                case 4:
                    System.out.print("Account Number: ");
                    bank.checkBalance(sc.nextInt());
                    break;

                case 5:
                    System.out.println("Thank you!");
                    return;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}