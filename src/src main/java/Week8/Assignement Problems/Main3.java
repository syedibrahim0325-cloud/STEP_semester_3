
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

interface Capability {
    boolean applyValue(int value);
    String getName();
}

class PowerCapability implements Capability {
    private boolean isOn;

    @Override
    public boolean applyValue(int value) {
        if (value != 0 && value != 1) {
            return false;
        }

        isOn = (value == 1);
        return true;
    }

    @Override
    public String getName() {
        return "Power";
    }

    public String getStatus() {
        return isOn ? "ON" : "OFF";
    }
}

class BrightnessCapability implements Capability {
    private int brightness;

    @Override
    public boolean applyValue(int value) {
        if (value < 0 || value > 100) {
            return false;
        }

        brightness = value;
        return true;
    }

    @Override
    public String getName() {
        return "Brightness";
    }

    public int getBrightness() {
        return brightness;
    }
}

class TemperatureCapability implements Capability {
    private int temperature;

    @Override
    public boolean applyValue(int value) {
        if (value < 16 || value > 30) {
            return false;
        }

        temperature = value;
        return true;
    }

    @Override
    public String getName() {
        return "Temperature";
    }

    public int getTemperature() {
        return temperature;
    }
}

class Device {
    private String name;
    private Map<String, Capability> capabilities;

    public Device(String name) {
        this.name = name;
        capabilities = new LinkedHashMap<>();
    }

    public String getName() {
        return name;
    }

    public void addCapability(Capability capability) {
        if (capabilities.containsKey(capability.getName())) {
            return;
        }

        capabilities.put(capability.getName(), capability);
        System.out.println(name + ": " + capability.getName() +
            " capability added.");
    }

    public boolean hasCapability(String capabilityName) {
        return capabilities.containsKey(capabilityName);
    }

    public void applyCapability(String capabilityName, int value) {
        Capability capability = capabilities.get(capabilityName);

        if (capability == null) {
            return;
        }

        if (!capability.applyValue(value)) {
            if (capabilityName.equals("Temperature")) {
                System.out.println("Rejected: " + name +
                    " temperature must be between 16°C and 30°C.");
            } else if (capabilityName.equals("Brightness")) {
                System.out.println("Rejected: " + name +
                    " brightness must be between 0% and 100%.");
            } else {
                System.out.println("Rejected: Invalid power value for " + name + ".");
            }
            return;
        }

        if (capability instanceof PowerCapability) {
            System.out.println(name + ": " +
                ((PowerCapability) capability).getStatus() + ".");
        } else if (capability instanceof BrightnessCapability) {
            System.out.println(name + ": brightness set to " +
                ((BrightnessCapability) capability).getBrightness() + "%.");
        } else if (capability instanceof TemperatureCapability) {
            System.out.println(name + ": temperature set to " +
                ((TemperatureCapability) capability).getTemperature() + "°C.");
        }
    }
}

class SceneStep {
    private String capabilityName;
    private int value;

    public SceneStep(String capabilityName, int value) {
        this.capabilityName = capabilityName;
        this.value = value;
    }

    public String getCapabilityName() {
        return capabilityName;
    }

    public int getValue() {
        return value;
    }
}

class Scene {
    private String name;
    private List<SceneStep> steps;

    public Scene(String name) {
        this.name = name;
        steps = new ArrayList<>();
    }

    public void addStep(SceneStep step) {
        steps.add(step);
    }

    public void execute(List<Device> devices) {
        System.out.println("Scene '" + name + "' started.");

        int actionsApplied = 0;

        for (SceneStep step : steps) {
            for (Device device : devices) {
                if (device.hasCapability(step.getCapabilityName())) {
                    device.applyCapability(
                        step.getCapabilityName(),
                        step.getValue()
                    );
                    actionsApplied++;
                }
            }
        }

        System.out.println("Scene '" + name +
            "' completed: " + actionsApplied + " actions applied.");
    }
}

class Lab {
    private List<Device> devices;

    public Lab() {
        devices = new ArrayList<>();
    }

    public void addDevice(Device device) {
        devices.add(device);
    }

    public List<Device> getDevices() {
        return devices;
    }
}

public class Main3 {
    public static void main(String[] args) {
        Lab lab = new Lab();

        Device ac = new Device("Lab AC");
        ac.addCapability(new PowerCapability());
        ac.addCapability(new TemperatureCapability());

        Device lights = new Device("Ceiling Lights");
        lights.addCapability(new PowerCapability());
        lights.addCapability(new BrightnessCapability());

        Device projector = new Device("Projector");
        projector.addCapability(new PowerCapability());

        lab.addDevice(ac);
        lab.addDevice(lights);
        lab.addDevice(projector);

        Scene lectureMode = new Scene("Lecture Mode");

        lectureMode.addStep(new SceneStep("Power", 1));
        lectureMode.addStep(new SceneStep("Brightness", 40));
        lectureMode.addStep(new SceneStep("Temperature", 24));

        lectureMode.execute(lab.getDevices());

        ac.applyCapability("Temperature", 12);

        projector.addCapability(new BrightnessCapability());
        projector.applyCapability("Brightness", 70);
    }
}
