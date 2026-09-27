package stacks;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

/**
 * Day 73: Next Greater Element
 *
 * For every element, find the first element to its right that is strictly
 * greater than it. If no such element exists, return -1.
 *
 * Example:
 *   [4, 5, 2, 10, 8]
 *   -> [5, 10, 10, -1, -1]
 *
 * This problem introduces the MONOTONIC STACK pattern.
 */
public class Day73NextGreaterElement {

    /**
     * Brute-force approach.
     *
     * For every element, scan everything to its right.
     *
     * Time: O(n^2)
     * Space: O(1) apart from the output array.
     */
    public static int[] nextGreaterBruteForce(int[] nums) {
        validateInput(nums);

        int[] result = new int[nums.length];
        Arrays.fill(result, -1);

        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[j] > nums[i]) {
                    result[i] = nums[j];
                    break;
                }
            }
        }

        return result;
    }

    /**
     * Optimal monotonic decreasing stack.
     *
     * The stack stores indices whose next greater element has not yet
     * been discovered.
     *
     * When nums[i] is greater than the value represented by the stack top,
     * nums[i] becomes the answer for that index.
     *
     * Time: O(n)
     * Space: O(n)
     */
    public static int[] nextGreater(int[] nums) {
        validateInput(nums);

        int[] result = new int[nums.length];
        Arrays.fill(result, -1);

        Deque<Integer> stack = new ArrayDeque<>();

        for (int i = 0; i < nums.length; i++) {

            while (!stack.isEmpty()
                    && nums[i] > nums[stack.peek()]) {

                int index = stack.pop();
                result[index] = nums[i];
            }

            stack.push(i);
        }

        return result;
    }

    /**
     * Same monotonic-stack idea, but scanning from right to left.
     *
     * The stack contains possible next-greater candidates.
     *
     * Time: O(n)
     * Space: O(n)
     */
    public static int[] nextGreaterRightToLeft(int[] nums) {
        validateInput(nums);

        int[] result = new int[nums.length];
        Arrays.fill(result, -1);

        Deque<Integer> stack = new ArrayDeque<>();

        for (int i = nums.length - 1; i >= 0; i--) {

            while (!stack.isEmpty()
                    && stack.peek() <= nums[i]) {
                stack.pop();
            }

            if (!stack.isEmpty()) {
                result[i] = stack.peek();
            }

            stack.push(nums[i]);
        }

        return result;
    }

    private static void validateInput(int[] nums) {
        if (nums == null) {
            throw new IllegalArgumentException("Input array cannot be null.");
        }
    }

    public static void main(String[] args) {
        int[][] examples = {
                {4, 5, 2, 10, 8},
                {2, 1, 2, 4, 3},
                {1, 2, 3, 4},
                {4, 3, 2, 1},
                {5, 5, 5},
                {}
        };

        for (int[] nums : examples) {
            System.out.println(
                    "Input:  " + Arrays.toString(nums)
            );

            System.out.println(
                    "Brute:  "
                            + Arrays.toString(nextGreaterBruteForce(nums))
            );

            System.out.println(
                    "Stack:  "
                            + Arrays.toString(nextGreater(nums))
            );

            System.out.println(
                    "RTL:    "
                            + Arrays.toString(nextGreaterRightToLeft(nums))
            );

            System.out.println();
        }
    }
}
