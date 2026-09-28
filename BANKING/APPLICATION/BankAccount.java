package PROJECTS.BANKING.APPLICATION;

import java.util.ArrayList;
//This Bank Account Class is used by the Customer.
public class BankAccount {
    //Fields
    private String accountNumber;
    private String accountName;
    private double balance;
    private String pin;

    private ArrayList<String> transactionHistory;

    // Constructor
    public BankAccount(String accountNumber, String accountName, double balance, String pin) {
        this.accountNumber = accountNumber;
        this.accountName = accountName;
        this.balance = balance;
        this.pin = pin;

        transactionHistory = new ArrayList<>();
    }

    // Getters
    public String getAccountNumber() {return accountNumber;}
    public String getAccountName() {return accountName;}
    public double getBalance() {return balance;}
    public boolean checkPin(String pin) {return this.pin.equals(pin);}

    //Methods
    //1. Deposit
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            transactionHistory.add("Deposited: GH₵" + amount);
            System.out.println("Deposit successful!");
        } else {
            System.out.println("Invalid amount.");
        }
    }

    //2. Withdraw
    public void withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid amount.");
        }
        else if (amount > balance) {
            System.out.println("Insufficient balance.");
        }
        else {
            balance -= amount;
            transactionHistory.add("Withdrawn: GH₵" + amount);
            System.out.println("Withdrawal successful!");
        }
    }

    //3. Change PIN
    public boolean changePin(String oldPin, String newPin) {
        if (this.pin.equals(oldPin)) {
            this.pin = newPin;
            return true;
        }
        return false;
    }

    //4. Add transaction
    public void addTransaction(String transaction) {
        transactionHistory.add(transaction);
    }

    //5. Display transaction history
    public void showTransactionHistory() {
        System.out.println("\n===== TRANSACTION HISTORY =====");
        if (transactionHistory.isEmpty()) {
            System.out.println("No transactions yet.");
        } else {
            for (String transaction : transactionHistory) {
                System.out.println(transaction);
            }
        }
    }
}
