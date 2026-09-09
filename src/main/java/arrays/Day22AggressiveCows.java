package arrays;

import java.util.Arrays;

/**
 * Day 22: Aggressive Cows.
 *
 * Place k cows in stalls so that the minimum distance between any two cows
 * is as large as possible.
 *
 * This is a classic Binary Search on Answer problem where we maximize a
 * feasible answer instead of minimizing it.
 */
public class Day22AggressiveCows {

    /**
     * Checks whether k cows can be placed with at least minDistance between
     * consecutive placed cows.
     *
     * Greedy idea: after placing the first cow at the leftmost stall, always
     * place the next cow at the earliest stall that is far enough away.
     * This leaves maximum room for the remaining cows.
     *
     * Time: O(n), Space: O(1)
     */
    public static boolean canPlace(int[] stalls, int k, int minDistance) {
        validateInput(stalls, k);

        int cowsPlaced = 1;
        int lastPosition = stalls[0];

        for (int i = 1; i < stalls.length; i++) {
            if (stalls[i] - lastPosition >= minDistance) {
                cowsPlaced++;
                lastPosition = stalls[i];

                if (cowsPlaced == k) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Returns the maximum possible minimum distance between any two cows.
     *
     * The stalls are sorted first. Then we binary-search the answer:
     *   left  = 1
     *   right = stalls[last] - stalls[first]
     *
     * If a distance is feasible, every smaller distance is also feasible.
     * Therefore the feasibility is monotonic.
     *
     * Time: O(n log(range)) after sorting
     * Space: O(1) auxiliary space apart from sorting implementation details
     */
    public static int maxMinimumDistance(int[] stalls, int k) {
        validateInput(stalls, k);

        int[] sortedStalls = stalls.clone();
        Arrays.sort(sortedStalls);

        int left = 1;
        int right = sortedStalls[sortedStalls.length - 1] - sortedStalls[0];
        int answer = 0;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (canPlace(sortedStalls, k, mid)) {
                answer = mid;
                left = mid + 1; // Try a larger minimum distance.
            } else {
                right = mid - 1; // Distance is too large.
            }
        }

        return answer;
    }

    private static void validateInput(int[] stalls, int k) {
        if (stalls == null || stalls.length == 0) {
            throw new IllegalArgumentException("Stalls must not be null or empty");
        }
        if (k < 2 || k > stalls.length) {
            throw new IllegalArgumentException("k must be between 2 and the number of stalls");
        }
    }

    public static void main(String[] args) {
        int[] stalls = {1, 2, 4, 8, 9};
        int k = 3;
        System.out.println("Maximum minimum distance: " + maxMinimumDistance(stalls, k));
        // Expected: 3
        // One optimal placement: cows at 1, 4, 8 (or 1, 4, 9).

        int[] secondExample = {10, 1, 2, 7, 5};
        int secondK = 3;
        System.out.println("Second example: " + maxMinimumDistance(secondExample, secondK));
        // Expected: 4
    }
}
