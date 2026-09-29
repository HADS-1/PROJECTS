package PROJECTS.INVENTORY.MANAGEMENT.SYSTEM;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class INVENTORY {

    // Fields
    private int ID;
    private String name;
    private double price;
    private int quantity;

    // Constructor
    public INVENTORY(int ID, String name, double price, int quantity) {
        this.ID = ID;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    // Getters
    public int getID() {return ID;}
    public String getName() {return name;}
    public double getPrice() {return price;}
    public int getQuantity() {return quantity;}

    // Setters
    public void setID(int ID) {this.ID = ID;}
    public void setName(String name) {this.name = name;}
    public void setPrice(double price) {this.price = price;}
    public void setQuantity(int quantity) {this.quantity = quantity;}

    // Display one product
    public void displayInventory() {
        System.out.println("Product ID : " + ID);
        System.out.println("Product Name : " + name);
        System.out.println("Product Price : " + price);
        System.out.println("Quantity : " + quantity);
    }

    // 1. Add Product
    public void saveToDatabase() {
        String sql = "INSERT INTO inventory (name, price, quantity) VALUES (?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, name);
            statement.setDouble(2, price);
            statement.setInt(3, quantity);

            statement.executeUpdate();
            ResultSet keys = statement.getGeneratedKeys();
            if (keys.next()) {
                this.ID = keys.getInt(1);
            }
            System.out.println("Product saved to database.");
        } catch (SQLException e) {
            System.out.println("Failed to save product.");
            e.printStackTrace();
        }
    }

    // 2. Display all products
    public static void displayAllProducts() {
        String sql = "SELECT * FROM inventory";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet result = statement.executeQuery();

            System.out.println("\n======================== ALL PRODUCTS ===============================");
            System.out.printf("%-7s %-8s %-16s %-15s %-15s%n", "No.", "ID", "NAME", "PRICE", "QUANTITY");
            System.out.println("---------------------------------------------------------------------");

            boolean hasProducts = false;
            int number = 1;
            while (result.next()) {
                hasProducts = true;
                int id = result.getInt("id");
                String name = result.getString("name");
                double price = result.getDouble("price");
                int quantity = result.getInt("quantity");

                System.out.printf("%-7d %-7d %-16s GHC %-10.2f %-18d%n", number, id, name, price, quantity);
                number++;
            }
            if (!hasProducts) {
                System.out.println("No Product Entry. Start By Adding products.");
            }
            System.out.println("=====================================================================");
        } catch (SQLException e) {
            System.out.println("Failed to display inventory.");
            e.printStackTrace();
        }
    }

    // 3. Update Product
    public static void updateProduct(int id, String newName, double newPrice, int newQuantity) {
        String sql = "UPDATE inventory SET name = ?, price = ?, quantity = ? WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, newName);
            statement.setDouble(2, newPrice);
            statement.setInt(3, newQuantity);
            statement.setInt(4, id);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Product updated successfully.");
            } else {
                System.out.println("Product ID not found.");
            }
        } catch (SQLException e) {
            System.out.println("Failed to update product.");
            e.printStackTrace();
        }
    }

    // 4. Delete Product
    public static void deleteProduct(int id) {
        String sql = "DELETE FROM inventory WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);

            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Product deleted successfully.");
            } else {
                System.out.println("Product ID not found.");
            }
        } catch (SQLException e) {
            System.out.println("Failed to delete product.");
            e.printStackTrace();
        }
    }

    // 5. Search Product
    public static void searchProduct(String productName) {
        String sql = "SELECT * FROM inventory WHERE name LIKE ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, "%" + productName + "%");
            ResultSet result = statement.executeQuery();

            boolean found = false;
            while (result.next()) {
                found = true;
                int id = result.getInt("id");
                String name = result.getString("name");
                double price = result.getDouble("price");
                int quantity = result.getInt("quantity");

                System.out.println("\nProduct Found");
                System.out.println("-------------------------");
                System.out.println("ID       : " + id);
                System.out.println("Name     : " + name);
                System.out.println("Price    : GHC " + price);
                System.out.println("Quantity : " + quantity);
            }
            if (!found) {
                System.out.println("Product Not Found.");
            }
        } catch (SQLException e) {
            System.out.println("Failed to search product.");
            e.printStackTrace();
        }
    }

    // 6. Sell Product
    public static void sellProduct(int id, int sellQuantity) {
        String selectSql = "SELECT quantity FROM inventory WHERE id = ?";

        String updateSql = "UPDATE inventory SET quantity = quantity - ? WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement selectStatement = connection.prepareStatement(selectSql);
             PreparedStatement updateStatement = connection.prepareStatement(updateSql)) {

            // Find current quantity
            selectStatement.setInt(1, id);
            ResultSet result = selectStatement.executeQuery();
            if (!result.next()) {
                System.out.println("Product ID not found.");
                return;
            }
            int currentQuantity = result.getInt("quantity");

            if (sellQuantity <= 0) {
                System.out.println("Quantity must be greater than 0.");
                return;
            }

            if (sellQuantity > currentQuantity) {
                System.out.println("Not enough stock available.");
                System.out.println("Available quantity: " + currentQuantity);
                return;
            }

            // Update database
            updateStatement.setInt(1, sellQuantity);
            updateStatement.setInt(2, id);
            updateStatement.executeUpdate();

            System.out.println("Sale successful.");
            System.out.println("Quantity sold: " + sellQuantity);
            System.out.println("Remaining stock: " + (currentQuantity - sellQuantity));
        } catch (SQLException e) {
            System.out.println("Failed to sell product.");
            e.printStackTrace();
        }
    }

    // 7. Restock Product
    public static void restockProduct(int id, int restockQuantity) {
        String selectSql = "SELECT quantity FROM inventory WHERE id = ?";

        String updateSql = "UPDATE inventory SET quantity = quantity + ? WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement selectStatement = connection.prepareStatement(selectSql);
             PreparedStatement updateStatement = connection.prepareStatement(updateSql)) {

            // Find current quantity
            selectStatement.setInt(1, id);
            ResultSet result = selectStatement.executeQuery();
            if (!result.next()) {
                System.out.println("Product ID not found.");
                return;
            }
            int currentQuantity = result.getInt("quantity");
            if (restockQuantity <= 0) {
                System.out.println("Quantity must be greater than 0.");
                return;
            }

            // Update database
            updateStatement.setInt(1, restockQuantity);
            updateStatement.setInt(2, id);
            updateStatement.executeUpdate();
            System.out.println("Product restocked successfully.");
            System.out.println("Added quantity: " + restockQuantity);
            System.out.println("New stock: " + (currentQuantity + restockQuantity));
        } catch (SQLException e) {
            System.out.println("Failed to restock product.");
            e.printStackTrace();
        }
    }
}