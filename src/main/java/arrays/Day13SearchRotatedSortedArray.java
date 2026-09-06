package arrays;

/**
 * Day 13: Search in a Rotated Sorted Array.
 *
 * Assumption: the array was sorted in ascending order and then rotated,
 * and all values are distinct.
 */
public class Day13SearchRotatedSortedArray {

    /**
     * Brute-force search for comparison.
     * Time: O(n), Space: O(1)
     */
    public static int searchBruteForce(int[] nums, int target) {
        validateArray(nums);

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == target) {
                return i;
            }
        }

        return -1;
    }

    /**
     * Binary search in a rotated sorted array.
     * Time: O(log n), Space: O(1)
     */
    public static int search(int[] nums, int target) {
        validateArray(nums);

        int left = 0;
        int right = nums.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (nums[mid] == target) {
                return mid;
            }

            // Left half is sorted.
            if (nums[left] <= nums[mid]) {
                if (nums[left] <= target && target < nums[mid]) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            }
            // Otherwise, the right half is sorted.
            else {
                if (nums[mid] < target && target <= nums[right]) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
        }

        return -1;
    }

    private static void validateArray(int[] nums) {
        if (nums == null || nums.length == 0) {
            throw new IllegalArgumentException("Array must not be null or empty");
        }
    }

    public static void main(String[] args) {
        int[] nums = {4, 5, 6, 7, 0, 1, 2};
        int target = 0;

        System.out.println("Brute force index: " + searchBruteForce(nums, target));
        System.out.println("Binary search index: " + search(nums, target));
    }
}
