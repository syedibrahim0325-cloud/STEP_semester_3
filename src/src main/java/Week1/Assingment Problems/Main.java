import java.util.Scanner;

public class Main {

    static void classifyWordLengths(String review) {

        int shortCount = 0;
        int mediumCount = 0;
        int longCount = 0;

        // Split the review into words
        String[] words = review.split("\\s+");

        // Check every word
        for (String word : words) {

            int length = word.length();

            if (length >= 1 && length <= 4) {
                shortCount++;
            }
            else if (length <= 8) {
                mediumCount++;
            }
            else {
                longCount++;
            }
        }

        System.out.println("Short: " + shortCount);
        System.out.println("Medium: " + mediumCount);
        System.out.println("Long: " + longCount);
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter movie review: ");
        String review = sc.nextLine();

        classifyWordLengths(review);

        sc.close();
    }
}