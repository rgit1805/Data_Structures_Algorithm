package arrays;

/**
 * Day 25: Median of Two Sorted Arrays.
 *
 * Finds the median of two individually sorted arrays in
 * O(log(min(m, n))) time using partition-based binary search.
 *
 * The smaller array is always searched to keep the complexity optimal.
 */
public class Day25MedianOfTwoSortedArrays {

    /**
     * Binary-search partition method.
     *
     * We choose a partition in the smaller array and derive the
     * corresponding partition in the larger array. A valid partition
     * has every value on the left <= every value on the right.
     *
     * Time: O(log(min(m, n)))
     * Space: O(1)
     */
    public static double findMedian(int[] nums1, int[] nums2) {
        validateArrays(nums1, nums2);

        // Always binary-search the smaller array.
        if (nums1.length > nums2.length) {
            return findMedian(nums2, nums1);
        }

        int m = nums1.length;
        int n = nums2.length;

        int left = 0;
        int right = m;

        while (left <= right) {
            int partition1 = left + (right - left) / 2;
            int partition2 = (m + n + 1) / 2 - partition1;

            int left1 = partition1 == 0 ? Integer.MIN_VALUE : nums1[partition1 - 1];
            int right1 = partition1 == m ? Integer.MAX_VALUE : nums1[partition1];

            int left2 = partition2 == 0 ? Integer.MIN_VALUE : nums2[partition2 - 1];
            int right2 = partition2 == n ? Integer.MAX_VALUE : nums2[partition2];

            // Correct partition:
            // left side contains the smaller half of the combined arrays.
            if (left1 <= right2 && left2 <= right1) {
                if ((m + n) % 2 == 1) {
                    return Math.max(left1, left2);
                }

                return (Math.max(left1, left2)
                        + (double) Math.min(right1, right2)) / 2.0;
            }

            if (left1 > right2) {
                // Too many elements from nums1 are on the left.
                right = partition1 - 1;
            } else {
                // Too few elements from nums1 are on the left.
                left = partition1 + 1;
            }
        }

        throw new IllegalArgumentException("Input arrays are not sorted");
    }

    private static void validateArrays(int[] nums1, int[] nums2) {
        if (nums1 == null || nums2 == null) {
            throw new IllegalArgumentException("Arrays must not be null");
        }

        if (nums1.length == 0 && nums2.length == 0) {
            throw new IllegalArgumentException("At least one array must be non-empty");
        }

        validateSorted(nums1);
        validateSorted(nums2);
    }

    private static void validateSorted(int[] nums) {
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] < nums[i - 1]) {
                throw new IllegalArgumentException(
                        "Arrays must be sorted in ascending order");
            }
        }
    }

    public static void main(String[] args) {
        int[] nums1 = {1, 3};
        int[] nums2 = {2};

        System.out.println("Median: " + findMedian(nums1, nums2));
        // Expected: 2.0

        int[] nums3 = {1, 2};
        int[] nums4 = {3, 4};

        System.out.println("Second example: " + findMedian(nums3, nums4));
        // Expected: 2.5
    }
}
