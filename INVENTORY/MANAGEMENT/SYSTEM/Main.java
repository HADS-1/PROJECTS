package PROJECTS.INVENTORY.MANAGEMENT.SYSTEM;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ArrayList<INVENTORY> products =new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        int choice;
        do{
            System.out.println("======  INVENTORY MANAGEMENT SYSTEM =====");
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
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice){
                case 1:  //Adding Product
                    int productId = 0;
                    if(productId >= 0 ){
                        System.out.print("Enter Product ID : ");
                        productId = scanner.nextInt();
                    }else {
                        System.out.println("Invalid Product ID.");
                    }scanner.nextLine();

                    System.out.print("Enter Product Name : ");
                    String productName = scanner.nextLine();

                    double productPrice = 0;
                    if(productPrice >= 0){
                        System.out.print("Enter Product Price : GHC ");
                        productPrice = scanner.nextDouble();
                    }else {
                        System.out.print("Wrong Price Entered ");
                    }scanner.nextLine();

                    int productQuantity = 0;
                    if(productQuantity >= 0){
                        System.out.print("Enter Product Quantity : ");
                        productQuantity = scanner.nextInt();
                    }else {
                        System.out.print("Invalid Product Quantity. ");
                    }scanner.nextLine();

                    INVENTORY product = new INVENTORY(productId, productName, productPrice, productQuantity);
                    products.add(product);
                    System.out.println("Product Added Successfully.");
                    break;

                case 2:  //Updating Product
                    System.out.print("Enter Product to Update : ");
                    String updateProduct = scanner.nextLine();

                    boolean nameFound = false;
                    for(INVENTORY proName : products){
                        if (proName.getName().equalsIgnoreCase(updateProduct)){
                            int newId = 0;
                            if(newId >= 0 ){
                                System.out.print("Enter Product ID : ");
                                newId = scanner.nextInt();
                            }else {
                                System.out.println("Invalid Product ID.");
                            }scanner.nextLine();

                            System.out.print("Enter product Name : ");
                            String newName = scanner.nextLine();

                            double newPrice = 0;
                            if(newPrice >= 0){
                                System.out.print("Enter Product Price : GHC ");
                                newPrice = scanner.nextDouble();
                            }else {
                                System.out.println("Invalid Product Price.");
                            }scanner.nextLine();

                            int newQuantity = 0;
                            if(newQuantity >= 0){
                                System.out.print("Enter Product Quantity : ");
                                newQuantity = scanner.nextInt();

                                nameFound =true;
                            }else {
                                System.out.println("Invalid Product Quantity.");
                            }scanner.nextLine();
                            break;
                        }
                    }if (!nameFound){
                    System.out.println("Product Not Found.");
                    }
                    break;

                case 3:  //Deleting Product
                    if(products.isEmpty()){
                        System.out.println("Product Not Found");
                    }else {
                        System.out.print("Enter Product Number to Delete : ");
                        int productNumber = scanner.nextInt();
                        scanner.nextLine();
                        if(productNumber >= 1 && productNumber <= products.size()){
                            INVENTORY deleteProduct = products.remove(productNumber - 1);
                            System.out.println(deleteProduct.getName() + " deleted Successfully.");
                        }else {
                            System.out.println("Invalid Product Name ");
                        }
                    }
                    break;

                case 4:  //Searching Product
                    System.out.print("Enter Product for Search : ");
                    String searchProduct = scanner.nextLine();

                    boolean found = false;
                    for(INVENTORY proName : products){
                        if (proName.getName().equalsIgnoreCase(searchProduct)){
                            System.out.println("Product Found ");
                            proName.displayInventory();
                            found = true;
                            break;
                        }
                    }if (!found){
                    System.out.println("Product Not Found.");
                }
                    break;
                case 5: // Selling Product
                    if (products.isEmpty()) {
                        System.out.println("No products available yet.");
                    } else {
                        System.out.print("Enter Product ID to sell: ");
                        int sellID = scanner.nextInt();
                        boolean foundProduct = false;
                        for (INVENTORY product1 : products) {
                            if (product1.getID() == sellID) {
                                foundProduct = true;
                                System.out.print("Enter quantity to sell: ");
                                int sellQuantity = scanner.nextInt();
                                if (sellQuantity <= 0) {
                                    System.out.println("Quantity must be greater than 0.");
                                } else if (sellQuantity > product1.getQuantity()) {
                                    System.out.println("Not enough stock available.");
                                    System.out.println("Available quantity: " + product1.getQuantity());
                                } else {
                                    int newQuantity = product1.getQuantity() - sellQuantity;
                                    product1.setQuantity(newQuantity);
                                    System.out.println("Sale successful.");
                                    System.out.println("Product: " + product1.getName());
                                    System.out.println("Quantity sold: " + sellQuantity);
                                    System.out.println("Remaining stock: " + product1.getQuantity());
                                }
                                break;
                            }
                        }
                        if (!foundProduct) {
                            System.out.println("Product ID not found.");
                        }
                    }
                    scanner.nextLine();
                    break;

                case 6: // Restocking Product
                    if (products.isEmpty()) {
                        System.out.println("No products available.");
                    } else {
                        System.out.print("Enter Product ID to restock: ");
                        int restockID = scanner.nextInt();
                        boolean foundProduct = false;
                        for (INVENTORY product1 : products) {
                            if (product1.getID() == restockID) {
                                foundProduct = true;
                                System.out.print("Enter quantity to add: ");
                                int restockQuantity = scanner.nextInt();
                                if (restockQuantity <= 0) {
                                    System.out.println("Quantity must be greater than 0.");
                                } else {
                                    int newQuantity = product1.getQuantity() + restockQuantity;
                                    product1.setQuantity(newQuantity);
                                    System.out.println("Product restocked successfully.");
                                    System.out.println("Product: " + product1.getName());
                                    System.out.println("Added quantity: " + restockQuantity);
                                    System.out.println("New stock: " + product1.getQuantity());
                                }
                                break;
                            }
                        }
                        if (!foundProduct) {
                            System.out.println("Product ID not found.");
                        }
                    }
                    scanner.nextLine();
                    break;

                case 7:  //Displaying all Products
                    if(products.isEmpty()){
                        System.out.println("No Product Entry. Start By Adding products.");
                    }else {
                        System.out.println("\n======================== ALL PRODUCTS ===============================");
                        System.out.printf("%-7s %-8s %-16s %-15s %-15s%n", "No.", "ID", "NAME", "PRICE", "QUANTITY");
                        System.out.println("---------------------------------------------------------------------");
                        int number = 1;
                        for (INVENTORY goods : products) {
                            System.out.printf("%-7d %-7d %-16s GHC %-15.2f %-18d%n",
                                    number,
                                    goods.getID(),
                                    goods.getName(),
                                    goods.getPrice(),
                                    goods.getQuantity());
                            number++;
                        }
                        System.out.println("=====================================================================");
                    }
                    break;

                case 8:  //Exiting
                    System.out.println("Thank you for using Inventory Management System.");
                    System.out.println("Signing Out......");
                    break;

                default:
                    System.out.println("Invalid Choice, Try again.");
                    break;
            }
        }while (choice != 8);

        scanner.close();
    }
}
