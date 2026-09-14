package arrays;

/**
 * Day 38: Trapping Rain Water.
 *
 * Given elevation heights, calculate how much rain water can be trapped
 * between the bars.
 */
public class Day38TrappingRainWater {

    /**
     * Brute-force solution.
     * For each position, find the highest bar on its left and right.
     *
     * Time: O(n^2), Space: O(1).
     */
    public static long trapBruteForce(int[] height) {
        validateInput(height);

        long water = 0;

        for (int i = 0; i < height.length; i++) {
            int leftMax = 0;
            int rightMax = 0;

            for (int left = 0; left <= i; left++) {
                leftMax = Math.max(leftMax, height[left]);
            }

            for (int right = i; right < height.length; right++) {
                rightMax = Math.max(rightMax, height[right]);
            }

            water += Math.min(leftMax, rightMax) - height[i];
        }

        return water;
    }

    /**
     * Prefix/suffix maximum solution.
     *
     * For every position i:
     * trapped water = min(maxLeft[i], maxRight[i]) - height[i].
     *
     * Time: O(n), Space: O(n).
     */
    public static long trapWithPrefixSuffix(int[] height) {
        validateInput(height);

        int n = height.length;
        int[] maxLeft = new int[n];
        int[] maxRight = new int[n];

        maxLeft[0] = height[0];
        for (int i = 1; i < n; i++) {
            maxLeft[i] = Math.max(maxLeft[i - 1], height[i]);
        }

        maxRight[n - 1] = height[n - 1];
        for (int i = n - 2; i >= 0; i--) {
            maxRight[i] = Math.max(maxRight[i + 1], height[i]);
        }

        long water = 0;
        for (int i = 0; i < n; i++) {
            water += Math.min(maxLeft[i], maxRight[i]) - height[i];
        }

        return water;
    }

    /**
     * Optimal two-pointer solution.
     *
     * leftMax and rightMax represent the highest walls seen so far.
     * When leftMax <= rightMax, the left side can be resolved because
     * rightMax is guaranteed to be at least as high as leftMax.
     * Otherwise, resolve the right side.
     *
     * Time: O(n), Space: O(1).
     */
    public static long trap(int[] height) {
        validateInput(height);

        int left = 0;
        int right = height.length - 1;
        int leftMax = 0;
        int rightMax = 0;
        long water = 0;

        while (left <= right) {
            if (height[left] <= height[right]) {
                if (height[left] >= leftMax) {
                    leftMax = height[left];
                } else {
                    water += (long) leftMax - height[left];
                }
                left++;
            } else {
                if (height[right] >= rightMax) {
                    rightMax = height[right];
                } else {
                    water += (long) rightMax - height[right];
                }
                right--;
            }
        }

        return water;
    }

    private static void validateInput(int[] height) {
        if (height == null) {
            throw new IllegalArgumentException("Height array must not be null");
        }

        for (int value : height) {
            if (value < 0) {
                throw new IllegalArgumentException(
                        "Heights must be non-negative");
            }
        }
    }

    public static void main(String[] args) {
        int[] heights = {0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1};

        System.out.println("Brute force: " + trapBruteForce(heights));
        System.out.println("Prefix/suffix: " + trapWithPrefixSuffix(heights));
        System.out.println("Two pointers: " + trap(heights));
        // Expected: 6

        int[] secondExample = {4, 2, 0, 3, 2, 5};
        System.out.println("Second example: " + trap(secondExample));
        // Expected: 9
    }
}
