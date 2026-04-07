package db;

public class PremiumBookingDAO extends BookingDAO {

    //  POLYMORPHISM (Override seat logic)
    @Override
    public int generateSeat() {

        // Lower berth pattern
        int lowerBerths[] = {1, 4, 7, 10, 13, 16, 19, 22, 25, 28};

        int index = (int)(Math.random() * lowerBerths.length);

        return lowerBerths[index];
    }

    //OPTIONAL BUT IMPORTANT (method override for full OOP clarity)
    @Override
    public String bookTicket(int passengerId, int trainId, String coach, String journeyDate) throws Exception {

        // You can add custom message here if needed
        System.out.println("Premium Booking (Senior Citizen)");

        // Call parent method
        return super.bookTicket(passengerId, trainId, coach, journeyDate);
    }
}