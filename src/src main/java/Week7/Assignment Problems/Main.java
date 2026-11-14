
abstract class Shape {

    private static int counter = 1000;
    private final String shapeId;

    // Constructor
    public Shape() {
        counter++;
        shapeId = "SH-" + counter;
    }

    // Abstract method
    public abstract double calculateArea();

    // Scale equally
    public void scale(double factor) {
        // Implemented by subclasses
    }

    // Scale unevenly
    public void scale(double xFactor, double yFactor) {
        scale(xFactor);
        scale(yFactor);
    }

    // Getter
    public String getShapeId() {
        return shapeId;
    }

    // Static method to print area
    public static void printArea(Shape s) {
        System.out.println("Area: " + s.calculateArea());
    }
}

// Circle class
class CircleShape extends Shape {

    private double radius;

    public CircleShape(double radius) {
        this.radius = radius;
    }

    @Override
    public double calculateArea() {
        return Math.PI * radius * radius;
    }

    @Override
    public void scale(double factor) {
        radius = radius * factor;
    }
}

// Square class
class SquareShape extends Shape {

    private double side;

    public SquareShape(double side) {
        this.side = side;
    }

    @Override
    public double calculateArea() {
        return side * side;
    }

    @Override
    public void scale(double factor) {
        side = side * factor;
    }
}

// Main class
public class Main {

    public static void main(String[] args) {

        CircleShape c = new CircleShape(5.0);
        SquareShape sq = new SquareShape(4.0);

        System.out.println("Circle Area: " + c.calculateArea());
        System.out.println("Square Area: " + sq.calculateArea());

        // Single-factor scaling
        sq.scale(2.0);
        System.out.println("Square Area after scaling: "
                + sq.calculateArea());

        // Two-factor scaling
        c.scale(2.0, 3.0);
        System.out.println("Circle Area after scaling: "
                + c.calculateArea());

        // Static method
        Shape.printArea(c);
        Shape.printArea(sq);

        // Shape IDs
        System.out.println("Circle ID: " + c.getShapeId());
        System.out.println("Square ID: " + sq.getShapeId());
    }
}
