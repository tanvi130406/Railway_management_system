package db;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class CancelBookingDAO {

    public static void cancelBooking(int bookingId) {
        try {
            Connection conn = DBConnection.getConnection();

if (conn == null) {
    System.out.println("Connection failed!");
    return;
}

            String query = "DELETE FROM booking WHERE booking_id = ?";

            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1, bookingId);

            int rows = pst.executeUpdate();

            if (rows > 0) {
                System.out.println("Booking Cancelled!");
            } else {
                System.out.println("Invalid Booking ID");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

