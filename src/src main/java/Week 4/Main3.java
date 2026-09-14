class DeliveryAccount {

    protected String studentId;
    protected double orderValue;

    // Class-level one-time setup
    static {
        System.out.println("DeliveryAccount system initialized.");
    }

    // Full constructor
    public DeliveryAccount(String studentId, double orderValue) {
        this.studentId = studentId;
        this.orderValue = orderValue;
    }

    // Provisional constructor
    public DeliveryAccount(String studentId) {
        this(studentId, 0.0);
    }

    // Final surge-fee calculation
    public final double calculateSurgeFee(int delayMinutes) {

        if (delayMinutes < 0) {
            throw new IllegalArgumentException("Delay cannot be negative");
        }

        // Flat rate: 1% per delayed minute
        return orderValue * delayMinutes * 1.0 / 100.0;
    }

    public void processAccount(
            DeliveryAccount account,
            double amount,
            int delayMinutes) {

        double fee = account.calculateSurgeFee(delayMinutes);

        System.out.println(
            "Processed " + account.studentId +
            " | Amount: " + amount +
            " | Surge fee: Rs " + fee
        );
    }

    public static void processBatch(
            DeliveryAccount[] accounts,
            double[] amounts,
            int[] delayMinutesArray) {

        // Validate parallel arrays
        if (accounts == null ||
            amounts == null ||
            delayMinutesArray == null) {

            throw new IllegalArgumentException(
                "Input arrays cannot be null"
            );
        }

        if (accounts.length != amounts.length ||
            accounts.length != delayMinutesArray.length) {

            throw new IllegalArgumentException(
                "All arrays must have the same length"
            );
        }

        int processed = 0;
        int nullSkipped = 0;
        int premium = 0;
        int regular = 0;
        double grandTotal = 0.0;

        DeliveryAccount processor = new DeliveryAccount("SYSTEM", 0);

        for (int i = 0; i < accounts.length; i++) {

            DeliveryAccount account = accounts[i];

            // Handle null safely
            if (account == null) {
                nullSkipped++;
                continue;
            }

            processor.processAccount(
                account,
                amounts[i],
                delayMinutesArray[i]
            );

            processed++;

            if (account instanceof PremiumDeliveryAccount) {
                premium++;
            } else {
                regular++;
            }

            grandTotal += account.calculateSurgeFee(
                delayMinutesArray[i]
            );
        }

        System.out.println();
        System.out.println(
            processed + " processed | " +
            nullSkipped + " null skipped | " +
            premium + " premium | " +
            regular + " regular | " +
            "grand total surge fees = Rs " +
            grandTotal
        );
    }
}


// Premium account
class PremiumDeliveryAccount extends DeliveryAccount {

    public PremiumDeliveryAccount(
            String studentId,
            double orderValue) {

        super(studentId, orderValue);
    }

    public PremiumDeliveryAccount(String studentId) {
        super(studentId);
    }
}


public class Main3 {
    public static void main(String[] args) {

        DeliveryAccount[] accounts = {
            new PremiumDeliveryAccount("STU001", 500),
            null,
            new DeliveryAccount("STU002", 300)
        };

        double[] amounts = {
            500,
            400,
            300
        };

        int[] delayMinutesArray = {
            10,
            5,
            0
        };

        DeliveryAccount.processBatch(
            accounts,
            amounts,
            delayMinutesArray
        );
    }
}

