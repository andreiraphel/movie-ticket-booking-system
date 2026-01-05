package movieticketbookingsytem;

public abstract class BookingSystem {

    /**
     * An abstract method that books the specified number of tickets for a
     * showtime.
     *
     * @param showTime    - available tickets for a particular showtime
     * @param noOfTickets - is the number of tickets to be booked
     */
    public abstract void bookTicket(String showTime, int noOfTickets);

    /**
     * An abstract method that checks if there are available tickets for a
     * particular showtime.
     *
     * @param showTime - available tickets for a particular showtime
     */
    public abstract void checkAvailability(String showTime);

    /**
     * An abstract method that cancels the specified number of tickets for a
     * showtime.
     *
     * @param showTime    - available tickets for a particular showtime
     * @param noOfTickets - is the number of tickets to be booked
     */
    public abstract void cancelReservation(String showTime, int noOfTickets);
}
