package arrays;

/**
 * Day 7 - Maximum Subarray (Kadane's Algorithm).
 *
 * Finds the largest possible sum of a contiguous subarray.
 */
public class Day07MaximumSubarray {

    public static void main(String[] args) {
        int[] numbers = {-2, 1, -3, 4, -1, 2, 1, -5, 4};

        System.out.println("Maximum sum (brute force): "
                + maxSubarraySumBruteForce(numbers));
        System.out.println("Maximum sum (Kadane's algorithm): "
                + maxSubarraySum(numbers));
    }

    /**
     * Check every possible contiguous subarray.
     * Time Complexity: O(n^2)
     * Space Complexity: O(1)
     */
    static int maxSubarraySumBruteForce(int[] numbers) {
        validate(numbers);

        int maximumSum = numbers[0];

        for (int start = 0; start < numbers.length; start++) {
            int currentSum = 0;
            for (int end = start; end < numbers.length; end++) {
                currentSum += numbers[end];
                maximumSum = Math.max(maximumSum, currentSum);
            }
        }

        return maximumSum;
    }

    /**
     * Kadane's algorithm: at each element, decide whether to extend
     * the current subarray or start a new subarray here.
     *
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     */
    static int maxSubarraySum(int[] numbers) {
        validate(numbers);

        int currentSum = numbers[0];
        int maximumSum = numbers[0];

        for (int i = 1; i < numbers.length; i++) {
            currentSum = Math.max(numbers[i], currentSum + numbers[i]);
            maximumSum = Math.max(maximumSum, currentSum);
        }

        return maximumSum;
    }

    private static void validate(int[] numbers) {
        if (numbers == null || numbers.length == 0) {
            throw new IllegalArgumentException("Array must not be null or empty");
        }
    }
}
