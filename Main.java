import java.util.Random;
import java.util.Scanner;

public class Main {

    // Method to decide the winner
    static String playRound(String playerMove, String computerMove) {

        if (playerMove.equals(computerMove)) {
            return "Draw";
        }

        if ((playerMove.equals("Rock") && computerMove.equals("Scissors")) ||
            (playerMove.equals("Paper") && computerMove.equals("Rock")) ||
            (playerMove.equals("Scissors") && computerMove.equals("Paper"))) {
            return "Player Wins";
        }

        return "Computer Wins";
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        Random random = new Random();

        int n = 5;

        String[] moves = {"Rock", "Paper", "Scissors"};

        // Arrays to store round information
        String[] playerMoves = new String[n];
        String[] computerMoves = new String[n];
        String[] results = new String[n];

        int wins = 0;
        int losses = 0;
        int draws = 0;

        // Play N rounds
        for (int i = 0; i < n; i++) {

            System.out.println("\nRound " + (i + 1));
            System.out.print("Enter your move (Rock/Paper/Scissors): ");

            String playerMove = sc.next();

            // Generate computer move randomly
            String computerMove = moves[random.nextInt(3)];

            // Find result
            String result = playRound(playerMove, computerMove);

            // Store information
            playerMoves[i] = playerMove;
            computerMoves[i] = computerMove;
            results[i] = result;

            System.out.println("Computer: " + computerMove);
            System.out.println("Result: " + result);

            // Update score
            if (result.equals("Player Wins")) {
                wins++;
            }
            else if (result.equals("Computer Wins")) {
                losses++;
            }
            else {
                draws++;
            }
        }

        // Display final summary
        System.out.println("\n========== FINAL SUMMARY ==========");
        System.out.printf("%-8s %-15s %-17s %-15s%n",
                "Round", "Player Move", "Computer Move", "Result");

        for (int i = 0; i < n; i++) {
            System.out.printf("%-8d %-15s %-17s %-15s%n",
                    (i + 1), playerMoves[i], computerMoves[i], results[i]);
        }

        // Calculate win percentage
        double winPercentage = ((double) wins / n) * 100;

        System.out.println("\nWins   : " + wins);
        System.out.println("Losses : " + losses);
        System.out.println("Draws  : " + draws);
        System.out.printf("Win %%  : %.1f%%%n", winPercentage);

        sc.close();
    }
}
// Ibrahim 