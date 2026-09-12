
class SrmStudent1 {

    // Shared by all students
    static String university = "SRM Institute of Science and Technology";
    static int admissionCount = 0;

    // Each object has its own copy
    static String name;
    static String regNo;
    static int attendance;

    // Constructor automatically generates regNo
    SrmStudent1(String name, int attendance) {
        this.name = name;
        this.attendance = attendance;

        admissionCount++;
        this.regNo = "SRM" + admissionCount;
    }

    // Instance method because it prints data of one student
    void printIdCard() {
        System.out.println("University: " + university);
        System.out.println("Name: " + name);
        System.out.println("Reg No: " + regNo);
        System.out.println("Attendance: " + attendance + "%");
        System.out.println();
    }

    // Static method because it belongs to the whole class
    static void printTotalAdmissions() {
        System.out.println("Total Admissions: " + admissionCount);
    }

    public static void main(String[] args) {

        // -------------------------------
        // BROKEN VERSION DEMONSTRATION
        // -------------------------------

        System.out.println("Broken version:");

        /*
         * If name, regNo and attendance were static,
         * there would be only one shared copy.
         *
         * Student 2 would overwrite Student 1's data.
         */

        SrmStudent1.name = "Ravi";
        SrmStudent1.regNo = "SRM001";
        SrmStudent1.attendance = 82;

        SrmStudent1.name = "Meera";
        SrmStudent1.regNo = "SRM002";
        SrmStudent1.attendance = 74;

        System.out.println(SrmStudent1.name);
        System.out.println(SrmStudent1.name);

        System.out.println(
            "Ravi's data was overwritten because the fields were static."
        );

        System.out.println();


        // -------------------------------
        // CORRECTED VERSION
        // -------------------------------

        System.out.println("Corrected version:");

        SrmStudent1 ravi = new SrmStudent1("Ravi", 82);
        SrmStudent1 meera = new SrmStudent1("Meera", 74);

        ravi.printIdCard();
        meera.printIdCard();

        SrmStudent1.printTotalAdmissions();
    }
}

