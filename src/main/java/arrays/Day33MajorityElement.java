package arrays;

/**
 * Day 33: Majority Element.
 *
 * A majority element is an element that appears more than n / 2 times.
 *
 * Uses the Boyer-Moore Voting Algorithm:
 * - candidate stores the current possible majority.
 * - count represents its current voting advantage.
 *
 * Time: O(n)
 * Extra Space: O(1)
 */
public class Day33MajorityElement {

    /**
     * Boyer-Moore Voting Algorithm.
     *
     * This method assumes that a majority element is guaranteed to exist.
     */
    public static int majorityElement(int[] nums) {
        validateInput(nums);

        int candidate = 0;
        int count = 0;

        for (int num : nums) {
            if (count == 0) {
                candidate = num;
            }

            count += (num == candidate) ? 1 : -1;
        }

        return candidate;
    }

    /**
     * Safer version when a majority element is NOT guaranteed.
     *
     * First phase finds a candidate.
     * Second phase verifies that the candidate occurs more than n / 2 times.
     *
     * Time: O(n)
     * Extra Space: O(1)
     */
    public static int majorityElementIfExists(int[] nums) {
        validateInput(nums);

        int candidate = majorityElement(nums);
        int frequency = 0;

        for (int num : nums) {
            if (num == candidate) {
                frequency++;
            }
        }

        if (frequency > nums.length / 2) {
            return candidate;
        }

        return -1;
    }

    private static void validateInput(int[] nums) {
        if (nums == null || nums.length == 0) {
            throw new IllegalArgumentException("Array must not be null or empty");
        }
    }

    public static void main(String[] args) {
        int[] nums = {2, 2, 1, 1, 1, 2, 2};

        System.out.println("Majority element: " + majorityElement(nums));
        // Expected: 2

        int[] noMajority = {1, 2, 3, 2};

        System.out.println("Majority if it exists: "
                + majorityElementIfExists(noMajority));
        // Expected: -1
    }
}
