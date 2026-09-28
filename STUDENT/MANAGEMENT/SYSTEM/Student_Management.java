package PROJECTS.STUDENT.MANAGEMENT.SYSTEM;

public class Student_Management {
    private String ID;
    private String name;
    private String program;
    private int level;
    private double gpa;
    //Constructor
    public Student_Management(String id,String name, String program, int level ){
        this.ID = id;
        this.name = name;
        this.program = program;
        this.level = level;
    }
    // Getters
    public String getID(){return ID;}
    public String getName() {return name;}
    public String getProgram(){return program;}
    public int getLevel() {return level;}
    public double getGpa(){return gpa;}
    //Method
    public void displayInformation(){
        System.out.println(" Student ID : "+ getID());
        System.out.println(" Student Name : "+ getName());
        System.out.println(" Student Program : "+getProgram());
        System.out.println(" Student Level : "+getLevel());
    }

    public void setLevel(int level) {
        this.level = level;
    }
    public void setGpa(double gpa){
        this.gpa = gpa;
    }


}
