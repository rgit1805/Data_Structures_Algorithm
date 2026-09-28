package stacks;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

/**
 * Day 76: Largest Rectangle in Histogram
 *
 * Given bar heights, find the largest rectangular area that can be formed.
 *
 * Example:
 *   [2, 1, 5, 6, 2, 3] -> 10
 *
 * This is a fundamental monotonic-stack problem and builds directly on
 * the previous Next Greater Element / Stock Span problems.
 */
public class Day76LargestRectangleInHistogram {

    /**
     * Brute-force approach.
     *
     * For each bar, expand left and right while bars remain at least
     * as tall as the current bar.
     *
     * Time: O(n^2)
     * Space: O(1)
     */
    public static long largestRectangleBruteForce(int[] heights) {
        validateInput(heights);

        long maxArea = 0;

        for (int i = 0; i < heights.length; i++) {
            int left = i;
            int right = i;

            while (left >= 0 && heights[left] >= heights[i]) {
                left--;
            }

            while (right < heights.length && heights[right] >= heights[i]) {
                right++;
            }

            long width = right - left - 1L;
            maxArea = Math.max(maxArea, width * heights[i]);
        }

        return maxArea;
    }

    /**
     * Optimal monotonic increasing stack.
     *
     * The stack stores indices whose heights are in increasing order.
     * When a shorter bar arrives, the taller bars being popped have found
     * their first smaller bar on the right.
     *
     * For a popped bar:
     *   height = heights[index]
     *   right boundary = current index
     *   left boundary = new stack top
     *
     * width = right - left - 1
     *
     * Time: O(n)
     * Space: O(n)
     */
    public static long largestRectangle(int[] heights) {
        validateInput(heights);

        Deque<Integer> stack = new ArrayDeque<>();
        long maxArea = 0;

        for (int i = 0; i <= heights.length; i++) {
            int currentHeight = (i == heights.length) ? 0 : heights[i];

            while (!stack.isEmpty()
                    && heights[stack.peek()] > currentHeight) {

                int heightIndex = stack.pop();
                long height = heights[heightIndex];

                int leftBoundary = stack.isEmpty() ? -1 : stack.peek();
                long width = i - leftBoundary - 1L;

                maxArea = Math.max(maxArea, height * width);
            }

            stack.push(i);
        }

        return maxArea;
    }

    /**
     * Alternative implementation using an explicit sentinel-free
     * cleanup phase. Useful for understanding what happens to bars that
     * survive until the end of the histogram.
     *
     * Time: O(n)
     * Space: O(n)
     */
    public static long largestRectangleWithCleanup(int[] heights) {
        validateInput(heights);

        Deque<Integer> stack = new ArrayDeque<>();
        long maxArea = 0;

        for (int i = 0; i < heights.length; i++) {
            while (!stack.isEmpty()
                    && heights[stack.peek()] > heights[i]) {
                maxArea = updateArea(heights, stack, i, maxArea);
            }

            stack.push(i);
        }

        while (!stack.isEmpty()) {
            maxArea = updateArea(heights, stack, heights.length, maxArea);
        }

        return maxArea;
    }

    private static long updateArea(
            int[] heights,
            Deque<Integer> stack,
            int rightBoundary,
            long currentMax
    ) {
        int heightIndex = stack.pop();
        long height = heights[heightIndex];

        int leftBoundary = stack.isEmpty() ? -1 : stack.peek();
        long width = rightBoundary - leftBoundary - 1L;

        return Math.max(currentMax, height * width);
    }

    private static void validateInput(int[] heights) {
        if (heights == null) {
            throw new IllegalArgumentException(
                    "Heights array cannot be null."
            );
        }

        for (int height : heights) {
            if (height < 0) {
                throw new IllegalArgumentException(
                        "Heights cannot be negative."
                );
            }
        }
    }

    public static void main(String[] args) {
        int[][] examples = {
                {2, 1, 5, 6, 2, 3},
                {2, 4},
                {6, 2, 5, 4, 5, 1, 6},
                {2, 2, 2, 2},
                {1, 2, 3, 4, 5},
                {5, 4, 3, 2, 1},
                {},
                {0, 0, 0}
        };

        for (int[] heights : examples) {
            long brute = largestRectangleBruteForce(heights);
            long optimal = largestRectangle(heights);
            long cleanup = largestRectangleWithCleanup(heights);

            System.out.println(
                    "Input: " + Arrays.toString(heights)
            );
            System.out.println("Brute: " + brute);
            System.out.println("Stack: " + optimal);
            System.out.println("Cleanup: " + cleanup);
            System.out.println();
        }
    }
}
