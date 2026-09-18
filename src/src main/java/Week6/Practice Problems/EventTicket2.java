class EventTicket2 {

    protected double basePrice;
    protected double amountPaid;

    private double[] lateFeeHistory;
    private int lateFeeCount;

    public EventTicket2(double basePrice) {
        this.basePrice = basePrice;
        this.amountPaid = 0.0;

        lateFeeHistory = new double[10];
        lateFeeCount = 0;
    }

    public void pay(double amount) {
        if (amount > 0) {
            amountPaid += amount;
        }
    }

    public double getBalanceDue() {
        return basePrice - amountPaid;
    }

    protected void applyLateFee(double amount) {
        amountPaid -= amount;

        if (lateFeeCount < lateFeeHistory.length) {
            lateFeeHistory[lateFeeCount] = amount;
            lateFeeCount++;
        }
    }

    public double[] getLateFeeHistory() {
        double[] copy = new double[lateFeeCount];

        for (int i = 0; i < lateFeeCount; i++) {
            copy[i] = lateFeeHistory[i];
        }

        return copy;
    }
}


class WorkshopTicket extends EventTicket2 {

    public WorkshopTicket(double basePrice) {
        super(basePrice);
    }

    @Override
    protected void applyLateFee(double amount) {
        super.applyLateFee(amount * 2);
    }
}