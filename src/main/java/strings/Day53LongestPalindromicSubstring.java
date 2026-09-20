package strings;

/**
 * Day 53: Longest Palindromic Substring.
 *
 * Find the longest contiguous substring of a given string that reads
 * the same from left to right and right to left.
 */
public class Day53LongestPalindromicSubstring {

    /**
     * Brute-force approach.
     *
     * Generate every substring and check whether it is a palindrome.
     *
     * Time: O(n^3)
     * Extra Space: O(1), excluding the returned substring.
     */
    public static String bruteForce(String text) {
        validateInput(text);

        if (text.length() < 2) {
            return text;
        }

        int bestStart = 0;
        int bestLength = 1;

        for (int left = 0; left < text.length(); left++) {
            for (int right = left; right < text.length(); right++) {
                if (isPalindrome(text, left, right)) {
                    int length = right - left + 1;

                    if (length > bestLength) {
                        bestStart = left;
                        bestLength = length;
                    }
                }
            }
        }

        return text.substring(bestStart, bestStart + bestLength);
    }

    /**
     * Expand Around Center.
     *
     * Every palindrome has a center:
     * - one character for odd-length palindromes
     * - a gap between two characters for even-length palindromes
     *
     * For every possible center, expand while the characters match.
     *
     * Time: O(n^2)
     * Extra Space: O(1), excluding the returned substring.
     */
    public static String expandAroundCenter(String text) {
        validateInput(text);

        if (text.length() < 2) {
            return text;
        }

        int bestStart = 0;
        int bestLength = 1;

        for (int center = 0; center < text.length(); center++) {
            int oddLength = expand(text, center, center);
            int evenLength = expand(text, center, center + 1);

            int currentLength = Math.max(oddLength, evenLength);

            if (currentLength > bestLength) {
                bestLength = currentLength;

                // Center-based conversion from palindrome length to start.
                bestStart = center - (currentLength - 1) / 2;
            }
        }

        return text.substring(bestStart, bestStart + bestLength);
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
    public static String dynamicProgramming(String text) {
        validateInput(text);

        if (text.length() < 2) {
            return text;
        }

        int n = text.length();
        boolean[][] dp = new boolean[n][n];

        int bestStart = 0;
        int bestLength = 1;

        // Every single character is a palindrome.
        for (int i = 0; i < n; i++) {
            dp[i][i] = true;
        }

        // Build by increasing substring length.
        for (int length = 2; length <= n; length++) {
            for (int left = 0; left + length <= n; left++) {
                int right = left + length - 1;

                if (text.charAt(left) == text.charAt(right)
                        && (length == 2 || dp[left + 1][right - 1])) {

                    dp[left][right] = true;

                    if (length > bestLength) {
                        bestStart = left;
                        bestLength = length;
                    }
                }
            }
        }

        return text.substring(bestStart, bestStart + bestLength);
    }

    private static int expand(String text, int left, int right) {
        while (left >= 0
                && right < text.length()
                && text.charAt(left) == text.charAt(right)) {
            left--;
            right++;
        }

        return right - left - 1;
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
        System.out.println(expandAroundCenter("babad"));
        // Expected: "bab" or "aba"

        System.out.println(expandAroundCenter("cbbd"));
        // Expected: "bb"

        System.out.println(expandAroundCenter("a"));
        // Expected: "a"

        System.out.println(expandAroundCenter("ac"));
        // Expected: "a" or "c"

        System.out.println(dynamicProgramming("forgeeksskeegfor"));
        // Expected: "geeksskeeg"
    }
}
