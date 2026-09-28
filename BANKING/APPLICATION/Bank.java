package PROJECTS.BANKING.APPLICATION;

import java.util.ArrayList;
//The Bank Class Is used by the Bank
public class Bank {
    private ArrayList<BankAccount> accounts;
    private int nextAccountNumber = 1001;

    // Constructor
    public Bank() {
        accounts = new ArrayList<>();
    }

    //Methods
    //1. Create account
    public BankAccount createAccount(String name, String pin, double initialDeposit) {
        String accountNumber = String.valueOf(nextAccountNumber);
        BankAccount newAccount = new BankAccount(accountNumber, name, initialDeposit, pin);
        accounts.add(newAccount);
        nextAccountNumber++;
        return newAccount;
    }

    //2. Find account
    public BankAccount findAccount(String accountNumber) {
        for (BankAccount account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return account;
            }
        }
        return null;
    }

    //3 Login
    public BankAccount login(String accountNumber, String pin) {
        BankAccount account = findAccount(accountNumber);
        if (account != null && account.checkPin(pin)) {
            return account;
        }
        return null;
    }

    //4. Transfer money
    public void transfer(BankAccount sender, String receiverAccountNumber, double amount) {
        BankAccount receiver = findAccount(receiverAccountNumber);
        // Check receiver
        if (receiver == null) {
            return;
        }
        // Prevent sending to yourself
        if (sender.getAccountNumber().equals(receiverAccountNumber)) {
            return;
        }
        // Check amount
        if (amount <= 0) {
            return;
        }
        // Check balance
        if (amount >= sender.getBalance()) {
            return;
        }
        // Remove money from sender
        sender.withdraw(amount);

        // Add money to receiver
        receiver.deposit(amount);

        // Add transaction history
        sender.addTransaction(
                "Transferred: GH₵ " + amount + " to " + receiverAccountNumber
        );
        receiver.addTransaction(
                "Received: GH₵ " + amount + " from " + sender.getAccountNumber()
        );

        System.out.println("Transfer successful!");
    }
}
