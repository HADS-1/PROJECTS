package PROJECTS.EXPENSE_TRACKER;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
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
                    expense.saveToDatabase();
                    System.out.println("Expense Added Successfully.");
                    break;

                case 2: // View All Expenses
                    EXPENSES.viewExpensesFromDatabase();
                    break;

                case 3: //Calculate Total
                   EXPENSES.calculateTotalFromDatabase();
                   break;

                case 4: // Spending By Category
                  EXPENSES.spendingByCategoryFromDatabase();
                    break;

                case 5: // Delete Expense
                    System.out.print("Enter expense ID to delete: ");
                    int expenseId = scanner.nextInt();
                    scanner.nextLine();

                    EXPENSES.deleteExpenseFromDatabase(expenseId);
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