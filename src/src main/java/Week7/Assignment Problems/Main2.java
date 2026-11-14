
interface Exportable {
    String exportData();
}

// ReportGenerator class
class ReportGenerator implements Exportable {

    private String reportName;

    // Shared counter
    private static int totalExports = 0;

    public ReportGenerator(String reportName) {
        this.reportName = reportName;
    }

    @Override
    public String exportData() {
        totalExports++;
        return "Exported report: " + reportName;
    }

    public static int getTotalExports() {
        return totalExports;
    }
}

// UserProfile class
class UserProfile implements Exportable {

    private String username;

    public UserProfile(String username) {
        this.username = username;
    }

    @Override
    public String exportData() {
        ReportGenerator.incrementExports();
        return "Exported profile: " + username;
    }
}

// Main class
public class Main2 {

    // Export all items
    public static void exportAll(Exportable[] items) {

        for (Exportable item : items) {
            System.out.println(item.exportData());
        }
    }

    public static void main(String[] args) {

        ReportGenerator r = new ReportGenerator("Sales Q1");
        UserProfile u = new UserProfile("jane_doe");

        // Individual exports
        System.out.println(r.exportData());
        System.out.println(u.exportData());

        // Upcasting
        Exportable ref = r;

        // Mixed array
        Exportable[] items = {ref, u};

        exportAll(items);

        System.out.println("Total exports: "
                + ReportGenerator.getTotalExports());
    }
}
