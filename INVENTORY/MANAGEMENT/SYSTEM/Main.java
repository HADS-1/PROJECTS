package PROJECTS.INVENTORY.MANAGEMENT.SYSTEM;

import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean running = true;

        while (running) {
            System.out.println("\n====== INVENTORY MANAGEMENT SYSTEM =====");
            System.out.println("1. Add Product.");
            System.out.println("2. Update Product.");
            System.out.println("3. Delete Product.");
            System.out.println("4. Search Product.");
            System.out.println("5. Sell Product.");
            System.out.println("6. Restock Product.");
            System.out.println("7. Display Inventory");
            System.out.println("8. Exit.");
            System.out.println("==========================================");

            System.out.print("Select Option : ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {

                // 1. Add Product
                case 1:
                    System.out.print("Enter Product Name : ");
                    String productName = scanner.nextLine();
                    double productPrice;

                    while (true) {
                        System.out.print("Enter Product Price : GHC ");
                        if (scanner.hasNextDouble()) {
                            productPrice = scanner.nextDouble();
                            scanner.nextLine();
                            if (productPrice > 0) {
                                break;
                            }

                            System.out.println("Price must be greater than 0.");
                        } else {
                            System.out.println("Invalid price.");
                            scanner.nextLine();
                        }
                    }
                    int productQuantity;

                    while (true) {
                        System.out.print("Enter Product Quantity : ");

                        if (scanner.hasNextInt()) {
                            productQuantity = scanner.nextInt();
                            scanner.nextLine();

                            if (productQuantity >= 0) {
                                break;
                            }

                            System.out.println("Quantity cannot be negative.");
                        } else {
                            System.out.println("Invalid quantity.");
                            scanner.nextLine();
                        }
                    }

                    INVENTORY product =
                            new INVENTORY(0, productName, productPrice, productQuantity);
                    product.saveToDatabase();
                    System.out.println("Product Added Successfully.");
                    break;


                // 2. Update Product
                case 2:
                    System.out.print("Enter Product ID to update: ");
                    int updateID = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("Enter new Product Name: ");
                    String newName = scanner.nextLine();

                    System.out.print("Enter new Product Price: GHC ");
                    double newPrice = scanner.nextDouble();

                    System.out.print("Enter new Product Quantity: ");
                    int newQuantity = scanner.nextInt();
                    scanner.nextLine();

                    if (newPrice <= 0) {
                        System.out.println("Price must be greater than 0.");
                        break;
                    }

                    if (newQuantity < 0) {
                        System.out.println("Quantity cannot be negative.");
                        break;
                    }

                    INVENTORY.updateProduct(updateID, newName, newPrice, newQuantity);
                    break;

                // 3. Delete Product
                case 3:
                    System.out.print("Enter Product ID to delete: ");
                    int deleteID = scanner.nextInt();
                    scanner.nextLine();
                    INVENTORY.deleteProduct(deleteID);
                    break;

                // 4. Search Product
                case 4:
                    System.out.print("Enter Product Name to search: ");
                    String searchName = scanner.nextLine();
                    INVENTORY.searchProduct(searchName);
                    break;

                // 5. Sell Product
                case 5:
                    System.out.print("Enter Product ID to sell: ");
                    int sellID = scanner.nextInt();
                    System.out.print("Enter quantity to sell: ");
                    int sellQuantity = scanner.nextInt();

                    scanner.nextLine();
                    INVENTORY.sellProduct(sellID, sellQuantity);
                    break;

                // 6. Restock Product
                case 6:
                    System.out.print("Enter Product ID to restock: ");
                    int restockID = scanner.nextInt();
                    System.out.print("Enter quantity to add: ");
                    int restockQuantity = scanner.nextInt();

                    scanner.nextLine();
                    INVENTORY.restockProduct(restockID, restockQuantity);
                    break;

                // 7. Display Inventory
                case 7:
                    INVENTORY.displayAllProducts();
                    break;

                // 8. Exit
                case 8:
                    running = false;
                    System.out.println("Thank you for using Inventory Management System.");
                    System.out.println("Signing Out......");
                    break;

                default:
                    System.out.println("Invalid Choice, Try again.");
            }
        }
        scanner.close();
    }
}