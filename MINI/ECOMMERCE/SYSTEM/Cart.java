package PROJECTS.MINI.ECOMMERCE.SYSTEM;

import java.util.ArrayList;

public class Cart {
    private ArrayList<Product> cartItems;  //Field

    //Constructor
    public Cart() {
        cartItems = new ArrayList<>();
    }

    //Methods
    //1. Add product to cart
    public void addProduct(Product product) {
        cartItems.add(product);
        System.out.println(product.getName() + " added to cart successfully!");
    }

    //2. Remove product from cart
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

    //3. Display cart
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

    //4. Calculate total
    public double calculateTotal() {
        double total = 0;
        for (Product product : cartItems) {
            total += product.getPrice();
        }
        return total;
    }

    //5. Checkout
    public void checkout() {
        if (cartItems.isEmpty()) {
            System.out.println("\nYour cart is empty. Nothing to checkout.");
            return;
        }
        double subtotal = calculateTotal();
        double discount = 0;

        // 10% discount if subtotal is GH₵1000 or more
        if (subtotal >= 1000) {
            discount = subtotal * 0.10;
        }
        double finalTotal = subtotal - discount;

        System.out.println("\n================================");
        System.out.println("           RECEIPT");
        System.out.println("================================");

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
