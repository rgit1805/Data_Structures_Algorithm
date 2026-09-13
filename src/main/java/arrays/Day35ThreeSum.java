package arrays;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Day 35: 3Sum.
 *
 * Find all unique triplets whose sum is zero.
 *
 * Pattern:
 * 1. Sort the array.
 * 2. Fix one element.
 * 3. Use two pointers for the remaining two elements.
 *
 * Time: O(n^2)
 * Extra space: O(1) apart from sorting and the output.
 */
public class Day35ThreeSum {

    /**
     * Returns all unique triplets [a, b, c] such that:
     * a + b + c == 0.
     *
     * Duplicate triplets are skipped.
     */
    public static List<List<Integer>> threeSum(int[] nums) {
        if (nums == null) {
            throw new IllegalArgumentException("Array must not be null");
        }

        int[] sorted = nums.clone();
        Arrays.sort(sorted);

        List<List<Integer>> result = new ArrayList<>();

        for (int i = 0; i < sorted.length - 2; i++) {

            // The same first value would produce duplicate triplets.
            if (i > 0 && sorted[i] == sorted[i - 1]) {
                continue;
            }

            // Once the fixed value is positive, the sum cannot be zero.
            if (sorted[i] > 0) {
                break;
            }

            int left = i + 1;
            int right = sorted.length - 1;

            while (left < right) {
                long sum = (long) sorted[i] + sorted[left] + sorted[right];

                if (sum == 0) {
                    result.add(Arrays.asList(
                            sorted[i], sorted[left], sorted[right]));

                    int leftValue = sorted[left];
                    int rightValue = sorted[right];

                    // Skip duplicate second and third values.
                    while (left < right && sorted[left] == leftValue) {
                        left++;
                    }

                    while (left < right && sorted[right] == rightValue) {
                        right--;
                    }

                } else if (sum < 0) {
                    left++;
                } else {
                    right--;
                }
            }
        }

        return result;
    }

    public static void main(String[] args) {
        int[] nums = {-1, 0, 1, 2, -1, -4};

        System.out.println("3Sum triplets: " + threeSum(nums));
        // Expected: [[-1, -1, 2], [-1, 0, 1]]

        int[] secondExample = {0, 0, 0, 0};
        System.out.println("Second example: " + threeSum(secondExample));
        // Expected: [[0, 0, 0]]
    }
}
