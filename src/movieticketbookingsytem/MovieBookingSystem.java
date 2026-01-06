package movieticketbookingsytem;

import java.util.HashMap;
import java.util.Map;

public class MovieBookingSystem extends BookingSystem {
    /**
     * Creates a map called "showTimes".
     */
    private static Map<String, Integer> showTimes = new HashMap<>();

    /**
     *
     * @param showtimes
     */
    public static void setShowTimes(final Map<String, Integer> showtimes) {
        showTimes = showtimes;
    }

    /**
     * Creates a map list called "bookedTickets".
     */
    private static Map<String, Integer> bookedTickets = new HashMap<>();

    /**
     *
     * @param bookedtickets
     */
    public static void setBookedTickets(
            final Map<String, Integer> bookedtickets) {
        bookedTickets = bookedtickets;
    }

    @Override
    public final void bookTicket(final String newShowTime,
            final int noOfTickets) {
        if (showTimes.get(newShowTime) != null) {
            int availableTickets = showTimes.get(newShowTime);
            if ((availableTickets - noOfTickets) >= 0) {
                showTimes.put(newShowTime, availableTickets - noOfTickets);
                if (bookedTickets.get(newShowTime) == null) {
                    bookedTickets.put(newShowTime, noOfTickets);
                } else {
                    int currentBooked = bookedTickets.get(newShowTime);
                    bookedTickets.put(newShowTime, currentBooked + noOfTickets);
                }
                System.out.println(noOfTickets
                        + " tickets successfully booked for " + newShowTime);
            } else {
                System.out.println(
                        "Not enough tickets available for this showtime");
            }
        } else {
            System.out.println("Invalid showtime.");
        }
    }

    @Override
    public final void checkAvailability(final String newShowTime) {
        System.out.println(showTimes.get(newShowTime));
    }

    @Override
    public final void cancelReservation(final String newShowTime,
            final int noOfTickets) {
        if (bookedTickets.get(newShowTime) != null) {
            int noOfBookedTickets = bookedTickets.get(newShowTime);
            int availableTickets = showTimes.get(newShowTime);

            if ((noOfBookedTickets - noOfTickets) >= 0) {
                showTimes.put(newShowTime, availableTickets + noOfTickets);
                bookedTickets.put(newShowTime, noOfBookedTickets - noOfTickets);
                System.out.println(noOfTickets
                        + " tickets successfully canceled for " + newShowTime);
            } else {
                System.out.println("Invalid operation (Attempt to"
                        + " cancel more tickets than booked)");
            }
        }
    }

    /**
     * main function.
     *
     * @param args
     */
    public static void main(final String[] args) {
        MovieBookingSystem movieBookingSystem = new MovieBookingSystem();

        final int showtime10 = 20;
        final int showtime11 = 30;
        final int showtime1 = 50;
        final int showtime3 = 15;
        final int showtime5 = 10;

        showTimes.put("10:00 AM", showtime10);
        showTimes.put("11:30 AM", showtime11);
        showTimes.put("1:00 PM", showtime1);
        showTimes.put("3:00 PM", showtime3);
        showTimes.put("5:30 PM", showtime5);

        final int testCase1 = 5;
        final int testCase2 = 100;
        final int testCase3 = 3;
        final int testCase4 = 2;
        final int testCase5 = 5;

        movieBookingSystem.bookTicket("10:00 AM", testCase1);
        movieBookingSystem.bookTicket("10:00 AM", testCase2);
        movieBookingSystem.cancelReservation("10:00 AM", testCase3);
        movieBookingSystem.bookTicket("1:00 PM", testCase4);
        movieBookingSystem.cancelReservation("1:00 PM", testCase5);

    }
}
