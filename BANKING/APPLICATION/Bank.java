package PROJECTS.BANKING.APPLICATION;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

//The Bank Class Is used by the Bank
public class Bank {

    //Methods
    //1. Create account
    public BankAccount createAccount(String name, String pin, double initialDeposit) {
        String accountNumber = generateAccountNumber();
        if (accountNumber == null) {
            return null;
        }
        String sql = "INSERT INTO accounts (account_number, account_name, balance, pin) VALUES (?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, accountNumber);
            statement.setString(2, name);
            statement.setDouble(3, initialDeposit);
            statement.setString(4, pin);
            statement.executeUpdate();

            BankAccount newAccount = new BankAccount(accountNumber, name, initialDeposit, pin);
            return newAccount;
        } catch (SQLException e) {
            System.out.println("Failed to create account.");
            e.printStackTrace();
            return null;
        }
    }

    //2. Find account
    public BankAccount findAccount(String accountNumber) {
        String sql = "SELECT * FROM accounts WHERE account_number = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, accountNumber);
            var result = statement.executeQuery();
            if (result.next()) {
                String name = result.getString("account_name");
                double balance = result.getDouble("balance");
                String pin = result.getString("pin");
                return new BankAccount(accountNumber, name, balance, pin);
            }
        } catch (SQLException e) {
            System.out.println("Failed to find account.");
            e.printStackTrace();
        }
        return null;
    }

    //3 Login
    public BankAccount login(String accountNumber, String pin) {
        String sql = "SELECT * FROM accounts WHERE account_number = ? AND pin = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, accountNumber);
            statement.setString(2, pin);

            var result = statement.executeQuery();
            if (result.next()) {
                String name = result.getString("account_name");
                double balance = result.getDouble("balance");
                BankAccount account = new BankAccount(accountNumber, name, balance, pin);
                return account;
            }
        } catch (SQLException e) {
            System.out.println("Login failed.");
            e.printStackTrace();
        }
        return null;
    }

    // 4. Transfer money
    public void transfer(BankAccount sender, String receiverAccountNumber, double amount) {

        // Find receiver from database
        BankAccount receiver = findAccount(receiverAccountNumber);

        // Check receiver
        if (receiver == null) {
            System.out.println("Receiver account not found.");
            return;
        }

        // Prevent sending to yourself
        if (sender.getAccountNumber().equals(receiverAccountNumber)) {
            System.out.println("You cannot transfer money to yourself.");
            return;
        }

        // Check amount
        if (amount <= 0) {
            System.out.println("Invalid transfer amount.");
            return;
        }

        // Check balance
        if (amount > sender.getBalance()) {
            System.out.println("Insufficient balance.");
            return;
        }
        double senderNewBalance = sender.getBalance() - amount;
        double receiverNewBalance = receiver.getBalance() + amount;

        String sql = "UPDATE accounts SET balance = ? WHERE account_number = ?";
        Connection connection = DatabaseConnection.getConnection();
        try {
            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement(sql);

            // Update sender
            statement.setDouble(1, senderNewBalance);
            statement.setString(2, sender.getAccountNumber());
            statement.executeUpdate();

            // Update receiver
            statement.setDouble(1, receiverNewBalance);
            statement.setString(2, receiver.getAccountNumber());
            statement.executeUpdate();

            // Update Java balances
            sender.setBalance(senderNewBalance);
            receiver.setBalance(receiverNewBalance);

            // Save sender transaction
            sender.saveTransaction(connection, "TRANSFER", amount, "Sent to "
                    + receiverAccountNumber
            );

            // Save receiver transaction
            receiver.saveTransaction(connection, "TRANSFER", amount,
                    "Received from " + sender.getAccountNumber()
            );
            connection.commit();

            System.out.println("Transfer successful!");

        } catch (SQLException e) {
        try {
            connection.rollback();
            System.out.println("Transfer rolled back.");
        } catch (SQLException rollbackError) {
            rollbackError.printStackTrace();
        }

        System.out.println("Transfer failed.");
        e.printStackTrace();}
    }

    // Generating A/cc number
    private String generateAccountNumber() {
        String sql = "SELECT MAX(CAST(account_number AS UNSIGNED)) FROM accounts";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            var result = statement.executeQuery();
            if (result.next()) {
                int highestAccountNumber = result.getInt(1);
                if (result.wasNull()) {
                    return "1001";
                }
                return String.valueOf(highestAccountNumber + 1);
            }
        } catch (SQLException e) {
            System.out.println("Failed to generate account number.");
            e.printStackTrace();
        }
        return null;
    }
}