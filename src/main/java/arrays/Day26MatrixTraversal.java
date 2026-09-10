package arrays;

/**
 * Day 26: 2D Arrays / Matrix Traversal.
 *
 * Introduces row-wise, column-wise, boundary and spiral traversal.
 */
public class Day26MatrixTraversal {

    /** Row-wise traversal: O(rows * cols) time, O(1) extra space. */
    public static void printRowWise(int[][] matrix) {
        validateMatrix(matrix);

        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[row].length; col++) {
                System.out.print(matrix[row][col] + " ");
            }
            System.out.println();
        }
    }

    /** Column-wise traversal for a rectangular matrix. */
    public static void printColumnWise(int[][] matrix) {
        validateMatrix(matrix);

        int rows = matrix.length;
        int cols = matrix[0].length;

        for (int col = 0; col < cols; col++) {
            for (int row = 0; row < rows; row++) {
                System.out.print(matrix[row][col] + " ");
            }
            System.out.println();
        }
    }

    /**
     * Returns the elements in clockwise spiral order.
     * Time: O(rows * cols), Space: O(rows * cols) for the result.
     */
    public static int[] spiralOrder(int[][] matrix) {
        validateMatrix(matrix);

        int rows = matrix.length;
        int cols = matrix[0].length;
        int[] result = new int[rows * cols];
        int index = 0;

        int top = 0;
        int bottom = rows - 1;
        int left = 0;
        int right = cols - 1;

        while (top <= bottom && left <= right) {
            // Top row: left -> right
            for (int col = left; col <= right; col++) {
                result[index++] = matrix[top][col];
            }
            top++;

            // Right column: top -> bottom
            for (int row = top; row <= bottom; row++) {
                result[index++] = matrix[row][right];
            }
            right--;

            // Bottom row: right -> left
            if (top <= bottom) {
                for (int col = right; col >= left; col--) {
                    result[index++] = matrix[bottom][col];
                }
                bottom--;
            }

            // Left column: bottom -> top
            if (left <= right) {
                for (int row = bottom; row >= top; row--) {
                    result[index++] = matrix[row][left];
                }
                left++;
            }
        }

        return result;
    }

    private static void validateMatrix(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0] == null
                || matrix[0].length == 0) {
            throw new IllegalArgumentException("Matrix must not be null or empty");
        }

        int columns = matrix[0].length;
        for (int[] row : matrix) {
            if (row == null || row.length != columns) {
                throw new IllegalArgumentException(
                        "Matrix must be rectangular (all rows same length)");
            }
        }
    }

    private static void printArray(int[] values) {
        for (int value : values) {
            System.out.print(value + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int[][] matrix = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };

        System.out.println("Row-wise:");
        printRowWise(matrix);

        System.out.println("Column-wise:");
        printColumnWise(matrix);

        System.out.println("Spiral order:");
        printArray(spiralOrder(matrix));
        // Expected: 1 2 3 6 9 8 7 4 5
    }
}
