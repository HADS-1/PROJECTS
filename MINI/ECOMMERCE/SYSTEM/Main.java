package PROJECTS.MINI.ECOMMERCE.SYSTEM;

import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        DatabaseConnection.getConnection();
        Cart cart = new Cart();

        int choice;
        do {
            System.out.println("\n========== ONLINE SHOP ==========");
            System.out.println("1. View Products");
            System.out.println("2. Add Product");
            System.out.println("3. Add to Cart");
            System.out.println("4. View Cart");
            System.out.println("5. Remove from Cart");
            System.out.println("6. Checkout");
            System.out.println("7. Exit");
            System.out.println("---------------------------------");
            System.out.print("Enter your choice: ");

            choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    Product.viewProductsFromDatabase();
                    break;

                case 2:
                    scanner.nextLine();
                    System.out.print("Enter Product Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter Product Price: GHC ");
                    double price = scanner.nextDouble();
                    if (price <= 0) {
                        System.out.println("Price must be greater than 0.");
                    } else {
                        Product.addProductToDatabase(name, price);
                    }
                    break;

                case 3:
                    addToCart(cart);
                    break;

                case 4:
                    cart.viewCart();
                    break;

                case 5:
                    removeFromCart(cart);
                    break;

                case 6:
                    cart.checkout();
                    break;

                case 7:
                    System.out.println("Thank you for visiting our shop!");
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 7);
    }

    // ADD PRODUCT TO CART
    public static void addToCart(Cart cart) {
        Product.viewProductsFromDatabase();
        System.out.print("\nEnter Product ID: ");
        int productId = scanner.nextInt();

        Product product = Product.findProductById(productId);
        if (product != null) {
            cart.addProduct(product);
        } else {
            System.out.println("Product not found.");
        }
    }

    // REMOVE PRODUCT FROM CART
    public static void removeFromCart(Cart cart) {
        cart.viewCart();
        System.out.print("\nEnter Product ID to remove: ");
        int productId = scanner.nextInt();
        cart.removeProduct(productId);
    }
}

