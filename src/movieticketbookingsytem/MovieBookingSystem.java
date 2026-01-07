package movieticketbookingsytem;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MovieBookingSystem extends BookingSystem {
    /**
     * Creates a map called "showTimes".
     */
    private static Map<String, Integer> showTimes = new HashMap<>();

    /**
     * Creates a map list called "bookedTickets".
     */
    private static Map<String, Integer> bookedTickets = new HashMap<>();

    /**
     * Accepts a map as argument.
     *
     * @param bookedtickets
     */
    public static void setBookedTickets(
            final Map<String, Integer> bookedtickets) {
        bookedTickets = bookedtickets;
    }

    /**
     * Accepts a map as argument.
     *
     * @param showtimes
     */
    public static void setShowTimes(final Map<String, Integer> showtimes) {
        showTimes = showtimes;
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
            System.out.println("\nInvalid showtime.\n");
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
        } else {
            System.out.println("\nInvalid showtime.\n");
        }
    }

    /**
     * Main function.
     *
     * @param args
     */
    public static void main(final String[] args) {
        MovieBookingSystem movieBookingSystem = new MovieBookingSystem();
        Scanner scanner = new Scanner(System.in);

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

        final int input1 = 0;
        final int input2 = 1;
        final int input3 = 2;
        final int input4 = 3;

        while (true) {
            System.out.println("\nEnter a command: ");
            System.out.println("\n'0' to exit");
            System.out.println("'1' to Book Tickets");
            System.out.println("'2' to Check Show Availability");
            System.out.println("'3' to Cancel Tickets");

            int userInput = scanner.nextInt();

            if (userInput == input1) {
                System.out.println("Exiting the program.");
                break;
            } else if (userInput == input2) {
                System.out.println(">>>>Book Ticket<<<<");
                System.out.println("----Available Shows----");

                for (String key : showTimes.keySet()) {
                    System.out.println(key);
                }

                scanner.nextLine();

                System.out.println("\nEnter Time Slot: HH:MM (AM/PM)");
                String userInputTime = scanner.nextLine().trim();
                System.out.println("You selected: " + userInputTime);

                System.out.println("\nEnter Number of Tickets: ");
                int userInputNoOfTickets = scanner.nextInt();
                System.out.println("You selected: " + userInputNoOfTickets);

                movieBookingSystem.bookTicket(userInputTime,
                        userInputNoOfTickets);
            } else if (userInput == input3) {
                scanner.nextLine();
                System.out.println(">>>>Check Showtime<<<<");
                System.out.println("\nEnter Time Slot: HH:MM (AM/PM)");
                String userInputTimeCheck = scanner.nextLine().trim();

                movieBookingSystem.checkAvailability(userInputTimeCheck);
            } else if (userInput == input4) {
                scanner.nextLine();
                System.out.println("\nEnter Time Slot: HH:MM (AM/PM)");
                String userInputTime = scanner.nextLine().trim();
                System.out.println("You selected: " + userInputTime);

                System.out.println("\nEnter Number of Tickets: ");
                int userInputNoOfTickets = scanner.nextInt();
                System.out.println("You selected: " + userInputNoOfTickets);

                scanner.nextLine();
                System.out
                        .println("\nAre you sure you want to cancel? (yes/no)");
                String verify = scanner.nextLine().trim();
                if (verify.equalsIgnoreCase("yes")) {
                    movieBookingSystem.cancelReservation(userInputTime,
                            userInputNoOfTickets);
                }
            }
        }
        scanner.close();
    }
}
