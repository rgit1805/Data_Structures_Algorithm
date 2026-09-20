package strings;

import java.util.HashMap;
import java.util.Map;

/**
 * Day 51: Permutation in String.
 *
 * Determine whether s2 contains a substring that is a permutation
 * (an anagram) of s1.
 */
public class Day51PermutationInString {

    /**
     * Brute-force approach.
     *
     * For every window of s2 having the same length as s1, build a
     * frequency map and compare it with the required frequencies.
     *
     * Time: O((n - m + 1) * m) average
     * Extra Space: O(k)
     */
    public static boolean bruteForce(String s1, String s2) {
        validateInput(s1, s2);

        if (s1.length() > s2.length()) {
            return false;
        }

        Map<Character, Integer> required = frequencyMap(s1);

        for (int left = 0; left <= s2.length() - s1.length(); left++) {
            Map<Character, Integer> window = new HashMap<>();

            for (int right = left; right < left + s1.length(); right++) {
                char ch = s2.charAt(right);
                window.put(ch, window.getOrDefault(ch, 0) + 1);
            }

            if (window.equals(required)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Optimal fixed-size sliding window.
     *
     * The window always has exactly s1.length() characters.
     * We maintain the frequency difference between the required
     * characters and the current window.
     *
     * matches counts how many distinct characters currently have
     * exactly the required frequency.
     *
     * Time: O(n + m) average
     * Extra Space: O(k)
     */
    public static boolean containsPermutation(String s1, String s2) {
        validateInput(s1, s2);

        if (s1.length() > s2.length()) {
            return false;
        }

        Map<Character, Integer> required = frequencyMap(s1);
        Map<Character, Integer> window = new HashMap<>();

        int requiredKinds = required.size();
        int matches = 0;
        int left = 0;

        for (int right = 0; right < s2.length(); right++) {
            char added = s2.charAt(right);

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

            // Keep the window size equal to s1.length().
            if (right - left + 1 > s1.length()) {
                char removed = s2.charAt(left++);

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

            if (right - left + 1 == s1.length() && matches == requiredKinds) {
                return true;
            }
        }

        return false;
    }

    /**
     * Specialized O(1)-space version for lowercase English letters.
     *
     * This version is useful in interviews when the character set is
     * explicitly limited to 'a' through 'z'.
     *
     * Time: O(n + m)
     * Extra Space: O(1)
     */
    public static boolean containsPermutationLowercase(String s1, String s2) {
        validateLowercaseInput(s1, s2);

        if (s1.length() > s2.length()) {
            return false;
        }

        int[] difference = new int[26];

        for (int i = 0; i < s1.length(); i++) {
            difference[s1.charAt(i) - 'a']++;
            difference[s2.charAt(i) - 'a']--;
        }

        int nonZero = 0;

        for (int value : difference) {
            if (value != 0) {
                nonZero++;
            }
        }

        if (nonZero == 0) {
            return true;
        }

        for (int right = s1.length(); right < s2.length(); right++) {
            int added = s2.charAt(right) - 'a';
            int removed = s2.charAt(right - s1.length()) - 'a';

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
                return true;
            }
        }

        return false;
    }

    private static Map<Character, Integer> frequencyMap(String text) {
        Map<Character, Integer> frequency = new HashMap<>();

        for (char ch : text.toCharArray()) {
            frequency.put(ch, frequency.getOrDefault(ch, 0) + 1);
        }

        return frequency;
    }

    private static void validateInput(String s1, String s2) {
        if (s1 == null || s2 == null) {
            throw new IllegalArgumentException("Strings must not be null");
        }
    }

    private static void validateLowercaseInput(String s1, String s2) {
        validateInput(s1, s2);

        for (char ch : s1.toCharArray()) {
            if (ch < 'a' || ch > 'z') {
                throw new IllegalArgumentException("s1 must contain only lowercase English letters");
            }
        }

        for (char ch : s2.toCharArray()) {
            if (ch < 'a' || ch > 'z') {
                throw new IllegalArgumentException("s2 must contain only lowercase English letters");
            }
        }
    }

    public static void main(String[] args) {
        System.out.println(containsPermutation("ab", "eidbaooo"));
        // Expected: true ("ba" is a permutation of "ab")

        System.out.println(containsPermutation("ab", "eidboaoo"));
        // Expected: false

        System.out.println(containsPermutation("adc", "dcda"));
        // Expected: true ("dcd" is not valid; "cda" is a permutation)

        System.out.println(containsPermutationLowercase("abc", "bbbca"));
        // Expected: true ("bca" is a permutation of "abc")
    }
}
