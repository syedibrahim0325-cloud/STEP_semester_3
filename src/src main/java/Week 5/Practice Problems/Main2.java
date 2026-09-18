class PatientVitals {

    private double[] readings;
    private int count;

    PatientVitals(double[] initialReadings) {
        readings = new double[500];
        count = 0;

        for (double reading : initialReadings) {
            recordReading(reading);
        }
    }

    void recordReading(double reading) {
        if (reading > 0 && reading <= 45 && count < 500) {
            readings[count] = reading;
            count++;
        }
    }

    double getAverage() {
        if (count == 0) {
            return 0;
        }

        double sum = 0;

        for (int i = 0; i < count; i++) {
            sum += readings[i];
        }

        return sum / count;
    }

    double[] getAllReadings() {
        double[] copy = new double[count];

        for (int i = 0; i < count; i++) {
            copy[i] = readings[i];
        }

        return copy;
    }
}

public class Main2 {

    public static void main(String[] args) {

        PatientVitals v = new PatientVitals(
            new double[]{36.5, -2, 37.1}
        );

        double[] readings = v.getAllReadings();

        for (double reading : readings) {
            System.out.print(reading + " ");
        }

        System.out.println();

        System.out.println("Average: " + v.getAverage());

        // Testing defensive copy
        readings[0] = 999;

        System.out.println("After modifying copy:");

        double[] newReadings = v.getAllReadings();

        for (double reading : newReadings) {
            System.out.print(reading + " ");
        }
    }
}