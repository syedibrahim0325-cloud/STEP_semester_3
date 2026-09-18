class RaceEntry {

    private String bibNumber;
    private double entryFee;
    private double amountPaid;

    // Shared by ALL RaceEntry objects
    private static int bibCounter = 0;

    // Cannot be changed after construction
    private final String entryCode;

    public RaceEntry(String bibNumber, double entryFee) {

        if (bibNumber == null || bibNumber.trim().length() < 4) {
            throw new IllegalArgumentException("Invalid bib number");
        }

        this.bibNumber = bibNumber;
        this.entryFee = entryFee;
        this.amountPaid = 0;

        // Increment exactly once for every successful object
        bibCounter++;

        // Create permanent entry code
        this.entryCode = "ENTRY" + bibCounter;
    }

    public void pay(double amount) {
        amountPaid += amount;
    }

    // Overloaded method
    public void pay(double amount, String mode) {
        pay(amount);
        System.out.println("Paying via " + mode);
    }

    public double getBalanceDue() {
        return entryFee - amountPaid;
    }

    public String getEntryCode() {
        return entryCode;
    }

    public String getBibNumber() {
        return bibNumber;
    }

    public double getEntryFee() {
        return entryFee;
    }

    public static boolean isValidDiscountCode(String code) {

        if (code == null || code.length() != 5) {
            return false;
        }

        if (code.charAt(0) != 'M') {
            return false;
        }

        if (!Character.isDigit(code.charAt(1))) {
            return false;
        }

        if (!Character.isDigit(code.charAt(2))) {
            return false;
        }

        if (!Character.isDigit(code.charAt(3))) {
            return false;
        }

        if (!Character.isUpperCase(code.charAt(4))) {
            return false;
        }

        return true;
    }

    public static int getBibCounter() {
        return bibCounter;
    }

    public static String settleNight(RaceEntry[] entries) {

        int processed = 0;
        int nullSkipped = 0;
        int relayCount = 0;
        int individualCount = 0;

        for (RaceEntry entry : entries) {

            if (entry == null) {
                nullSkipped++;
                continue;
            }

            processed++;

            if (entry instanceof RelayTeamEntry) {
                relayCount++;
            } else {
                individualCount++;
            }
        }

        return processed + " processed | "
                + nullSkipped + " null skipped | "
                + relayCount + " relay | "
                + individualCount + " individual";
    }
}

class RunnerEntry extends RaceEntry {

    private String category;

    public RunnerEntry(String bibNumber, double entryFee,
                       String category) {
        super(bibNumber, entryFee);
        this.category = category;
    }
}

class EliteRunnerEntry extends RunnerEntry {

    private double sponsorBonus;

    public EliteRunnerEntry(String bibNumber, double entryFee,
                            String category, double sponsorBonus) {
        super(bibNumber, entryFee, category);
        this.sponsorBonus = sponsorBonus;
    }
}

class RelayTeamEntry extends RaceEntry {

    private int teamSize;

    public RelayTeamEntry(String bibNumber, double entryFee,
                          int teamSize) {
        super(bibNumber, entryFee);
        this.teamSize = teamSize;
    }
}

public class Main5 {

    public static void main(String[] args) {

        RaceEntry r1 =
                new RaceEntry("BIB1001", 100);

        RunnerEntry r2 =
                new RunnerEntry("BIB2001", 80, "Open 10K");

        EliteRunnerEntry elite =
                new EliteRunnerEntry(
                        "BIB3001", 150,
                        "Elite Marathon", 500);

        RelayTeamEntry relay =
                new RelayTeamEntry("BIB4001", 300, 4);

        System.out.println(r1.getEntryCode());
        System.out.println(r2.getEntryCode());
        System.out.println(elite.getEntryCode());
        System.out.println(relay.getEntryCode());

        System.out.println(
                RaceEntry.isValidDiscountCode("M123A")
        );

        System.out.println(
                RaceEntry.isValidDiscountCode("M12A")
        );

        System.out.println(
                RaceEntry.isValidDiscountCode("X123A")
        );

        r1.pay(10, "UPI");

        RaceEntry[] entries = {
                elite,
                null,
                relay
        };

        System.out.println(
                RaceEntry.settleNight(entries)
        );

        System.out.println(
                RaceEntry.getBibCounter()
        );
    }
}