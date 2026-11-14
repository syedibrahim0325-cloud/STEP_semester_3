
interface Alertable {
    String sendAlert(String message);
}

// Base class
class SecuritySensor {

    protected String zoneName;

    public SecuritySensor(String zoneName) {
        this.zoneName = zoneName;
    }

    public String getZoneName() {
        return zoneName;
    }
}

// Child class implementing interface
class MotionSensor extends SecuritySensor implements Alertable {

    public MotionSensor(String zoneName) {
        super(zoneName);
    }

    @Override
    public String sendAlert(String message) {
        return "[" + zoneName + "] " + message;
    }
}

// Multilevel inheritance
class DualZoneMotionSensor extends MotionSensor {

    private String secondZoneName;

    public DualZoneMotionSensor(String zoneName, String secondZoneName) {
        super(zoneName);
        this.secondZoneName = secondZoneName;
    }

    @Override
    public String sendAlert(String message) {
        return super.sendAlert(message) +
               " [also covering " + secondZoneName + "]";
    }
}

// Independent class implementing the same interface
class SmokeDetector implements Alertable {

    private String deviceId;

    public SmokeDetector(String deviceId) {
        this.deviceId = deviceId;
    }

    @Override
    public String sendAlert(String message) {
        return "[" + deviceId + "] " + message;
    }
}

public class Main2 {

    // Broadcast alert to all devices
    static void broadcastAll(Alertable[] devices, String message) {

        for (Alertable device : devices) {
            System.out.println(device.sendAlert(message));
        }
    }

    // Safe downcasting using instanceof
    static String getZoneIfMotionSensor(Alertable a) {

        if (a instanceof MotionSensor) {
            MotionSensor m = (MotionSensor) a;
            return m.getZoneName();
        }

        return "Not a motion sensor";
    }

    public static void main(String[] args) {

        MotionSensor m = new MotionSensor("Living Room");

        DualZoneMotionSensor d =
                new DualZoneMotionSensor("Hallway", "Stairwell");

        SmokeDetector s = new SmokeDetector("SD-01");

        System.out.println(m.sendAlert("Motion detected"));

        System.out.println(d.sendAlert("Motion detected"));

        System.out.println(s.sendAlert("Smoke detected"));

        // Mixed array containing different device types
        Alertable[] devices = {m, d, s};

        System.out.println("\nBroadcasting alerts:");

        broadcastAll(devices, "Emergency detected");

        System.out.println("\nChecking device zones:");

        System.out.println(getZoneIfMotionSensor(m));
        System.out.println(getZoneIfMotionSensor(s));
    }
}

