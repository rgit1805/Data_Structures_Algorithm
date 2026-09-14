package arrays;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Day 36: 4Sum.
 *
 * Find all unique quadruplets whose sum equals the target.
 * Pattern: sorting + two fixed elements + two pointers.
 */
public class Day36FourSum {

    /**
     * Time: O(n^3)
     * Extra space: O(1) apart from output and the sorted copy.
     */
    public static List<List<Integer>> fourSum(int[] nums, long target) {
        if (nums == null) {
            throw new IllegalArgumentException("Array must not be null");
        }

        int[] sorted = nums.clone();
        Arrays.sort(sorted);
        List<List<Integer>> result = new ArrayList<>();

        for (int i = 0; i < sorted.length - 3; i++) {
            if (i > 0 && sorted[i] == sorted[i - 1]) {
                continue;
            }

            for (int j = i + 1; j < sorted.length - 2; j++) {
                if (j > i + 1 && sorted[j] == sorted[j - 1]) {
                    continue;
                }

                int left = j + 1;
                int right = sorted.length - 1;

                while (left < right) {
                    long sum = (long) sorted[i] + sorted[j]
                            + sorted[left] + sorted[right];

                    if (sum == target) {
                        result.add(Arrays.asList(
                                sorted[i], sorted[j], sorted[left], sorted[right]));

                        int leftValue = sorted[left];
                        int rightValue = sorted[right];

                        while (left < right && sorted[left] == leftValue) {
                            left++;
                        }
                        while (left < right && sorted[right] == rightValue) {
                            right--;
                        }
                    } else if (sum < target) {
                        left++;
                    } else {
                        right--;
                    }
                }
            }
        }

        return result;
    }

    public static void main(String[] args) {
        int[] nums = {1, 0, -1, 0, -2, 2};

        System.out.println("4Sum quadruplets: " + fourSum(nums, 0));
        // Expected: [[-2, -1, 1, 2], [-2, 0, 0, 2], [-1, 0, 0, 1]]

        int[] secondExample = {2, 2, 2, 2, 2};
        System.out.println("Second example: " + fourSum(secondExample, 8));
        // Expected: [[2, 2, 2, 2]]
    }
}
