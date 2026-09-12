
class srmStudent {
    String name;
    int regNo;
    int attendance;

    // Constructor
    SrmStudent(String name, int regNo, int attendance) {
        this.name = name;
        this.regNo = regNo;
        this.attendance = attendance;
    }

    // Instance method: updates the attendance of one particular student
    void addAttendanceUpdate(int newAttendance) {
        attendance = newAttendance;
    }

    // Instance method: checks the eligibility of one particular student
    boolean isEligible() {
        return attendance >= 75;
    }

    /*
     * classAverage() is static because it calculates the average
     * of many students and does not belong to one particular student.
     * isEligible() is not static because it checks the attendance
     * of one specific student object.
     */
    static double classAverage(SrmStudent[] students) {
        int total = 0;

        for (SrmStudent student : students) {
            total += student.attendance;
        }

        return (double) total / students.length;
    }

    public static void main(String[] args) {

        // Array of five SrmStudent objects
        SrmStudent[] students = {
            new SrmStudent("Ravi", 101, 82),
            new SrmStudent("Anitha", 102, 68),
            new SrmStudent("Karthik", 103, 91),
            new SrmStudent("Meera", 104, 74),
            new SrmStudent("Suresh", 105, 60)
        };

        // Print name, attendance and eligibility
        for (SrmStudent student : students) {
            if (student.isEligible()) {
                System.out.println(student.name + " - "
                        + student.attendance + "% - Eligible");
            } else {
                System.out.println(student.name + " - "
                        + student.attendance + "% - Detained");
            }
        }

        // Calling static method using class name
        double average = SrmStudent.classAverage(students);

        System.out.println("Class average: " + average + "%");
    }
}
```
