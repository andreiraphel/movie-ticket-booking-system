package movieticketbookingsytem;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MovieBookingSystemTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private MovieBookingSystem movieBookingSystem;
    
    private PrintStream originalOut;
    private InputStream originalIn;

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outContent));
        movieBookingSystem = new MovieBookingSystem();

        Map<String, Integer> showTimes = new HashMap<>();
        Map<String, Integer> bookedTickets = new HashMap<>();
        showTimes.put("10:00 AM", 20);
        showTimes.put("11:30 AM", 30);
        showTimes.put("1:00 PM", 50);
        showTimes.put("3:00 PM", 15);
        showTimes.put("5:30 PM", 10);

        MovieBookingSystem.setShowTimes(showTimes);
        MovieBookingSystem.setBookedTickets(bookedTickets);
    }
    
    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
        System.setIn(originalIn);
    }

    @Test
    void testBookTicketMethod_WhenBookingValidTickets_PrintSuccess() {
        movieBookingSystem.bookTicket("10:00 AM", 5);

        String expectedOutput = "5 tickets successfully booked for 10:00 AM";
        String actualOutput = outContent.toString().trim();

        assertEquals(expectedOutput, actualOutput);
    }
    
    @Test
    void testBookTicketMethod_WhenBookingInvalidTickets_PrintInvalid() {
        movieBookingSystem.bookTicket("10:00 AM", 100);

        String expectedOutput = "Not enough tickets available for this showtime";
        String actualOutput = outContent.toString().trim();

        assertEquals(expectedOutput, actualOutput);
    }
    
    @Test
    void testBookTicketMethod_WhenBookingInvalidShowtime_PrintInvalid() {
        movieBookingSystem.bookTicket("10:01 AM", 10);

        String expectedOutput = "Invalid showtime.";
        String actualOutput = outContent.toString().trim();

        assertEquals(expectedOutput, actualOutput);
    }
    
    @Test
    void testBookTicketMethod_WhenBookingOnAnExistingShowtime_PrintSuccess() {
        movieBookingSystem.bookTicket("10:00 AM", 10);
        movieBookingSystem.bookTicket("10:00 AM", 10);

        String expectedOutput = "10 tickets successfully booked for 10:00 AM" + System.lineSeparator() + "10 tickets successfully booked for 10:00 AM";
        String actualOutput = outContent.toString().trim();

        assertEquals(expectedOutput, actualOutput);
    }
    
    @Test
    void testCheckAvailabilityMethod_WhenPrintingTheAvailableTickets_PrintsNumberOfAvailableTickets() {
        movieBookingSystem.checkAvailability("10:00 AM");

        String expectedOutput = "20";
        String actualOutput = outContent.toString().trim();

        assertEquals(expectedOutput, actualOutput);
    }
    
    @Test
    void testCancelReservationMethod_WhenCancelingValidTickets_PrintsSuccess() {
        movieBookingSystem.bookTicket("10:00 AM", 5);
        movieBookingSystem.cancelReservation("10:00 AM", 3);

        String expectedOutput = "5 tickets successfully booked for 10:00 AM" + System.lineSeparator() + "3 tickets successfully canceled for 10:00 AM";
        String actualOutput = outContent.toString().trim();

        assertEquals(expectedOutput, actualOutput);
    }
    
    @Test
    void testCancelReservationMethod_WhenCancelingInvalidTickets_PrintsInvalid() {
        movieBookingSystem.bookTicket("10:00 AM", 5);
        movieBookingSystem.cancelReservation("10:00 AM", 6);

        String expectedOutput = "5 tickets successfully booked for 10:00 AM" + System.lineSeparator() + "Invalid operation (Attempt to cancel more tickets than booked)";
        String actualOutput = outContent.toString().trim();

        assertEquals(expectedOutput, actualOutput);
    }
    
    @Test
    void testCancelReservationMethod_WhenCancelingUsingInvalidShowtime_PrintsInvalid() {
        movieBookingSystem.bookTicket("10:00 AM", 5);
        movieBookingSystem.cancelReservation("11:00 AM", 6);

        String expectedOutput = "5 tickets successfully booked for 10:00 AM" + System.lineSeparator() + "\nInvalid showtime.";
        String actualOutput = outContent.toString().trim();

        assertEquals(expectedOutput, actualOutput);
    }
    
    
    
 // ------------------ MAIN() TESTS ------------------

    @Test
    void testMain_BookTicketsFlow() {
        String input =
                "1\n" +
                "10:00 AM\n" +
                "3\n" +
                "0\n";

        System.setIn(new ByteArrayInputStream(input.getBytes()));

        MovieBookingSystem.main(new String[]{});

        String output = outContent.toString();

        assertTrue(output.contains(">>>>Book Ticket<<<<"));
        assertTrue(output.contains("You selected: 10:00 AM"));
        assertTrue(output.contains("3 tickets successfully booked for 10:00 AM"));
        assertTrue(output.contains("Exiting the program."));
    }

    @Test
    void testMain_CheckAvailabilityFlow() {
        String input =
                "2\n" +
                "10:00 AM\n" +
                "0\n";

        System.setIn(new ByteArrayInputStream(input.getBytes()));
        MovieBookingSystem.main(new String[]{});

        String output = outContent.toString();

        assertTrue(output.contains(">>>>Check Showtime<<<<"));
        assertTrue(output.contains("20"));
    }


    @Test
    void testMain_CancelTicketsFlow() {
        String input =
                "1\n" +
                "10:00 AM\n" +
                "5\n" +
                "3\n" +
                "10:00 AM\n" +
                "3\n" +
                "yes\n" +
                "0\n";

        System.setIn(new ByteArrayInputStream(input.getBytes()));
        MovieBookingSystem.main(new String[]{});

        String output = outContent.toString();

        assertTrue(output.contains("5 tickets successfully booked for 10:00 AM"));
        assertTrue(output.contains("3 tickets successfully canceled for 10:00 AM"));
    }


}
