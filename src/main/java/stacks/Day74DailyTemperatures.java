package stacks;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

/**
 * Day 74: Daily Temperatures
 *
 * Given daily temperatures, return how many days one must wait until a
 * warmer temperature. If no warmer day exists, return 0.
 *
 * Example:
 *   [73, 74, 75, 71, 69, 72, 76, 73]
 *   -> [1, 1, 4, 2, 1, 1, 0, 0]
 *
 * This is a direct application of the monotonic decreasing stack pattern
 * introduced in Day 73.
 */
public class Day74DailyTemperatures {

    /**
     * Brute-force solution.
     *
     * For each day, scan future days until a warmer temperature is found.
     *
     * Time: O(n^2)
     * Space: O(1) apart from the output array.
     */
    public static int[] dailyTemperaturesBruteForce(int[] temperatures) {
        validateInput(temperatures);

        int[] result = new int[temperatures.length];

        for (int i = 0; i < temperatures.length; i++) {
            for (int j = i + 1; j < temperatures.length; j++) {
                if (temperatures[j] > temperatures[i]) {
                    result[i] = j - i;
                    break;
                }
            }
        }

        return result;
    }

    /**
     * Optimal monotonic decreasing stack.
     *
     * The stack stores indices of days whose warmer day has not yet
     * been found. Temperatures represented by those indices are kept
     * in decreasing order.
     *
     * When today's temperature is warmer than the temperature at the
     * stack top, today's index resolves that previous day.
     *
     * Time: O(n)
     * Space: O(n)
     */
    public static int[] dailyTemperatures(int[] temperatures) {
        validateInput(temperatures);

        int[] result = new int[temperatures.length];
        Deque<Integer> stack = new ArrayDeque<>();

        for (int today = 0; today < temperatures.length; today++) {

            while (!stack.isEmpty()
                    && temperatures[today] > temperatures[stack.peek()]) {

                int previousDay = stack.pop();
                result[previousDay] = today - previousDay;
            }

            stack.push(today);
        }

        return result;
    }

    /**
     * Right-to-left monotonic stack solution.
     *
     * For each day, remove temperatures that cannot be the next warmer
     * day because they are not warmer than the current temperature.
     *
     * Time: O(n)
     * Space: O(n)
     */
    public static int[] dailyTemperaturesRightToLeft(int[] temperatures) {
        validateInput(temperatures);

        int[] result = new int[temperatures.length];
        Deque<Integer> stack = new ArrayDeque<>();

        for (int today = temperatures.length - 1; today >= 0; today--) {

            while (!stack.isEmpty()
                    && temperatures[stack.peek()] <= temperatures[today]) {
                stack.pop();
            }

            if (!stack.isEmpty()) {
                result[today] = stack.peek() - today;
            }

            stack.push(today);
        }

        return result;
    }

    private static void validateInput(int[] temperatures) {
        if (temperatures == null) {
            throw new IllegalArgumentException(
                    "Temperature array cannot be null."
            );
        }
    }

    public static void main(String[] args) {
        int[][] examples = {
                {73, 74, 75, 71, 69, 72, 76, 73},
                {30, 40, 50, 60},
                {30, 60, 90},
                {90, 80, 70, 60},
                {70, 70, 71},
                {}
        };

        for (int[] temperatures : examples) {
            System.out.println(
                    "Input:  " + Arrays.toString(temperatures)
            );

            System.out.println(
                    "Brute:  "
                            + Arrays.toString(
                                    dailyTemperaturesBruteForce(temperatures)
                            )
            );

            System.out.println(
                    "Stack:  "
                            + Arrays.toString(
                                    dailyTemperatures(temperatures)
                            )
            );

            System.out.println(
                    "RTL:    "
                            + Arrays.toString(
                                    dailyTemperaturesRightToLeft(temperatures)
                            )
            );

            System.out.println();
        }
    }
}
