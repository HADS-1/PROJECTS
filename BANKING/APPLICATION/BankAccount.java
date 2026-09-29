package PROJECTS.BANKING.APPLICATION;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

//This Bank Account Class is used by the Customer.
public class BankAccount {
    //Fields
    private String accountNumber;
    private String accountName;
    private double balance;
    private String pin;

    // Constructor
    public BankAccount(String accountNumber, String accountName, double balance, String pin) {
        this.accountNumber = accountNumber;
        this.accountName = accountName;
        this.balance = balance;
        this.pin = pin;
    }

    // Getters
    public String getAccountNumber() {return accountNumber;}
    public String getAccountName() {return accountName;}
    public double getBalance() {return balance;}
    public boolean checkPin(String pin) {return this.pin.equals(pin);}

    //Methods
    //1. Deposit
    public void deposit(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid amount.");
            return;
        }
        double newBalance = balance + amount;
        String sql = "UPDATE accounts SET balance = ? WHERE account_number = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDouble(1, newBalance);
            statement.setString(2, accountNumber);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {

                // Update Java balance
                balance = newBalance;

                // Save deposit transaction
                saveTransaction(connection, "DEPOSIT", amount, "Money deposited");
                System.out.println("Deposit successful!");
            } else {
                System.out.println("Account not found.");
            }
        } catch (SQLException e) {
            System.out.println("Deposit failed.");
            e.printStackTrace();
        }
    }

    //2. Withdraw
    public void withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid amount.");
            return;
        }
        if (amount > balance) {
            System.out.println("Insufficient balance.");
            return;
        }

        double newBalance = balance - amount;
        String sql = "UPDATE accounts SET balance = ? WHERE account_number = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDouble(1, newBalance);
            statement.setString(2, accountNumber);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {

                // Update Java balance
                balance = newBalance;

                // Save withdrawal transaction
                saveTransaction(connection, "WITHDRAW", amount, "Money withdrawn");
                System.out.println("Withdrawal successful!");
            } else {
                System.out.println("Account not found.");
            }
        } catch (SQLException e) {
            System.out.println("Withdrawal failed.");
            e.printStackTrace();
        }
    }

    // 3. Change PIN
    public boolean changePin(String oldPin, String newPin) {
        // Check old PIN
        if (!this.pin.equals(oldPin)) {
            return false;
        }

        String sql = "UPDATE accounts SET pin = ? WHERE account_number = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, newPin);
            statement.setString(2, accountNumber);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                // Update Java PIN
                this.pin = newPin;
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Failed to change PIN.");
            e.printStackTrace();
        }
        return false;
    }

    //4. Add transaction
    public void saveTransaction(Connection connection,
                                String type,
                                double amount,
                                String description) throws SQLException{

        String sql = "INSERT INTO transactions " +
                "(account_number, transaction_type, amount, description) " +
                "VALUES (?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql))  {
            statement.setString(1, accountNumber);
            statement.setString(2, type);
            statement.setDouble(3, amount);
            statement.setString(4, description);

            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Failed to save transaction.");
            e.printStackTrace();
        }
    }

    //5. Display transaction history
    public void showTransactionHistory() {
        String sql = "SELECT transaction_type, amount, description, transaction_date " +
                "FROM transactions " +
                "WHERE account_number = ? " +
                "ORDER BY transaction_date DESC";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, accountNumber);

            var result = statement.executeQuery();
            System.out.println("\n===== TRANSACTION HISTORY =====");

            boolean hasTransactions = false;
            while (result.next()) {
                hasTransactions = true;
                String type = result.getString("transaction_type");
                double amount = result.getDouble("amount");
                String description = result.getString("description");
                String date = result.getString("transaction_date");

                System.out.println("--------------------------------");
                System.out.println("Type: " + type);
                System.out.println("Amount: GH₵" + amount);
                System.out.println("Description: " + description);
                System.out.println("Date: " + date);
            }
            if (!hasTransactions) {
                System.out.println("No transactions yet.");
            }
        } catch (SQLException e) {
            System.out.println("Failed to load transaction history.");
            e.printStackTrace();
        }
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
