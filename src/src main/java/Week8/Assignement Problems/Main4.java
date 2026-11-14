
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

interface CreditPolicy {
    int getCreditLimit();
    String getStudentType();
}

class RegularPolicy implements CreditPolicy {
    public int getCreditLimit() {
        return 24;
    }

    public String getStudentType() {
        return "Regular";
    }
}

class HonorsPolicy implements CreditPolicy {
    public int getCreditLimit() {
        return 28;
    }

    public String getStudentType() {
        return "Honors";
    }
}

class ExchangePolicy implements CreditPolicy {
    public int getCreditLimit() {
        return 20;
    }

    public String getStudentType() {
        return "Exchange";
    }
}

class Student {
    private String name;
    private int currentCredits;
    private CreditPolicy creditPolicy;
    private List<Elective> enrolledElectives;

    public Student(String name, int currentCredits, CreditPolicy creditPolicy) {
        this.name = name;
        this.currentCredits = currentCredits;
        this.creditPolicy = creditPolicy;
        enrolledElectives = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public int getCurrentCredits() {
        return currentCredits;
    }

    public int getCreditLimit() {
        return creditPolicy.getCreditLimit();
    }

    public String getStudentType() {
        return creditPolicy.getStudentType();
    }

    public boolean canEnroll(int credits) {
        return currentCredits + credits <= getCreditLimit();
    }

    public void addCredits(int credits) {
        currentCredits += credits;
    }

    public void removeCredits(int credits) {
        currentCredits -= credits;
    }

    public boolean isEnrolled(Elective elective) {
        return enrolledElectives.contains(elective);
    }

    public void addElective(Elective elective) {
        if (!enrolledElectives.contains(elective)) {
            enrolledElectives.add(elective);
        }
    }

    public void removeElective(Elective elective) {
        enrolledElectives.remove(elective);
    }
}

class Enrollment {
    private Student student;
    private Elective elective;

    public Enrollment(Student student, Elective elective) {
        this.student = student;
        this.elective = elective;
    }

    public Student getStudent() {
        return student;
    }
}

class Elective {
    private String name;
    private int credits;
    private int capacity;
    private List<Enrollment> enrolledStudents;
    private Queue<Student> waitlist;

    public Elective(String name, int credits, int capacity) {
        this.name = name;
        this.credits = credits;
        this.capacity = capacity;
        enrolledStudents = new ArrayList<>();
        waitlist = new LinkedList<>();
    }

    public String getName() {
        return name;
    }

    public int getCredits() {
        return credits;
    }

    public boolean isFull() {
        return enrolledStudents.size() >= capacity;
    }

    public boolean isEnrolled(Student student) {
        for (Enrollment enrollment : enrolledStudents) {
            if (enrollment.getStudent() == student) {
                return true;
            }
        }
        return false;
    }

    public boolean isWaiting(Student student) {
        return waitlist.contains(student);
    }

    public boolean enroll(Student student) {
        if (isFull()) {
            return false;
        }

        enrolledStudents.add(new Enrollment(student, this));
        student.addCredits(credits);
        student.addElective(this);
        return true;
    }

    public void addToWaitlist(Student student) {
        waitlist.offer(student);
    }

    public boolean drop(Student student) {
        for (int i = 0; i < enrolledStudents.size(); i++) {
            Enrollment enrollment = enrolledStudents.get(i);

            if (enrollment.getStudent() == student) {
                enrolledStudents.remove(i);
                student.removeCredits(credits);
                student.removeElective(this);
                return true;
            }
        }
        return false;
    }

    public Student promoteNextEligible() {
        int waitingCount = waitlist.size();

        for (int i = 0; i < waitingCount; i++) {
            Student student = waitlist.poll();

            if (student.canEnroll(credits)) {
                enroll(student);
                return student;
            } else {
                waitlist.offer(student);
            }
        }

        return null;
    }
}

class EnrollmentService {

    public void enrollStudent(Student student, Elective elective) {
        if (elective.isEnrolled(student) || elective.isWaiting(student)) {
            System.out.println("Enrollment failed: " + student.getName() +
                " is already enrolled or waitlisted.");
            return;
        }

        if (!student.canEnroll(elective.getCredits())) {
            System.out.println("Enrollment failed: " + student.getName() +
                " would exceed the " + student.getStudentType() +
                " credit limit (" +
                (student.getCurrentCredits() + elective.getCredits()) +
                "/" + student.getCreditLimit() + ").");
            return;
        }

        if (elective.isFull()) {
            elective.addToWaitlist(student);
            System.out.println(elective.getName() + " is full.");
            System.out.println(student.getName() +
                " added to waitlist (position " + getWaitlistPosition(elective, student) + ").");
            return;
        }

        elective.enroll(student);

        System.out.println(student.getName() + " enrolled in " +
            elective.getName() + " (credits: " +
            student.getCurrentCredits() + "/" +
            student.getCreditLimit() + ").");
    }

    private int getWaitlistPosition(Elective elective, Student student) {
        return 1;
    }

    public void dropStudent(Student student, Elective elective) {
        if (!elective.drop(student)) {
            System.out.println("Drop failed: " + student.getName() +
                " is not enrolled in " + elective.getName() + ".");
            return;
        }

        System.out.println(student.getName() + " dropped " +
            elective.getName() + " (credits: " +
            student.getCurrentCredits() + "/" +
            student.getCreditLimit() + ").");

        Student promoted = elective.promoteNextEligible();

        if (promoted != null) {
            System.out.println(promoted.getName() +
                " promoted from waitlist and enrolled in " +
                elective.getName() + " (credits: " +
                promoted.getCurrentCredits() + "/" +
                promoted.getCreditLimit() + ").");
        }
    }
}

public class Main4 {
    public static void main(String[] args) {
        EnrollmentService service = new EnrollmentService();

        Elective cloudComputing =
            new Elective("Cloud Computing", 4, 2);

        Student asha = new Student("Asha", 20, new RegularPolicy());
        Student ravi = new Student("Ravi", 22, new HonorsPolicy());
        Student neha = new Student("Neha", 12, new ExchangePolicy());
        Student kiran = new Student("Kiran", 22, new RegularPolicy());

        service.enrollStudent(asha, cloudComputing);
        service.enrollStudent(ravi, cloudComputing);
        service.enrollStudent(neha, cloudComputing);
        service.enrollStudent(kiran, cloudComputing);

        service.dropStudent(asha, cloudComputing);
    }
}

