package strings;

import java.util.HashMap;
import java.util.Map;

/**
 * Day 49: Longest Repeating Character Replacement.
 *
 * Given an uppercase string and at most k replacements, find the
 * length of the longest substring that can be converted into a
 * string containing only one repeated character.
 */
public class Day49LongestRepeatingCharacterReplacement {

    /**
     * Brute-force approach.
     *
     * For every starting position, expand the substring while tracking
     * the most frequent character. A window is valid when:
     *
     * windowLength - maxFrequency <= k
     *
     * Time: O(n^2)
     * Extra Space: O(1) because the alphabet is fixed at 26 characters.
     */
    public static int bruteForce(String text, int k) {
        validateInput(text, k);

        int best = 0;

        for (int left = 0; left < text.length(); left++) {
            int[] frequency = new int[26];
            int maxFrequency = 0;

            for (int right = left; right < text.length(); right++) {
                int index = text.charAt(right) - 'A';
                frequency[index]++;
                maxFrequency = Math.max(maxFrequency, frequency[index]);

                int windowLength = right - left + 1;
                int replacementsNeeded = windowLength - maxFrequency;

                if (replacementsNeeded <= k) {
                    best = Math.max(best, windowLength);
                }
            }
        }

        return best;
    }

    /**
     * Optimal sliding-window solution.
     *
     * Window invariant:
     *   windowLength - maxFrequency <= k
     *
     * maxFrequency represents the count of the most frequent character
     * inside the current window. All other characters can be replaced
     * with that character.
     *
     * Time: O(n)
     * Extra Space: O(1) because there are only 26 uppercase letters.
     */
    public static int longestReplacement(String text, int k) {
        validateInput(text, k);

        int[] frequency = new int[26];
        int left = 0;
        int maxFrequency = 0;
        int best = 0;

        for (int right = 0; right < text.length(); right++) {
            int index = text.charAt(right) - 'A';
            frequency[index]++;
            maxFrequency = Math.max(maxFrequency, frequency[index]);

            int windowLength = right - left + 1;

            while (windowLength - maxFrequency > k) {
                frequency[text.charAt(left) - 'A']--;
                left++;
                windowLength = right - left + 1;
            }

            best = Math.max(best, windowLength);
        }

        return best;
    }

    private static void validateInput(String text, int k) {
        if (text == null) {
            throw new IllegalArgumentException("String must not be null");
        }

        if (k < 0) {
            throw new IllegalArgumentException("k must not be negative");
        }

        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            if (ch < 'A' || ch > 'Z') {
                throw new IllegalArgumentException(
                        "Input must contain only uppercase English letters"
                );
            }
        }
    }

    public static void main(String[] args) {
        System.out.println(longestReplacement("ABAB", 2));
        // Expected: 4

        System.out.println(longestReplacement("AABABBA", 1));
        // Expected: 4

        System.out.println(longestReplacement("AAAA", 0));
        // Expected: 4

        System.out.println(longestReplacement("ABCDE", 1));
        // Expected: 2
    }
}
