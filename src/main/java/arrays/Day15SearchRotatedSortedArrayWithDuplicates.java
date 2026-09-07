package arrays;

/**
 * Day 15: Search in a rotated sorted array with duplicates.
 *
 * Unlike Day 13, duplicate values can make it impossible to determine
 * which half is sorted from nums[left], nums[mid], and nums[right].
 */
public class Day15SearchRotatedSortedArrayWithDuplicates {

    /**
     * Brute-force search for comparison.
     * Time: O(n), Space: O(1)
     */
    public static boolean searchBruteForce(int[] nums, int target) {
        validateArray(nums);

        for (int num : nums) {
            if (num == target) {
                return true;
            }
        }

        return false;
    }

    /**
     * Binary search that handles duplicate values.
     * Average Time: O(log n), Worst-case Time: O(n), Space: O(1)
     */
    public static boolean search(int[] nums, int target) {
        validateArray(nums);

        int left = 0;
        int right = nums.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (nums[mid] == target) {
                return true;
            }

            // When left, mid, and right are equal, we cannot identify
            // a sorted half. Shrink the search space safely.
            if (nums[left] == nums[mid] && nums[mid] == nums[right]) {
                left++;
                right--;
                continue;
            }

            // Left half is sorted.
            if (nums[left] <= nums[mid]) {
                if (nums[left] <= target && target < nums[mid]) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            }
            // Right half is sorted.
            else {
                if (nums[mid] < target && target <= nums[right]) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
        }

        return false;
    }

    private static void validateArray(int[] nums) {
        if (nums == null || nums.length == 0) {
            throw new IllegalArgumentException("Array must not be null or empty");
        }
    }

    public static void main(String[] args) {
        int[] nums = {2, 5, 6, 0, 0, 1, 2};
        int target = 0;

        System.out.println("Brute force found: " + searchBruteForce(nums, target));
        System.out.println("Binary search found: " + search(nums, target));
    }
}
