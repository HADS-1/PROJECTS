package PROJECTS.BANKING.APPLICATION;

import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        DatabaseConnection.getConnection();

        Bank bank = new Bank();

        boolean running = true;
        while (running) {
            System.out.println("\n===== BANKING APPLICATION =====");
            System.out.println("1. Create Account");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    System.out.print("Enter your name: ");
                    String name = scanner.nextLine();

                    System.out.print("Enter PIN: ");
                    String pin = scanner.nextLine();

                    System.out.print("Enter initial deposit: ");
                    double deposit = scanner.nextDouble();

                    BankAccount account = bank.createAccount(name, pin, deposit);
                    if (account != null) {
                        System.out.println("\nAccount created successfully!");
                        System.out.println("Your account number is: " + account.getAccountNumber());
                    } else {
                        System.out.println("Account creation failed.");
                    }
                    break;

                case 2:
                    System.out.print("Enter account number: ");
                    String accountNumber = scanner.nextLine();

                    System.out.print("Enter PIN: ");
                    String loginPin = scanner.nextLine();

                    BankAccount loggedInAccount = bank.login(accountNumber, loginPin);

                    if (loggedInAccount == null) {
                        System.out.println("Invalid account number or PIN.");
                    } else {
                        System.out.println("\nLogin successful!");
                        System.out.println("Welcome " + loggedInAccount.getAccountName());
                        bankingMenu(bank, loggedInAccount);
                    }
                    break;

                case 3:
                    running = false;
                    System.out.println("Thank you for using Bank Of Dovi.");
                    break;

                default:

                    System.out.println("Invalid choice.");
            }
        }
        scanner.close();
    }

    // Banking menu
    public static void bankingMenu(Bank bank, BankAccount account) {
        boolean loggedIn = true;

        while (loggedIn) {
            System.out.println("\n===== BANKING MENU =====");
            System.out.println("1. Check Balance");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer");
            System.out.println("5. Transaction History");
            System.out.println("6. Change PIN");
            System.out.println("7. Logout");
            System.out.println("-------------------------");
            System.out.print("Enter choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:  //Checking Balance
                    System.out.println("Balance: GH₵" + account.getBalance());
                    break;

                case 2:  //Deposit Amount
                    System.out.print("Enter amount: ");
                    double depositAmount = scanner.nextDouble();
                    account.deposit(depositAmount);
                    break;

                case 3:  //Withdrawing Money
                    System.out.print("Enter amount: ");
                    double withdrawAmount = scanner.nextDouble();
                    account.withdraw(withdrawAmount);
                    break;

                case 4:  //Transfer Money
                    System.out.print("Enter receiver account number: ");
                    String receiver = scanner.nextLine();
                    System.out.print("Enter amount: ");

                    double transferAmount = scanner.nextDouble();
                    bank.transfer(account, receiver, transferAmount);
                    break;

                case 5:  //Transaction History
                    account.showTransactionHistory();
                    break;

                case 6:  //Changing Pin
                    System.out.print("Enter old PIN: ");
                    String oldPin = scanner.nextLine();

                    System.out.print("Enter new PIN: ");
                    String newPin = scanner.nextLine();

                    if (account.changePin(oldPin, newPin)) {
                        System.out.println("PIN changed successfully!");

                    } else {
                        System.out.println("Incorrect old PIN.");
                    }
                    break;

                case 7:  //Logging Out
                    loggedIn = false;
                    System.out.println("Thanks For Using Bank Of Dovi");
                    System.out.println("Logging out........");
                    break;

                default:

                    System.out.println("Invalid choice.");
            }
        }
    }
}
