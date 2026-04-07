package ui;

import javax.swing.*;
import java.awt.*;

public class MyBookings extends JFrame {

    JTable table;

    public MyBookings() {

        setTitle("My Bookings");

        String columns[]={"Passenger","Train","Seat","Date","PNR"};

        String data[][]={
                {"Rahul","Deccan Express","12","2026-04-01","PNR1001"},
                {"Priya","Intercity Express","15","2026-04-02","PNR1002"}
        };

        table = new JTable(data,columns);

        JScrollPane sp = new JScrollPane(table);

        add(sp);

        setSize(600,400);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
