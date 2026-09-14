package arrays;

import java.util.Arrays;

/**
 * Day 39: Remove Duplicates from Sorted Array + In-Place Techniques.
 *
 * Focus: slow/fast pointers and modifying an array without creating another
 * array for the result.
 */
public class Day39RemoveDuplicatesAndInPlaceTechniques {

    /**
     * Removes duplicates from a sorted array in-place.
     *
     * The first unique element is kept at index 0. The fast pointer scans
     * the array while the slow pointer marks the position for the next
     * unique value.
     *
     * Time: O(n), Space: O(1).
     *
     * @return number of unique elements; the first returned elements of the
     *         input array contain the unique values.
     */
    public static int removeDuplicates(int[] nums) {
        validateInput(nums);

        if (nums.length == 0) {
            return 0;
        }

        int slow = 0;

        for (int fast = 1; fast < nums.length; fast++) {
            if (nums[fast] != nums[slow]) {
                slow++;
                nums[slow] = nums[fast];
            }
        }

        return slow + 1;
    }

    /**
     * Removes all occurrences of a target value in-place.
     *
     * This is the same slow/fast pointer pattern, but the condition is based
     * on whether the current value should be kept.
     *
     * Time: O(n), Space: O(1).
     *
     * @return the number of elements remaining after removal.
     */
    public static int removeElement(int[] nums, int target) {
        validateInput(nums);

        int slow = 0;

        for (int fast = 0; fast < nums.length; fast++) {
            if (nums[fast] != target) {
                nums[slow] = nums[fast];
                slow++;
            }
        }

        return slow;
    }

    /**
     * Moves all zeroes to the end while preserving the relative order of
     * non-zero elements.
     *
     * Uses a slow pointer to identify the next position where a non-zero
     * value belongs, followed by filling the remaining positions with zero.
     *
     * Time: O(n), Space: O(1).
     */
    public static void moveZeroes(int[] nums) {
        validateInput(nums);

        int slow = 0;

        for (int fast = 0; fast < nums.length; fast++) {
            if (nums[fast] != 0) {
                nums[slow] = nums[fast];
                slow++;
            }
        }

        while (slow < nums.length) {
            nums[slow] = 0;
            slow++;
        }
    }

    private static void validateInput(int[] nums) {
        if (nums == null) {
            throw new IllegalArgumentException("Array must not be null");
        }
    }

    private static void printResult(String label, int[] nums, int length) {
        System.out.println(label + Arrays.toString(Arrays.copyOf(nums, length)));
    }

    public static void main(String[] args) {
        int[] duplicates = {1, 1, 2, 2, 2, 3, 4, 4};
        int uniqueCount = removeDuplicates(duplicates);
        printResult("Unique values: ", duplicates, uniqueCount);
        // Expected: [1, 2, 3, 4]

        int[] values = {3, 2, 2, 3, 4, 2, 5};
        int remaining = removeElement(values, 2);
        printResult("After removing 2: ", values, remaining);
        // Expected: [3, 3, 4, 5]

        int[] zeroes = {0, 1, 0, 3, 12, 0, 5};
        moveZeroes(zeroes);
        System.out.println("After moving zeroes: " + Arrays.toString(zeroes));
        // Expected: [1, 3, 12, 5, 0, 0, 0]
    }
}
