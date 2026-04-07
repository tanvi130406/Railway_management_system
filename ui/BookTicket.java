package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import db.DBConnection;
import db.BookingDAO;
import db.Bookable;
import db.PremiumBookingDAO;

public class BookTicket extends JFrame implements ActionListener {

    JLabel title, trainLabel, countLabel, coachLabel;
    JTextField countField;
    JComboBox<String> coachBox;
    JButton book;

    int selectedTrainId;
    String journeyDate; 

    
    public BookTicket(int trainId, String date) {
        this.selectedTrainId = trainId;
        this.journeyDate = date;

        setTitle("Book Ticket");
        setLayout(null);

        title = new JLabel("Book Ticket");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setBounds(170, 30, 200, 40);

        trainLabel = new JLabel("Train ID: " + selectedTrainId + " | Date: " + journeyDate);
        trainLabel.setBounds(130, 80, 300, 30);

        countLabel = new JLabel("No. of Passengers:");
        countLabel.setBounds(100, 140, 150, 30);

        countField = new JTextField();
        countField.setBounds(250, 140, 120, 30);

        coachLabel = new JLabel("Coach:");
        coachLabel.setBounds(100, 190, 100, 30);

        String coaches[] = {"1AC", "2AC", "3AC", "Sleeper"};
        coachBox = new JComboBox<>(coaches);
        coachBox.setBounds(250, 190, 120, 30);

        book = new JButton("Book Ticket");
        book.setBounds(180, 260, 150, 40);

        book.addActionListener(this);

        add(title);
        add(trainLabel);
        add(countLabel);
        add(countField);
        add(coachLabel);
        add(coachBox);
        add(book);

        setSize(500, 380);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {

        try {
            String countText = countField.getText();

            if (countText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Enter number of passengers!");
                return;
            }

            int count = Integer.parseInt(countText);
            String coach = (String) coachBox.getSelectedItem();

            Connection conn = DBConnection.getConnection();

            if (conn == null) {
                JOptionPane.showMessageDialog(this, "Database connection failed!");
                return;
            }

            String finalPNR = "";

            for (int i = 1; i <= count; i++) {

                String name = JOptionPane.showInputDialog(this, "Enter name for Passenger " + i);
                String ageStr = JOptionPane.showInputDialog(this, "Enter age for Passenger " + i);
                String gender = JOptionPane.showInputDialog(this, "Enter gender (Male/Female/Other)");

                if (name == null || ageStr == null || gender == null ||
                        name.isEmpty() || ageStr.isEmpty() || gender.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Invalid input! Booking stopped.");
                    return;
                }

                if (!name.matches("[a-zA-Z ]+")) {
                    JOptionPane.showMessageDialog(this, "Enter valid name!");
                    return;
                }

                int age = Integer.parseInt(ageStr);

                //  POLYMORPHISM
                Bookable dao;

                if (age >= 60) {
                    dao = new PremiumBookingDAO();
                    JOptionPane.showMessageDialog(this, "Senior Citizen → Lower Berth Preferred");
                } else {
                    dao = new BookingDAO();
                }

                // Insert Passenger
                String query = "INSERT INTO Passenger(name, age, gender) VALUES (?, ?, ?)";
                PreparedStatement pst = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

                pst.setString(1, name);
                pst.setInt(2, age);
                pst.setString(3, gender);

                pst.executeUpdate();

                ResultSet rs = pst.getGeneratedKeys();
                int passengerId = 0;

                if (rs.next()) {
                    passengerId = rs.getInt(1);
                }

                
                String pnr = dao.bookTicket(passengerId, selectedTrainId, coach, journeyDate);

                finalPNR = pnr;
            }

            JOptionPane.showMessageDialog(this,
                    "Tickets Booked Successfully!\nPNR: " + finalPNR);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Enter valid number!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}