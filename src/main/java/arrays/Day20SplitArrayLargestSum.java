package arrays;

/**
 * Day 20: Split Array Largest Sum.
 *
 * Given a non-negative array, split it into at most k contiguous subarrays
 * so that the largest subarray sum is as small as possible.
 *
 * This is a classic Binary Search on Answer problem.
 */
public class Day20SplitArrayLargestSum {

    /**
     * Brute-force style greedy check for a fixed maximum allowed sum.
     * Time: O(n), Space: O(1)
     */
    public static boolean canSplit(int[] nums, int k, long maxAllowedSum) {
        validateInput(nums, k);

        int subarrays = 1;
        long currentSum = 0;

        for (int num : nums) {
            if (currentSum + num <= maxAllowedSum) {
                currentSum += num;
            } else {
                subarrays++;
                currentSum = num;
            }
        }

        return subarrays <= k;
    }

    /**
     * Finds the minimum possible value of the largest subarray sum.
     *
     * Search space:
     *   left  = largest element (minimum possible answer)
     *   right = total sum (maximum possible answer)
     *
     * Time: O(n log(sum(nums)))
     * Space: O(1)
     */
    public static long splitArray(int[] nums, int k) {
        validateInput(nums, k);

        long left = 0;
        long right = 0;

        for (int num : nums) {
            left = Math.max(left, num);
            right += num;
        }

        long answer = right;

        while (left <= right) {
            long mid = left + (right - left) / 2;

            if (canSplit(nums, k, mid)) {
                answer = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }

        return answer;
    }

    private static void validateInput(int[] nums, int k) {
        if (nums == null || nums.length == 0) {
            throw new IllegalArgumentException("Array must not be null or empty");
        }
        if (k <= 0 || k > nums.length) {
            throw new IllegalArgumentException("k must be between 1 and array length");
        }
        for (int num : nums) {
            if (num < 0) {
                throw new IllegalArgumentException("This solution requires non-negative array values");
            }
        }
    }

    public static void main(String[] args) {
        int[] nums = {7, 2, 5, 10, 8};
        int k = 2;

        System.out.println("Minimum largest subarray sum: " + splitArray(nums, k));
        // Expected: 18 -> [7, 2, 5] and [10, 8]

        int[] secondExample = {1, 2, 3, 4, 5};
        System.out.println("Second example: " + splitArray(secondExample, 2));
        // Expected: 9 -> [1, 2, 3] and [4, 5]
    }
}
