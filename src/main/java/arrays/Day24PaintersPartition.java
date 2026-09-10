package arrays;

import java.util.Arrays;

/**
 * Day 24: Painter's Partition.
 *
 * Divide boards into k contiguous groups so that the maximum total
 * board length assigned to any painter is minimized.
 *
 * This is another Binary Search on Answer problem.
 */
public class Day24PaintersPartition {

    /**
     * Checks whether all boards can be painted by at most k painters
     * when no painter may receive more than maxLength total work.
     *
     * Time: O(n), Space: O(1)
     */
    public static boolean canPaint(int[] boards, int k, long maxLength) {
        validateInput(boards, k);

        int painters = 1;
        long currentLength = 0;

        for (int board : boards) {
            if (currentLength + board <= maxLength) {
                currentLength += board;
            } else {
                painters++;
                currentLength = board;
            }
        }

        return painters <= k;
    }

    /**
     * Returns the minimum possible maximum work assigned to one painter.
     *
     * Search space:
     *   left  = longest board
     *   right = total board length
     *
     * Time: O(n log(sum(boards)))
     * Space: O(1) auxiliary space
     */
    public static long minMaximumWork(int[] boards, int k) {
        validateInput(boards, k);

        long left = 0;
        long right = 0;

        for (int board : boards) {
            left = Math.max(left, board);
            right += board;
        }

        long answer = right;

        while (left <= right) {
            long mid = left + (right - left) / 2;

            if (canPaint(boards, k, mid)) {
                answer = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }

        return answer;
    }

    private static void validateInput(int[] boards, int k) {
        if (boards == null || boards.length == 0) {
            throw new IllegalArgumentException("Boards must not be null or empty");
        }
        if (k <= 0 || k > boards.length) {
            throw new IllegalArgumentException(
                    "Painters must be between 1 and the number of boards");
        }
        for (int board : boards) {
            if (board < 0) {
                throw new IllegalArgumentException("Board lengths must be non-negative");
            }
        }
    }

    public static void main(String[] args) {
        int[] boards = {10, 20, 30, 40};
        int painters = 2;

        System.out.println("Minimum maximum work: "
                + minMaximumWork(boards, painters));
        // Expected: 60
        // Allocation: [10, 20, 30] | [40]

        int[] secondExample = {5, 5, 5, 5};
        System.out.println("Second example: "
                + minMaximumWork(secondExample, 2));
        // Expected: 10
    }
}
