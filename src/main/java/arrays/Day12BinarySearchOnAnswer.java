package arrays;

/**
 * Day 12 - Binary Search on Answer / Search Space.
 *
 * Instead of searching an array for a value, binary search can find the
 * smallest feasible answer. The key requirement is a monotonic feasibility
 * condition: if a value works, every larger value must also work (or vice
 * versa).
 *
 * Example: Given weights of packages and D days, find the minimum ship
 * capacity that can deliver all packages within D days.
 */
public class Day12BinarySearchOnAnswer {

    public static void main(String[] args) {
        int[] weights = {1, 2, 3, 1, 1};
        int days = 4;

        System.out.println("Minimum ship capacity: "
                + minCapacity(weights, days));
    }

    /**
     * Finds the minimum ship capacity needed to ship all packages in order
     * within the given number of days.
     *
     * Search space:
     *   lower bound = heaviest package
     *   upper bound = total weight (ship everything in one day)
     *
     * Time Complexity: O(n log(sum(weights)))
     * Space Complexity: O(1)
     */
    static int minCapacity(int[] weights, int days) {
        validate(weights, days);

        int left = 0;
        int right = 0;

        for (int weight : weights) {
            left = Math.max(left, weight);
            right += weight;
        }

        int answer = right;

        while (left <= right) {
            int capacity = left + (right - left) / 2;

            if (canShipWithinDays(weights, days, capacity)) {
                answer = capacity;
                // Try a smaller capacity.
                right = capacity - 1;
            } else {
                // Capacity is too small; increase it.
                left = capacity + 1;
            }
        }

        return answer;
    }

    /**
     * Checks whether all packages can be shipped within days using capacity.
     * Packages must be shipped in the given order.
     */
    private static boolean canShipWithinDays(int[] weights, int days, int capacity) {
        int usedDays = 1;
        int currentLoad = 0;

        for (int weight : weights) {
            if (currentLoad + weight > capacity) {
                usedDays++;
                currentLoad = 0;
            }

            currentLoad += weight;

            if (usedDays > days) {
                return false;
            }
        }

        return true;
    }

    private static void validate(int[] weights, int days) {
        if (weights == null || weights.length == 0) {
            throw new IllegalArgumentException("Weights must not be null or empty");
        }

        if (days <= 0) {
            throw new IllegalArgumentException("Days must be positive");
        }

        for (int weight : weights) {
            if (weight <= 0) {
                throw new IllegalArgumentException("Package weights must be positive");
            }
        }

        if (days > weights.length) {
            throw new IllegalArgumentException(
                    "Days cannot exceed the number of packages for this problem");
        }
    }
}
