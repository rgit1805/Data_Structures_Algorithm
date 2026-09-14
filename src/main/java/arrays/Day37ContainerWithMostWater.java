package arrays;

/**
 * Day 37: Container With Most Water.
 *
 * Find two vertical lines that form a container holding the maximum amount
 * of water. The area is limited by the shorter line.
 */
public class Day37ContainerWithMostWater {

    /**
     * Brute-force comparison of every pair.
     * Time: O(n^2), Space: O(1).
     */
    public static long maxAreaBruteForce(int[] heights) {
        validateInput(heights);

        long maxArea = 0;

        for (int left = 0; left < heights.length - 1; left++) {
            for (int right = left + 1; right < heights.length; right++) {
                long width = right - left;
                long height = Math.min(heights[left], heights[right]);
                maxArea = Math.max(maxArea, width * height);
            }
        }

        return maxArea;
    }

    /**
     * Two-pointer solution.
     *
     * Area = min(leftHeight, rightHeight) * (right - left).
     * Moving the taller line cannot improve the limiting height, while the
     * width becomes smaller. Therefore, move the shorter line inward.
     *
     * Time: O(n), Space: O(1).
     */
    public static long maxArea(int[] heights) {
        validateInput(heights);

        int left = 0;
        int right = heights.length - 1;
        long maxArea = 0;

        while (left < right) {
            long width = right - left;
            long height = Math.min(heights[left], heights[right]);
            maxArea = Math.max(maxArea, width * height);

            if (heights[left] < heights[right]) {
                left++;
            } else {
                right--;
            }
        }

        return maxArea;
    }

    private static void validateInput(int[] heights) {
        if (heights == null || heights.length < 2) {
            throw new IllegalArgumentException(
                    "At least two heights are required");
        }

        for (int height : heights) {
            if (height < 0) {
                throw new IllegalArgumentException(
                        "Heights must be non-negative");
            }
        }
    }

    public static void main(String[] args) {
        int[] heights = {1, 8, 6, 2, 5, 4, 8, 3, 7};

        System.out.println("Brute force max area: "
                + maxAreaBruteForce(heights));
        System.out.println("Two-pointer max area: "
                + maxArea(heights));
        // Expected: 49
    }
}
