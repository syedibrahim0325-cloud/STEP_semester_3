
abstract class StaffMember {

    private double baseSalary;
    protected double bonusRate;

    // Constructor 1: Default bonus rate
    public StaffMember(double baseSalary) {
        this(baseSalary, 0.10);
    }

    // Constructor 2: Custom bonus rate
    public StaffMember(double baseSalary, double bonusRate) {
        setSalary(baseSalary);
        this.bonusRate = bonusRate;
    }

    // Abstract method
    public abstract double calculateBonus();

    // Getter
    public double getSalary() {
        return baseSalary;
    }

    // Setter
    public void setSalary(double baseSalary) {
        if (baseSalary < 0) {
            throw new IllegalArgumentException(
                "Salary cannot be negative"
            );
        }
        this.baseSalary = baseSalary;
    }
}

// Interface
interface Auditable {
    String auditRecord();
}

// Child class
class TeamLead extends StaffMember implements Auditable {

    private int teamSize;

    // Constructor 1: Default bonus rate
    public TeamLead(double baseSalary, int teamSize) {
        super(baseSalary);
        this.teamSize = teamSize;
    }

    // Constructor 2: Custom bonus rate
    public TeamLead(double baseSalary, double bonusRate,
                    int teamSize) {
        super(baseSalary, bonusRate);
        this.teamSize = teamSize;
    }

    @Override
    public double calculateBonus() {
        return getSalary() * bonusRate;
    }

    @Override
    public String auditRecord() {
        return "TeamLead audit: " + teamSize +
               " team members, salary $" + getSalary();
    }
}

public class Main3 {

    // Check whether staff member is auditable
    static String getAuditIfApplicable(StaffMember s) {

        if (s instanceof Auditable) {
            Auditable a = (Auditable) s;
            return a.auditRecord();
        }

        return "No audit is required";
    }

    public static void main(String[] args) {

        // Default bonus rate = 10%
        TeamLead t = new TeamLead(60000, 5);

        System.out.println("Bonus: " + t.calculateBonus());

        // Custom bonus rate = 20%
        TeamLead t2 = new TeamLead(60000, 0.20, 5);

        System.out.println("Bonus: " + t2.calculateBonus());

        // Testing negative salary
        try {
            t.setSalary(-5000);
        } catch (IllegalArgumentException e) {
            System.out.println("Rejected: " + e.getMessage());
        }

        System.out.println("Current Salary: " + t.getSalary());

        // Upcasting
        StaffMember ref = t;

        System.out.println(getAuditIfApplicable(ref));
    }
}

