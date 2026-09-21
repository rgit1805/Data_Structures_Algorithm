package strings;

/**
 * Day 54: Palindromic Substrings.
 *
 * Count every contiguous substring that is a palindrome.
 * Different positions count as different palindromic substrings,
 * even when their text is identical.
 */
public class Day54PalindromicSubstrings {

    /**
     * Brute-force approach.
     *
     * Generate every possible substring and check whether it is a palindrome.
     *
     * Time: O(n^3)
     * Extra Space: O(1)
     */
    public static int bruteForce(String text) {
        validateInput(text);

        int count = 0;

        for (int left = 0; left < text.length(); left++) {
            for (int right = left; right < text.length(); right++) {
                if (isPalindrome(text, left, right)) {
                    count++;
                }
            }
        }

        return count;
    }

    /**
     * Expand Around Center.
     *
     * Every palindrome has either:
     * 1. One character as its center (odd length), or
     * 2. A gap between two characters as its center (even length).
     *
     * Instead of returning the longest palindrome, every successful
     * expansion contributes one palindrome to the answer.
     *
     * Time: O(n^2)
     * Extra Space: O(1)
     */
    public static int expandAroundCenter(String text) {
        validateInput(text);

        int count = 0;

        for (int center = 0; center < text.length(); center++) {
            count += countPalindromesFromCenter(text, center, center);
            count += countPalindromesFromCenter(text, center, center + 1);
        }

        return count;
    }

    /**
     * Dynamic Programming approach.
     *
     * dp[left][right] is true when text[left..right] is a palindrome.
     *
     * Recurrence:
     * dp[left][right] =
     *     text[left] == text[right]
     *     AND (length <= 2 OR dp[left + 1][right - 1])
     *
     * Time: O(n^2)
     * Extra Space: O(n^2)
     */
    public static int dynamicProgramming(String text) {
        validateInput(text);

        int n = text.length();

        if (n == 0) {
            return 0;
        }

        boolean[][] dp = new boolean[n][n];
        int count = 0;

        for (int length = 1; length <= n; length++) {
            for (int left = 0; left + length <= n; left++) {
                int right = left + length - 1;

                if (text.charAt(left) == text.charAt(right)
                        && (length <= 2 || dp[left + 1][right - 1])) {

                    dp[left][right] = true;
                    count++;
                }
            }
        }

        return count;
    }

    private static int countPalindromesFromCenter(
            String text,
            int left,
            int right) {

        int count = 0;

        while (left >= 0
                && right < text.length()
                && text.charAt(left) == text.charAt(right)) {

            count++;
            left--;
            right++;
        }

        return count;
    }

    private static boolean isPalindrome(String text, int left, int right) {
        while (left < right) {
            if (text.charAt(left) != text.charAt(right)) {
                return false;
            }

            left++;
            right--;
        }

        return true;
    }

    private static void validateInput(String text) {
        if (text == null) {
            throw new IllegalArgumentException("String must not be null");
        }
    }

    public static void main(String[] args) {
        System.out.println(expandAroundCenter("abc"));
        // Expected: 3 -> a, b, c

        System.out.println(expandAroundCenter("aaa"));
        // Expected: 6 -> a, a, a, aa, aa, aaa

        System.out.println(expandAroundCenter("abba"));
        // Expected: 6 -> a, b, b, a, bb, abba

        System.out.println(dynamicProgramming("racecar"));
        // Expected: 10
    }
}
