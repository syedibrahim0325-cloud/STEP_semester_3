class EventTicket1 {

    protected String attendeeId;
    protected double basePrice;
    protected double amountPaid;

    public EventTicket1(String attendeeId, double basePrice) {
        if (attendeeId == null || attendeeId.trim().length() < 4) {
            throw new IllegalArgumentException("Invalid attendee ID");
        }

        this.attendeeId = attendeeId;
        this.basePrice = basePrice;
        this.amountPaid = 0.0;
    }

    public void pay(double amount) {
        if (amount > 0) {
            amountPaid += amount;
        }
    }

    public double getBalanceDue() {
        return basePrice - amountPaid;
    }

    public void printTicket() {
        System.out.println(
            "Standard Event Ticket | Balance Due: " + getBalanceDue()
        );
    }

    public static String registerBatch(String[] attendeeIds, double basePrice) {
        int registered = 0;
        int rejected = 0;

        for (String id : attendeeIds) {
            try {
                new EventTicket(id, basePrice);
                registered++;
            } catch (IllegalArgumentException e) {
                rejected++;
            }
        }

        return "Registered: " + registered + " | Rejected: " + rejected;
    }
}


class WorkshopTicket extends EventTicket1 {

    protected String track;

    public WorkshopTicket(String attendeeId, double basePrice, String track) {
        super(attendeeId, basePrice);
        this.track = track;
    }

    @Override
    public void printTicket() {
        System.out.println(
            "Workshop Ticket | Track: " + track +
            " | Balance Due: " + getBalanceDue()
        );
    }
}


class PremiumWorkshopTicket extends WorkshopTicket {

    private double kitFee;

    public PremiumWorkshopTicket(String attendeeId, double basePrice,
                                 String track, double kitFee) {
        super(attendeeId, basePrice, track);
        this.kitFee = kitFee;
    }

    @Override
    public double getBalanceDue() {
        return basePrice + kitFee - amountPaid;
    }

    @Override
    public void printTicket() {
        System.out.println(
            "Premium Workshop Ticket | Track: " + track +
            " | Kit Fee: " + kitFee +
            " | Balance Due: " + getBalanceDue()
        );
    }
}


class HackathonTicket extends EventTicket1 {

    private String teamName;

    public HackathonTicket(String attendeeId, double basePrice,
                           String teamName) {
        super(attendeeId, basePrice);
        this.teamName = teamName;
    }

    @Override
    public void printTicket() {
        System.out.println(
            "Hackathon Ticket | Team: " + teamName +
            " | Balance Due: " + getBalanceDue()
        );
    }
}


class TicketUtils {

    public static String classifyGeneration(EventTicket1 ticket) {

        if (ticket instanceof PremiumWorkshopTicket) {
            return "Multilevel descendant (3 generations deep)";
        }

        if (ticket instanceof HackathonTicket) {
            return "Hierarchical sibling (independent branch)";
        }

        if (ticket instanceof WorkshopTicket) {
            return "Intermediate descendant (2 generations deep)";
        }

        return "Base generation";
    }


    public static double getTotalBalanceDue(EventTicket1[] tickets) {

        double total = 0.0;

        for (EventTicket1 ticket : tickets) {
            total += ticket.getBalanceDue();
        }

        return total;
    }
}