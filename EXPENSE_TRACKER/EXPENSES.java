package PROJECTS.EXPENSE_TRACKER;

import java.time.LocalDate;

public class EXPENSES {
    private final String name;
    private final double amount;
    private final String category;
    private final LocalDate date;

    public EXPENSES(String name, double amount, String category, LocalDate date ){
        this.name = name;
        this.amount = amount;
        this.category = category;
        this.date = date;
    }
    public void displayExpense(){
        System.out.println("Expense Name : " + name);
        System.out.println("Expense Amount : "+amount);
        System.out.println("Category : "+category);
        System.out.println("Date : "+date);
    }

    //Getters
    public String getName(){ return  name;}
    public double getAmount(){return amount; }
    public String getCategory(){return category; }
    public LocalDate getDate(){return date;}

}
