package queues;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

/**
 * Day 87: Sliding Window Maximum using a monotonic deque.
 *
 * The deque stores ARRAY INDICES, not values. Indices remain in window order,
 * and their corresponding values are kept in decreasing order from front to
 * rear. The front index therefore identifies the maximum of the current window.
 */
public class Day87SlidingWindowMaximum {

    /**
     * Optimized solution.
     *
     * @param numbers input array
     * @param k window size
     * @return maximum value for each contiguous window of size k
     * Time: O(n), because every index is added and removed at most once.
     * Space: O(k), for the deque, excluding the output array.
     */
    public static int[] maxSlidingWindow(int[] numbers, int k) {
        validateInput(numbers, k);

        if (numbers.length == 0) {
            return new int[0];
        }

        int[] result = new int[numbers.length - k + 1];
        Deque<Integer> deque = new ArrayDeque<>(); // stores indices
        int resultIndex = 0;

        for (int i = 0; i < numbers.length; i++) {
            // Remove indices that have fallen outside the current window.
            while (!deque.isEmpty() && deque.peekFirst() <= i - k) {
                deque.removeFirst();
            }

            // Remove smaller or equal values: they cannot be the maximum
            // while this newer, larger-or-equal value remains in the window.
            while (!deque.isEmpty()
                    && numbers[deque.peekLast()] <= numbers[i]) {
                deque.removeLast();
            }

            deque.addLast(i);

            // A complete window exists once we have processed k elements.
            if (i >= k - 1) {
                result[resultIndex++] = numbers[deque.peekFirst()];
            }
        }

        return result;
    }

    /**
     * Straightforward baseline for comparison.
     * Time: O(n * k); auxiliary space: O(1), excluding the output array.
     */
    public static int[] maxSlidingWindowBruteForce(int[] numbers, int k) {
        validateInput(numbers, k);

        if (numbers.length == 0) {
            return new int[0];
        }

        int[] result = new int[numbers.length - k + 1];

        for (int start = 0; start <= numbers.length - k; start++) {
            int maximum = numbers[start];

            for (int j = start + 1; j < start + k; j++) {
                maximum = Math.max(maximum, numbers[j]);
            }

            result[start] = maximum;
        }

        return result;
    }

    private static void validateInput(int[] numbers, int k) {
        if (numbers == null) {
            throw new IllegalArgumentException("Input array must not be null");
        }
        if (numbers.length == 0) {
            if (k != 0) {
                throw new IllegalArgumentException(
                        "For an empty array, window size must be 0");
            }
            return;
        }
        if (k < 1 || k > numbers.length) {
            throw new IllegalArgumentException(
                    "Window size must be between 1 and array length");
        }
    }

    private static void runExample(int[] numbers, int k) {
        int[] optimized = maxSlidingWindow(numbers, k);
        int[] bruteForce = maxSlidingWindowBruteForce(numbers, k);

        System.out.println("Input: " + Arrays.toString(numbers) + ", k = " + k);
        System.out.println("Optimized: " + Arrays.toString(optimized));
        System.out.println("Brute force: " + Arrays.toString(bruteForce));

        if (!Arrays.equals(optimized, bruteForce)) {
            throw new AssertionError("Optimized and brute-force results differ");
        }
    }

    public static void main(String[] args) {
        runExample(new int[]{1, 3, -1, -3, 5, 3, 6, 7}, 3);
        // Expected: [3, 3, 5, 5, 6, 7]

        runExample(new int[]{4, 4, 4, 4}, 2);
        // Expected: [4, 4, 4]

        runExample(new int[]{9}, 1);
        // Expected: [9]

        runExample(new int[]{-8, -2, -5, -1}, 2);
        // Expected: [-2, -2, -1]
    }
}
