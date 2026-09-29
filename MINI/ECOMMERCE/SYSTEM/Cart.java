package PROJECTS.MINI.ECOMMERCE.SYSTEM;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Cart {
    // Field
    private ArrayList<Product> cartItems;

    // Constructor
    public Cart() {
        cartItems = new ArrayList<>();
    }

    // 1. Add product to cart
    public void addProduct(Product product) {
        cartItems.add(product);
        System.out.println(product.getName() + " added to cart successfully!");
    }

    // 2. Remove product from cart
    public void removeProduct(int productId) {
        for (Product product : cartItems) {
            if (product.getId() == productId) {
                cartItems.remove(product);
                System.out.println(product.getName() + " removed from cart.");
                return;
            }
        }
        System.out.println("Product not found in cart.");
    }

    // 3. Display cart
    public void viewCart() {
        if (cartItems.isEmpty()) {
            System.out.println("\nYour cart is empty.");
            return;
        }
        System.out.println("\n===== CART =====");
        for (Product product : cartItems) {
            System.out.printf("%-15s GH₵%.2f%n", product.getName(), product.getPrice());
        }
        System.out.println("----------------------------");
        System.out.printf("Total:          GH₵%.2f%n", calculateTotal());
    }

    // 4. Calculate total
    public double calculateTotal() {
        double total = 0;
        for (Product product : cartItems) {
            total += product.getPrice();
        }
        return total;
    }

    // 5. Checkout
    public void checkout() {
        if (cartItems.isEmpty()) {
            System.out.println("\nYour cart is empty. Nothing to checkout.");
            return;
        }

        // Calculate subtotal
        double subtotal = calculateTotal();

        // Calculate discount
        double discount = 0;
        if (subtotal >= 1000) {
            discount = subtotal * 0.10;
        }

        // Calculate final total
        double finalTotal = subtotal - discount;

        // Save order
        String sql = "INSERT INTO orders (subtotal, discount, total) VALUES (?, ?, ?)";
        int orderId;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            statement.setDouble(1, subtotal);
            statement.setDouble(2, discount);
            statement.setDouble(3, finalTotal);

            statement.executeUpdate();
            ResultSet keys = statement.getGeneratedKeys();

            if (keys.next()) {
                orderId = keys.getInt(1);
            } else {
                System.out.println("Could not create order.");
                return;
            }
        } catch (SQLException e) {
            System.out.println("Failed to save order.");
            e.printStackTrace();
            return;
        }

        // Save products in the order
        String itemSql = "INSERT INTO order_items (order_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";

        for (Product product : cartItems) {
            try (Connection connection = DatabaseConnection.getConnection();
                 PreparedStatement statement = connection.prepareStatement(itemSql)) {

                statement.setInt(1, orderId);
                statement.setInt(2, product.getId());
                statement.setInt(3, 1);
                statement.setDouble(4, product.getPrice());
                statement.executeUpdate();
            } catch (SQLException e) {
                System.out.println("Failed to save order item.");
                e.printStackTrace();
            }
        }

        // Display receipt
        System.out.println("\n================================");
        System.out.println("           RECEIPT");
        System.out.println("================================");
        System.out.println("Order ID: " + orderId);

        for (Product product : cartItems) {
            System.out.printf("%-15s GH₵%.2f%n", product.getName(), product.getPrice());
        }
        System.out.println("--------------------------------");
        System.out.printf("Subtotal:        GH₵%.2f%n", subtotal);
        System.out.printf("Discount:        GH₵%.2f%n", discount);
        System.out.println("--------------------------------");
        System.out.printf("TOTAL:           GH₵%.2f%n", finalTotal);
        System.out.println("\nThank you for shopping!");
        System.out.println("================================");

        // Empty cart after checkout
        cartItems.clear();
    }
}