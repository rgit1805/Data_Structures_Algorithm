package arrays;

/**
 * Day 28: Set Matrix Zeroes.
 *
 * If a cell is 0, set its entire row and column to 0.
 *
 * The optimal solution uses the first row and first column as marker
 * storage, achieving O(1) extra space.
 */
public class Day28SetMatrixZeroes {

    /**
     * Brute-force approach using separate row/column marker arrays.
     *
     * Time: O(m * n)
     * Extra Space: O(m + n)
     */
    public static void setZeroesWithMarkers(int[][] matrix) {
        validateMatrix(matrix);

        int rows = matrix.length;
        int cols = matrix[0].length;

        boolean[] zeroRows = new boolean[rows];
        boolean[] zeroCols = new boolean[cols];

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (matrix[row][col] == 0) {
                    zeroRows[row] = true;
                    zeroCols[col] = true;
                }
            }
        }

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (zeroRows[row] || zeroCols[col]) {
                    matrix[row][col] = 0;
                }
            }
        }
    }

    /**
     * Optimal in-place solution.
     *
     * The first row and first column store whether their corresponding
     * rows/columns need to become zero.
     *
     * Time: O(m * n)
     * Extra Space: O(1)
     */
    public static void setZeroes(int[][] matrix) {
        validateMatrix(matrix);

        int rows = matrix.length;
        int cols = matrix[0].length;

        boolean firstRowZero = false;
        boolean firstColZero = false;

        // Check whether the first row itself contains a zero.
        for (int col = 0; col < cols; col++) {
            if (matrix[0][col] == 0) {
                firstRowZero = true;
                break;
            }
        }

        // Check whether the first column itself contains a zero.
        for (int row = 0; row < rows; row++) {
            if (matrix[row][0] == 0) {
                firstColZero = true;
                break;
            }
        }

        // Use first row and first column as markers.
        for (int row = 1; row < rows; row++) {
            for (int col = 1; col < cols; col++) {
                if (matrix[row][col] == 0) {
                    matrix[row][0] = 0;
                    matrix[0][col] = 0;
                }
            }
        }

        // Apply marked rows.
        for (int row = 1; row < rows; row++) {
            if (matrix[row][0] == 0) {
                for (int col = 1; col < cols; col++) {
                    matrix[row][col] = 0;
                }
            }
        }

        // Apply marked columns.
        for (int col = 1; col < cols; col++) {
            if (matrix[0][col] == 0) {
                for (int row = 1; row < rows; row++) {
                    matrix[row][col] = 0;
                }
            }
        }

        // Finally handle the original first row/column.
        if (firstRowZero) {
            for (int col = 0; col < cols; col++) {
                matrix[0][col] = 0;
            }
        }

        if (firstColZero) {
            for (int row = 0; row < rows; row++) {
                matrix[row][0] = 0;
            }
        }
    }

    private static void validateMatrix(int[][] matrix) {
        if (matrix == null || matrix.length == 0
                || matrix[0] == null || matrix[0].length == 0) {
            throw new IllegalArgumentException("Matrix must not be null or empty");
        }

        int cols = matrix[0].length;
        for (int[] row : matrix) {
            if (row == null || row.length != cols) {
                throw new IllegalArgumentException(
                        "Matrix must be rectangular");
            }
        }
    }

    private static void printMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            for (int value : row) {
                System.out.print(value + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        int[][] matrix = {
                {1, 1, 1},
                {1, 0, 1},
                {1, 1, 1}
        };

        System.out.println("Before:");
        printMatrix(matrix);

        setZeroes(matrix);

        System.out.println("After:");
        printMatrix(matrix);

        // Expected:
        // 1 0 1
        // 0 0 0
        // 1 0 1
    }
}
