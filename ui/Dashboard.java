package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Dashboard extends JFrame implements ActionListener {

    JLabel title;
    JButton searchTrain, myBooking, exit;
    JPanel panel;

    Dashboard(String username) {

        setTitle("Mini IRCTC Dashboard");

        panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(240,245,255));

        // Title
        title = new JLabel("Welcome to MINI IRCTC");
        title.setFont(new Font("Arial", Font.BOLD, 26));
        title.setBounds(120,40,350,40);

        // Buttons
        searchTrain = new JButton("Search Trains");
        searchTrain.setBounds(170,120,160,40);

        myBooking = new JButton("My Bookings");
        myBooking.setBounds(170,200,160,40);

        exit = new JButton("Exit");
        exit.setBounds(170,280,160,40);

        // Add listeners
        searchTrain.addActionListener(this);
        myBooking.addActionListener(this);
        exit.addActionListener(this);

        panel.add(title);
        panel.add(searchTrain);
        panel.add(myBooking);
        panel.add(exit);

        add(panel);

        setSize(500,420);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {

        if(e.getSource()==searchTrain) {
            new SearchTrain();
        }

        if(e.getSource()==myBooking) {
            new ViewBookings(); 
        }

        if(e.getSource()==exit) {
            System.exit(0);
        }
    }
}