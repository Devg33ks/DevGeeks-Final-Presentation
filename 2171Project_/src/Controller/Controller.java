package Controller;
import Database.*;
import EmployeeManagement.*;
import Report.*;
import UI.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Controller {
    private String name;
    private String balance;
    private Double bal;
    private String id;
    private boolean check;

    public Controller(){
    }

    public void addEmployee(String name, String balance, String id, String roomType,boolean check,boolean check2){
        try{
            bal = Double.parseDouble(balance);
        }catch(NumberFormatException  e){
            ErrorHandling er = new ErrorHandling("Enter an amount as balance","Invalid Entry");
            er.setVisible(true);
            e.printStackTrace();
        }


        if (name.split(" ").length != 2){
            ErrorHandling er = new ErrorHandling("Enter an a valid name: 'firstnam lastname'","Invalid Entry");
            er.setVisible(true);
        }else{
            //}
            if (((check == true)) || (check2 == true)){
                //now creating emplyee and calculation length of stay
                //instead of creating an object we wouldve created GuI class obj and called on function
                Employee employee = new Employee(id,name,roomType,bal);
                employee.makePayment();
                /*try{
                    employee.setRoomNum(Room.TrackAvaliableRooms("Rooms.txt", roomType));
                }catch(IOException e){
                    e.printStackTrace();
                }*/

                if (employee.getRoomNum()!=""){
                    employee.save();}
                else{
                    ErrorHandling er = new ErrorHandling("Room of this type is not available","Invalid Entry");
                    er.setVisible(true);
                }

                //creating conformation screen , with record to be saved to file
                //instead of creating an object we wouldve created GuI class obj and called on function
                //AddEmployeeScreen.setVisible(false);
            }else{
                ErrorHandling er = new ErrorHandling("Please select an Room Type","Invalid Entry");
                er.setVisible(true);}
        }
    }

    public void editEmployee(String newname,String balance,String roomtype,String roomnum,String id,String employeeRecord,boolean keep){
        String oldbalance= employeeRecord.split(" ")[3];
        String lastpayment= employeeRecord.split(" ")[6];
        String duedate= employeeRecord.split(" ")[7];
        if (newname.split(" ").length == 2){
            
            try{

                //Double newBalance = Double.parseDouble(jTextField1.getText());
                if ((roomtype.equals("D")) || (roomtype.equals("K"))){
                    DatabaseManager database = new DatabaseManager();
                    database.deleteRecord(employeeRecord);
                    Employee employee =new Employee();
                    //employeeRecord.split(" ")[0],jTextField4.getText(), jTextField2.getText(),newBalance
                    employee.setId(id);
                    employee.setName(newname);
                    employee.setRoomType(roomtype);
                    System.out.println(employee.getBalance());
                    employee.setBalance(Double.parseDouble(balance));
                    employee.setStartDate(lastpayment);
                    employee.setEndDate(duedate);
                    if (!balance.equals(oldbalance)){
                        employee.makePayment();
                    }
                    if (keep == true){
                        employee.setRoomNum(roomnum);
                    }else{
                        try{
                            employee.setRoomNum(Room.AssignRooms("Rooms.txt",roomtype));

                        }catch(IOException e){
                            e.printStackTrace();
                        }
                    }
                    employee.save();

                }else{
                    ErrorHandling er = new ErrorHandling("Room type Invalid. Enter either 'D' or'K'","Invalid Entry");
                    er.setVisible(true);
                }
            }catch(NumberFormatException  e){
                ErrorHandling er = new ErrorHandling("Enter an amount as balance","Invalid Entry");
                er.setVisible(true);
                e.printStackTrace();
            }
            //this.setVisible(false);
        }else{
            ErrorHandling er = new ErrorHandling("Enter an a valid name: 'firstnam lastname'","Invalid Entry");
            er.setVisible(true);
        }

    }
    public void deleteEmployee(String employeeRecord){
        DatabaseManager database = new DatabaseManager();
        database.deleteRecord(employeeRecord);
    }


    public ArrayList<String> mainDisplay(){
        DatabaseManager database = new DatabaseManager();
        return database.getEmployeeList();

    }
    public String searchEmployee(String id){

        Report report = new Report();
        return report.searchEmployee(id);
    }
    public ArrayList<String>  roomDisplay(){
        DatabaseManager database = new DatabaseManager();
        return database.getRoomListing();

    }

    public void roomOverride(String roomnum, String comment, boolean check1, boolean check2){
        Room room = new Room();

        DatabaseManager over = new DatabaseManager();
        if (check1 == true ){
            try{
                over.logMaintenanceInfo(Integer.parseInt(roomnum), comment);
                over.changeroomstatus("Rooms.txt", Integer.parseInt(roomnum),"Closed");
                over.changeRoom("Databasefile.txt", "0", roomnum);
            }catch(IOException e){
                e.printStackTrace();
            }

        }else if(check2 == true ){
            try{
                System.out.println("rdio");
                for (String record : DatabaseManager.getEmployeeList()){
                    if (record.split(" ")[5].equals(roomnum)){
                        over.changeRoom("Databasefile.txt", "0", roomnum);
                    }
                }
                over.changeroomstatus("Rooms.txt", Integer.parseInt(roomnum),"Available");

            }catch(IOException e){
                e.printStackTrace();
            }
        }else{
            ErrorHandling er = new ErrorHandling("Please select an action","Invalid Entry");
            er.setVisible(true);
        }


    }

    public String rentCollection(String name, String roomType, String roomNum, String employeeRecord,double newBalance){
        Report report = new Report();
        String lastpayment= employeeRecord.split(" ")[6];
        String duedate= employeeRecord.split(" ")[7];
        DatabaseManager data = new DatabaseManager();
        data.deleteRecord(employeeRecord);
        Employee employee = new Employee();
        employee.setId(employeeRecord.split(" ")[0]);
        employee.setName(name);
        employee.setRoomType(roomType);
        employee.setBalance(newBalance);
        employee.setStartDate(lastpayment);
        employee.setEndDate(duedate);
        employee.setRoomNum(roomNum);
        employee.makePayment();
        employee.save();
        System.out.println(employee.getName());
        employeeRecord = report.searchEmployee(employee.getID());
        return employeeRecord;
    }

    public ArrayList<String> getNotif(){
        Notification notify = new Notification();
        ArrayList<String> notification = new ArrayList<String>();
        try {
            notification=notify.UpdateManager();
        }catch (IOException e){

        }
        return notification;
    }
    public  static ArrayList<String> getRecovery(){
        String readerline ;
        ArrayList<String> ans = new ArrayList<String>();

        try{
            FileReader fr = new FileReader("Pass2.txt");
            BufferedReader br = new BufferedReader(fr);

            while( (readerline=br.readLine()) != null ){
                System.out.println(readerline);
                if(readerline != null){
                    ans.add(readerline);
                }
            }
            br.close();
        }catch(IOException e){
            e.printStackTrace();
        }
        return(ans);
    }
    public  static String getPass(){
        String readerline ;
        String pass = " ";

        try{
            FileReader fr = new FileReader("Password.txt");
            BufferedReader br = new BufferedReader(fr);

            while( (readerline=br.readLine()) != null ){
                System.out.println(readerline);
                if(readerline != null){
                    pass =readerline;
                }
            }
            br.close();
        }catch(IOException e){
            e.printStackTrace();
        }


        return(pass);
    }
}


