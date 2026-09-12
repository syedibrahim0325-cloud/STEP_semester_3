
class HostelRoom {

    String roomNo;
    int beds;
    int occupied;

    // Constructor
    HostelRoom(String roomNo, int beds, int occupied) {
        this.roomNo = roomNo;
        this.beds = beds;
        this.occupied = occupied;
    }

    // Allot one bed
    void allot(String name) {
        if (occupied < beds) {
            occupied++;
            System.out.println(name + " allotted to room " + roomNo);
        }
    }

    // Find the first room having an empty bed
    static HostelRoom findAvailableRoom(HostelRoom[] rooms) {

        for (HostelRoom room : rooms) {
            if (room != null && room.occupied < room.beds) {
                return room;
            }
        }

        // No room is available
        return null;
    }

    static void safeAllot(HostelRoom[] rooms, String studentName) {

        HostelRoom room = findAvailableRoom(rooms);

        // Check for null before using the room
        if (room != null) {
            room.allot(studentName);
        } else {
            System.out.println("No rooms available for " + studentName);
        }
    }

    public static void main(String[] args) {

        // Case 1: One room is available
        HostelRoom[] rooms1 = {
            new HostelRoom("C-214", 3, 2),
            new HostelRoom("C-507", 2, 2)
        };

        System.out.println("Rooms: C-214 (2/3), C-507 (2/2)");
        safeAllot(rooms1, "Divya");

        System.out.println();

        // Case 2: All rooms are full
        HostelRoom[] rooms2 = {
            new HostelRoom("C-214", 3, 3),
            new HostelRoom("C-507", 2, 2)
        };

        System.out.println("Rooms: C-214 (3/3), C-507 (2/2)");
        safeAllot(rooms2, "Divya");
    }
}

