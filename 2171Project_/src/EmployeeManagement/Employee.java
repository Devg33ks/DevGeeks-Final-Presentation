package EmployeeManagement;


import java.util.*;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import Database.DatabaseManager;

public class Employee {
    private String id;
    private String name;
    private String roomType;
    private String roomNum="";
    private String currentDatetxt;
    private double balance;
    private double bal;
    private Room room;
    private LocalDateTime rentDue;
    private String endDate;
    private int lengthStay;
    final static double kingroom = 24;

    final static double doubleroom = 23;

    public Employee(){
    }
    public Employee(String id,String name, String roomType, double balance) {
        this.room= new Room(roomType);
        this.id = id;
        this.name = name;
        this.roomType = room.getRoomType();
        this.roomNum = room.getRoomNumber();
        this.balance = balance;
        final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MMMM yyyy  HH:mm:ss", Locale.ENGLISH);
        LocalDateTime now = LocalDateTime.now();
        currentDatetxt = now.toString();
    }

    public double getBalance() {
        return balance;

    }

    public String getName(){
        return name;
    }

    public String getRoomType() {
        return roomType;//;
    }

    public String getRoomNum(){
        return roomNum;

    }

    public String getStartDate(){
        return currentDatetxt;
    }

    public String getID(){
        return id;
    }

    public String getEndDate(){ return endDate;}

    public int getLenghtStay() {
        return lengthStay;
    }

    public void setEndDate(String date){ this.endDate=date; }

    public void setId(String id){
        this.id = id;
    }

    public void setName(String name){ this.name =name;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setRoom(Room room){
        this.room = room;
    }

    public void setRoomNum(String num){
        this.roomNum = num;
    }
    public void setRoomType(String type){
        this.roomType = type;
    }
    public void setStartDate(String date){ this.currentDatetxt= date;}
    public String toString() {
        return(getID()+" "+ getName()+" "+ getBalance()+" "+getRoomType()+" "+getRoomNum()+" "+getStartDate()+" "+getEndDate());
    }


    public void makePayment() {




        if (roomType.equals("K")) {
            double length = balance / kingroom;
            lengthStay = (int) length;
            double remainingBal = balance - (lengthStay * kingroom) ;
            balance = remainingBal;
            System.out.println("Length of stay is: " + length + "days");
            System.out.println("Remaining Balance is: $" + remainingBal);
            LocalDateTime date = LocalDateTime.now();
            rentDue = date.plusDays(lengthStay);
            endDate = rentDue.toString();
            System.out.println("Rent for this employee is due: " + rentDue);


        } else if (roomType.equals("D")) {
            double length = balance / doubleroom;
            lengthStay = (int) length;
            double remainingBal = balance - (lengthStay * doubleroom) ;
            balance = remainingBal;
            System.out.println("Length of stay is : " + lengthStay + "days");
            System.out.println("Remaining Balance is: $" + remainingBal);
            LocalDateTime date = LocalDateTime.now();
            rentDue = date.plusDays(lengthStay);
            endDate = rentDue.toString();
            System.out.println("Rent for this employee is due: " + rentDue);

        } else {
            System.out.println("Invalid Room Type Entered.");// ref to gui and end process
        }


    }



    public void save(){

        DatabaseManager database= new DatabaseManager();
        database.addToDatabase(this.toString());


        // create reprt and send name, balance, room type and room number as an array or string
    }

}

