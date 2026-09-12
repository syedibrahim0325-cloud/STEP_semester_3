
class Employee {
    private int empId;
    private String empName;
    private double salary;

    Employee(int empId, String empName, double salary) {
        this.empId = empId;
        this.empName = empName;
        this.salary = salary;
    }

    double getSalary() {
        return salary;
    }
}


class ManagerEmployee extends Employee {
    private double teamBonus;

    ManagerEmployee(int empId, String empName, double salary, double teamBonus) {
        super(empId, empName, salary);
        this.teamBonus = teamBonus;
    }

    double effectiveSalary() {
        return getSalary() + teamBonus;
    }
}


class ParkingSlot {
    String slotNo;
    int capacity;
    int occupiedCount;

    ParkingSlot(String slotNo, int capacity, int occupiedCount) {
        this.slotNo = slotNo;
        this.capacity = capacity;
        this.occupiedCount = occupiedCount;
    }

    void allot(String vehicleNo) {
        if (occupiedCount < capacity) {
            occupiedCount++;
            System.out.println(vehicleNo + " allotted to " + slotNo);
        }
    }

    static ParkingSlot findAvailableSlot(ParkingSlot[] slots) {
        for (ParkingSlot slot : slots) {
            if (slot != null && slot.occupiedCount < slot.capacity) {
                return slot;
            }
        }

        return null;
    }

    static ParkingSlot safeAllot(ParkingSlot[] slots, String vehicleNo) {
        ParkingSlot slot = findAvailableSlot(slots);

        if (slot != null) {
            slot.allot(vehicleNo);
            return slot;
        } else {
            System.out.println("No parking available for " + vehicleNo);
            return null;
        }
    }
}


class CompanyEmployeeRecord {

    String name;
    String empId;

    // Objects stored inside another object
    Employee employee;
    ParkingSlot slot;

    static int totalRecords = 0;

    CompanyEmployeeRecord(String name, String empId,
                          Employee employee, ParkingSlot slot) {

        this.name = name;
        this.empId = empId;
        this.employee = employee;
        this.slot = slot;

        totalRecords++;
    }

    String fullProfile() {

        double pay;

        // Check whether the employee is a Manager
        if (employee instanceof ManagerEmployee) {
            pay = ((ManagerEmployee) employee).effectiveSalary();
        } else {
            pay = employee.getSalary();
        }

        String parking;

        if (slot != null) {
            parking = slot.slotNo;
        } else {
            parking = "no parking assigned";
        }

        return name + " | Pay: Rs " + pay + " | Slot: " + parking;
    }
}


public class Main {

    public static void main(String[] args) {

        // Parking slots
        ParkingSlot[] slots = {
            new ParkingSlot("A1", 1, 0),
            new ParkingSlot("A2", 1, 0)
        };


        // Employees
        Employee manager =
            new ManagerEmployee(101, "Divya", 70000, 8000);

        Employee employee =
            new Employee(102, "Karan", 40000,);

        Employee intern =
            new Employee(103, "Meera", 10000);


        // Allot parking to only two employees
        ParkingSlot slot1 =
            ParkingSlot.safeAllot(slots, "DIVYA-CAR");

        ParkingSlot slot2 =
            ParkingSlot.safeAllot(slots, "KARAN-CAR");


        // Third employee intentionally gets no parking
        ParkingSlot slot3 = null;


        // Create three company records
        CompanyEmployeeRecord record1 =
            new CompanyEmployeeRecord("Divya", "E101",
                                      manager, slot1);

        CompanyEmployeeRecord record2 =
            new CompanyEmployeeRecord("Karan", "E102",
                                      employee, slot2);

        CompanyEmployeeRecord record3 =
            new CompanyEmployeeRecord("Meera", "E103",
                                      intern, slot3);


        // Print complete profiles
        System.out.println(record1.fullProfile());
        System.out.println(record2.fullProfile());
        System.out.println(record3.fullProfile());

        System.out.println("Total records: "
                           + CompanyEmployeeRecord.totalRecords);
    }
}

