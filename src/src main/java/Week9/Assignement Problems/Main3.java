
import java.util.HashMap;

public class Main3 {

    public static int countPeriods(int[] transactions, int k) {

        HashMap<Integer, Integer> map = new HashMap<>();

        int sum = 0;
        int count = 0;

        // Initially, prefix sum 0 occurs once
        map.put(0, 1);

        for (int num : transactions) {

            sum += num;

            // Check if an earlier prefix sum exists
            if (map.containsKey(sum - k)) {
                count += map.get(sum - k);
            }

            // Store the current prefix sum
            map.put(sum, map.getOrDefault(sum, 0) + 1);
        }

        return count;
    }

    public static void main(String[] args) {

        int[] transactions = {3, 4, -7, 1, 3, 3, 1, -4};

        int k = 7;

        System.out.println(countPeriods(transactions, k));

        int[] transactions2 = {1, 2, 3};

        System.out.println(countPeriods(transactions2, 10));
    }
}

