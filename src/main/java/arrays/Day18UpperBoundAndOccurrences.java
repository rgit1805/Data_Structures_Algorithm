package arrays;

/**
 * Day 18: Upper Bound and First/Last Position.
 *
 * Upper bound is the first index whose value is strictly greater than
 * the target. Together with lower bound, it gives a powerful way to
 * locate ranges of duplicate values in a sorted array.
 */
public class Day18UpperBoundAndOccurrences {

    /**
     * Returns the first index with value > target.
     * Time: O(log n), Space: O(1)
     */
    public static int upperBound(int[] nums, int target) {
        validateArray(nums);

        int left = 0;
        int right = nums.length;

        // Search in [left, right), where right is exclusive.
        while (left < right) {
            int mid = left + (right - left) / 2;

            if (nums[mid] <= target) {
                // mid cannot be the answer because upper bound needs > target.
                left = mid + 1;
            } else {
                // mid may be the first value greater than target.
                right = mid;
            }
        }

        return left;
    }

    /**
     * Returns the first index with value >= target (lower bound).
     * Time: O(log n), Space: O(1)
     */
    public static int lowerBound(int[] nums, int target) {
        validateArray(nums);

        int left = 0;
        int right = nums.length;

        while (left < right) {
            int mid = left + (right - left) / 2;

            if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }

        return left;
    }

    /**
     * Returns the first occurrence of target, or -1 if absent.
     */
    public static int firstOccurrence(int[] nums, int target) {
        int index = lowerBound(nums, target);
        return index < nums.length && nums[index] == target ? index : -1;
    }

    /**
     * Returns the last occurrence of target, or -1 if absent.
     * Upper bound points one position after the last target.
     */
    public static int lastOccurrence(int[] nums, int target) {
        int index = upperBound(nums, target) - 1;
        return index >= 0 && nums[index] == target ? index : -1;
    }

    /**
     * Counts occurrences using lower and upper bounds.
     * Time: O(log n), Space: O(1)
     */
    public static int countOccurrences(int[] nums, int target) {
        int first = lowerBound(nums, target);
        int afterLast = upperBound(nums, target);

        // If lowerBound does not point to target, target is absent.
        if (first == nums.length || nums[first] != target) {
            return 0;
        }

        return afterLast - first;
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
        int[] nums = {1, 2, 2, 2, 4, 5, 5, 7};
        int target = 2;

        System.out.println("Lower bound: " + lowerBound(nums, target));
        System.out.println("Upper bound: " + upperBound(nums, target));
        System.out.println("First occurrence: " + firstOccurrence(nums, target));
        System.out.println("Last occurrence: " + lastOccurrence(nums, target));
        System.out.println("Count: " + countOccurrences(nums, target));
    }
}
