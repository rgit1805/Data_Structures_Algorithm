package arrays;

/**
 * Day 4 - Sliding Window pattern.
 *
 * Finds the maximum sum of any contiguous subarray of fixed size k.
 */
public class Day04SlidingWindow {

    public static void main(String[] args) {
        int[] numbers = {2, 1, 5, 1, 3, 2};
        int k = 3;

        System.out.println("Maximum sum of a subarray of size " + k + ": "
                + maxSumBruteForce(numbers, k));
        System.out.println("Maximum sum using sliding window: "
                + maxSumSlidingWindow(numbers, k));
    }

    /**
     * Brute-force approach.
     * Time Complexity: O(n * k)
     * Space Complexity: O(1)
     */
    static int maxSumBruteForce(int[] numbers, int k) {
        validate(numbers, k);

        int maxSum = Integer.MIN_VALUE;

        for (int i = 0; i <= numbers.length - k; i++) {
            int currentSum = 0;
            for (int j = i; j < i + k; j++) {
                currentSum += numbers[j];
            }
            maxSum = Math.max(maxSum, currentSum);
        }

        return maxSum;
    }

    /**
     * Optimized fixed-size sliding window.
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     */
    static int maxSumSlidingWindow(int[] numbers, int k) {
        validate(numbers, k);

        int windowSum = 0;
        for (int i = 0; i < k; i++) {
            windowSum += numbers[i];
        }

        int maxSum = windowSum;

        for (int right = k; right < numbers.length; right++) {
            windowSum += numbers[right];
            windowSum -= numbers[right - k];
            maxSum = Math.max(maxSum, windowSum);
        }

        return maxSum;
    }

    private static void validate(int[] numbers, int k) {
        if (numbers == null || numbers.length == 0) {
            throw new IllegalArgumentException("Array must not be empty");
        }
        if (k <= 0 || k > numbers.length) {
            throw new IllegalArgumentException("k must be between 1 and array length");
        }
    }
}
