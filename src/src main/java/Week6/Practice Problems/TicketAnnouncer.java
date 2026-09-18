class EventTicket3 {

    protected double basePrice;

    public EventTicket3(double basePrice) {
        this.basePrice = basePrice;
    }

    public double getBalanceDue() {
        return basePrice;
    }

    public String printTicket() {
        return "Standard | Balance: " + getBalanceDue();
    }
}


class WorkshopTicket extends EventTicket3 {

    private String track;

    public WorkshopTicket(double basePrice, String track) {
        super(basePrice);
        this.track = track;
    }

    public String getTrack() {
        return track;
    }

    @Override
    public String printTicket() {
        return "Workshop | Track: " + track +
               " | Balance: " + getBalanceDue();
    }
}


class TicketAnnouncer {

    public static String batchPrint(EventTicket3[] tickets) {

        StringBuilder report = new StringBuilder();

        for (EventTicket3 ticket : tickets) {

            // Polymorphism
            report.append(ticket.printTicket());

            // Safe downcast
            if (ticket instanceof WorkshopTicket) {
                WorkshopTicket workshop = (WorkshopTicket) ticket;

                report.append(" [Track via downcast: ")
                      .append(workshop.getTrack())
                      .append("]");
            }

            report.append(" | ");
        }

        return report.toString();
    }
}