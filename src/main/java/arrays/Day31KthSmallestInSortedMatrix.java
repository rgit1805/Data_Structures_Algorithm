package arrays;

/**
 * Day 31: K-th Smallest Element in a Sorted Matrix.
 *
 * Every row and every column is sorted in ascending order.
 * Binary-search the VALUE range rather than matrix indexes.
 */
public class Day31KthSmallestInSortedMatrix {

    /** Counts elements <= target using staircase traversal. O(n) time, O(1) space. */
    public static int countLessThanOrEqual(int[][] matrix, int target) {
        validateMatrix(matrix);

        int n = matrix.length;
        int row = n - 1;
        int col = 0;
        int count = 0;

        while (row >= 0 && col < n) {
            if (matrix[row][col] <= target) {
                count += row + 1;
                col++;
            } else {
                row--;
            }
        }

        return count;
    }

    /**
     * Returns the k-th smallest element.
     * Time: O(n log(maxValue - minValue)), Space: O(1).
     */
    public static int kthSmallest(int[][] matrix, int k) {
        validateMatrix(matrix);

        int n = matrix.length;
        if (k < 1 || k > n * n) {
            throw new IllegalArgumentException("k must be between 1 and n^2");
        }

        int low = matrix[0][0];
        int high = matrix[n - 1][n - 1];

        while (low < high) {
            int mid = low + (high - low) / 2;

            if (countLessThanOrEqual(matrix, mid) >= k) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }

        return low;
    }

    private static void validateMatrix(int[][] matrix) {
        if (matrix == null || matrix.length == 0) {
            throw new IllegalArgumentException("Matrix must not be null or empty");
        }

        int n = matrix.length;

        for (int[] row : matrix) {
            if (row == null || row.length != n) {
                throw new IllegalArgumentException("Matrix must be square (n x n)");
            }
        }

        for (int row = 0; row < n; row++) {
            for (int col = 1; col < n; col++) {
                if (matrix[row][col] < matrix[row][col - 1]) {
                    throw new IllegalArgumentException("Every row must be sorted");
                }
            }
        }

        for (int col = 0; col < n; col++) {
            for (int row = 1; row < n; row++) {
                if (matrix[row][col] < matrix[row - 1][col]) {
                    throw new IllegalArgumentException("Every column must be sorted");
                }
            }
        }
    }

    public static void main(String[] args) {
        int[][] matrix = {
                {1, 5, 9},
                {10, 11, 13},
                {12, 13, 15}
        };

        System.out.println("3rd smallest: " + kthSmallest(matrix, 3));
        System.out.println("8th smallest: " + kthSmallest(matrix, 8));
    }
}
