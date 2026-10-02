package PROJECTS.HOTEL.RESERVATION.SYSTEM;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Connection connection = DatabaseConnection.getConnection();

        if (connection == null) {
            System.out.println("Unable to connect to the database.");
            scanner.close();
            return;
        }
        System.out.println("Database connected successfully!");

        while (true) {
            System.out.println();
            System.out.println("==============================");
            System.out.println("   HOTEL RESERVATION SYSTEM");
            System.out.println("==============================");
            System.out.println("1. Reserve a room");
            System.out.println("2. View Reservations");
            System.out.println("3. Get Room Number");
            System.out.println("4. Update Reservation");
            System.out.println("5. Delete Reservation");
            System.out.println("0. Exit");
            System.out.println("==============================");

            int choice = readInt(scanner, "Choose an option: ");

            try {
                switch (choice) {

                    case 1 -> Hotel_Reservation.reserveRoom(connection, scanner);

                    case 2 -> Hotel_Reservation.viewReservations(connection, scanner);

                    case 3 -> Hotel_Reservation.getRoomNumber(connection, scanner);

                    case 4 -> Hotel_Reservation.updateReservation(connection, scanner);

                    case 5 -> Hotel_Reservation.deleteReservation(connection, scanner);

                    case 0 -> {
                        Hotel_Reservation.exit();
                        connection.close();
                        scanner.close();
                        return;
                    }

                    default ->
                            System.out.println("Invalid Choice. Try again.");
                }
            } catch (SQLException e) {
                System.out.println("Database error occurred.");
                e.printStackTrace();

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Exit interrupted.");
                return;
            }
        }
    }

    private static int readInt(Scanner scanner, String prompt) {
        System.out.print(prompt);

        while (!scanner.hasNextInt()) {
            System.out.println("Invalid number. Please enter a valid integer.");
            scanner.next();
            System.out.print(prompt);
        }
        int value = scanner.nextInt();
        scanner.nextLine();
        return value;
    }
}