
import java.util.ArrayList;
import java.util.List;

class FoodItem {
    private String name;
    private double price;

    public FoodItem(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}

class Restaurant {
    private String name;
    private List<FoodItem> menu;

    public Restaurant(String name) {
        this.name = name;
        menu = new ArrayList<>();
    }

    public void addFoodItem(FoodItem item) {
        menu.add(item);
    }

    public String getName() {
        return name;
    }
}

class LineItem {
    private FoodItem foodItem;
    private int quantity;

    public LineItem(FoodItem foodItem, int quantity) {
        this.foodItem = foodItem;
        this.quantity = quantity;
    }

    public double getSubtotal() {
        return foodItem.getPrice() * quantity;
    }

    public String getItemName() {
        return foodItem.getName();
    }

    public int getQuantity() {
        return quantity;
    }
}

interface IPaymentMethod {
    boolean pay(double amount);
    String getPaymentName();
}

class CreditCardPayment implements IPaymentMethod {
    @Override
    public boolean pay(double amount) {
        System.out.println("Payment via Credit Card successful.");
        return true;
    }

    @Override
    public String getPaymentName() {
        return "Credit Card";
    }
}

class DigitalWalletPayment implements IPaymentMethod {
    @Override
    public boolean pay(double amount) {
        System.out.println("Payment via Digital Wallet failed.");
        return false;
    }

    @Override
    public String getPaymentName() {
        return "Digital Wallet";
    }
}

class CashOnDeliveryPayment implements IPaymentMethod {
    @Override
    public boolean pay(double amount) {
        System.out.println("Cash on Delivery selected.");
        return true;
    }

    @Override
    public String getPaymentName() {
        return "Cash on Delivery";
    }
}

class NotificationService {
    public void notifyCustomer(String message) {
        System.out.println("Notification: " + message);
    }
}

class Customer {
    private String name;
    private List<Order> orders;

    public Customer(String name) {
        this.name = name;
        orders = new ArrayList<>();
    }

    public void addOrder(Order order) {
        orders.add(order);
    }

    public String getName() {
        return name;
    }
}

class Order {
    private int orderId;
    private Customer customer;
    private List<LineItem> items;
    private String status;
    private NotificationService notificationService;

    public Order(int orderId, Customer customer,
                 NotificationService notificationService) {
        this.orderId = orderId;
        this.customer = customer;
        this.notificationService = notificationService;
        items = new ArrayList<>();
        status = "Created";
        customer.addOrder(this);
        System.out.println("Order created.");
    }

    public void addItem(FoodItem foodItem, int quantity) {
        if (quantity <= 0) {
            System.out.println("Invalid quantity.");
            return;
        }

        items.add(new LineItem(foodItem, quantity));
        System.out.println("Added " + foodItem.getName() +
            " (Qty " + quantity + ")");
    }

    public double calculateTotal() {
        double total = 0;

        for (LineItem item : items) {
            total += item.getSubtotal();
        }

        return total;
    }

    public void placeOrder(IPaymentMethod paymentMethod) {
        if (items.isEmpty()) {
            System.out.println("Cannot place order: Order must contain at least one item.");
            return;
        }

        status = "Pending Payment";
        System.out.println("Order placed successfully.");

        boolean paymentSuccessful = paymentMethod.pay(calculateTotal());

        if (paymentSuccessful) {
            status = "Paid";
            System.out.println("Order status: " + status + ".");
            notificationService.notifyCustomer(
                "Order #" + orderId + " placed and paid."
            );
        } else {
            System.out.println("Order status: " + status + ".");
            notificationService.notifyCustomer(
                "Order #" + orderId + " placed, awaiting payment."
            );
        }
    }
}

public class Main5 {
    public static void main(String[] args) {
        NotificationService notificationService =
            new NotificationService();

        Customer customer = new Customer("John");

        Restaurant restaurant = new Restaurant("Food Palace");

        FoodItem pizza = new FoodItem("Pizza", 250);
        FoodItem soda = new FoodItem("Soda", 50);
        FoodItem burger = new FoodItem("Burger", 150);

        restaurant.addFoodItem(pizza);
        restaurant.addFoodItem(soda);
        restaurant.addFoodItem(burger);

        Order order1 = new Order(123, customer, notificationService);

        order1.addItem(pizza, 2);
        order1.addItem(soda, 1);

        Order emptyOrder = new Order(125, customer, notificationService);
        emptyOrder.placeOrder(new CreditCardPayment());

        order1.placeOrder(new CreditCardPayment());

        Order order2 = new Order(124, customer, notificationService);
        order2.addItem(burger, 1);
        order2.placeOrder(new DigitalWalletPayment());
    }
}
