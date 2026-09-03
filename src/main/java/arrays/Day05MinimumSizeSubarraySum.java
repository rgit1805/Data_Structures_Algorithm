package arrays;

/**
 * Day 5 - Variable-size Sliding Window pattern.
 *
 * Finds the minimum length of a contiguous subarray whose sum is
 * greater than or equal to a target. All numbers must be positive.
 */
public class Day05MinimumSizeSubarraySum {

    public static void main(String[] args) {
        int[] numbers = {2, 3, 1, 2, 4, 3};
        int target = 7;

        System.out.println("Minimum length (brute force): "
                + minSubarrayLengthBruteForce(numbers, target));
        System.out.println("Minimum length (sliding window): "
                + minSubarrayLength(numbers, target));
    }

    /**
     * Brute-force approach.
     * Time Complexity: O(n^2)
     * Space Complexity: O(1)
     */
    static int minSubarrayLengthBruteForce(int[] numbers, int target) {
        validate(numbers, target);

        int minLength = Integer.MAX_VALUE;

        for (int left = 0; left < numbers.length; left++) {
            int sum = 0;
            for (int right = left; right < numbers.length; right++) {
                sum += numbers[right];
                if (sum >= target) {
                    minLength = Math.min(minLength, right - left + 1);
                    break;
                }
            }
        }

        return minLength == Integer.MAX_VALUE ? 0 : minLength;
    }

    /**
     * Variable-size sliding window.
     * Expand the window until the target is reached, then shrink it
     * from the left while the condition remains satisfied.
     *
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     */
    static int minSubarrayLength(int[] numbers, int target) {
        validate(numbers, target);

        int left = 0;
        int windowSum = 0;
        int minLength = Integer.MAX_VALUE;

        for (int right = 0; right < numbers.length; right++) {
            windowSum += numbers[right];

            while (windowSum >= target) {
                minLength = Math.min(minLength, right - left + 1);
                windowSum -= numbers[left];
                left++;
            }
        }

        return minLength == Integer.MAX_VALUE ? 0 : minLength;
    }

    private static void validate(int[] numbers, int target) {
        if (numbers == null || numbers.length == 0) {
            throw new IllegalArgumentException("Array must not be empty");
        }
        if (target <= 0) {
            throw new IllegalArgumentException("Target must be positive");
        }
        for (int number : numbers) {
            if (number <= 0) {
                throw new IllegalArgumentException("All numbers must be positive");
            }
        }
    }
}
