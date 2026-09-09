package arrays;

/**
 * Day 19: Integer Square Root using Binary Search.
 *
 * Finds floor(sqrt(x)) without using Math.sqrt().
 * The same idea is a Binary Search on Answer: search the possible answers
 * and check whether mid * mid is <= x.
 */
public class Day19SquareRootBinarySearch {

    /**
     * Linear-search approach for comparison.
     * Time: O(sqrt(x)), Space: O(1)
     */
    public static int squareRootBruteForce(int x) {
        validateInput(x);

        if (x < 2) {
            return x;
        }

        int answer = 1;
        for (int i = 1; i <= x / i; i++) {
            answer = i;
        }
        return answer;
    }

    /**
     * Binary Search on Answer.
     * Returns floor(sqrt(x)).
     * Time: O(log x), Space: O(1)
     */
    public static int squareRoot(int x) {
        validateInput(x);

        if (x < 2) {
            return x;
        }

        int left = 1;
        int right = x / 2;
        int answer = 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            // Use x / mid instead of mid * mid to avoid integer overflow.
            if (mid <= x / mid) {
                answer = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return answer;
    }

    private static void validateInput(int x) {
        if (x < 0) {
            throw new IllegalArgumentException("Square root is defined here for non-negative integers");
        }
    }

    public static void main(String[] args) {
        int[] inputs = {0, 1, 4, 8, 16, 25, 50};

        for (int x : inputs) {
            System.out.println(
                    "x = " + x
                            + ", brute force = " + squareRootBruteForce(x)
                            + ", binary search = " + squareRoot(x));
        }
    }
}
