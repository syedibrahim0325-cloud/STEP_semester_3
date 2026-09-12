
class FeeAccount {
    private int regNo;
    private double totalFee;
    private double amountPaid;

    // Constructor
    FeeAccount(int regNo, double totalFee, double amountPaid) {
        this.regNo = regNo;
        this.totalFee = totalFee;
        this.amountPaid = amountPaid;
    }

    // Pay method
    void pay(double amount) {
        if (amount > 0) {
            amountPaid += amount;
        } else {
            System.out.println("Payment must be positive.");
        }
    }

    // Calculate outstanding amount
    double getDue() {
        return totalFee - amountPaid;
    }
}


// Child class 1
class HostelFeeAccount extends FeeAccount {

    HostelFeeAccount(int regNo, double totalFee, double amountPaid) {
        super(regNo, totalFee, amountPaid);
    }

    void payInTwoInstallments(double amount) {
        pay(amount / 2);
        pay(amount / 2);
    }
}


// Child class 2
class ScholarshipFeeAccount extends FeeAccount {

    private double scholarshipPercent;

    ScholarshipFeeAccount(int regNo, double totalFee,
                          double amountPaid, double scholarshipPercent) {
        super(regNo, totalFee, amountPaid);

        if (scholarshipPercent >= 0 && scholarshipPercent <= 100) {
            this.scholarshipPercent = scholarshipPercent;
        } else {
            this.scholarshipPercent = 0;
        }
    }

    double effectiveDue() {
        double due = getDue();
        return due - (due * scholarshipPercent / 100);
    }
}


// Main class
public class FEE {

    public static void main(String[] args) {

        // Plain account
        FeeAccount plain = new FeeAccount(101, 150000, 0);
        plain.pay(150000);

        // Hostel account
        HostelFeeAccount hostel =
                new HostelFeeAccount(102, 200000, 0);
        hostel.pay(60000);

        // Scholarship account
        ScholarshipFeeAccount scholarship =
                new ScholarshipFeeAccount(103, 180000, 0, 20);

        // Array containing all three account types
        FeeAccount[] accounts = {plain, hostel, scholarship};

        for (FeeAccount account : accounts) {

            if (account instanceof HostelFeeAccount) {
                // Extra behaviour of HostelFeeAccount
                // (not needed here because 60000 is already paid)
            }

            if (account instanceof ScholarshipFeeAccount) {
                ScholarshipFeeAccount s =
                        (ScholarshipFeeAccount) account;

                System.out.println("Scholarship account effective due: Rs "
                        + s.effectiveDue());

            } else if (account instanceof HostelFeeAccount) {

                System.out.println("Hostel account due: Rs "
                        + account.getDue());

            } else {

                System.out.println("Plain account due: Rs "
                        + account.getDue());
            }
        }
    }
}

