package strings;

import java.util.Arrays;

public class Day59ShortestPalindrome {

    /*
     * Problem:
     * Add the minimum number of characters to the beginning of a string
     * so that the entire string becomes a palindrome.
     *
     * Example:
     * "aacecaaa" -> "aaacecaaa"
     * "abcd"     -> "dcbabcd"
     */

    public static String bruteForce(String text) {
        validate(text);

        if (text.length() <= 1) {
            return text;
        }

        int longestPrefixLength = 1;

        for (int end = text.length() - 1; end >= 0; end--) {
            if (isPalindrome(text, 0, end)) {
                longestPrefixLength = end + 1;
                break;
            }
        }

        String suffix = text.substring(longestPrefixLength);
        return new StringBuilder(suffix).reverse().append(text).toString();
    }

    /*
     * KMP-based solution:
     *
     * Build:
     *     text + "#" + reverse(text)
     *
     * The last LPS value gives the length of the longest palindromic
     * prefix of the original string.
     */
    public static String shortestPalindrome(String text) {
        validate(text);

        if (text.length() <= 1) {
            return text;
        }

        String reversed = new StringBuilder(text).reverse().toString();
        String combined = text + "#" + reversed;

        int[] lps = buildLps(combined);
        int longestPalindromicPrefix = lps[combined.length() - 1];

        String suffix = text.substring(longestPalindromicPrefix);

        return new StringBuilder(suffix)
                .reverse()
                .append(text)
                .toString();
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

    private static int[] buildLps(String text) {
        int[] lps = new int[text.length()];

        int length = 0;
        int i = 1;

        while (i < text.length()) {
            if (text.charAt(i) == text.charAt(length)) {
                lps[i] = ++length;
                i++;
            } else if (length > 0) {
                length = lps[length - 1];
            } else {
                lps[i] = 0;
                i++;
            }
        }

        return lps;
    }

    private static void validate(String text) {
        if (text == null) {
            throw new IllegalArgumentException("Text must not be null.");
        }
    }

    public static void main(String[] args) {
        System.out.println("Brute force: "
                + bruteForce("aacecaaa")); // aaacecaaa

        System.out.println("KMP: "
                + shortestPalindrome("aacecaaa")); // aaacecaaa

        System.out.println("KMP: "
                + shortestPalindrome("abcd")); // dcbabcd

        System.out.println("KMP: "
                + shortestPalindrome("race")); // ecarace

        System.out.println("KMP: "
                + shortestPalindrome("aba")); // aba

        System.out.println("KMP: "
                + shortestPalindrome("")); // ""

        String example = "aacecaaa";
        String reversed = new StringBuilder(example).reverse().toString();
        String combined = example + "#" + reversed;

        System.out.println("Combined string: " + combined);
        System.out.println("LPS: " + Arrays.toString(buildLps(combined)));
    }
}
