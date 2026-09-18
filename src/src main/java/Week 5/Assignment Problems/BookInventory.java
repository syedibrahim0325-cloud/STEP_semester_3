class BookInventory {

    private int copiesTotal;
    private int copiesAvailable;

    BookInventory(int copiesTotal) {

        if (copiesTotal <= 0) {
            throw new IllegalArgumentException("Invalid copies total");
        }

        this.copiesTotal = copiesTotal;
        this.copiesAvailable = copiesTotal;
    }

    void checkOut() {

        if (copiesAvailable > 0) {
            copiesAvailable--;
        }
    }

    void checkIn() {

        if (copiesAvailable < copiesTotal) {
            copiesAvailable++;
        }
    }

    int getCopiesAvailable() {
        return copiesAvailable;
    }
}