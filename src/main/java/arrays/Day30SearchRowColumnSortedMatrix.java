package arrays;

/**
 * Day 30: Search in a Row and Column Sorted Matrix.
 *
 * Matrix properties:
 * - Each row is sorted from left to right.
 * - Each column is sorted from top to bottom.
 *
 * Optimal approach: start at the top-right corner.
 * - If current > target, move left.
 * - If current < target, move down.
 * - If current == target, found.
 *
 * Time: O(rows + cols)
 * Extra Space: O(1)
 */
public class Day30SearchRowColumnSortedMatrix {

    /**
     * Brute-force search.
     * Time: O(rows * cols), Space: O(1)
     */
    public static boolean searchBruteForce(int[][] matrix, int target) {
        validateMatrix(matrix);

        for (int[] row : matrix) {
            for (int value : row) {
                if (value == target) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Staircase search from the top-right corner.
     *
     * At every step, one complete row or column is eliminated.
     */
    public static boolean search(int[][] matrix, int target) {
        validateMatrix(matrix);

        int rows = matrix.length;
        int cols = matrix[0].length;

        int row = 0;
        int col = cols - 1;

        while (row < rows && col >= 0) {
            int current = matrix[row][col];

            if (current == target) {
                return true;
            }

            if (current > target) {
                col--;
            } else {
                row++;
            }
        }

        return false;
    }

    private static void validateMatrix(int[][] matrix) {
        if (matrix == null || matrix.length == 0
                || matrix[0] == null || matrix[0].length == 0) {
            throw new IllegalArgumentException("Matrix must not be null or empty");
        }

        int cols = matrix[0].length;

        for (int[] row : matrix) {
            if (row == null || row.length != cols) {
                throw new IllegalArgumentException("Matrix must be rectangular");
            }
        }

        // Validate rows.
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 1; col < cols; col++) {
                if (matrix[row][col] < matrix[row][col - 1]) {
                    throw new IllegalArgumentException(
                            "Every row must be sorted in ascending order");
                }
            }
        }

        // Validate columns.
        for (int col = 0; col < cols; col++) {
            for (int row = 1; row < matrix.length; row++) {
                if (matrix[row][col] < matrix[row - 1][col]) {
                    throw new IllegalArgumentException(
                            "Every column must be sorted in ascending order");
                }
            }
        }
    }

    public static void main(String[] args) {
        int[][] matrix = {
                {1, 4, 7, 11, 15},
                {2, 5, 8, 12, 19},
                {3, 6, 9, 16, 22},
                {10, 13, 14, 17, 24},
                {18, 21, 23, 26, 30}
        };

        int target = 5;

        System.out.println("Brute force found: "
                + searchBruteForce(matrix, target));
        System.out.println("Staircase search found: "
                + search(matrix, target));

        System.out.println("Search for 20: " + search(matrix, 20));
        // Expected: false
    }
}
