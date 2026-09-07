package arrays;

/**
 * Day 16: Find a peak element using binary search.
 *
 * A peak is an element that is greater than its adjacent element(s).
 * For boundary elements, only the existing neighbor matters.
 */
public class Day16FindPeakElement {

    /**
     * Brute-force approach: check every element for peak property.
     * Time: O(n), Space: O(1)
     */
    public static int findPeakBruteForce(int[] nums) {
        validateArray(nums);

        if (nums.length == 1) {
            return 0;
        }

        for (int i = 0; i < nums.length; i++) {
            boolean greaterThanLeft = i == 0 || nums[i] > nums[i - 1];
            boolean greaterThanRight = i == nums.length - 1 || nums[i] > nums[i + 1];

            if (greaterThanLeft && greaterThanRight) {
                return i;
            }
        }

        return -1;
    }

    /**
     * Binary-search approach.
     * Time: O(log n), Space: O(1)
     *
     * If nums[mid] < nums[mid + 1], a peak must exist on the right.
     * Otherwise, a peak exists at mid or somewhere on the left.
     */
    public static int findPeak(int[] nums) {
        validateArray(nums);

        int left = 0;
        int right = nums.length - 1;

        while (left < right) {
            int mid = left + (right - left) / 2;

            if (nums[mid] < nums[mid + 1]) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }

        return left;
    }

    private static void validateArray(int[] nums) {
        if (nums == null || nums.length == 0) {
            throw new IllegalArgumentException("Array must not be null or empty");
        }
    }

    public static void main(String[] args) {
        int[] nums = {1, 2, 3, 1};

        System.out.println("Brute force peak index: " + findPeakBruteForce(nums));
        System.out.println("Binary search peak index: " + findPeak(nums));
    }
}
