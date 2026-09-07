package arrays;

/**
 * Day 17: Search Insert Position and Lower Bound.
 *
 * The lower bound is the first index whose value is greater than or equal
 * to the target. If the target is absent, that index is where the target
 * should be inserted to keep the array sorted.
 */
public class Day17SearchInsertPosition {

    /**
     * Linear-search approach for comparison.
     * Time: O(n), Space: O(1)
     */
    public static int searchInsertBruteForce(int[] nums, int target) {
        validateArray(nums);

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] >= target) {
                return i;
            }
        }

        return nums.length;
    }

    /**
     * Binary-search lower bound.
     * Time: O(log n), Space: O(1)
     */
    public static int searchInsert(int[] nums, int target) {
        validateArray(nums);

        int left = 0;
        int right = nums.length;

        // Search in [left, right), where right is exclusive.
        while (left < right) {
            int mid = left + (right - left) / 2;

            if (nums[mid] < target) {
                // nums[mid] cannot be the answer.
                left = mid + 1;
            } else {
                // mid may be the first valid position.
                right = mid;
            }
        }

        return left;
    }

    /**
     * Finds the first index with value >= target.
     * This is the reusable lower-bound pattern.
     */
    public static int lowerBound(int[] nums, int target) {
        return searchInsert(nums, target);
    }

    private static void validateArray(int[] nums) {
        if (nums == null) {
            throw new IllegalArgumentException("Array must not be null");
        }

        for (int i = 1; i < nums.length; i++) {
            if (nums[i] < nums[i - 1]) {
                throw new IllegalArgumentException("Array must be sorted in ascending order");
            }
        }
    }

    public static void main(String[] args) {
        int[] nums = {1, 3, 5, 6};

        System.out.println("Brute force insert position for 5: "
                + searchInsertBruteForce(nums, 5));
        System.out.println("Binary search insert position for 5: "
                + searchInsert(nums, 5));
        System.out.println("Insert position for 2: " + searchInsert(nums, 2));
        System.out.println("Insert position for 7: " + searchInsert(nums, 7));
    }
}
