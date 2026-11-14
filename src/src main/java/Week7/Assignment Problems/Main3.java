
abstract class ServiceableVehicle {

    // Private mileage field
    private double mileage;

    // Constructor
    public ServiceableVehicle() {
        mileage = 0.0;
    }

    // Abstract method
    public abstract String performMaintenance();

    // Getter
    public double getMileage() {
        return mileage;
    }

    // Add mileage with validation
    public void addMileage(double km) {

        if (km < 0) {
            throw new IllegalArgumentException(
                "Distance cannot be negative"
            );
        }

        mileage = mileage + km;
    }
}

// Insurance interface
interface Insurable {

    String getInsuranceInfo();
}

// Forklift class
class Forklift extends ServiceableVehicle implements Insurable {

    protected String assetTag;

    public Forklift(String assetTag) {
        this.assetTag = assetTag;
    }

    @Override
    public String performMaintenance() {
        return "Forklift " + assetTag
                + ": hydraulic and fork inspection complete";
    }

    @Override
    public String getInsuranceInfo() {
        return "Insured under fleet policy - Asset " + assetTag;
    }
}

// HeavyDutyForklift class
class HeavyDutyForklift extends Forklift {

    public HeavyDutyForklift(String assetTag) {
        super(assetTag);
    }

    @Override
    public String performMaintenance() {

        return super.performMaintenance()
                + " | high-pressure hydraulic check complete";
    }
}

// Main class
public class Main3 {

    // Check insurance availability
    public static String getInsuranceIfApplicable(
            ServiceableVehicle v) {

        if (v instanceof Insurable) {

            Insurable insuredVehicle = (Insurable) v;

            return insuredVehicle.getInsuranceInfo();
        }

        return "No insurance record exists";
    }

    public static void main(String[] args) {

        Forklift f = new Forklift("FL-22");

        // Mileage operations
        f.addMileage(120);

        System.out.println("Mileage: " + f.getMileage());

        // Maintenance
        System.out.println(f.performMaintenance());

        // Insurance
        System.out.println(getInsuranceIfApplicable(f));

        System.out.println();

        // Heavy duty forklift
        HeavyDutyForklift hd =
                new HeavyDutyForklift("HD-9");

        System.out.println(hd.performMaintenance());

        // Check insurance for heavy duty forklift
        System.out.println(getInsuranceIfApplicable(hd));
    }
}

