
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

enum LeaveStatus {
    PENDING,
    APPROVED,
    REJECTED
}

interface LeavePolicy {
    boolean isLeaveAllowed(LocalDate startDate, LocalDate endDate);
}

abstract class Employee implements LeavePolicy {
    private int employeeId;
    private String name;

    public Employee(int employeeId, String name) {
        this.employeeId = employeeId;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract int getMaximumLeaveDays();

    @Override
    public boolean isLeaveAllowed(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null ||
            endDate.isBefore(startDate)) {
            return false;
        }

        long days = ChronoUnit.DAYS.between(startDate, endDate) + 1;
        return days <= getMaximumLeaveDays();
    }
}

class FullTimeEmployee extends Employee {
    public FullTimeEmployee(int id, String name) {
        super(id, name);
    }

    @Override
    public int getMaximumLeaveDays() {
        return 30;
    }
}

class PartTimeEmployee extends Employee {
    public PartTimeEmployee(int id, String name) {
        super(id, name);
    }

    @Override
    public int getMaximumLeaveDays() {
        return 15;
    }
}

class ContractEmployee extends Employee {
    public ContractEmployee(int id, String name) {
        super(id, name);
    }

    @Override
    public int getMaximumLeaveDays() {
        return 7;
    }
}

class LeaveRequest {
    private int requestId;
    private Employee employee;
    private LocalDate startDate;
    private LocalDate endDate;
    private LeaveStatus status;

    public LeaveRequest(int requestId, Employee employee,
                        LocalDate startDate, LocalDate endDate) {
        this.requestId = requestId;
        this.employee = employee;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = LeaveStatus.PENDING;
    }

    public Employee getEmployee() {
        return employee;
    }

    public LeaveStatus getStatus() {
        return status;
    }

    public boolean updateStatus(LeaveStatus newStatus) {
        if (status != LeaveStatus.PENDING) {
            System.out.println("Cannot change status: " +
                status + " request cannot revert to Pending.");
            return false;
        }

        if (newStatus == LeaveStatus.PENDING) {
            System.out.println("Invalid status transition.");
            return false;
        }

        status = newStatus;
        return true;
    }
}

class LeaveManager {
    private int nextRequestId = 1;

    public LeaveRequest submitLeaveRequest(Employee employee,
                                           LocalDate startDate,
                                           LocalDate endDate) {
        if (!employee.isLeaveAllowed(startDate, endDate)) {
            System.out.println("Leave request rejected: Invalid dates or leave limit exceeded.");
            return null;
        }

        LeaveRequest request = new LeaveRequest(
            nextRequestId++, employee, startDate, endDate
        );

        System.out.println("Leave request submitted by " +
            employee.getName() + " for " + startDate +
            " to " + endDate + ". Status: Pending.");

        return request;
    }

    public void reviewRequest(LeaveRequest request, LeaveStatus decision) {
        if (request == null) {
            return;
        }

        if (decision != LeaveStatus.APPROVED &&
            decision != LeaveStatus.REJECTED) {
            System.out.println("Invalid review decision.");
            return;
        }

        if (request.updateStatus(decision)) {
            System.out.println("Leave request for " +
                request.getEmployee().getName() + " " +
                decision.toString().toLowerCase() +
                ". Status: " + decision + ".");
        }
    }

    public void changeRequestStatus(LeaveRequest request,
                                    LeaveStatus newStatus) {
        if (request != null) {
            request.updateStatus(newStatus);
        }
    }
}

public class Main4
 {
    public static void main(String[] args) {
        LeaveManager manager = new LeaveManager();

        Employee john = new FullTimeEmployee(1, "John Doe");
        Employee jane = new PartTimeEmployee(2, "Jane Smith");

        LeaveRequest request1 = manager.submitLeaveRequest(
            john,
            LocalDate.of(2024, 10, 10),
            LocalDate.of(2024, 10, 12)
        );

        manager.reviewRequest(request1, LeaveStatus.APPROVED);

        LeaveRequest request2 = manager.submitLeaveRequest(
            jane,
            LocalDate.of(2024, 11, 1),
            LocalDate.of(2024, 11, 5)
        );

        manager.changeRequestStatus(request1, LeaveStatus.PENDING);
    }
}
