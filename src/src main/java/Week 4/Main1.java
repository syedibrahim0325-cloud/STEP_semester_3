
class Canteen {
    public String canteenCode;
    public String canteenName;
    public int trustScore;

    // Main constructor
    public Canteen(String canteenCode, String canteenName, int trustScore) {
        this.canteenCode = canteenCode;
        this.canteenName = canteenName;
        this.trustScore = trustScore;
    }

    // Constructor with default trust score = 3
    public Canteen(String canteenCode, String canteenName) {
        this(canteenCode, canteenName, 3);
    }

    // Comparison method
    public int compareTo(Canteen other) {

        // Higher trust score should come first
        if (this.trustScore != other.trustScore) {
            return other.trustScore - this.trustScore;
        }

        // Tie-break: compare codes ignoring case
        return this.canteenCode.compareToIgnoreCase(other.canteenCode);
    }

    // Manual ranking using selection sort
    public static Canteen[] rankCanteens(Canteen[] canteens) {

        for (int i = 0; i < canteens.length - 1; i++) {

            int best = i;

            for (int j = i + 1; j < canteens.length; j++) {

                if (canteens[j].compareTo(canteens[best]) < 0) {
                    best = j;
                }
            }

            // Swap
            Canteen temp = canteens[i];
            canteens[i] = canteens[best];
            canteens[best] = temp;
        }

        return canteens;
    }
}


public class Main1 {
    public static void main(String[] args) {

        Canteen[] canteens = {
            new Canteen("HB3-C", "Spice Junction", 3),
            new Canteen("hb1-c", "Grand Mess", 5),
            new Canteen("HB2-C", "Southern Treats")
        };

        Canteen[] ranked = Canteen.rankCanteens(canteens);

        for (Canteen c : ranked) {
            System.out.println(c.canteenCode);
        }
    }
}

