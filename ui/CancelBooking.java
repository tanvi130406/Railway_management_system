package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import db.CancelBookingDAO;

public class CancelBooking extends JFrame implements ActionListener {

    JTextField bookingIdField;
    JButton cancelBtn;

    public CancelBooking() {

        setTitle("Cancel Booking");
        setLayout(new FlowLayout());

        add(new JLabel("Enter Booking ID:"));
        bookingIdField = new JTextField(10);
        add(bookingIdField);

        cancelBtn = new JButton("Cancel Ticket");
        add(cancelBtn);

        cancelBtn.addActionListener(this);

        setSize(300, 200);
        setLocationRelativeTo(null); 
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {

        try {
            String bookingText = bookingIdField.getText();

            
            if (bookingText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter Booking ID!");
                return;
            }

            
            int bookingId = Integer.parseInt(bookingText);

            
            CancelBookingDAO.cancelBooking(bookingId);

            
            JOptionPane.showMessageDialog(this, "Ticket Cancelled Successfully!");

        } catch (NumberFormatException ex) {//handles invalid booking id 
            JOptionPane.showMessageDialog(this, "Enter valid numeric Booking ID!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            ex.printStackTrace(); 
        }
    }

    public static void main(String[] args) {
        new CancelBooking();
    }
}