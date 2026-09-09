package arrays;

/**
 * Day 21: Koko Eating Bananas.
 *
 * Find the minimum eating speed k such that all banana piles can be eaten
 * within h hours. Koko eats from only one pile per hour and may finish a
 * pile before the hour ends.
 *
 * This is a classic Binary Search on Answer problem.
 */
public class Day21KokoEatingBananas {

    /**
     * Checks whether a given eating speed can finish all piles within h hours.
     *
     * For a pile of size p, hours needed = ceil(p / speed).
     * Integer arithmetic: ceil(p / speed) = (p + speed - 1) / speed.
     *
     * Time: O(n), Space: O(1)
     */
    public static boolean canFinish(int[] piles, int h, int speed) {
        validateInput(piles, h);

        if (speed <= 0) {
            return false;
        }

        long hours = 0;

        for (int pile : piles) {
            hours += (pile + (long) speed - 1) / speed;

            // No need to continue once the limit is exceeded.
            if (hours > h) {
                return false;
            }
        }

        return true;
    }

    /**
     * Finds the minimum integer eating speed that finishes all piles in h hours.
     *
     * Search space:
     *   left  = 1 banana/hour
     *   right = largest pile (always sufficient)
     *
     * Time: O(n log(max(piles)))
     * Space: O(1)
     */
    public static int minEatingSpeed(int[] piles, int h) {
        validateInput(piles, h);

        int left = 1;
        int right = 0;

        for (int pile : piles) {
            right = Math.max(right, pile);
        }

        int answer = right;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (canFinish(piles, h, mid)) {
                answer = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }

        return answer;
    }

    private static void validateInput(int[] piles, int h) {
        if (piles == null || piles.length == 0) {
            throw new IllegalArgumentException("Piles must not be null or empty");
        }
        if (h < piles.length) {
            throw new IllegalArgumentException("Hours must be at least the number of piles");
        }
        for (int pile : piles) {
            if (pile <= 0) {
                throw new IllegalArgumentException("Pile sizes must be positive");
            }
        }
    }

    public static void main(String[] args) {
        int[] piles = {3, 6, 7, 11};
        int h = 8;
        System.out.println("Minimum eating speed: " + minEatingSpeed(piles, h));
        // Expected: 4

        int[] secondExample = {30, 11, 23, 4, 20};
        int secondHours = 5;
        System.out.println("Second example: " + minEatingSpeed(secondExample, secondHours));
        // Expected: 30
    }
}
