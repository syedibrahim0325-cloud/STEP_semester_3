
final class SurgeFeeCalculator {

    // Configured minimum surge percentage
    private final double minimumSurgePercent;

    public SurgeFeeCalculator(double minimumSurgePercent) {
        this.minimumSurgePercent = minimumSurgePercent;
    }

    // Calculation rule is final
    public final double calculateSurgeFee(double orderValue, int delayMinutes) {

        // Validate at calculation time
        if (orderValue < 0 || delayMinutes < 0) {
            throw new IllegalArgumentException(
                "Order value and delay cannot be negative"
            );
        }

        // No delay = no surge fee
        if (delayMinutes == 0) {
            return 0.0;
        }

        double surgePercent = 0.0;

        // First 5 minutes: 0.5% per minute
        int firstTier = Math.min(delayMinutes, 5);
        surgePercent += firstTier * 0.5;

        // Minutes 6 to 15: 1% per minute
        if (delayMinutes > 5) {
            int secondTier = Math.min(delayMinutes, 15) - 5;
            surgePercent += secondTier * 1.0;
        }

        // Minutes 16 onward: 2% per minute
        if (delayMinutes > 15) {
            int thirdTier = delayMinutes - 15;
            surgePercent += thirdTier * 2.0;
        }

        // Apply configured minimum only when delayed
        surgePercent = Math.max(surgePercent, minimumSurgePercent);

        return orderValue * surgePercent / 100.0;
    }
}


public class Main2 {
    public static void main(String[] args) {

        SurgeFeeCalculator calculator =
            new SurgeFeeCalculator(1.0);

        System.out.println("Rs " +
            calculator.calculateSurgeFee(500, 0));

        System.out.println("Rs " +
            calculator.calculateSurgeFee(500, 1));

        System.out.println("Rs " +
            calculator.calculateSurgeFee(500, 16));
    }
}

