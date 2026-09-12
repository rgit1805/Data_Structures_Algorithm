package arrays;

import java.util.ArrayList;
import java.util.List;

/**
 * Day 32: Pascal's Triangle.
 *
 * Each row starts and ends with 1.
 * Every interior value is the sum of the two values directly above it.
 *
 * Example:
 *        1
 *       1 1
 *      1 2 1
 *     1 3 3 1
 *    1 4 6 4 1
 */
public class Day32PascalsTriangle {

    /**
     * Builds the first numRows rows.
     *
     * Time: O(numRows^2)
     * Space: O(numRows^2) for the returned triangle.
     */
    public static List<List<Integer>> generate(int numRows) {
        if (numRows < 0) {
            throw new IllegalArgumentException("Number of rows cannot be negative");
        }

        List<List<Integer>> triangle = new ArrayList<>();

        for (int row = 0; row < numRows; row++) {
            List<Integer> currentRow = new ArrayList<>();

            currentRow.add(1);

            for (int col = 1; col < row; col++) {
                int value = triangle.get(row - 1).get(col - 1)
                        + triangle.get(row - 1).get(col);
                currentRow.add(value);
            }

            if (row > 0) {
                currentRow.add(1);
            }

            triangle.add(currentRow);
        }

        return triangle;
    }

    /**
     * Returns a single zero-indexed row.
     *
     * Uses the recurrence:
     * C(row, col) = C(row, col - 1) * (row - col + 1) / col
     *
     * Time: O(row)
     * Space: O(row)
     */
    public static List<Long> getRow(int rowIndex) {
        if (rowIndex < 0) {
            throw new IllegalArgumentException("Row index cannot be negative");
        }

        List<Long> row = new ArrayList<>();
        long value = 1;

        for (int col = 0; col <= rowIndex; col++) {
            row.add(value);

            if (col < rowIndex) {
                value = value * (rowIndex - col) / (col + 1);
            }
        }

        return row;
    }

    public static void main(String[] args) {
        System.out.println("First 5 rows:");
        List<List<Integer>> triangle = generate(5);

        for (List<Integer> row : triangle) {
            System.out.println(row);
        }

        System.out.println("Row 4: " + getRow(4));
        // Expected: [1, 4, 6, 4, 1]
    }
}
