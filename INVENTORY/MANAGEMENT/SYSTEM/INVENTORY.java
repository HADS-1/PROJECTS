package PROJECTS.INVENTORY.MANAGEMENT.SYSTEM;

public class INVENTORY {
    //Fields
    private int ID;
    private String name;
    private double price;
    private int quantity;

    //Constructor
    public INVENTORY(int ID, String name, double price, int quantity){
        this.ID = ID;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    //Getters
    public int getID(){return ID;}
    public String getName(){return name;}
    public double getPrice(){return price;}
    public int getQuantity(){return quantity;}

    // Setters
    public void setID(int ID) {this.ID = ID;}
    public void setName(String name) {this.name = name;}
    public void setPrice(double price) {this.price = price;}
    public void setQuantity(int quantity) {this.quantity = quantity;}

    //Method
    public void displayInventory(){
        System.out.println("Product ID : " +ID);
        System.out.println("Product Name : " + name);
        System.out.println("Product Price : " +price);
        System.out.println("Quantity : "+quantity);
    }
}
