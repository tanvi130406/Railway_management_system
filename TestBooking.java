import db.BookingDAO;

public class TestBooking {
    public static void main(String[] args) {

        // Hardcoded values
        int passengerId = 1;
        int trainId = 2;
        int seatNumber = 25;

        BookingDAO.insertBooking(passengerId, trainId, seatNumber);
    }
}
