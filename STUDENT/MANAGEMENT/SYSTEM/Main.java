package PROJECTS.STUDENT.MANAGEMENT.SYSTEM;

import java.util.Scanner;

public class Main {

    static Scanner scanner = new Scanner(System.in);

    // CALCULATE GPA
    public static double calculateGPA() {
        double totalPoint = 0;
        int numberOfCourse = 5;

        for (int i = 1; i <= numberOfCourse; i++) {
            System.out.print("Enter grade for course " + i + ": ");
            String grade = scanner.nextLine().toUpperCase();

            switch (grade) {
                case "A":
                    totalPoint += 4.0;
                    break;

                case "B":
                    totalPoint += 3.0;
                    break;

                case "C":
                    totalPoint += 2.0;
                    break;

                case "D":
                    totalPoint += 1.0;
                    break;

                case "F":
                    totalPoint += 0.0;
                    break;


                default:
                    System.out.println("Invalid grade. " + "Enter A, B, C, D or F.");
                    i--;
                    break;
            }
        }
        return totalPoint / numberOfCourse;
    }
    public static void main(String[] args) {
        int option;

        do {
            System.out.println("\n=========== STUDENT MANAGEMENT SYSTEM ===========");
            System.out.println("1. Add Student");
            System.out.println("2. View Students");
            System.out.println("3. Update Student Information");
            System.out.println("4. Delete Student");
            System.out.println("5. Search Student");
            System.out.println("6. Sort Students by Program");
            System.out.println("7. Calculate Student GPA");
            System.out.println("8. Exit");
            System.out.println("=================================================");
            System.out.print("Select Option: ");

            option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    System.out.println("\n==== ADD NEW STUDENT ====");
                    System.out.print("Enter Student ID: ");
                    String studentID = scanner.nextLine();

                    // Check if ID already exists
                    Student_Management existingStudent = Student_Management.findStudentByID(studentID);

                    if (existingStudent != null) {
                        System.out.println("This Student ID already exists.");
                        break;
                    }

                    System.out.print("Enter Student Full Name: ");
                    String fullName = scanner.nextLine();

                    System.out.print("Enter Program: ");
                    String program = scanner.nextLine();

                    System.out.print("Enter Level: ");
                    int level = scanner.nextInt();
                    scanner.nextLine();

                    Student_Management student = new Student_Management(studentID, fullName, program, level);
                    student.saveToDatabase();
                    break;

                case 2:
                    Student_Management.viewStudentsFromDatabase();
                    break;

                case 3:
                    System.out.print("Enter Student ID to update: ");
                    String updateID = scanner.nextLine();

                    Student_Management studentToUpdate = Student_Management.findStudentByID(updateID);
                    if (studentToUpdate == null) {
                        System.out.println("Student not found.");
                        break;
                    }
                    System.out.println("Student Found.");

                    System.out.print("Enter new level: ");
                    int newLevel = scanner.nextInt();
                    scanner.nextLine();

                    Student_Management.updateStudentLevel(updateID, newLevel);
                    break;

                case 4:
                    System.out.print("Enter Student ID to delete: ");
                    String deleteID = scanner.nextLine();
                    Student_Management.deleteStudent(deleteID);
                    break;

                case 5:
                    System.out.print("Enter Student ID to search: ");
                    String searchID = scanner.nextLine();
                    Student_Management.searchStudent(searchID);
                    break;

                case 6:
                    Student_Management.sortStudentsByProgram();
                    break;

                case 7:
                    System.out.print("Enter Student ID: ");
                    String gpaID = scanner.nextLine();

                    Student_Management gpaStudent = Student_Management.findStudentByID(gpaID);

                    if (gpaStudent == null) {
                        System.out.println("Student does not exist.");
                        break;
                    }
                    System.out.println("Student Found.");

                    double gpa = calculateGPA();
                    System.out.printf("GPA: %.2f%n", gpa);
                    Student_Management.updateGPA(gpaID, gpa);
                    break;

                case 8:
                    System.out.println("\nThanks for using " + "Student Management System.");
                    System.out.println("Logging out...");
                    break;

                default:
                    System.out.println("Invalid option. Try again.");
                    break;
            }
        } while (option != 8);
        scanner.close();
    }
}
