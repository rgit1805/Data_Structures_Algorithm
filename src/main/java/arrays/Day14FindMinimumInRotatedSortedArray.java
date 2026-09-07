package arrays;

/**
 * Day 14: Find the minimum element in a rotated sorted array.
 *
 * Assumption: the array was sorted in ascending order and then rotated,
 * and all values are distinct.
 */
public class Day14FindMinimumInRotatedSortedArray {

    /**
     * Brute-force approach for comparison.
     * Time: O(n), Space: O(1)
     */
    public static int findMinBruteForce(int[] nums) {
        validateArray(nums);

        int minimum = nums[0];
        for (int i = 1; i < nums.length; i++) {
            minimum = Math.min(minimum, nums[i]);
        }

        return minimum;
    }

    /**
     * Binary search approach.
     * Time: O(log n), Space: O(1)
     */
    public static int findMin(int[] nums) {
        validateArray(nums);

        int left = 0;
        int right = nums.length - 1;

        while (left < right) {
            int mid = left + (right - left) / 2;

            // If mid is greater than the rightmost value, the minimum
            // must be in the right half.
            if (nums[mid] > nums[right]) {
                left = mid + 1;
            } else {
                // mid can itself be the minimum, so keep it.
                right = mid;
            }
        }

        return nums[left];
    }

    private static void validateArray(int[] nums) {
        if (nums == null || nums.length == 0) {
            throw new IllegalArgumentException("Array must not be null or empty");
        }
    }

    public static void main(String[] args) {
        int[] nums = {4, 5, 6, 7, 0, 1, 2};

        System.out.println("Brute force minimum: " + findMinBruteForce(nums));
        System.out.println("Binary search minimum: " + findMin(nums));
    }
}
