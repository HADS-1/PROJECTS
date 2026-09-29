package PROJECTS.EXPENSE_TRACKER;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        DatabaseConnection.getConnection();

        ArrayList <EXPENSES> expensesList = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        int choice;
        do{
            System.out.println("=====   EXPENSE TRACKER   =====");
            System.out.println("1. Add Expense. ");
            System.out.println("2. View Expense.");
            System.out.println("3. Calculate Total. ");
            System.out.println("4. Spending by Category." );
            System.out.println("5. Delete Expense. ");
            System.out.println("6. Exit. ");
            System.out.print("Enter Choice :  ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice){
                case 1: //Add Expense
                    System.out.print("Enter Expenses name : ");
                    String expenseName = scanner.nextLine();

                    double expenseAmount;
                    while (true) {
                        System.out.print("Enter Expense amount : GHC ");
                        if (scanner.hasNextDouble()) {
                            expenseAmount = scanner.nextDouble();
                            scanner.nextLine();
                            if (expenseAmount > 0) {
                                break;
                            } else {
                                System.out.println("Amount must be greater than 0.");
                            }
                        } else {
                            System.out.println("Invalid amount. Please enter a number.");
                            scanner.nextLine();
                        }
                    }

                    System.out.print("Enter Expense Category : ");
                    String expenseCategory = scanner.nextLine();

                    LocalDate expenseDate;
                    while (true) {
                        System.out.print("Enter Expense Date in form yyyy-MM-dd : ");
                        String dateInput = scanner.nextLine();
                        try {
                            expenseDate = LocalDate.parse(dateInput);
                            break;
                        } catch (DateTimeParseException e) {
                            System.out.println("Invalid date. Please use yyyy-MM-dd.");
                        }
                    }
                    EXPENSES expense = new EXPENSES(expenseName, expenseAmount, expenseCategory, expenseDate);
                    expensesList.add(expense);
                    System.out.println("Expense Added Successfully.");
                    break;

                case 2: // View All Expenses
                    if (expensesList.isEmpty()) {
                        System.out.println("No expenses recorded yet.");
                    } else {
                        System.out.println("\n=========================== ALL EXPENSES ===============================");
                        System.out.printf("%-5s %-20s %-12s %-15s %-15s%n", "No.", "Name", "Amount", "Category", "Date");
                        System.out.println("--------------------------------------------------------------------------");
                        int number = 1;
                        for (EXPENSES expenses : expensesList) {
                            System.out.printf("%-5d %-20s GHC %-8.2f %-15s %-15s%n",
                                    number,
                                    expenses.getName(),
                                    expenses.getAmount(),
                                    expenses.getCategory(),
                                    expenses.getDate());
                            number++;
                        }
                        System.out.println("==========================================================================");
                    }
                    break;

                case 3: //Calculate Total
                    double total = 0;
                    for(EXPENSES expenses : expensesList){
                        total += expenses.getAmount();
                    }
                    System.out.println("Total Expenses : GHC "+total);
                    break;

                case 4: // Spending By Category
                    if (expensesList.isEmpty()) {
                        System.out.println("No expenses recorded yet.");
                    } else {
                        HashMap<String, Double> categoryTotals = new HashMap<>();
                        for (EXPENSES expenses : expensesList) {
                            String category = expenses.getCategory();
                            double amount = expenses.getAmount();
                            categoryTotals.put(
                                    category,
                                    categoryTotals.getOrDefault(category, 0.0) + amount
                            );
                        }
                        System.out.println("===== SPENDING BY CATEGORY =====");
                        for (String category : categoryTotals.keySet()) {
                            System.out.println(
                                    category + ": GHC " + categoryTotals.get(category)
                            );
                        }
                    }
                    break;

                case 5: // Delete Expense
                    if (expensesList.isEmpty()) {
                        System.out.println("No expenses to delete.");
                    } else {
                        System.out.print("Enter expense number to delete: ");
                        int expenseNumber = scanner.nextInt();
                        scanner.nextLine();
                        if (expenseNumber >= 1 && expenseNumber <= expensesList.size()) {
                            EXPENSES deletedExpense = expensesList.remove(expenseNumber - 1);
                            System.out.println(
                                    deletedExpense.getName() + " deleted successfully."
                            );
                        } else {
                            System.out.println("Invalid expense number.");
                        }
                    }
                    break;


                case 6: // Exist
                    System.out.println("Exiting, Thank You for using Expense Tracker ....");
                    break;

                default:
                    System.out.println("Invalid Choice. Try again");
            }
        }while (choice != 6);

        scanner.close();
    }
}