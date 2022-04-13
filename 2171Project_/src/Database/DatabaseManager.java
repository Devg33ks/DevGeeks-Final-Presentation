package Database;

import EmployeeManagement.*;
import java.io.*;
import java.util.ArrayList;

public class DatabaseManager {
    private String rFile = "Rooms.txt";
    private String employeeFile = "Databasefile.txt";

    public DatabaseManager() {
    }

    public static ArrayList<String> getEmployeeList() {
        String readerline;
        ArrayList<String> employeelist = new ArrayList<String>();

        try {
            FileReader fr = new FileReader("Databasefile.txt");
            BufferedReader br = new BufferedReader(fr);

            while ((readerline = br.readLine()) != null) {

                if (readerline != null) {
                    employeelist.add(readerline);
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (employeelist);
    }

    public static void addToDatabase(String employeerecord) {
        try {
            ArrayList<String> emplist = new ArrayList<String>();
            emplist = getEmployeeList();
            System.out.println(emplist);
            BufferedWriter bw = new BufferedWriter(new FileWriter("Databasefile.txt"));
            for (String record : emplist) {
                // System.out.println(record);
                bw.write(record + "\n");
            }
            bw.write(employeerecord);
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Used only in report class to assist in file editting
    public static void updateDatabase(ArrayList<String> employeelist) {
        try {
            ArrayList<String> emplist = new ArrayList<String>();
            System.out.println(emplist);
            BufferedWriter bw = new BufferedWriter(new FileWriter("Databasefile.txt"));
            for (String record : employeelist) {
                // System.out.println(record);
                bw.write(record + "\n");
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //Deletes a record and rewrites file
    public static void deleteRecord(String record) {
        ArrayList<String> employeeList = new ArrayList<String>();
        employeeList = getEmployeeList();
        employeeList.remove(record);
        updateDatabase(employeeList);
    }

    public static ArrayList<String> getRoomListing() {
        String readerline;
        ArrayList<String> roomlist = new ArrayList<String>();

        try {
            FileReader fr = new FileReader("Rooms.txt");
            BufferedReader br = new BufferedReader(fr);

            while ((readerline = br.readLine()) != null) {

                if (readerline != null) {
                    roomlist.add(readerline);
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (roomlist);
    }

    public static int logMaintenanceInfo(int roomNumber, String info) throws IOException {
        //FileWriter myWriter = null;
        try {
            if ((roomNumber == 0) ) {
                System.out.println("Please enter valid room number");
            } else {
                FileWriter myWriter = new FileWriter("Maintenance.txt", true);
                myWriter.write("Room" + roomNumber + ":" + info);
                myWriter.write("\r\n");
                myWriter.close();
            }
        } catch (IOException e) {
            System.out.println("An error has occured");
        }
        return roomNumber;
    }


    public static String changeroomstatus(String rfile, int roomNumber, String status) throws IOException {

        File f1 = new File(rfile); //Creation of File Descriptor for input file
        String[] words = null;  //Initialize the word Array
        FileReader fr = new FileReader(f1);  //Creation of File Reader object
        BufferedReader br = new BufferedReader(fr); //Creation of BufferedReader object
        FileWriter writer = null;
        String s;
        String id;
        String oldLine = "";
        String newLine= "";
        int num;
        int roomnum;
        int count = 0;   //Intialize the word to zero
        if (roomNumber != 0) {
            try {
                while ((s = br.readLine()) != null) //Reading Content from the file
                {
                    num = 0;
                    System.out.println(s);
                    String line[] = s.split(" ");
                    roomnum = Integer.parseInt(line[0]);
                    Integer.toString(roomNumber);

                    if (roomNumber == roomnum) {

                        count++;
                        id = line[0];
                        
                        if (s.contains("Available")) {
                            //System.out.println("HIIIIIIIIIIIIIIII");
                            newLine = s.replace("Available", status);
                        } else if (s.contains("N/A")){
                            System.out.println("HIIIIIIIIIIIIIIII");
                            newLine = s.replace("N/A", status);
                        } else if (s.contains("Closed")){
                            newLine = s.replace("Closed", status);
                        }
                        oldLine = oldLine + newLine + System.lineSeparator();
                        //System.out.println(newLine);

                        writer = new FileWriter(f1);
                        writer.write(newLine);
                    } else {
                        oldLine = oldLine + s + System.lineSeparator();
                        writer = new FileWriter(f1);
                        writer.write(oldLine);
                        num++;
                        //System.out.println("HIIIIIIIIIIIIIIII");
                    }
                }
            } catch (IOException ex) {
                System.out.println("File not found");
            } finally {
                fr.close();
                writer.close();
            }
            //System.out.println("Room "+ roomNumber+" closed for maintenance");
            //System.out.println("Room number invalid");
        }
        //if (num > 0) {
        //System.out.println("Room Number is invalidd");}
        String oldroomNumber = Integer.toString(roomNumber);
        return oldroomNumber;
    }


    public static void changeRoom(String efile, String newroom, String oldroomNumber) throws IOException {
        File f1 = new File(efile); //Creation of File Descriptor for input file
        String[] words = null;  //Initialize the word Array
        FileReader fr = new FileReader(f1);  //Creation of File Reader object
        BufferedReader br = new BufferedReader(fr); //Creation of BufferedReader object
        FileWriter writer = null;
        String s;
        String id;
        String oldLine = "";
        String newLine = "";

        // String input="Java]";   // Input word to be searched
        int count = 0;   //Intialize the word to zero

            try {
                while ((s = br.readLine()) != null) //Reading Content from the file
                {
                    //System.out.println(s);
                    String line[] = s.split(" ");

                    if (s.contains(oldroomNumber)) {

                        String roomtype = line[4];
                        System.out.println(roomtype);

                        String rum = Room.AssignRooms("Rooms.txt", roomtype);
                        System.out.println(rum);
                        newLine = s.replace(oldroomNumber,rum);

                        oldLine = oldLine + newLine + System.lineSeparator();
                        System.out.println(newLine);
                        writer = new FileWriter(f1);
                        writer.write(newLine);
                    } else {
                        //System.out.println("Cant find room number");
                        oldLine = oldLine + s + System.lineSeparator();
                        writer = new FileWriter(f1);
                        writer.write(oldLine);
                    }
                }
            } catch (IOException ex) {
                System.out.println("File not found");
                ;
            } finally {
                fr.close();
                writer.close();

            }

    }


}


