
import java.util.ArrayList;
import java.util.List;

enum ParcelStatus {
    BOOKED,
    PICKED_UP,
    IN_TRANSIT,
    OUT_FOR_DELIVERY,
    DELIVERED,
    CANCELLED
}

interface ShippingType {
    double calculateCharge(double weight);
    String getName();
}

class StandardShipping implements ShippingType {
    @Override
    public double calculateCharge(double weight) {
        return 40 + (10 * weight);
    }

    @Override
    public String getName() {
        return "Standard";
    }
}

class ExpressShipping implements ShippingType {
    @Override
    public double calculateCharge(double weight) {
        return 80 + (15 * weight);
    }

    @Override
    public String getName() {
        return "Express";
    }
}

class FragileShipping implements ShippingType {
    private StandardShipping standardShipping = new StandardShipping();

    @Override
    public double calculateCharge(double weight) {
        return standardShipping.calculateCharge(weight) + 50;
    }

    @Override
    public String getName() {
        return "Fragile";
    }
}

interface NotificationChannel {
    void sendNotification(String parcelId, ParcelStatus status);
}

class SmsChannel implements NotificationChannel {
    @Override
    public void sendNotification(String parcelId, ParcelStatus status) {
        System.out.println("[SMS] " + parcelId + " is now " + status + ".");
    }
}

class EmailChannel implements NotificationChannel {
    @Override
    public void sendNotification(String parcelId, ParcelStatus status) {
        System.out.println("[Email] " + parcelId + " is now " + status + ".");
    }
}

class Customer {
    private String name;
    private List<Parcel> parcels;

    public Customer(String name) {
        this.name = name;
        parcels = new ArrayList<>();
    }

    public void addParcel(Parcel parcel) {
        parcels.add(parcel);
    }

    public String getName() {
        return name;
    }
}

class Parcel {
    private String parcelId;
    private double weight;
    private ShippingType shippingType;
    private Customer customer;
    private ParcelStatus status;
    private List<NotificationChannel> channels;

    public Parcel(String parcelId, double weight,
                  ShippingType shippingType, Customer customer) {
        this.parcelId = parcelId;
        this.weight = weight;
        this.shippingType = shippingType;
        this.customer = customer;
        this.status = ParcelStatus.BOOKED;
        channels = new ArrayList<>();
    }

    public void subscribe(NotificationChannel channel) {
        channels.add(channel);
    }

    public double calculateCharge() {
        return shippingType.calculateCharge(weight);
    }

    public ParcelStatus getStatus() {
        return status;
    }

    public String getParcelId() {
        return parcelId;
    }

    private void notifyChannels() {
        for (NotificationChannel channel : channels) {
            channel.sendNotification(parcelId, status);
        }
    }

    public boolean updateStatus(ParcelStatus newStatus) {
        if (status == ParcelStatus.CANCELLED ||
            status == ParcelStatus.DELIVERED) {
            System.out.println("Invalid transition: " + status +
                " → " + newStatus + " is not allowed.");
            return false;
        }

        if (newStatus.ordinal() != status.ordinal() + 1 ||
            newStatus == ParcelStatus.CANCELLED) {
            System.out.println("Invalid transition: " + status +
                " → " + newStatus + " is not allowed.");
            return false;
        }

        status = newStatus;
        notifyChannels();
        return true;
    }

    public boolean cancel() {
        if (status != ParcelStatus.BOOKED) {
            System.out.println("Cancellation failed: " + parcelId +
                " can be cancelled only while BOOKED.");
            return false;
        }

        status = ParcelStatus.CANCELLED;
        notifyChannels();
        System.out.println("Parcel " + parcelId + " cancelled successfully.");
        return true;
    }
}

class ParcelService {
    private List<Parcel> parcels;

    public ParcelService() {
        parcels = new ArrayList<>();
    }

    public Parcel bookParcel(String parcelId, double weight,
                             ShippingType shippingType,
                             Customer customer,
                             List<NotificationChannel> channels) {
        if (weight <= 0) {
            System.out.println("Booking failed: Weight must be greater than zero.");
            return null;
        }

        Parcel parcel = new Parcel(parcelId, weight, shippingType, customer);

        for (NotificationChannel channel : channels) {
            parcel.subscribe(channel);
        }

        parcels.add(parcel);
        customer.addParcel(parcel);

        System.out.println("Parcel " + parcelId + " booked (" +
            shippingType.getName() + ", " + weight + " kg).");

        System.out.printf("Charge: ₹%.2f%n", parcel.calculateCharge());

        parcel.updateStatus(ParcelStatus.BOOKED);

        return parcel;
    }

    public void updateParcelStatus(Parcel parcel, ParcelStatus newStatus) {
        if (parcel != null) {
            parcel.updateStatus(newStatus);
        }
    }

    public void cancelParcel(Parcel parcel) {
        if (parcel != null) {
            parcel.cancel();
        }
    }
}

public class Main2 {
    public static void main(String[] args) {
        Customer customer = new Customer("John");

        ParcelService service = new ParcelService();

        List<NotificationChannel> channels = new ArrayList<>();
        channels.add(new SmsChannel());
        channels.add(new EmailChannel());

        Parcel parcel = service.bookParcel(
            "P101",
            2,
            new ExpressShipping(),
            customer,
            channels
        );

        service.updateParcelStatus(parcel, ParcelStatus.PICKED_UP);

        service.cancelParcel(parcel);

        service.updateParcelStatus(parcel, ParcelStatus.IN_TRANSIT);

        service.updateParcelStatus(parcel, ParcelStatus.DELIVERED);
    }
}

