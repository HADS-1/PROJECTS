package PROJECTS.EXPENSE_TRACKER;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class EXPENSES {
    private final String name;
    private final double amount;
    private final String category;
    private final LocalDate date;

    public EXPENSES(String name, double amount, String category, LocalDate date ){
        this.name = name;
        this.amount = amount;
        this.category = category;
        this.date = date;
    }
    public void displayExpense(){
        System.out.println("Expense Name : " + name);
        System.out.println("Expense Amount : "+amount);
        System.out.println("Category : "+category);
        System.out.println("Date : "+date);
    }

    //Getters
    public String getName(){ return  name;}
    public double getAmount(){return amount; }
    public String getCategory(){return category; }
    public LocalDate getDate(){return date;}

    //DataBase
    public void saveToDatabase(){
        String sql = "INSERT INTO expenses (name, amount, category, expense_date) VALUES(?, ?, ?, ?)";

        try(Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)){

            statement.setString(1, name);
            statement.setDouble(2, amount);
            statement.setString(3, category);
            statement.setDate(4, java.sql.Date.valueOf(date));

            statement.executeUpdate();
            System.out.println("Expense saved to Datebase.");
        } catch (SQLException e) {
            System.out.println("Failed to save expense.");
            e.printStackTrace();
        }
    }

    // TO View Expenses.
    public static void viewExpensesFromDatabase() {
        String sql = "SELECT * FROM expenses";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            var result = statement.executeQuery();
            System.out.println("\n=========================== ALL EXPENSES ===============================");
            System.out.printf("%-5s %-20s %-12s %-15s %-15s%n", "ID", "Name", "Amount", "Category", "Date");
            System.out.println("--------------------------------------------------------------------------");

            boolean hasExpenses = false;
            while (result.next()) {
                hasExpenses = true;

                int id = result.getInt("id");
                String name = result.getString("name");
                double amount = result.getDouble("amount");
                String category = result.getString("category");
                String date = result.getString("expense_date");

                System.out.printf("%-5d %-20s GHC %-8.2f %-15s %-15s%n", id, name, amount, category, date);
            }

            if (!hasExpenses) {
                System.out.println("No expenses recorded yet.");
            }
            System.out.println("==========================================================================");
        } catch (SQLException e) {
            System.out.println("Failed to load expenses.");
            e.printStackTrace();
        }
    }

    //Calculat Total Expense
    public static void calculateTotalFromDatabase() {
        String sql = "SELECT SUM(amount) AS total FROM expenses";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            var result = statement.executeQuery();

            if (result.next()) {
                double total = result.getDouble("total");
                System.out.println("Total Expenses: GHC " + total);
            }
        } catch (SQLException e) {
            System.out.println("Failed to calculate total.");
            e.printStackTrace();
        }
    }

    //Spending By Category
    public static void spendingByCategoryFromDatabase() {
        String sql = "SELECT category, SUM(amount) AS total FROM expenses GROUP BY category";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            var result = statement.executeQuery();

            System.out.println("\n===== SPENDING BY CATEGORY =====");
            boolean hasExpenses = false;
            while (result.next()) {
                hasExpenses = true;
                String category = result.getString("category");
                double total = result.getDouble("total");
                System.out.println(category + ": GHC " + total);
            }

            if (!hasExpenses) {
                System.out.println("No expenses recorded yet.");
            }
        } catch (SQLException e) {
            System.out.println("Failed to calculate spending by category.");
            e.printStackTrace();
        }
    }

    //Delete Expense
    public static void deleteExpenseFromDatabase(int id) {
        String sql = "DELETE FROM expenses WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);

            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Expense deleted successfully.");
            } else {
                System.out.println("Expense not found.");
            }
        } catch (SQLException e) {
            System.out.println("Failed to delete expense.");
            e.printStackTrace();
        }
    }
}
