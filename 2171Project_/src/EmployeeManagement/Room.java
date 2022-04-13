package EmployeeManagement;

import Database.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class Room{
    private String roomNumber;
    private String roomType;

    public Room(){}

    public Room(String roomType){
        this.roomType = roomType;
        try{
            this.roomNumber = AssignRooms("Rooms.txt",this.roomType);
        } catch (IOException ex){
            System.out.println("File not found");
        }

    }

    public String getRoomNumber(){
        return roomNumber;
    }

    public void setRoomNumber(int roomNumumber){
        this.roomNumber = roomNumber;
    }

    public String getRoomType(){
        return roomType;
    }

    public void setRoomType(){
        this.roomType = roomType;
    }




    public static String AssignRooms(String rfile,String roomType) throws IOException {
        String status;
        String type;
        int num;
        DatabaseManager data = new DatabaseManager();
        ArrayList<String> rooms = new ArrayList<String>();
        rooms = data.getRoomListing();
        for (String room : rooms) {
            status = room.split(" ")[1];
            type = room.split(" ")[2];
            if ((status.equals("Available")) && type.equals(roomType)) {
                num = Integer.parseInt(room.split(" ")[0]);
                data.changeroomstatus(rfile, num, "N/A");
                return Integer.toString(num);
            }
        }
        return "";
    }





    public static void main(String[] args) throws IOException{
        AssignRooms("Rooms.txt","K");
    }


}
