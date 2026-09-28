package PROJECTS.MINI.ECOMMERCE.SYSTEM;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        // Create products
        ArrayList<Product> products = new ArrayList<>();

        products.add(new Product(1, "Laptop", 5000));
        products.add(new Product(2, "Mouse", 100));
        products.add(new Product(3, "Keyboard", 200));
        products.add(new Product(4, "Headphones", 300));

        // Create cart
        Cart cart = new Cart();
        int choice;

        do {
            System.out.println("\n===== ONLINE SHOP =====");
            System.out.println("1. View Products");
            System.out.println("2. Add to Cart");
            System.out.println("3. View Cart");
            System.out.println("4. Remove from Cart");
            System.out.println("5. Checkout");
            System.out.println("6. Exit");
            System.out.println("-------------------------");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    viewProducts(products);
                    break;

                case 2:
                    addToCart(products, cart);
                    break;

                case 3:
                    cart.viewCart();
                    break;

                case 4:
                    removeFromCart(cart);
                    break;

                case 5:
                    cart.checkout();
                    break;

                case 6:
                    System.out.println("Thank you for visiting our shop!");
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }

        } while (choice != 6);
    }

    // Display all products
    public static void viewProducts(ArrayList<Product> products) {
        System.out.println("\n=========== PRODUCTS ============");
        System.out.printf("%-5s %-15s %-10s%n", "ID", "PRODUCT", "PRICE");
        System.out.println("--------------------------------");
        for (Product product : products) {
            product.displayProduct();
        }
    }

    // Add product to cart
    public static void addToCart(ArrayList<Product> products, Cart cart) {
        viewProducts(products);

        System.out.print("\nEnter Product ID: ");
        int productId = scanner.nextInt();

        for (Product product : products) {
            if (product.getId() == productId) {
                cart.addProduct(product);
                return;
            }
        }
        System.out.println("Product not found.");
    }

    // Remove product from cart
    public static void removeFromCart(Cart cart) {
        cart.viewCart();

        System.out.print("\nEnter Product ID to remove: ");
        int productId = scanner.nextInt();
        cart.removeProduct(productId);
    }
}