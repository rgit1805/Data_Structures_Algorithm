package strings;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Day 48: Longest Substring Without Repeating Characters.
 *
 * Find the length of the longest contiguous substring containing
 * no repeated characters.
 */
public class Day48LongestSubstringWithoutRepeating {

    /**
     * Brute-force solution.
     *
     * Start at every index and use a set to detect duplicates.
     *
     * Time: O(n^2) average
     * Extra Space: O(k)
     */
    public static int bruteForce(String text) {
        validateInput(text);

        int best = 0;

        for (int start = 0; start < text.length(); start++) {
            Set<Character> seen = new HashSet<>();

            for (int end = start; end < text.length(); end++) {
                char ch = text.charAt(end);

                if (!seen.add(ch)) {
                    break;
                }

                best = Math.max(best, end - start + 1);
            }
        }

        return best;
    }

    /**
     * Sliding window with a HashSet.
     *
     * The window [left, right] always contains unique characters.
     *
     * Time: O(n) average
     * Extra Space: O(k)
     */
    public static int slidingWindowSet(String text) {
        validateInput(text);

        Set<Character> window = new HashSet<>();
        int left = 0;
        int best = 0;

        for (int right = 0; right < text.length(); right++) {
            char ch = text.charAt(right);

            while (window.contains(ch)) {
                window.remove(text.charAt(left));
                left++;
            }

            window.add(ch);
            best = Math.max(best, right - left + 1);
        }

        return best;
    }

    /**
     * Optimized sliding window with the last seen index of each character.
     *
     * Instead of removing characters one by one, jump left directly
     * past the previous occurrence.
     *
     * Time: O(n) average
     * Extra Space: O(k)
     */
    public static int slidingWindowMap(String text) {
        validateInput(text);

        Map<Character, Integer> lastSeen = new HashMap<>();
        int left = 0;
        int best = 0;

        for (int right = 0; right < text.length(); right++) {
            char ch = text.charAt(right);

            if (lastSeen.containsKey(ch)) {
                left = Math.max(left, lastSeen.get(ch) + 1);
            }

            lastSeen.put(ch, right);
            best = Math.max(best, right - left + 1);
        }

        return best;
    }

    private static void validateInput(String text) {
        if (text == null) {
            throw new IllegalArgumentException("String must not be null");
        }
    }

    public static void main(String[] args) {
        System.out.println(bruteForce("abcabcbb"));
        // Expected: 3

        System.out.println(slidingWindowSet("bbbbb"));
        // Expected: 1

        System.out.println(slidingWindowMap("pwwkew"));
        // Expected: 3

        System.out.println(slidingWindowMap(""));
        // Expected: 0
    }
}
