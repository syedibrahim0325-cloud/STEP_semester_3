
class FeeAccount {

    private int regNo;
    private double totalFee;
    private double amountPaid;

    FeeAccount(int regNo, double totalFee, double amountPaid) {
        this.regNo = regNo;
        this.totalFee = totalFee;
        this.amountPaid = amountPaid;
    }

    void pay(double amount) {
        if (amount > 0) {
            amountPaid += amount;
        } else {
            System.out.println("Payment rejected: amount must be positive.");
        }
    }

    double getDue() {
        return totalFee - amountPaid;
    }
}


// HostelFeeAccount inherits FeeAccount
class HostelFeeAccount extends FeeAccount {

    HostelFeeAccount(int regNo, double totalFee, double amountPaid) {
        super(regNo, totalFee, amountPaid);
    }

    void payInTwoInstallments(double amount) {
        pay(amount / 2);
        pay(amount / 2);
    }
}


// Represents a hostel room
class HostelRoom {

    String roomNo;
    int beds;
    int occupied;

    HostelRoom(String roomNo, int beds, int occupied) {
        this.roomNo = roomNo;
        this.beds = beds;
        this.occupied = occupied;
    }

    void allot(String name) {
        if (occupied < beds) {
            occupied++;
            System.out.println(name + " allotted to room " + roomNo);
        }
    }

    // Returns first available room or null
    static HostelRoom findAvailableRoom(HostelRoom[] rooms) {

        for (HostelRoom room : rooms) {
            if (room != null && room.occupied < room.beds) {
                return room;
            }
        }

        return null;
    }

    // Null-safe room allotment
    static HostelRoom safeAllot(HostelRoom[] rooms, String studentName) {

        HostelRoom room = findAvailableRoom(rooms);

        if (room != null) {
            room.allot(studentName);
            return room;
        } else {
            System.out.println("No rooms available for " + studentName);
            return null;
        }
    }
}


// Student contains objects of other classes
class SrmStudent {

    String name;
    String regNo;

    // Composition: Student HAS-A HostelFeeAccount
    HostelFeeAccount feeAccount;

    // Composition: Student HAS-A HostelRoom
    // It can also be null if no room is allotted
    HostelRoom room;

    // Static counter shared by all students
    static int totalStudents = 0;

    SrmStudent(String name, String regNo,
               HostelFeeAccount feeAccount) {

        this.name = name;
        this.regNo = regNo;
        this.feeAccount = feeAccount;

        room = null;

        totalStudents++;
    }

    // Print complete student status
    String fullStatus() {

        String roomNumber;

        if (room != null) {
            roomNumber = room.roomNo;
        } else {
            roomNumber = "unallotted";
        }

        return name + " | Due: Rs "
                + feeAccount.getDue()
                + " | Room: " + roomNumber;
    }
}


public class Main {

    public static void main(String[] args) {

        // ---------------------------------------------
        // Create hostel rooms
        // ---------------------------------------------

        HostelRoom[] rooms = {
            new HostelRoom("C-214", 1, 0),
            new HostelRoom("C-507", 1, 0)
        };


        // ---------------------------------------------
        // Create three students
        // ---------------------------------------------

        SrmStudent ravi = new SrmStudent(
                "Ravi",
                "SRM001",
                new HostelFeeAccount(101, 200000, 0)
        );

        SrmStudent anitha = new SrmStudent(
                "Anitha",
                "SRM002",
                new HostelFeeAccount(102, 200000, 0)
        );

        SrmStudent karthik = new SrmStudent(
                "Karthik",
                "SRM003",
                new HostelFeeAccount(103, 200000, 0)
        );


        // ---------------------------------------------
        // Allot rooms to only two students
        // ---------------------------------------------

        ravi.room = HostelRoom.safeAllot(rooms, ravi.name);

        anitha.room = HostelRoom.safeAllot(rooms, anitha.name);

        // No room is left for Karthik
        karthik.room = HostelRoom.safeAllot(rooms, karthik.name);


        // ---------------------------------------------
        // Process payments
        // ---------------------------------------------

        ravi.feeAccount.pay(60000);       // Valid
        anitha.feeAccount.pay(20000);     // Valid
        karthik.feeAccount.pay(-5000);    // Rejected


        // ---------------------------------------------
        // Print complete status
        // ---------------------------------------------

        System.out.println();
        System.out.println(ravi.fullStatus());
        System.out.println(anitha.fullStatus());
        System.out.println(karthik.fullStatus());


        // ---------------------------------------------
        // Total number of students
        // ---------------------------------------------

        System.out.println("Total students: "
                + SrmStudent.totalStudents);
    }
}

