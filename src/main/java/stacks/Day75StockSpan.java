package stacks;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

/**
 * Day 75: Stock Span
 *
 * For each day's stock price, find the number of consecutive days ending
 * today for which the price was less than or equal to today's price.
 *
 * Example:
 *   Prices: [100, 80, 60, 70, 60, 75, 85]
 *   Span:   [1,   1,  1,  2,  1,  4,  6]
 *
 * This is another important monotonic-stack application.
 */
public class Day75StockSpan {

    /**
     * Brute-force approach.
     *
     * For each day, walk backward while prices are <= today's price.
     *
     * Time: O(n^2)
     * Space: O(1) apart from output.
     */
    public static int[] calculateSpanBruteForce(int[] prices) {
        validateInput(prices);

        int[] span = new int[prices.length];

        for (int i = 0; i < prices.length; i++) {
            int count = 1;

            for (int j = i - 1; j >= 0; j--) {
                if (prices[j] <= prices[i]) {
                    count++;
                } else {
                    break;
                }
            }

            span[i] = count;
        }

        return span;
    }

    /**
     * Optimal monotonic decreasing stack.
     *
     * The stack stores indices of prices that are still useful as
     * previous greater elements.
     *
     * Before calculating today's span, remove all previous prices that
     * are <= today's price. The remaining stack top is the nearest
     * previous greater price.
     *
     * Time: O(n)
     * Space: O(n)
     */
    public static int[] calculateSpan(int[] prices) {
        validateInput(prices);

        int[] span = new int[prices.length];
        Deque<Integer> stack = new ArrayDeque<>();

        for (int today = 0; today < prices.length; today++) {

            while (!stack.isEmpty()
                    && prices[stack.peek()] <= prices[today]) {
                stack.pop();
            }

            if (stack.isEmpty()) {
                span[today] = today + 1;
            } else {
                span[today] = today - stack.peek();
            }

            stack.push(today);
        }

        return span;
    }

    /**
     * Online-style stock span calculator.
     *
     * Instead of receiving the complete array, prices can be processed
     * one at a time. Each stack entry stores:
     *   [price, span]
     *
     * This demonstrates why monotonic stacks are useful in streaming
     * and incremental processing.
     */
    public static class StockSpanner {
        private final Deque<Entry> stack = new ArrayDeque<>();

        /**
         * Adds one day's price and returns its span.
         *
         * Amortized time: O(1) per next() call.
         * Worst-case single call: O(n).
         * Space: O(n).
         */
        public int next(int price) {
            int currentSpan = 1;

            while (!stack.isEmpty()
                    && stack.peek().price <= price) {
                currentSpan += stack.pop().span;
            }

            stack.push(new Entry(price, currentSpan));
            return currentSpan;
        }

        private static class Entry {
            private final int price;
            private final int span;

            private Entry(int price, int span) {
                this.price = price;
                this.span = span;
            }
        }
    }

    private static void validateInput(int[] prices) {
        if (prices == null) {
            throw new IllegalArgumentException(
                    "Price array cannot be null."
            );
        }
    }

    public static void main(String[] args) {
        int[] prices = {100, 80, 60, 70, 60, 75, 85};

        System.out.println("Prices: "
                + Arrays.toString(prices));

        System.out.println("Brute:  "
                + Arrays.toString(calculateSpanBruteForce(prices)));

        System.out.println("Stack:  "
                + Arrays.toString(calculateSpan(prices)));

        StockSpanner spanner = new StockSpanner();

        System.out.println("\nOnline StockSpanner:");

        for (int price : prices) {
            System.out.println(
                    "Price = " + price
                            + ", Span = " + spanner.next(price)
            );
        }

        System.out.println("\nExpected:");
        System.out.println("[1, 1, 1, 2, 1, 4, 6]");
    }
}
