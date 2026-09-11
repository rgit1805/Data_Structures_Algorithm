package arrays;

/**
 * Day 27: Rotate a square matrix 90 degrees clockwise in-place.
 *
 * Pattern:
 *   1. Transpose the matrix.
 *   2. Reverse every row.
 *
 * Time: O(n^2)
 * Extra Space: O(1)
 */
public class Day27RotateMatrix90Degrees {

    /** Rotates the matrix 90 degrees clockwise in-place. */
    public static void rotateClockwise(int[][] matrix) {
        validateSquareMatrix(matrix);

        transpose(matrix);
        reverseRows(matrix);
    }

    /**
     * Transpose: convert matrix[i][j] into matrix[j][i].
     * Only the upper triangle is swapped to avoid undoing swaps.
     */
    private static void transpose(int[][] matrix) {
        for (int row = 0; row < matrix.length; row++) {
            for (int col = row + 1; col < matrix.length; col++) {
                int temp = matrix[row][col];
                matrix[row][col] = matrix[col][row];
                matrix[col][row] = temp;
            }
        }
    }

    /** Reverse each row in-place. */
    private static void reverseRows(int[][] matrix) {
        for (int row = 0; row < matrix.length; row++) {
            int left = 0;
            int right = matrix[row].length - 1;

            while (left < right) {
                int temp = matrix[row][left];
                matrix[row][left] = matrix[row][right];
                matrix[row][right] = temp;
                left++;
                right--;
            }
        }
    }

    private static void validateSquareMatrix(int[][] matrix) {
        if (matrix == null || matrix.length == 0) {
            throw new IllegalArgumentException("Matrix must not be null or empty");
        }

        for (int[] row : matrix) {
            if (row == null || row.length != matrix.length) {
                throw new IllegalArgumentException(
                        "Matrix must be square (n x n)");
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
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };

        System.out.println("Before rotation:");
        printMatrix(matrix);

        rotateClockwise(matrix);

        System.out.println("After 90-degree clockwise rotation:");
        printMatrix(matrix);

        // Expected:
        // 7 4 1
        // 8 5 2
        // 9 6 3
    }
}
