package UI;

import Controller.Controller;

import java.io.IOException;
import java.util.*;

import javax.swing.*;
import java.awt.Dimension;
import java.util.Scanner;
import java.util.*;
import javax.swing.table.DefaultTableModel;
import java.io.*;  
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Font;

public class mainScreen extends JFrame
{
    private JPanel sidePanel;
    private ArrayList<String> employeeList;
    private Font font = new Font("Times New Roman", Font.BOLD, 15); 
    private Object[] row;
    private DefaultTableModel model = new DefaultTableModel();   
    private JScrollPane pane;
    private JTable table;
    private JPanel headingPanel;
    private JPanel welcomePanel;  

    private JButton addButton;
    private JButton editButton;
    private JButton manageRoom;
    private JButton collectPayment;
    private JButton notification;
    JFrame frame = new JFrame();

    private JButton exitButton;  
  
    public mainScreen() {

        //initComponents();
        //Report report = new Report();
        Controller con= new Controller();

        employeeList = con.mainDisplay();

        String[] columnNames = {"ID", "Guest Name", "Balance","Room Type","Room Number", "Last Payment", "Next Payment"};
    
        table = new JTable();//creating the table
        table.setModel(model);
        table.setForeground(Color.decode("#000"));
        table.setBackground(Color.WHITE);
      

        JLabel background;
        ImageIcon img = new ImageIcon("Logo.png");

        background = new JLabel("",img, JLabel.CENTER);

        background.setBounds(10,320, 300, 300);

        model=new javax.swing.table.DefaultTableModel(columnNames,0);
        table.setModel(model);
        showTable(employeeList);

        add(background);


        //panel that has the heading of the database
        headingPanel = new JPanel();
        headingPanel.setBounds(250, 6, 550, 35);
        headingPanel.setBackground(Color.decode("#c8ebf4"));

        JLabel heading = new JLabel("GUEST LISTING");
        heading.setBounds(60, 60, 500, 120);
        heading.setFont(font);
        heading.setForeground(Color.decode("#8B4513")); 


        headingPanel.add(heading);//adds heading 


        //panel that stores the welcome logo
        welcomePanel = new JPanel();
        welcomePanel.setBounds(5,50, 300, 40);
        welcomePanel.setBackground(Color.decode("#8B4513"));

        //welcome lable customisation
        JLabel welcomeLabel = new JLabel("WELCOME TO QUALITY INN HOTEL");
        welcomeLabel.setBounds(1, 0, 100, 35);
        welcomeLabel.setFont(font);
        welcomeLabel.setForeground(Color.white);


        //panel that stores the buttons associated with different functions
        sidePanel = new JPanel();
        sidePanel.setBounds(5, 80, 300, 180);
        sidePanel.setBackground(Color.decode("#60f566"));

        
        welcomePanel.add(welcomeLabel);//adding the label to the welcome panel

        //customization of the frame
        setTitle("QUALITY INN AND SUITES HOTEL");
        setSize(1100, 600);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.decode("#c8ebf4"));

        //customisation of the table
        model.setColumnIdentifiers(columnNames);
        table.setModel(model);
        table.setFont(font);
        table.setForeground(Color.decode("#24a0d2"));
        table.setBackground(Color.WHITE);
      

        //adding scroll panel and its customization
        pane = new JScrollPane(table);
        pane.setFont(font);
        pane.setForeground(Color.white);
        pane.setBackground(Color.white);
        pane.setBounds(320, 50, 700, 480);

        //buttons
        exitButton = new JButton("EXIT");
        exitButton.setBackground(Color.WHITE);
        exitButton.setForeground(Color.decode("#24a0d2")); //#2fb7ee
        exitButton.setFont(font);
        exitButton.setBounds(10, 20, 400, 55);
        exitButton.addActionListener(new ExitButtonListener());

        editButton = new JButton("EDIT EMPLOYEE");
        editButton.setBackground(Color.WHITE);;
        editButton.setForeground(Color.decode("#24a0d2"));
        editButton.setFont(font);
        editButton.setBounds(10, 100, 200, 20);
        editButton.addActionListener(new editButtonListener());

        addButton = new JButton("CHECK-IN GUEST");
        addButton.setBackground(Color.WHITE);;
        addButton.setForeground(Color.decode("#24a0d2"));
        addButton.setFont(font);
        addButton.setBounds(10, 143, 200, 20);
        addButton.addActionListener(new addButtonListener());


        manageRoom = new JButton("MANAGE ROOMS");
        manageRoom.setBackground(Color.WHITE);;
        manageRoom.setFont(font);
        manageRoom.setForeground(Color.decode("#24a0d2"));
        manageRoom.setBounds(10, 200, 180, 20);
        manageRoom.addActionListener(new ManageRoomButtonListener());

        collectPayment = new JButton("COLLECT PAYMENT");
        collectPayment.setBackground(Color.WHITE);
        collectPayment.setForeground(Color.decode("#24a0d2"));
        collectPayment.setFont(font);
        collectPayment.setBounds(10, 200, 200, 20);
        collectPayment.addActionListener(new PaymentButtonListener());

        notification = new JButton("NOTIFICATION");
        notification.setBackground(Color.WHITE);
        notification.setForeground(Color.decode("#24a0d2"));
        notification.setFont(font);
        notification.setBounds(10, 200, 200, 20);
        notification.addActionListener(new NotificationButtonListener());

    

        sidePanel.add(addButton);
        sidePanel.add(editButton);
        sidePanel.add(manageRoom);
        sidePanel.add(collectPayment);
        sidePanel.add(notification);
        sidePanel.add(exitButton);//adda home button to side panel
        

        add(sidePanel);//adds panel to the frame
        add(pane);//adds scrool-pane to the frame
        add(headingPanel); //adds heading panel to the frame
        add(welcomePanel); // adds welcome panel to the frame

        row = new Object[5];

     
        setVisible(true);
        
    }

            
    private void showTable(ArrayList<String> employeerecs){
        for (String rec:employeerecs)
        {addToTable(rec);}

    }

    public void addToTable(String rec){
        String[] record = rec.split(" ");
        String name = record[1] +" " + record[2];
    
        String[] item ={record[0],name , record[3], record [4], record[5], record[6], record[7]};
        model.addRow(item);
    }

    public void removeFromTable(String rec){
        employeeList.remove(rec);
        this.showTable(employeeList);
    }

    private class addButtonListener implements ActionListener{
            public void actionPerformed(ActionEvent event){
                AddEmployeeScreen next = new AddEmployeeScreen();
                next.setVisible(true);
                setVisible(false);
                
            }
    }
    //ManageRoomButtonListener
    private class ManageRoomButtonListener implements ActionListener{
            public void actionPerformed(ActionEvent event){
               RoomManagementScreen next = new RoomManagementScreen();
                next.setVisible(true);
                setVisible(false);
            }
    }

    private class editButtonListener implements ActionListener{
            public void actionPerformed(ActionEvent event){
                SearchScreen next = new SearchScreen("editButton");
                next.setVisible(true); 
                setVisible(false);             
            }
    }

      private class PaymentButtonListener implements ActionListener{
            public void actionPerformed(ActionEvent event){
                    SearchScreen next = new SearchScreen("CollectButton");
                    next.setVisible(true);
                    setVisible(false);            
            }
    }

    private class NotificationButtonListener implements ActionListener{
        public void actionPerformed(ActionEvent event){
            NotificationScreen next = new NotificationScreen();
                next.setVisible(true);       
        }
}

    private class ExitButtonListener implements ActionListener {
        
        public void actionPerformed(ActionEvent e)
            {
              System.exit(0);
            }

    }

   


}