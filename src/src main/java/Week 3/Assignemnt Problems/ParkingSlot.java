class ParkingSlot {
    String slotNo;
    int capacity;
    int occupiedCount;

    ParkingSlot(String slotNo, int capacity, int occupiedCount) {
        this.slotNo = slotNo;
        this.capacity = capacity;
        this.occupiedCount = occupiedCount;
    }

    void allot(String vehicleNo) {
        if (occupiedCount < capacity) {
            occupiedCount++;
            System.out.println(vehicleNo + " allotted to slot " + slotNo);
        }
    }

    static ParkingSlot findAvailableSlot(ParkingSlot[] slots) {
        for (ParkingSlot slot : slots) {
            if (slot != null && slot.occupiedCount < slot.capacity) {
                return slot;
            }
        }

        return null;
    }

    static void safeAllot(ParkingSlot[] slots, String vehicleNo) {
        ParkingSlot slot = findAvailableSlot(slots);

        // Check for null before accessing the slot.
        if (slot != null) {
            slot.allot(vehicleNo);
        } else {
            System.out.println("No slots available for vehicle " + vehicleNo);
        }
    }

    /*
     * ParkingSlot[] contains references to ParkingSlot objects.
     * Passing the array to a method does not copy the actual slot objects.
     * Therefore, changes made to a slot inside the method affect the
     * original ParkingSlot object.
     */

    public static void main(String[] args) {

        // Case 1: An available slot exists
        ParkingSlot[] availableSlots = {
            new ParkingSlot("P101", 2, 1),
            new ParkingSlot("P102", 2, 2)
        };

        safeAllot(availableSlots, "TN01AB1234");


        // Case 2: All slots are full
        ParkingSlot[] fullSlots = {
            new ParkingSlot("P201", 2, 2),
            new ParkingSlot("P202", 1, 1)
        };

        safeAllot(fullSlots, "TN02CD5678");
    }
}

