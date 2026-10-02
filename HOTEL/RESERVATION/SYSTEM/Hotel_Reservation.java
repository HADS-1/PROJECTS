package PROJECTS.HOTEL.RESERVATION.SYSTEM;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Hotel_Reservation {

    //Making Reservations
    public static void reserveRoom(Connection connection, Scanner scanner) {

        String guestName = readLine(scanner, "Enter guest name: ");
        int roomNumber = readInt(scanner, "Enter room number: ");
        String contactNumber = readLine(scanner, "Enter contact number: ");

        String sql = """
                INSERT INTO reservation_table
                (guest_name, room_number, contact_number)
                VALUES (?, ?, ?)
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, guestName);
            statement.setInt(2, roomNumber);
            statement.setString(3, contactNumber);

            int affectedRows = statement.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Reservation Successful!");
            } else {
                System.out.println("Reservation Failed.");
            }

        } catch (SQLException e) {
            System.out.println("Error making reservation.");
            e.printStackTrace();
        }
    }

    // View Reservations
    public static void viewReservations(
            Connection connection,
            Scanner scanner
    ) throws SQLException {

        String sql = """
                SELECT reservation_id,
                       guest_name,
                       room_number,
                       contact_number,
                       reservation_date
                FROM reservation_table
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            System.out.println();
            System.out.println("Current Reservations:");
            System.out.println(
                    "+----------------+-----------------+-------------+--------------------+---------------------+"
            );
            System.out.printf(
                    "| %-14s | %-15s | %-11s | %-18s | %-19s |%n",
                    "Reservation ID",
                    "Guest",
                    "Room Number",
                    "Contact Number",
                    "Reservation Date"
            );
            System.out.println(
                    "+----------------+-----------------+-------------+--------------------+---------------------+"
            );

            boolean found = false;
            while (resultSet.next()) {

                found = true;

                int reservationId = resultSet.getInt("reservation_id");

                String guestName = resultSet.getString("guest_name");

                int roomNumber = resultSet.getInt("room_number");

                String contactNumber = resultSet.getString("contact_number");

                String reservationDate = resultSet.getString("reservation_date");

                System.out.printf("| %-14d | %-15s | %-11d | %-18s | %-19s |%n",
                        reservationId, guestName, roomNumber, contactNumber, reservationDate);
            }

            System.out.println(
                    "+----------------+-----------------+-------------+--------------------+---------------------+"
            );
            if (!found) {
                System.out.println("No reservations found.");
            }
        }
    }


    // Get Room
    public static void getRoomNumber(Connection connection, Scanner scanner) {

        int reservationId = readInt(scanner, "Enter reservation ID: ");

        String guestName = readLine(scanner, "Enter guest name: ");

        String sql = """
                SELECT room_number
                FROM reservation_table
                WHERE reservation_id = ?
                AND guest_name = ?
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, reservationId);
            statement.setString(2, guestName);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    int roomNumber = resultSet.getInt("room_number");

                    System.out.println("Room number for Reservation ID " + reservationId +
                            " and Guest " + guestName + " is: " + roomNumber);
                } else {
                    System.out.println("Reservation not found for the given ID and guest name.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error finding room number.");
            e.printStackTrace();
        }
    }


    //Update Reservations
    public static void updateReservation(Connection connection, Scanner scanner) {

        int reservationId = readInt(scanner, "Enter reservation ID to update: ");

        if (!reservationExists(connection, reservationId)) {
            System.out.println("Reservation not found for the given ID.");
            return;
        }
        String newGuestName = readLine(scanner, "Enter new guest name: ");

        int newRoomNumber = readInt(scanner, "Enter new room number: ");

        String newContactNumber = readLine(scanner, "Enter new contact number: ");

        String sql = """
                UPDATE reservation_table
                SET guest_name = ?,
                    room_number = ?,
                    contact_number = ?
                WHERE reservation_id = ?
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, newGuestName);
            statement.setInt(2, newRoomNumber);
            statement.setString(3, newContactNumber);
            statement.setInt(4, reservationId);

            int affectedRows = statement.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Reservation Updated Successfully!");
            } else {
                System.out.println("Reservation update failed.");
            }
        } catch (SQLException e) {
            System.out.println("Error updating reservation.");
            e.printStackTrace();
        }
    }

    //Delete Reservations
    public static void deleteReservation(Connection connection, Scanner scanner) {

        int reservationId = readInt(scanner, "Enter reservation ID to delete: ");

        if (!reservationExists(connection, reservationId)) {
            System.out.println("Reservation not found for the given ID.");
            return;
        }
        String sql = "DELETE FROM reservation_table WHERE reservation_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, reservationId);

            int affectedRows = statement.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Reservation deleted successfully!");
            } else {
                System.out.println("Reservation deletion failed.");
            }
        } catch (SQLException e) {
            System.out.println("Error deleting reservation.");
            e.printStackTrace();
        }
    }

    //Exist
    private static boolean reservationExists(Connection connection, int reservationId) {
        String sql = """
                SELECT reservation_id
                FROM reservation_table
                WHERE reservation_id = ?
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, reservationId);

            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            System.out.println("Error checking reservation.");
            e.printStackTrace();
            return false;
        }
    }

    public static void exit() throws InterruptedException {

        System.out.println();
        System.out.println("Exiting System...");

        for (int i = 0; i < 5; i++) {
            System.out.print(".");
            Thread.sleep(500);
        }

        System.out.println();
        System.out.println("Thank You For Using Hotel Reservation System!");
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

    private static String readLine(Scanner scanner, String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
}