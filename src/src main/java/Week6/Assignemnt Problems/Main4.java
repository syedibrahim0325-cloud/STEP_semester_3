class RaceEntry {
    private String bibNumber;
    private double entryFee;
    private double amountPaid;

    public RaceEntry(String bibNumber, double entryFee) {
        if (bibNumber == null || bibNumber.trim().length() < 4) {
            throw new IllegalArgumentException("Invalid bib number");
        }

        this.bibNumber = bibNumber;
        this.entryFee = entryFee;
        this.amountPaid = 0;
    }

    public void pay(double amount) {
        amountPaid += amount;
    }

    public double getBalanceDue() {
        return entryFee - amountPaid;
    }

    public String getBibNumber() {
        return bibNumber;
    }

    public double getEntryFee() {
        return entryFee;
    }

    public String announce() {
        return "Race Entry | Bib: " + bibNumber
                + " | Balance: " + getBalanceDue();
    }

    public static String announceAll(RaceEntry[] entries) {
        StringBuilder report = new StringBuilder();

        for (RaceEntry entry : entries) {

            // Polymorphism
            report.append(entry.announce());

            // Safe downcast
            if (entry instanceof RelayTeamEntry) {
                RelayTeamEntry relay = (RelayTeamEntry) entry;

                report.append(" [Team size via downcast: ")
                      .append(relay.getTeamSize())
                      .append("]");
            }

            report.append(" | ");
        }

        return report.toString();
    }
}

class RunnerEntry extends RaceEntry {
    private String category;

    public RunnerEntry(String bibNumber, double entryFee, String category) {
        super(bibNumber, entryFee);
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    @Override
    public String announce() {
        return "Runner Entry | Bib: " + getBibNumber()
                + " | Category: " + category
                + " | Balance: " + getBalanceDue();
    }
}

class RelayTeamEntry extends RaceEntry {
    private int teamSize;

    public RelayTeamEntry(String bibNumber, double entryFee, int teamSize) {
        super(bibNumber, entryFee);
        this.teamSize = teamSize;
    }

    public int getTeamSize() {
        return teamSize;
    }

    @Override
    public String announce() {
        return "Relay Team | Bib: " + getBibNumber()
                + " | Team Size: " + teamSize
                + " | Balance: " + getBalanceDue();
    }
}

public class Main4{
    public static void main(String[] args) {

        RunnerEntry runner =
                new RunnerEntry("BIB2001", 80, "Open 10K");

        runner.pay(-10); // only for demonstration of the given balance

        RelayTeamEntry relay =
                new RelayTeamEntry("BIB4001", 300, 4);

        RaceEntry[] fleet = {runner, relay};

        System.out.println(
                RaceEntry.announceAll(fleet)
        );
    }
}