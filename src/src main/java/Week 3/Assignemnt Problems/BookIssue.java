
class BookIssue {

    String title;
    String borrowerName;
    int daysOverdue;

    // Constructor
    BookIssue(String title, String borrowerName, int daysOverdue) {
        this.title = title;
        this.borrowerName = borrowerName;
        this.daysOverdue = daysOverdue;
    }

    // Calculate fine for one book
    double fineAmount() {
        if (daysOverdue > 0) {
            return daysOverdue * 5;
        } else {
            return 0;
        }
    }

    // Check whether one book is severely overdue
    boolean isSeverelyOverdue() {
        return daysOverdue > 14;
    }

    /*
     * totalFineCollected() is static because it calculates the
     * total fine for an array of many BookIssue objects.
     *
     * fineAmount() is not static because it calculates the fine
     * for one particular BookIssue object.
     */
    static double totalFineCollected(BookIssue[] issues) {

        double total = 0;

        for (BookIssue issue : issues) {
            total += issue.fineAmount();
        }

        return total;
    }


    public static void main(String[] args) {

        // Array of five BookIssue objects
        BookIssue[] issues = {
            new BookIssue("Clean Code", "Ravi", 18),
            new BookIssue("Effective Java", "Anitha", 5),
            new BookIssue("Refactoring", "Karthik", 0),
            new BookIssue("DSA Handbook", "Meera", 21),
            new BookIssue("Design Patterns", "Suresh", 9)
        };


        // Print overdue status of each book
        for (BookIssue issue : issues) {

            if (issue.isSeverelyOverdue()) {

                System.out.println(issue.title + " - "
                        + issue.daysOverdue
                        + " days - Severely overdue");

            } else {

                System.out.println(issue.title + " - "
                        + issue.daysOverdue
                        + " days - OK");
            }
        }


        // Calculate total fine using class name
        double total = BookIssue.totalFineCollected(issues);

        System.out.println("Total fine collected: Rs " + total);
    }
}

