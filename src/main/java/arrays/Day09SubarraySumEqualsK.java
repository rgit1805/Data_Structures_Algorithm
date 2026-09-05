package arrays;

import java.util.HashMap;
import java.util.Map;

/**
 * Day 9 - Subarray Sum Equals K.
 *
 * Uses prefix sums + HashMap to count contiguous subarrays whose
 * sum equals a target value. This works even when the array contains
 * negative numbers, unlike the variable sliding-window approach.
 */
public class Day09SubarraySumEqualsK {

    public static void main(String[] args) {
        int[] numbers = {1, 2, 3, -2, 2};
        int target = 3;

        System.out.println("Count (brute force): "
                + countSubarraysBruteForce(numbers, target));
        System.out.println("Count (prefix sum + HashMap): "
                + countSubarrays(numbers, target));
    }

    /**
     * Check every possible contiguous subarray.
     * Time Complexity: O(n^2)
     * Space Complexity: O(1)
     */
    static int countSubarraysBruteForce(int[] numbers, int target) {
        validate(numbers);

        int count = 0;

        for (int start = 0; start < numbers.length; start++) {
            int sum = 0;
            for (int end = start; end < numbers.length; end++) {
                sum += numbers[end];
                if (sum == target) {
                    count++;
                }
            }
        }

        return count;
    }

    /**
     * If currentPrefix - target has appeared before, every occurrence
     * represents a subarray ending at the current index with sum target.
     *
     * Time Complexity: O(n) average
     * Space Complexity: O(n)
     */
    static int countSubarrays(int[] numbers, int target) {
        validate(numbers);

        Map<Integer, Integer> prefixFrequency = new HashMap<>();
        prefixFrequency.put(0, 1);

        int prefixSum = 0;
        int count = 0;

        for (int number : numbers) {
            prefixSum += number;

            count += prefixFrequency.getOrDefault(prefixSum - target, 0);
            prefixFrequency.put(
                    prefixSum,
                    prefixFrequency.getOrDefault(prefixSum, 0) + 1
            );
        }

        return count;
    }

    private static void validate(int[] numbers) {
        if (numbers == null || numbers.length == 0) {
            throw new IllegalArgumentException("Array must not be null or empty");
        }
    }
}
