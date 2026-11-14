
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

enum RoomCategory {
    STANDARD(150),
    DELUXE(200),
    SUITE(300);

    private final double pricePerNight;

    RoomCategory(double pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }
}

enum ReservationStatus {
    ACTIVE,
    CANCELLED
}

class Customer {
    private int customerId;
    private String name;

    public Customer(int customerId, String name) {
        this.customerId = customerId;
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

class Room {
    private int roomNumber;
    private RoomCategory category;

    public Room(int roomNumber, RoomCategory category) {
        this.roomNumber = roomNumber;
        this.category = category;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public RoomCategory getCategory() {
        return category;
    }

    public double calculatePrice(LocalDate checkIn, LocalDate checkOut) {
        long nights = ChronoUnit.DAYS.between(checkIn, checkOut);
        return nights * category.getPricePerNight();
    }

    public boolean isAvailable(LocalDate checkIn, LocalDate checkOut,
                               List<Reservation> reservations) {
        for (Reservation reservation : reservations) {
            if (reservation.getRoom().getRoomNumber() == roomNumber &&
                reservation.getStatus() == ReservationStatus.ACTIVE) {

                boolean overlaps =
                    checkIn.isBefore(reservation.getCheckOut()) &&
                    checkOut.isAfter(reservation.getCheckIn());

                if (overlaps) {
                    return false;
                }
            }
        }
        return true;
    }
}

class Reservation {
    private int reservationId;
    private Room room;
    private Customer customer;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private LocalDate cancellationDeadline;
    private ReservationStatus status;
    private double totalPrice;

    public Reservation(int reservationId, Room room, Customer customer,
                       LocalDate checkIn, LocalDate checkOut,
                       LocalDate cancellationDeadline) {
        this.reservationId = reservationId;
        this.room = room;
        this.customer = customer;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.cancellationDeadline = cancellationDeadline;
        this.status = ReservationStatus.ACTIVE;
        this.totalPrice = room.calculatePrice(checkIn, checkOut);
    }

    public Room getRoom() {
        return room;
    }

    public LocalDate getCheckIn() {
        return checkIn;
    }

    public LocalDate getCheckOut() {
        return checkOut;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public boolean cancel(LocalDate currentDate) {
        if (status == ReservationStatus.CANCELLED ||
            currentDate.isAfter(cancellationDeadline)) {
            return false;
        }

        status = ReservationStatus.CANCELLED;
        return true;
    }
}

class Hotel {
    private List<Room> rooms;
    private List<Reservation> reservations;
    private int nextReservationId;

    public Hotel() {
        rooms = new ArrayList<>();
        reservations = new ArrayList<>();
        nextReservationId = 1;
    }

    public void addRoom(Room room) {
        rooms.add(room);
    }

    public Reservation bookRoom(int roomNumber, Customer customer,
                                LocalDate checkIn, LocalDate checkOut,
                                LocalDate cancellationDeadline) {
        if (!checkOut.isAfter(checkIn)) {
            System.out.println("Booking failed: Invalid date range.");
            return null;
        }

        Room selectedRoom = null;

        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNumber) {
                selectedRoom = room;
                break;
            }
        }

        if (selectedRoom == null) {
            System.out.println("Booking failed: Room " + roomNumber + " does not exist.");
            return null;
        }

        if (!selectedRoom.isAvailable(checkIn, checkOut, reservations)) {
            System.out.println("Booking failed: " +
                selectedRoom.getCategory() + " Room " + roomNumber +
                " is not available for " + checkIn + " to " + checkOut + ".");
            return null;
        }

        Reservation reservation = new Reservation(
            nextReservationId++, selectedRoom, customer,
            checkIn, checkOut, cancellationDeadline
        );

        reservations.add(reservation);

        System.out.printf("%s Room %d booked from %s to %s. Total price: $%.2f%n",
            selectedRoom.getCategory(), roomNumber,
            checkIn, checkOut, reservation.getTotalPrice());

        return reservation;
    }

    public void cancelReservation(Reservation reservation, LocalDate currentDate) {
        if (reservation == null) {
            return;
        }

        if (reservation.cancel(currentDate)) {
            System.out.println("Reservation for " +
                reservation.getRoom().getCategory() + " Room " +
                reservation.getRoom().getRoomNumber() +
                " cancelled successfully.");
        } else {
            System.out.println("Cancellation failed: Deadline has passed or reservation is already cancelled.");
        }
    }
}

public class Main3 {
    public static void main(String[] args) {
        Hotel hotel = new Hotel();

        hotel.addRoom(new Room(101, RoomCategory.DELUXE));
        hotel.addRoom(new Room(205, RoomCategory.STANDARD));

        Customer customer = new Customer(1, "John");

        LocalDate checkIn1 = LocalDate.of(2024, 12, 1);
        LocalDate checkOut1 = LocalDate.of(2024, 12, 5);

        LocalDate checkIn2 = LocalDate.of(2024, 12, 3);
        LocalDate checkOut2 = LocalDate.of(2024, 12, 7);

        LocalDate cancellationDeadline = LocalDate.of(2024, 11, 30);

        Reservation reservation1 = hotel.bookRoom(
            101, customer, checkIn1, checkOut1, cancellationDeadline
        );

        hotel.bookRoom(
            205, customer, checkIn2, checkOut2, cancellationDeadline
        );

        hotel.bookRoom(
            101, customer, checkIn2, checkOut2, cancellationDeadline
        );

        hotel.cancelReservation(
            reservation1, LocalDate.of(2024, 11, 29)
        );
    }
}

