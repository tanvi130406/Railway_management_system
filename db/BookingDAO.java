package db;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class BookingDAO implements Bookable {

    // Generate PNR
    public static String generatePNR() {
        long time = System.currentTimeMillis();
        int random = (int)(Math.random() * 1000);
        return "PNR" + time + random;
    }

    // Seat generation
    public int generateSeat() {
        return (int)(Math.random() * 100 + 1);
    }

    // BOOK TICKET (INSERT)
    @Override
    public String bookTicket(int passengerId, int trainId, String coach, String journeyDate) throws Exception {

        Connection conn = DBConnection.getConnection();

        if (conn == null) {
            System.out.println("Connection failed!");
            return null;
        }

        String pnr = generatePNR();
        int seatNumber = generateSeat();

        String query = "INSERT INTO booking (passenger_id, train_id, booking_date, seat_number, coach, pnr, journey_date) VALUES (?, ?, CURRENT_TIMESTAMP, ?, ?, ?, ?)";

        PreparedStatement pst = conn.prepareStatement(query);

        pst.setInt(1, passengerId);
        pst.setInt(2, trainId);
        pst.setInt(3, seatNumber);
        pst.setString(4, coach);
        pst.setString(5, pnr);
        pst.setString(6, journeyDate);

        pst.executeUpdate();

        System.out.println("Booking Successful! PNR: " + pnr);

        return pnr;
    }

    //  UPDATE METHOD 
    public static void updateSeat(int bookingId, int newSeat) {
        try {
            Connection conn = DBConnection.getConnection();

            if (conn == null) {
                System.out.println("Connection failed!");
                return;
            }

            String query = "UPDATE booking SET seat_number = ? WHERE booking_id = ?";

            PreparedStatement pst = conn.prepareStatement(query);

            pst.setInt(1, newSeat);
            pst.setInt(2, bookingId);

            int rows = pst.executeUpdate();

            if (rows > 0) {
                System.out.println("Seat Updated Successfully!");
            } else {
                System.out.println("Booking ID not found!");
            }

        } catch (Exception e) {
            System.out.println("Error updating seat!");
            e.printStackTrace();
        }
    }
}