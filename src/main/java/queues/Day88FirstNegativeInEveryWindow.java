package queues;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

/**
 * Day 88: First negative integer in every window of size k.
 *
 * For each window, return its first negative value. If a window contains no
 * negative value, the result for that window is 0 (the conventional sentinel
 * used by this problem).
 */
public class Day88FirstNegativeInEveryWindow {

    /**
     * Optimized solution using a queue of indices of negative elements.
     *
     * Time: O(n) — each index is added and removed at most once.
     * Auxiliary space: O(k) — at most k negative indices are retained.
     */
    public static int[] firstNegativeInEveryWindow(int[] numbers, int k) {
        validateInput(numbers, k);

        int windowCount = numbers.length - k + 1;
        int[] result = new int[windowCount];
        Deque<Integer> negativeIndices = new ArrayDeque<>();

        for (int i = 0; i < numbers.length; i++) {
            // Expire negative elements that are left of the current window.
            while (!negativeIndices.isEmpty()
                    && negativeIndices.peekFirst() <= i - k) {
                negativeIndices.removeFirst();
            }

            // Only negative values can be an answer for this problem.
            if (numbers[i] < 0) {
                negativeIndices.addLast(i);
            }

            // The first full window ends at index k - 1.
            if (i >= k - 1) {
                result[i - k + 1] = negativeIndices.isEmpty()
                        ? 0
                        : numbers[negativeIndices.peekFirst()];
            }
        }

        return result;
    }

    /**
     * Brute-force reference implementation.
     * Time: O(n * k); auxiliary space: O(1), excluding output.
     */
    public static int[] firstNegativeInEveryWindowBruteForce(
            int[] numbers, int k) {
        validateInput(numbers, k);

        int[] result = new int[numbers.length - k + 1];

        for (int start = 0; start <= numbers.length - k; start++) {
            for (int j = start; j < start + k; j++) {
                if (numbers[j] < 0) {
                    result[start] = numbers[j];
                    break;
                }
            }
        }

        return result;
    }

    private static void validateInput(int[] numbers, int k) {
        if (numbers == null) {
            throw new IllegalArgumentException("Input array must not be null");
        }
        if (numbers.length == 0 || k < 1 || k > numbers.length) {
            throw new IllegalArgumentException(
                    "Window size must be between 1 and array length");
        }
    }

    private static void verify(int[] numbers, int k, int[] expected) {
        int[] actual = firstNegativeInEveryWindow(numbers, k);
        int[] bruteForce = firstNegativeInEveryWindowBruteForce(numbers, k);

        if (!Arrays.equals(actual, expected)) {
            throw new AssertionError("Expected " + Arrays.toString(expected)
                    + " but got " + Arrays.toString(actual));
        }
        if (!Arrays.equals(actual, bruteForce)) {
            throw new AssertionError("Optimized and brute-force results differ");
        }

        System.out.println("Input: " + Arrays.toString(numbers) + ", k = " + k);
        System.out.println("First negatives: " + Arrays.toString(actual));
    }

    public static void main(String[] args) {
        verify(
                new int[]{12, -1, -7, 8, -15, 30, 16, 28},
                3,
                new int[]{-1, -1, -7, -15, -15, 0});

        verify(
                new int[]{1, 2, 3, 4},
                2,
                new int[]{0, 0, 0});

        verify(
                new int[]{-5, 2, -3},
                1,
                new int[]{-5, 0, -3});

        verify(
                new int[]{4, -2, -7, 6},
                4,
                new int[]{-2});
    }
}
