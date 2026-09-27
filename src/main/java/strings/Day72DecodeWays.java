package strings;

/**
 * Day 72: Decode Ways
 *
 * A message containing digits can be decoded using:
 *   1 -> A, 2 -> B, ..., 26 -> Z
 *
 * Determine the number of possible decodings.
 *
 * Examples:
 *   "12"  -> 2  ("AB", "L")
 *   "226" -> 3  ("BBF", "BZ", "VF")
 *   "06"  -> 0
 *
 * The important constraint is that '0' cannot be decoded alone.
 * It can only participate in "10" or "20".
 */
public class Day72DecodeWays {

    /**
     * Brute-force recursive solution.
     *
     * Time: O(2^n) worst case.
     * Space: O(n) recursion depth.
     */
    public static long decodeRecursive(String text) {
        validateInput(text);
        return decodeFrom(text, 0);
    }

    private static long decodeFrom(String text, int index) {
        if (index == text.length()) {
            return 1;
        }

        if (text.charAt(index) == '0') {
            return 0;
        }

        long ways = decodeFrom(text, index + 1);

        if (index + 1 < text.length()) {
            int value = twoDigitValue(text, index);

            if (value >= 10 && value <= 26) {
                ways += decodeFrom(text, index + 2);
            }
        }

        return ways;
    }

    /**
     * Top-down dynamic programming with memoization.
     *
     * Time: O(n)
     * Space: O(n) memo + O(n) recursion stack.
     */
    public static long decodeMemoized(String text) {
        validateInput(text);

        Long[] memo = new Long[text.length() + 1];
        return decodeMemoized(text, 0, memo);
    }

    private static long decodeMemoized(
            String text,
            int index,
            Long[] memo
    ) {
        if (index == text.length()) {
            return 1;
        }

        if (text.charAt(index) == '0') {
            return 0;
        }

        if (memo[index] != null) {
            return memo[index];
        }

        long ways = decodeMemoized(text, index + 1, memo);

        if (index + 1 < text.length()) {
            int value = twoDigitValue(text, index);

            if (value >= 10 && value <= 26) {
                ways += decodeMemoized(text, index + 2, memo);
            }
        }

        memo[index] = ways;
        return ways;
    }

    /**
     * Bottom-up dynamic programming.
     *
     * dp[i] = number of ways to decode text.substring(i).
     *
     * Time: O(n)
     * Space: O(n)
     */
    public static long decodeBottomUp(String text) {
        validateInput(text);

        int n = text.length();

        if (n == 0) {
            return 1;
        }

        long[] dp = new long[n + 1];
        dp[n] = 1;

        for (int index = n - 1; index >= 0; index--) {
            if (text.charAt(index) == '0') {
                dp[index] = 0;
                continue;
            }

            dp[index] = dp[index + 1];

            if (index + 1 < n) {
                int value = twoDigitValue(text, index);

                if (value >= 10 && value <= 26) {
                    dp[index] += dp[index + 2];
                }
            }
        }

        return dp[0];
    }

    /**
     * Space-optimized DP.
     *
     * At each position only the next two DP states are required.
     *
     * Time: O(n)
     * Space: O(1)
     */
    public static long decode(String text) {
        validateInput(text);

        int n = text.length();

        if (n == 0) {
            return 1;
        }

        long next = 1; // dp[index + 1]
        long nextNext = 0; // dp[index + 2]

        for (int index = n - 1; index >= 0; index--) {
            long current;

            if (text.charAt(index) == '0') {
                current = 0;
            } else {
                current = next;

                if (index + 1 < n) {
                    int value = twoDigitValue(text, index);

                    if (value >= 10 && value <= 26) {
                        current += nextNext;
                    }
                }
            }

            nextNext = next;
            next = current;
        }

        return next;
    }

    private static int twoDigitValue(String text, int index) {
        return (text.charAt(index) - '0') * 10
                + (text.charAt(index + 1) - '0');
    }

    private static void validateInput(String text) {
        if (text == null) {
            throw new IllegalArgumentException("Input cannot be null.");
        }

        for (int i = 0; i < text.length(); i++) {
            if (!Character.isDigit(text.charAt(i))) {
                throw new IllegalArgumentException(
                        "Input must contain only digits."
                );
            }
        }
    }

    public static void main(String[] args) {
        String[] examples = {
                "12",
                "226",
                "06",
                "10",
                "20",
                "2101",
                "11106",
                "",
                "1",
                "27"
        };

        System.out.println("Decode Ways:");

        for (String text : examples) {
            System.out.println(
                    """ + text + "" -> " + decode(text)
            );
        }

        System.out.println("\nExpected:");
        System.out.println(""12" -> 2");
        System.out.println(""226" -> 3");
        System.out.println(""06" -> 0");
        System.out.println(""10" -> 1");
        System.out.println(""20" -> 1");
        System.out.println(""2101" -> 1");
        System.out.println(""11106" -> 2");
        System.out.println(""" -> 1");
        System.out.println(""1" -> 1");
        System.out.println(""27" -> 1");
    }
}
