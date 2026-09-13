package arrays;

import java.util.ArrayList;
import java.util.List;

/**
 * Day 34: Majority Element II.
 *
 * Find every element that appears more than n / 3 times.
 *
 * There can be at most TWO such elements.
 * Boyer-Moore is extended to maintain two candidates and two vote counts.
 */
public class Day34MajorityElementII {

    /**
     * Finds all elements occurring more than n / 3 times.
     *
     * Phase 1: Find up to two candidates.
     * Phase 2: Verify their actual frequencies.
     *
     * Time: O(n)
     * Extra Space: O(1) excluding the output list.
     */
    public static List<Integer> majorityElements(int[] nums) {
        validateInput(nums);

        int candidate1 = 0;
        int candidate2 = 0;
        int count1 = 0;
        int count2 = 0;

        // Phase 1: Candidate selection.
        for (int num : nums) {
            if (count1 > 0 && num == candidate1) {
                count1++;
            } else if (count2 > 0 && num == candidate2) {
                count2++;
            } else if (count1 == 0) {
                candidate1 = num;
                count1 = 1;
            } else if (count2 == 0) {
                candidate2 = num;
                count2 = 1;
            } else {
                // Three different values cancel one vote each.
                count1--;
                count2--;
            }
        }

        // Phase 2: Verify candidates.
        count1 = 0;
        count2 = 0;

        for (int num : nums) {
            if (num == candidate1) {
                count1++;
            } else if (num == candidate2) {
                count2++;
            }
        }

        List<Integer> result = new ArrayList<>();

        if (count1 > nums.length / 3) {
            result.add(candidate1);
        }

        if (count2 > nums.length / 3 && candidate2 != candidate1) {
            result.add(candidate2);
        }

        return result;
    }

    private static void validateInput(int[] nums) {
        if (nums == null || nums.length == 0) {
            throw new IllegalArgumentException("Array must not be null or empty");
        }
    }

    public static void main(String[] args) {
        int[] nums = {3, 2, 3};

        System.out.println("Majority elements: " + majorityElements(nums));
        // Expected: [3]

        int[] secondExample = {1, 1, 1, 3, 3, 2, 2, 2};

        System.out.println("Second example: "
                + majorityElements(secondExample));
        // Expected: [1, 2]
    }
}
