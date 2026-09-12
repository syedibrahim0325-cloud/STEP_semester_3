
// Broken version
class BrokenLibraryMember {

    static String name;
    static String memberId;
    static int booksIssued;

    BrokenLibraryMember(String name, String memberId, int booksIssued) {
        BrokenLibraryMember.name = name;
        BrokenLibraryMember.memberId = memberId;
        BrokenLibraryMember.booksIssued = booksIssued;
    }

    /*
     * Why static is WRONG here:
     *
     * name:
     * Each library member has a different name, so it must belong
     * to each individual object.
     *
     * memberId:
     * Every member must have a different ID, so it cannot be shared.
     *
     * booksIssued:
     * Each member can issue a different number of books, so this
     * must also belong to each individual object.
     *
     * Because all three fields are static, there is only ONE copy
     * of each field for the entire class. Creating the second member
     * overwrites the first member's data.
     */
}


// Corrected version
class LibraryMember {

    // Static fields: shared by all members
    static String libraryName = "SRM Library";
    static int memberCount = 0;

    // Instance fields: separate copy for every member
    String name;
    String memberId;
    int booksIssued;

    LibraryMember(String name, int booksIssued) {
        this.name = name;
        this.booksIssued = booksIssued;

        memberCount++;

        // Automatically generate member ID
        this.memberId = "LM-" + (1000 + memberCount);
    }

    // Instance method: works on one particular member
    void printMemberCard() {
        System.out.println(name + " | " + memberId);
    }

    // Static method: works on the whole class
    static void printTotalMembers() {
        System.out.println("Total members: " + memberCount);
    }
}


public class Main {

    public static void main(String[] args) {

        // --------------------------------
        // BROKEN VERSION
        // --------------------------------

        System.out.println("Broken version:");

        BrokenLibraryMember member1 =
                new BrokenLibraryMember("Aditi", "LM-1001", 2);

        BrokenLibraryMember member2 =
                new BrokenLibraryMember("Rohan", "LM-1002", 3);

        // Both now show Rohan because the second object
        // overwrote the shared static fields.
        System.out.println(member1.name);
        System.out.println(member2.name);


        // --------------------------------
        // FIXED VERSION
        // --------------------------------

        System.out.println("\nFixed version:");

        LibraryMember m1 =
                new LibraryMember("Aditi", 2);

        LibraryMember m2 =
                new LibraryMember("Rohan", 3);

        m1.printMemberCard();
        m2.printMemberCard();

        LibraryMember.printTotalMembers();
    }
}

