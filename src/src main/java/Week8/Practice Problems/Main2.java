
import java.util.ArrayList;
import java.util.List;

// Abstract Vehicle class
abstract class Vehicle {

    protected String vehicleName;
    private boolean available;

    public Vehicle(String vehicleName) {
        this.vehicleName = vehicleName;
        this.available = true;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public boolean isAvailable() {
        return available;
    }

    // Each category defines its own pricing
    public abstract double calculateRentalCharge(int days);

    // Package-private availability update
    void setAvailable(boolean available) {
        this.available = available;
    }
}

// Standard car
class StandardCar extends Vehicle {

    private static final double DAILY_RATE = 50.0;

    public StandardCar(String vehicleName) {
        super(vehicleName);
    }

    @Override
    public double calculateRentalCharge(int days) {
        return DAILY_RATE * days;
    }
}

// Luxury car
class LuxuryCar extends Vehicle {

    private static final double DAILY_RATE = 100.0;

    public LuxuryCar(String vehicleName) {
        super(vehicleName);
    }

    @Override
    public double calculateRentalCharge(int days) {
        return DAILY_RATE * days;
    }
}

// SUV
class SUV extends Vehicle {

    private static final double DAILY_RATE = 80.0;

    public SUV(String vehicleName) {
        super(vehicleName);
    }

    @Override
    public double calculateRentalCharge(int days) {
        return DAILY_RATE * days;
    }
}

// Customer class
class Customer {

    private String customerName;
    private List<Rental> rentalHistory;

    public Customer(String customerName) {
        this.customerName = customerName;
        rentalHistory = new ArrayList<>();
    }

    public String getCustomerName() {
        return customerName;
    }

    public void addRental(Rental rental) {
        rentalHistory.add(rental);
    }

    public List<Rental> getRentalHistory() {
        return rentalHistory;
    }
}

// Rental class
class Rental {

    private Customer customer;
    private Vehicle vehicle;
    private int days;
    private double totalAmount;
    private boolean active;

    public Rental(Customer customer, Vehicle vehicle, int days) {
        this.customer = customer;
        this.vehicle = vehicle;
        this.days = days;

        // Polymorphic pricing
        this.totalAmount = vehicle.calculateRentalCharge(days);

        this.active = true;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public boolean isActive() {
        return active;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void returnVehicle() {

        if (active) {
            active = false;
            vehicle.setAvailable(true);
        }
    }
}

// RentalService class
class RentalService {

    public Rental rentVehicle(
            Customer customer, Vehicle vehicle, int days) {

        if (days <= 0) {
            System.out.println("Rental duration must be positive.");
            return null;
        }

        if (!vehicle.isAvailable()) {
            System.out.println(
                vehicle.getVehicleName() + " is not available."
            );
            return null;
        }

        // Mark vehicle unavailable
        vehicle.setAvailable(false);

        Rental rental = new Rental(customer, vehicle, days);

        // Add rental to customer's history
        customer.addRental(rental);

        System.out.printf(
            "%s rented for %d days. Total charge: $%.2f%n",
            vehicle.getVehicleName(),
            days,
            rental.getTotalAmount()
        );

        return rental;
    }

    public void returnVehicle(Rental rental) {

        if (rental == null || !rental.isActive()) {
            System.out.println("Invalid or already returned rental.");
            return;
        }

        rental.returnVehicle();

        System.out.println(
            rental.getVehicle().getVehicleName()
            + " returned. Now available."
        );
    }
}

// Main class
public class Main2 {

    public static void main(String[] args) {

        Customer customer = new Customer("John");

        Vehicle luxury = new LuxuryCar("Luxury Car A");
        Vehicle standard = new StandardCar("Standard Car B");

        RentalService service = new RentalService();

        // Rent vehicles
        Rental r1 = service.rentVehicle(
            customer, luxury, 3
        );

        Rental r2 = service.rentVehicle(
            customer, standard, 5
        );

        // Return luxury car
        service.returnVehicle(r1);

        // Check availability
        System.out.println(
            "Luxury Car A available: " + luxury.isAvailable()
        );
    }
}

