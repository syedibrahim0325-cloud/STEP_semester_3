
abstract class PaymentMethod {

    private static int counter = 1000;
    private final String transactionId;

    // Constructor
    public PaymentMethod() {
        counter++;
        transactionId = "TXN-" + counter;
    }

    // Abstract method
    public abstract String processPayment(double amount);

    // Overloaded method
    public String processPayment(double amount, String note) {
        return processPayment(amount) + " (" + note + ")";
    }

    // Getter
    public String getTransactionId() {
        return transactionId;
    }
}

// Credit Card Payment
class CreditCardPayment extends PaymentMethod {

    private String cardNumberLastFour;

    public CreditCardPayment(String cardNumberLastFour) {
        super();
        this.cardNumberLastFour = cardNumberLastFour;
    }

    @Override
    public String processPayment(double amount) {
        return "Charged $" + amount +
               " to card ending " + cardNumberLastFour +
               " - Txn " + getTransactionId();
    }
}

// Cash Payment
class CashPayment extends PaymentMethod {

    public CashPayment() {
        super();
    }

    @Override
    public String processPayment(double amount) {
        return "Received $" + amount +
               " in cash - Txn " + getTransactionId();
    }
}

public class Main {

    // Accepts parent class reference
    static void printConfirmation(PaymentMethod payment, double amount) {
        System.out.println(payment.processPayment(amount));
    }

    public static void main(String[] args) {

        CreditCardPayment cc = new CreditCardPayment("4471");

        System.out.println(cc.processPayment(250.0));

        CashPayment cash = new CashPayment();

        System.out.println(cash.processPayment(40.0));

        System.out.println(cc.processPayment(250.0, "Birthday gift"));

        // Upcasting: Child class object stored in parent class reference
        PaymentMethod ref = cc;

        printConfirmation(ref, 250.0);
    }
}
