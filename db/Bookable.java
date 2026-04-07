package db;
//custom interface
public interface Bookable {

    
    String bookTicket(int passengerId, int trainId, String coach, String journeyDate) throws Exception;
}