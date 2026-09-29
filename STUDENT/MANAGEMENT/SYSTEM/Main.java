package PROJECTS.STUDENT.MANAGEMENT.SYSTEM;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;


public class Main {
    static Scanner scanner = new Scanner(System.in);
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
                    System.out.println("Invalid grade. Please enter A, B, C, D or F.");
                    i--;
                    break;
            }
        }
        return totalPoint / numberOfCourse;
    }
    public static void main(String[] args) {
        DatabaseConnection.getConnection();

        ArrayList<Student_Management> students = new ArrayList<>();
        int options;
        do{
            System.out.println("=========== STUDENTS MANAGEMENT SYSTEM ===========");
            System.out.println("1. Add Students. ");
            System.out.println("2. View Students. ");
            System.out.println("3. Update Students Information. ");
            System.out.println("4. Delete Student Details. ");
            System.out.println("5. Search Student.");
            System.out.println("6. Sort students by Programs");
            System.out.println("7. Calculate Student's Grade Point Average (GPA). ");
            System.out.println("8. Exit.");
            System.out.println("=============================================");
            System.out.print("Select Option to Start : ");
            options = scanner.nextInt();
            scanner.nextLine();

            switch (options){
                case 1: // Adding Students
                    System.out.println("==== Adding New Student Details To The School ===");
                    String studentID;
                    while (true) {
                        System.out.print("Enter Students ID : ");
                        studentID = scanner.nextLine();
                        boolean idExists = false;
                        for (Student_Management student : students) {
                            if (student.getID().equalsIgnoreCase(studentID)) {
                                idExists = true;
                                break;
                            }
                        }
                        if (idExists) {
                            System.out.println("This Student ID already exists. Enter a different ID.");
                        } else {
                            break;
                        }
                    }

                    System.out.print("Enter Students full Name : ");
                    String fullName = scanner.nextLine();

                    System.out.print("Enter Program to read : ");
                    String courseRead = scanner.nextLine();

                    System.out.print("Enter Level : ");
                    int firstYear = scanner.nextInt();
                    scanner.nextLine();
                    Student_Management freshers = new Student_Management(studentID, fullName, courseRead,firstYear);
                    students.add(freshers);
                    System.out.println("Student added Successfully.");
                    break;

                case 2: // View Students
                    if(students.isEmpty()){
                        System.out.println("No Student In the System Yet, Start by Adding Student. Thanks");
                    }else {
                        System.out.println("\n ==================================  ALL  STUDENTS  ============================");
                        System.out.printf(
                                "%-5s %-10s %-20s %-25s %-10s %-10s%n",
                                "No", "ID", "STUDENT NAME", "PROGRAM", "LEVEL", "GPA"
                        );                        System.out.println("----------------------------------------------------------------------------------");
                        int number = 1;
                        for (Student_Management student : students){
                            System.out.printf(
                                    "%-5d %-10s %-20s %-25s %-10d %-10.2f%n",
                                    number,
                                    student.getID(),
                                    student.getName(),
                                    student.getProgram(),
                                    student.getLevel(),
                                    student.getGpa()
                            );
                            number++;
                        }
                        System.out.println("=================================================================================");
                    }break;

                case 3: // Updating Student Inform
                    System.out.print("Enter Students ID to Update : ");
                    String updateID = scanner.nextLine();

                    boolean found = false;
                    for(Student_Management student:students){
                        if(student.getID().equalsIgnoreCase(updateID)){
                            System.out.println("Student Found.");
                            found = true;

                            System.out.print("Enter new level : ");
                            int updateLevel = scanner.nextInt();
                            scanner.nextLine();
                            student.setLevel(updateLevel);
                            System.out.println("Student information updated successfully.");
                            break;
                        }
                    }if(!found){
                    System.out.println("Student not found!");
                    }break;

                case 4: // Deleting Students Details
                    System.out.print("Enters Student ID to delete :");
                    String deleteID = scanner.nextLine();

                    boolean delID = false;
                    for (int i = 0; i < students.size(); i++) {
                        if (students.get(i).getID().equalsIgnoreCase(deleteID)) {
                            students.remove(i);
                            delID = true;
                            System.out.println("Student removed successfully.");
                            break;
                        }
                    }if(!delID){
                    System.out.println("Student Does not exist.");
                    }break;

                case 5: // Searching A Student
                    System.out.print("Enter ID to view Students Details :");
                    String searchStudent = scanner.nextLine();

                    boolean idFound = false;
                    for (Student_Management student : students){
                        if(student.getID().equalsIgnoreCase(searchStudent)){
                            System.out.println("Student Found.");
                            System.out.println("---------------------------");
                            student.displayInformation();
                            idFound = true;
                            break;
                        }
                    }if(!idFound){
                    System.out.println("Student of this ID those not exists.");
                    }break;

                case 6: // Sorting by Programs
                    if(students.isEmpty()){
                        System.out.println("No student Registered Yet, Try by Adding Students");
                    }else {
                        students.sort(Comparator.comparing(Student_Management :: getProgram));
                        System.out.println("Students Sorted by Program Successfully.");
                    }break;

                case 7:  //Calculate GPA
                    System.out.print("Enter Student ID :");
                    String ID = scanner.nextLine();
                    boolean id_found = false;
                    for(Student_Management student : students){
                        if(student.getID().equalsIgnoreCase(ID)){
                            System.out.println("Student Found");
                            double gpa = calculateGPA();
                            student.setGpa(gpa);
                            System.out.println("GPA: " + gpa);
                            id_found = true;
                        }
                    }if(!id_found){
                    System.out.println("Student Does not Exist.");
                    }break;


                case 8:  //Logging Out
                    System.out.println("Thanks For Using Student Management System.");
                    System.out.println("Logging Out......");
                    break;

                default:
                    System.out.println("Invalid Option, try Again.");
                    break;
            }
        }while (options != 8);
    }
}
