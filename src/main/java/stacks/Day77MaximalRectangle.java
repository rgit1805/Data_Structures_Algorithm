package stacks;

import java.util.Arrays;
import java.util.Deque;
import java.util.ArrayDeque;

/**
 * Day 77: Maximal Rectangle
 *
 * Given a binary matrix containing '0' and '1', find the area of the
 * largest rectangle containing only '1's.
 *
 * Example:
 *   1 0 1 0 0
 *   1 0 1 1 1
 *   1 1 1 1 1
 *   1 0 0 1 0
 *
 * Answer: 6
 *
 * Key idea:
 * Convert each matrix row into a histogram of consecutive 1s and reuse
 * the Largest Rectangle in Histogram algorithm from Day 76.
 */
public class Day77MaximalRectangle {

    /**
     * Brute-force-style approach.
     *
     * For every cell containing 1, expand possible rectangles and verify
     * their area using prefix heights.
     *
     * This implementation is intentionally simple for comparison with
     * the optimized histogram solution.
     *
     * Time: O(rows * cols * rows * cols) in the worst case.
     * Space: O(cols) for heights.
     */
    public static long maximalRectangleBruteForce(char[][] matrix) {
        validateInput(matrix);

        if (matrix.length == 0 || matrix[0].length == 0) {
            return 0;
        }

        int rows = matrix.length;
        int cols = matrix[0].length;
        int[] heights = new int[cols];
        long maxArea = 0;

        for (int row = 0; row < rows; row++) {
            updateHeights(matrix[row], heights);

            for (int left = 0; left < cols; left++) {
                int minimumHeight = Integer.MAX_VALUE;

                for (int right = left; right < cols; right++) {
                    minimumHeight = Math.min(
                            minimumHeight,
                            heights[right]
                    );

                    long width = right - left + 1L;
                    maxArea = Math.max(
                            maxArea,
                            width * minimumHeight
                    );
                }
            }
        }

        return maxArea;
    }

    /**
     * Optimal solution.
     *
     * Every row becomes the base of a histogram:
     *
     * matrix:
     *   1 0 1 1
     *   1 1 1 1
     *
     * heights after row 1:
     *   1 0 1 1
     *
     * heights after row 2:
     *   2 1 2 2
     *
     * For every row, calculate the largest histogram rectangle.
     *
     * Time: O(rows * cols)
     * Space: O(cols)
     */
    public static long maximalRectangle(char[][] matrix) {
        validateInput(matrix);

        if (matrix.length == 0 || matrix[0].length == 0) {
            return 0;
        }

        int cols = matrix[0].length;
        int[] heights = new int[cols];
        long maxArea = 0;

        for (char[] row : matrix) {
            updateHeights(row, heights);
            maxArea = Math.max(
                    maxArea,
                    largestRectangle(heights)
            );
        }

        return maxArea;
    }

    /**
     * Reuses the monotonic-stack algorithm from Day 76.
     *
     * Time: O(cols)
     * Space: O(cols)
     */
    private static long largestRectangle(int[] heights) {
        Deque<Integer> stack = new ArrayDeque<>();
        long maxArea = 0;

        for (int i = 0; i <= heights.length; i++) {
            int currentHeight = i == heights.length
                    ? 0
                    : heights[i];

            while (!stack.isEmpty()
                    && heights[stack.peek()] > currentHeight) {

                int heightIndex = stack.pop();
                long height = heights[heightIndex];

                int leftBoundary = stack.isEmpty()
                        ? -1
                        : stack.peek();

                long width = i - leftBoundary - 1L;

                maxArea = Math.max(
                        maxArea,
                        height * width
                );
            }

            stack.push(i);
        }

        return maxArea;
    }

    private static void updateHeights(char[] row, int[] heights) {
        for (int col = 0; col < row.length; col++) {
            if (row[col] == '1') {
                heights[col]++;
            } else {
                heights[col] = 0;
            }
        }
    }

    private static void validateInput(char[][] matrix) {
        if (matrix == null) {
            throw new IllegalArgumentException(
                    "Matrix cannot be null."
            );
        }

        if (matrix.length == 0) {
            return;
        }

        if (matrix[0] == null) {
            throw new IllegalArgumentException(
                    "Matrix rows cannot be null."
            );
        }

        int cols = matrix[0].length;

        for (char[] row : matrix) {
            if (row == null || row.length != cols) {
                throw new IllegalArgumentException(
                        "Matrix must be rectangular."
                );
            }

            for (char value : row) {
                if (value != '0' && value != '1') {
                    throw new IllegalArgumentException(
                            "Matrix can contain only '0' and '1'."
                    );
                }
            }
        }
    }

    private static void printMatrix(char[][] matrix) {
        for (char[] row : matrix) {
            System.out.println(Arrays.toString(row));
        }
    }

    public static void main(String[] args) {
        char[][] matrix1 = {
                {'1', '0', '1', '0', '0'},
                {'1', '0', '1', '1', '1'},
                {'1', '1', '1', '1', '1'},
                {'1', '0', '0', '1', '0'}
        };

        char[][] matrix2 = {
                {'1', '1', '1', '1'},
                {'1', '1', '1', '1'},
                {'1', '1', '1', '1'}
        };

        char[][] matrix3 = {
                {'0', '0'},
                {'0', '0'}
        };

        char[][] matrix4 = {
                {'1'}
        };

        char[][][] examples = {
                matrix1,
                matrix2,
                matrix3,
                matrix4,
                {}
        };

        for (char[][] matrix : examples) {
            System.out.println("Matrix:");

            if (matrix.length == 0) {
                System.out.println("[]");
            } else {
                printMatrix(matrix);
            }

            System.out.println(
                    "Brute: "
                            + maximalRectangleBruteForce(matrix)
            );

            System.out.println(
                    "Optimal: "
                            + maximalRectangle(matrix)
            );

            System.out.println();
        }
    }
}
