package arrays;

/**
 * Day 29: Search in a 2D Matrix.
 *
 * Matrix properties:
 * 1. Each row is sorted in ascending order.
 * 2. The first element of each row is greater than the last element
 *    of the previous row.
 *
 * Therefore, the matrix can be treated as one sorted 1D array.
 */
public class Day29SearchIn2DMatrix {

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
     * Binary search by treating the matrix as a flattened sorted array.
     *
     * For a virtual index:
     *   row = index / cols
     *   col = index % cols
     *
     * Time: O(log(rows * cols)), Space: O(1)
     */
    public static boolean search(int[][] matrix, int target) {
        validateMatrix(matrix);

        int rows = matrix.length;
        int cols = matrix[0].length;

        int left = 0;
        int right = rows * cols - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            int row = mid / cols;
            int col = mid % cols;
            int value = matrix[row][col];

            if (value == target) {
                return true;
            }

            if (value < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
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

        // Verify the matrix satisfies the sorted-matrix property.
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 1; col < cols; col++) {
                if (matrix[row][col] < matrix[row][col - 1]) {
                    throw new IllegalArgumentException(
                            "Each row must be sorted in ascending order");
                }
            }

            if (row > 0 && matrix[row][0] <= matrix[row - 1][cols - 1]) {
                throw new IllegalArgumentException(
                        "Each row must start after the previous row ends");
            }
        }
    }

    public static void main(String[] args) {
        int[][] matrix = {
                {1, 3, 5, 7},
                {10, 11, 16, 20},
                {23, 30, 34, 60}
        };

        int target = 3;

        System.out.println("Brute force found: "
                + searchBruteForce(matrix, target));
        System.out.println("Binary search found: "
                + search(matrix, target));

        System.out.println("Search for 13: " + search(matrix, 13));
        // Expected: false
    }
}
