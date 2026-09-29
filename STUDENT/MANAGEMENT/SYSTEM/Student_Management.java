package PROJECTS.STUDENT.MANAGEMENT.SYSTEM;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Student_Management {
    // FIELDS
    private String ID;
    private String name;
    private String program;
    private int level;
    private double gpa;

    // CONSTRUCTOR
    public Student_Management(String id, String name, String program, int level) {
        this.ID = id;
        this.name = name;
        this.program = program;
        this.level = level;
    }

    // GETTERS
    public String getID() {return ID;}
    public String getName() {return name;}
    public String getProgram() {return program;}
    public int getLevel() {return level;}
    public double getGpa() {return gpa;}

    // SETTERS
    public void setLevel(int level) {this.level = level;}
    public void setGpa(double gpa) {this.gpa = gpa;}

    // DISPLAY INFORMATION
    public void displayInformation() {
        System.out.println("Student ID      : " + ID);
        System.out.println("Student Name    : " + name);
        System.out.println("Student Program : " + program);
        System.out.println("Student Level   : " + level);
        System.out.println("Student GPA     : " + gpa);
    }

    // SAVE STUDENT
    public void saveToDatabase() {
        String sql = "INSERT INTO students (student_id, name, program, level, gpa) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, ID);
            statement.setString(2, name);
            statement.setString(3, program);
            statement.setInt(4, level);
            statement.setDouble(5, gpa);
            statement.executeUpdate();

            System.out.println("Student saved successfully.");
        } catch (SQLException e) {
            System.out.println("Failed to save student.");
            e.printStackTrace();
        }
    }

    // VIEW ALL STUDENTS
    public static void viewStudentsFromDatabase() {
        String sql = "SELECT * FROM students";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            ResultSet result = statement.executeQuery();
            System.out.println("\n========================== ALL STUDENTS ==========================");
            System.out.printf("%-5s %-12s %-20s %-25s %-8s %-8s%n", "No", "ID", "STUDENT NAME", "PROGRAM", "LEVEL", "GPA");
            System.out.println("-------------------------------------------------------------------");

            boolean found = false;
            int number = 1;
            while (result.next()) {
                found = true;
                String studentID = result.getString("student_id");
                String studentName = result.getString("name");
                String program = result.getString("program");

                int level = result.getInt("level");
                double gpa = result.getDouble("gpa");
                System.out.printf("%-5d %-12s %-20s %-25s %-8d %-8.2f%n", number, studentID, studentName, program, level, gpa);
                number++;
            }
            if (!found) {
                System.out.println("No students registered yet.");
            }
            System.out.println("===================================================================");
        } catch (SQLException e) {
            System.out.println("Failed to load students.");
            e.printStackTrace();
        }
    }

    // FIND STUDENT
    public static Student_Management findStudentByID(String studentID) {
        String sql = "SELECT * FROM students WHERE student_id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, studentID);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                Student_Management student = new Student_Management(result.getString("student_id"),
                        result.getString("name"),result.getString("program"), result.getInt("level"));

                student.setGpa(result.getDouble("gpa"));
                return student;
            }
        } catch (SQLException e) {
            System.out.println("Failed to find student.");
            e.printStackTrace();
        }
        return null;
    }

    // UPDATE STUDENT LEVEL
    public static void updateStudentLevel(String studentID, int newLevel
    ) {
        String sql = "UPDATE students SET level = ? WHERE student_id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setInt(1, newLevel);
            statement.setString(2, studentID);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Student information updated successfully.");
            } else {
                System.out.println("Student not found.");
            }
        } catch (SQLException e) {
            System.out.println("Failed to update student.");
            e.printStackTrace();
        }
    }

    // DELETE STUDENT
    public static void deleteStudent(String studentID) {
        String sql = "DELETE FROM students WHERE student_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, studentID);

            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Student removed successfully.");
            } else {
                System.out.println("Student does not exist.");
            }
        } catch (SQLException e) {
            System.out.println("Failed to delete student.");
            e.printStackTrace();
        }
    }

    // SEARCH STUDENT
    public static void searchStudent(String studentID) {
        Student_Management student = findStudentByID(studentID);

        if (student != null) {
            System.out.println("\nStudent Found.");
            System.out.println("---------------------------");
            student.displayInformation();
        } else {
            System.out.println("Student with this ID does not exist.");
        }
    }

    // SORT STUDENTS BY PROGRAM
    public static void sortStudentsByProgram() {
        String sql = "SELECT * FROM students ORDER BY program ASC";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            ResultSet result = statement.executeQuery();
            System.out.println("\n==================== STUDENTS SORTED BY PROGRAM ====================");
            System.out.printf("%-5s %-12s %-20s %-25s %-8s %-8s%n", "No", "ID", "STUDENT NAME", "PROGRAM", "LEVEL", "GPA");
            System.out.println("-------------------------------------------------------------------");

            boolean found = false;
            int number = 1;
            while (result.next()) {
                found = true;
                System.out.printf("%-5d %-12s %-20s %-25s %-8d %-8.2f%n",
                        number, result.getString("student_id"), result.getString("name"), result.getString("program"), result.getInt("level"), result.getDouble("gpa")
                );
                number++;
            }
            if (!found) {
                System.out.println("No students registered yet.");
            }
            System.out.println("===================================================================");
        } catch (SQLException e) {
            System.out.println("Failed to sort students.");
            e.printStackTrace();
        }
    }

    // UPDATE GPA

    public static void updateGPA(String studentID, double gpa) {
        String sql = "UPDATE students SET gpa = ? WHERE student_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setDouble(1, gpa);
            statement.setString(2, studentID);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("GPA saved successfully.");
            } else {
                System.out.println("Student not found.");
            }
        } catch (SQLException e) {
            System.out.println("Failed to save GPA.");
            e.printStackTrace();
        }
    }
}
