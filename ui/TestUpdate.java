package ui;
import java.util.Scanner;
import db.BookingDAO;
public class TestUpdate {
    public static void main(String[] args) {
        Scanner sc =new Scanner(System.in);
        System.out.println("Enter booking ID to update seat:");
        int bookingId=sc.nextInt();
        System.out.println("Enter new seat number:");
        int newSeat=sc.nextInt();
        BookingDAO.updateSeat(bookingId,newSeat);
        System.err.println("Seat updated successfully");
        sc.close();
    }
}
