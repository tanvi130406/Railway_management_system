package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import db.ViewBookingDAO;

public class ViewBookings extends JFrame implements ActionListener {

    JTextArea area;
    JButton loadBtn;

    public ViewBookings() {

        setTitle("View Bookings");
        setLayout(new FlowLayout());

        loadBtn = new JButton("Load Bookings");
        area = new JTextArea(15, 40);

        add(loadBtn);
        add(new JScrollPane(area));

        loadBtn.addActionListener(this);

        setSize(500, 400);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {

        ArrayList<String> list = ViewBookingDAO.getAllBookings();

        area.setText("");

        for (String s : list) {
            area.append(s + "\n");
        }

        if (list.size() == 0) {
            area.setText("No bookings found");
        }
    }

    public static void main(String[] args) {
        new ViewBookings();
    }
}
