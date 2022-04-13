package Report;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import Database.DatabaseManager;

public class Notification {
    private LocalDateTime today;

    public Notification() {

    }

    public ArrayList<String> UpdateManager() throws IOException {
        String name="";

        ArrayList<String > due = new ArrayList<String>();


        String duedate = "";
        int count = 0;
        LocalDateTime now = LocalDateTime.now();
        String currdate = LocalDateTime.now().toString();
        LocalDateTime tommorrow = LocalDateTime.now().plusDays(1);
        Double bal= Double.parseDouble("123");
        for (String record : DatabaseManager.getEmployeeList()){
            String line[] = record.split(" ");
            if (line[7] == currdate) {
                count++;
                name = line[0].concat(" ").concat(line[1]);
                duedate = "Rent for" + name + "is due today" + currdate;
                due.add(duedate);

            } else if (LocalDateTime.parse(line[7]).isAfter(now) && LocalDateTime.parse(line[7]).isBefore(tommorrow)) {
                count++;
                name = line[0].concat(" ").concat(line[1]);
                String date = LocalDateTime.parse(line[7]).toString();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd   HH:mm");
                String empdate = LocalDateTime.parse(line[7]).format(formatter);
                duedate = "Rent for " + name + " is due " + empdate;
                due.add(duedate);
            }

        }
        if (count == 0) {
            System.out.println("no rent due");
        }
        /*}

        try {
            while ((s = br.readLine()) != null) {
                String line[] = s.split(" ");
                if (line[7] == currdate) {
                    count++;
                    name = line[0].concat(" ").concat(line[1]);
                    duedate = "Rent for" + name + "is due today" + currdate;
                    due.add(duedate);

                } else if (LocalDateTime.parse(line[7]).isAfter(now) && LocalDateTime.parse(line[7]).isBefore(tommorrow)) {
                    count++;
                    name = line[0].concat(" ").concat(line[1]);
                    String date = LocalDateTime.parse(line[7]).toString();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd   HH:mm");
                    String empdate = LocalDateTime.parse(line[7]).format(formatter);
                    duedate = "Rent for " + name + " is due " + empdate;
                    due.add(duedate);
                }

            }
            if (count == 0) {
                System.out.println("no rent due");
            }

        } catch (IOException ex) {
            System.out.println("File not found");
        }*/
        return due;
    }
}

