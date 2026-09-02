package arrays;

import java.util.HashMap;
import java.util.Map;

/**
 * Day 3 - Two Sum.
 *
 * Demonstrates the difference between a brute-force solution and an
 * optimized HashMap-based solution.
 */
public class Day03TwoSum {

    public static void main(String[] args) {
        int[] numbers = {2, 7, 11, 15};
        int target = 9;

        int[] bruteForceResult = twoSumBruteForce(numbers, target);
        int[] optimizedResult = twoSumOptimized(numbers, target);

        System.out.println("Brute-force result: "
                + bruteForceResult[0] + ", " + bruteForceResult[1]);
        System.out.println("Optimized result: "
                + optimizedResult[0] + ", " + optimizedResult[1]);
    }

    /**
     * Time Complexity: O(n^2)
     * Space Complexity: O(1)
     */
    static int[] twoSumBruteForce(int[] numbers, int target) {
        for (int i = 0; i < numbers.length; i++) {
            for (int j = i + 1; j < numbers.length; j++) {
                if (numbers[i] + numbers[j] == target) {
                    return new int[]{i, j};
                }
            }
        }
        return new int[]{-1, -1};
    }

    /**
     * Time Complexity: O(n)
     * Space Complexity: O(n)
     */
    static int[] twoSumOptimized(int[] numbers, int target) {
        Map<Integer, Integer> seen = new HashMap<>();

        for (int i = 0; i < numbers.length; i++) {
            int complement = target - numbers[i];

            if (seen.containsKey(complement)) {
                return new int[]{seen.get(complement), i};
            }

            seen.put(numbers[i], i);
        }

        return new int[]{-1, -1};
    }
}
