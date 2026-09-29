package PROJECTS.MINI.ECOMMERCE.SYSTEM;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Product {
    // Fields
    private int id;
    private String name;
    private double price;

    // Constructor
    public Product(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    // Getters
    public int getId() {return id;}
    public String getName() {return name;}
    public double getPrice() {return price;}

    // Display one product
    public void displayProduct() {
        System.out.printf("%-5d %-15s GH₵%.2f%n", id, name, price);
    }

    //Add Products
    public static void addProductToDatabase(String name, double price) {

        String sql = "INSERT INTO products (name, price) VALUES (?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, name);
            statement.setDouble(2, price);

            statement.executeUpdate();

            System.out.println("Product added successfully!");

        } catch (SQLException e) {
            System.out.println("Failed to add product.");
            e.printStackTrace();
        }
    }

    // Display products from MariaDB
    public static void viewProductsFromDatabase() {
        String sql = "SELECT * FROM products";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            ResultSet result = statement.executeQuery();
            System.out.println("\n=========== PRODUCTS ============");
            System.out.printf("%-5s %-15s %-10s%n", "ID", "PRODUCT", "PRICE");
            System.out.println("--------------------------------");

            boolean found = false;
            while (result.next()) {
                found = true;
                int id = result.getInt("id");
                String name = result.getString("name");
                double price = result.getDouble("price");

                System.out.printf("%-5d %-15s GH₵%.2f%n", id, name, price);
            }
            if (!found) {
                System.out.println("No products found.");
            }
        } catch (SQLException e) {
            System.out.println("Failed to load products.");
            e.printStackTrace();
        }
    }

    // Find one product from MariaDB
    public static Product findProductById(int productId) {
        String sql = "SELECT * FROM products WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, productId);

            ResultSet result = statement.executeQuery();
            if (result.next()) {
                int id = result.getInt("id");
                String name = result.getString("name");
                double price = result.getDouble("price");
                return new Product(id, name, price);
            }
        } catch (SQLException e) {
            System.out.println("Failed to find product.");
            e.printStackTrace();
        }
        return null;
    }
}