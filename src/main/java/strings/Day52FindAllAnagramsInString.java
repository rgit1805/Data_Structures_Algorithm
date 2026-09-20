package strings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Day 52: Find All Anagrams in a String.
 *
 * Find every starting index in text where a substring is an anagram
 * of pattern.
 */
public class Day52FindAllAnagramsInString {

    /**
     * Brute-force approach.
     *
     * Build a frequency map for every fixed-size window and compare it
     * with the pattern's frequency map.
     *
     * Time: O((n - m + 1) * m) average
     * Extra Space: O(k)
     */
    public static List<Integer> bruteForce(String text, String pattern) {
        validateInput(text, pattern);

        List<Integer> result = new ArrayList<>();

        if (pattern.isEmpty() || pattern.length() > text.length()) {
            return result;
        }

        Map<Character, Integer> required = frequencyMap(pattern);

        for (int left = 0; left <= text.length() - pattern.length(); left++) {
            Map<Character, Integer> window = new HashMap<>();

            for (int right = left; right < left + pattern.length(); right++) {
                char ch = text.charAt(right);
                window.put(ch, window.getOrDefault(ch, 0) + 1);
            }

            if (window.equals(required)) {
                result.add(left);
            }
        }

        return result;
    }

    /**
     * Optimal fixed-size sliding window.
     *
     * The window always has exactly pattern.length() characters.
     * 'matches' counts how many distinct required characters currently
     * have exactly their required frequency.
     *
     * When matches == requiredKinds, the current window is an anagram.
     *
     * Time: O(n + m) average
     * Extra Space: O(k + r), excluding the output list.
     */
    public static List<Integer> findAnagrams(String text, String pattern) {
        validateInput(text, pattern);

        List<Integer> result = new ArrayList<>();

        if (pattern.isEmpty() || pattern.length() > text.length()) {
            return result;
        }

        Map<Character, Integer> required = frequencyMap(pattern);
        Map<Character, Integer> window = new HashMap<>();

        int requiredKinds = required.size();
        int matches = 0;
        int left = 0;

        for (int right = 0; right < text.length(); right++) {
            char added = text.charAt(right);

            if (required.containsKey(added)) {
                int before = window.getOrDefault(added, 0);
                int after = before + 1;
                window.put(added, after);

                if (before == required.get(added)) {
                    matches--;
                }

                if (after == required.get(added)) {
                    matches++;
                }
            }

            // Keep a fixed-size window.
            if (right - left + 1 > pattern.length()) {
                char removed = text.charAt(left++);

                if (required.containsKey(removed)) {
                    int before = window.get(removed);
                    int after = before - 1;
                    window.put(removed, after);

                    if (before == required.get(removed)) {
                        matches--;
                    }

                    if (after == required.get(removed)) {
                        matches++;
                    }
                }
            }

            if (right - left + 1 == pattern.length() && matches == requiredKinds) {
                result.add(left);
            }
        }

        return result;
    }

    /**
     * Specialized O(1)-space frequency-array solution for lowercase
     * English letters.
     *
     * Time: O(n + m)
     * Extra Space: O(1), excluding the output list.
     */
    public static List<Integer> findAnagramsLowercase(String text, String pattern) {
        validateLowercaseInput(text, pattern);

        List<Integer> result = new ArrayList<>();

        if (pattern.isEmpty() || pattern.length() > text.length()) {
            return result;
        }

        int[] difference = new int[26];

        for (int i = 0; i < pattern.length(); i++) {
            difference[pattern.charAt(i) - 'a']++;
            difference[text.charAt(i) - 'a']--;
        }

        int nonZero = 0;

        for (int value : difference) {
            if (value != 0) {
                nonZero++;
            }
        }

        if (nonZero == 0) {
            result.add(0);
        }

        for (int right = pattern.length(); right < text.length(); right++) {
            int added = text.charAt(right) - 'a';
            int removed = text.charAt(right - pattern.length()) - 'a';

            if (difference[added] == 0) {
                nonZero++;
            }
            difference[added]--;

            if (difference[added] == 0) {
                nonZero--;
            }

            if (difference[removed] == 0) {
                nonZero++;
            }
            difference[removed]++;

            if (difference[removed] == 0) {
                nonZero--;
            }

            if (nonZero == 0) {
                result.add(right - pattern.length() + 1);
            }
        }

        return result;
    }

    private static Map<Character, Integer> frequencyMap(String text) {
        Map<Character, Integer> frequency = new HashMap<>();

        for (char ch : text.toCharArray()) {
            frequency.put(ch, frequency.getOrDefault(ch, 0) + 1);
        }

        return frequency;
    }

    private static void validateInput(String text, String pattern) {
        if (text == null || pattern == null) {
            throw new IllegalArgumentException("Text and pattern must not be null");
        }
    }

    private static void validateLowercaseInput(String text, String pattern) {
        validateInput(text, pattern);

        for (char ch : text.toCharArray()) {
            if (ch < 'a' || ch > 'z') {
                throw new IllegalArgumentException(
                        "Text must contain only lowercase English letters"
                );
            }
        }

        for (char ch : pattern.toCharArray()) {
            if (ch < 'a' || ch > 'z') {
                throw new IllegalArgumentException(
                        "Pattern must contain only lowercase English letters"
                );
            }
        }
    }

    public static void main(String[] args) {
        System.out.println(findAnagrams("cbaebabacd", "abc"));
        // Expected: [0, 6]

        System.out.println(findAnagrams("abab", "ab"));
        // Expected: [0, 1, 2]

        System.out.println(findAnagramsLowercase("baa", "aa"));
        // Expected: [1]

        System.out.println(findAnagrams("abcdef", "gh"));
        // Expected: []
    }
}
