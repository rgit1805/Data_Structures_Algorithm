package arrays;

/**
 * Day 8 - Prefix Sum.
 *
 * Prefix sums turn repeated range-sum calculations into O(1) queries
 * after O(n) preprocessing.
 */
public class Day08PrefixSum {

    public static void main(String[] args) {
        int[] numbers = {2, 4, 1, 5, 3, 6};
        int left = 1;
        int right = 4;

        System.out.println("Range sum (brute force): "
                + rangeSumBruteForce(numbers, left, right));
        System.out.println("Range sum (prefix sum): "
                + rangeSum(numbers, left, right));
    }

    /**
     * Add every value from left to right for each query.
     * Time Complexity per query: O(n) in the worst case.
     * Space Complexity: O(1).
     */
    static int rangeSumBruteForce(int[] numbers, int left, int right) {
        validate(numbers, left, right);

        int sum = 0;
        for (int i = left; i <= right; i++) {
            sum += numbers[i];
        }
        return sum;
    }

    /**
     * Build a prefix-sum array where prefix[i] stores the sum of
     * numbers[0] through numbers[i - 1].
     *
     * Range sum [left, right] = prefix[right + 1] - prefix[left].
     *
     * Preprocessing: O(n)
     * Query: O(1)
     * Extra Space: O(n)
     */
    static int rangeSum(int[] numbers, int left, int right) {
        validate(numbers, left, right);

        int[] prefix = buildPrefixSum(numbers);
        return prefix[right + 1] - prefix[left];
    }

    static int[] buildPrefixSum(int[] numbers) {
        if (numbers == null) {
            throw new IllegalArgumentException("Array must not be null");
        }

        int[] prefix = new int[numbers.length + 1];

        for (int i = 0; i < numbers.length; i++) {
            prefix[i + 1] = prefix[i] + numbers[i];
        }

        return prefix;
    }

    private static void validate(int[] numbers, int left, int right) {
        if (numbers == null || numbers.length == 0) {
            throw new IllegalArgumentException("Array must not be null or empty");
        }
        if (left < 0 || right >= numbers.length || left > right) {
            throw new IllegalArgumentException("Invalid range");
        }
    }
}
