
package db;

import java.sql.*;
import java.util.ArrayList;

public class ViewBookingDAO {

    public static ArrayList<String> getAllBookings() {
        ArrayList<String> list = new ArrayList<>();

        try {
            Connection conn = DBConnection.getConnection();

            if (conn == null) {
                System.out.println("Connection failed!");
                return list;
            }

            String query = "SELECT p.name, t.train_name, b.booking_date, b.seat_number, b.pnr, b.journey_date " +
                    "FROM booking b " +
                    "JOIN passenger p ON b.passenger_id = p.passenger_id " +
                    "JOIN train t ON b.train_id = t.train_id";

            PreparedStatement pst = conn.prepareStatement(query);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {

                int seat = rs.getInt("seat_number");

                // Berth logic
                String berthType;
                if (seat % 3 == 1) {
                    berthType = "Lower Berth";
                } else {
                    berthType = "Upper/Middle";
                }

                String data =
                        "Name: " + rs.getString("name") + " | " +
                        "Train: " + rs.getString("train_name") + " | " +
                        "Date: " + rs.getDate("booking_date") + " | " +
                        "Journey Date: " + rs.getDate("journey_date") + " | " +  
                        "Seat: " + seat + " (" + berthType + ") | " +
                        "PNR: " + rs.getString("pnr");

                list.add(data);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}