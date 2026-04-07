package db;
//base class for booking seat which is inherited
public class BaseBooking {

    protected int generateSeat() {
    int seat = (int)(Math.random() * 100 + 1);
    return seat;
}
}