package arrays;

/**
 * Day 6 - Single-pass / Greedy pattern.
 *
 * Finds the maximum profit from buying once and selling once.
 */
public class Day06BestTimeToBuyAndSellStock {

    public static void main(String[] args) {
        int[] prices = {7, 1, 5, 3, 6, 4};

        System.out.println("Maximum profit (brute force): "
                + maxProfitBruteForce(prices));
        System.out.println("Maximum profit (single pass): "
                + maxProfit(prices));
    }

    /**
     * Try every possible buy/sell pair.
     * Time Complexity: O(n^2)
     * Space Complexity: O(1)
     */
    static int maxProfitBruteForce(int[] prices) {
        validate(prices);

        int maxProfit = 0;

        for (int buy = 0; buy < prices.length - 1; buy++) {
            for (int sell = buy + 1; sell < prices.length; sell++) {
                maxProfit = Math.max(maxProfit, prices[sell] - prices[buy]);
            }
        }

        return maxProfit;
    }

    /**
     * Keep track of the lowest price seen so far and calculate the
     * best profit if we sell on the current day.
     *
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     */
    static int maxProfit(int[] prices) {
        validate(prices);

        int minimumPrice = prices[0];
        int maximumProfit = 0;

        for (int i = 1; i < prices.length; i++) {
            maximumProfit = Math.max(maximumProfit, prices[i] - minimumPrice);
            minimumPrice = Math.min(minimumPrice, prices[i]);
        }

        return maximumProfit;
    }

    private static void validate(int[] prices) {
        if (prices == null || prices.length < 2) {
            throw new IllegalArgumentException("At least two prices are required");
        }
        for (int price : prices) {
            if (price < 0) {
                throw new IllegalArgumentException("Prices cannot be negative");
            }
        }
    }
}
