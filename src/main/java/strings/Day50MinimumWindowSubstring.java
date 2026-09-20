package strings;

import java.util.HashMap;
import java.util.Map;

/**
 * Day 50: Minimum Window Substring.
 *
 * Find the smallest substring of source that contains every character
 * from target with the required frequency.
 */
public class Day50MinimumWindowSubstring {

    /**
     * Brute-force approach.
     *
     * For every possible start index, expand the window and check whether
     * it contains all required target characters.
     *
     * Time: O(n^2 * k) in the general HashMap implementation.
     * Extra Space: O(k), where k is the number of distinct target characters.
     */
    public static String bruteForce(String source, String target) {
        validateInput(source, target);

        if (target.isEmpty() || source.isEmpty() || target.length() > source.length()) {
            return "";
        }

        Map<Character, Integer> required = buildFrequencyMap(target);
        String best = "";

        for (int left = 0; left < source.length(); left++) {
            Map<Character, Integer> window = new HashMap<>();

            for (int right = left; right < source.length(); right++) {
                char ch = source.charAt(right);
                window.put(ch, window.getOrDefault(ch, 0) + 1);

                if (containsRequired(window, required)) {
                    String candidate = source.substring(left, right + 1);

                    if (best.isEmpty() || candidate.length() < best.length()) {
                        best = candidate;
                    }

                    break;
                }
            }
        }

        return best;
    }

    /**
     * Optimal sliding-window solution.
     *
     * 'required' stores how many copies of each character are needed.
     * 'window' stores how many copies are currently inside the window.
     *
     * formed = number of distinct required characters whose required
     * frequency has been completely satisfied.
     *
     * A window is valid when formed == required.size().
     *
     * Time: O(n + m) average
     * Extra Space: O(k)
     */
    public static String minWindow(String source, String target) {
        validateInput(source, target);

        if (target.isEmpty() || source.isEmpty() || target.length() > source.length()) {
            return "";
        }

        Map<Character, Integer> required = buildFrequencyMap(target);
        Map<Character, Integer> window = new HashMap<>();

        int requiredKinds = required.size();
        int formed = 0;

        int left = 0;
        int bestStart = 0;
        int bestLength = Integer.MAX_VALUE;

        for (int right = 0; right < source.length(); right++) {
            char rightChar = source.charAt(right);

            if (required.containsKey(rightChar)) {
                int newCount = window.getOrDefault(rightChar, 0) + 1;
                window.put(rightChar, newCount);

                if (newCount == required.get(rightChar)) {
                    formed++;
                }
            }

            // Once the window is valid, shrink it as much as possible.
            while (formed == requiredKinds) {
                int windowLength = right - left + 1;

                if (windowLength < bestLength) {
                    bestLength = windowLength;
                    bestStart = left;
                }

                char leftChar = source.charAt(left);

                if (required.containsKey(leftChar)) {
                    int currentCount = window.get(leftChar);

                    if (currentCount == required.get(leftChar)) {
                        formed--;
                    }

                    window.put(leftChar, currentCount - 1);
                }

                left++;
            }
        }

        return bestLength == Integer.MAX_VALUE
                ? ""
                : source.substring(bestStart, bestStart + bestLength);
    }

    private static Map<Character, Integer> buildFrequencyMap(String text) {
        Map<Character, Integer> frequency = new HashMap<>();

        for (char ch : text.toCharArray()) {
            frequency.put(ch, frequency.getOrDefault(ch, 0) + 1);
        }

        return frequency;
    }

    private static boolean containsRequired(
            Map<Character, Integer> window,
            Map<Character, Integer> required) {

        for (Map.Entry<Character, Integer> entry : required.entrySet()) {
            if (window.getOrDefault(entry.getKey(), 0) < entry.getValue()) {
                return false;
            }
        }

        return true;
    }

    private static void validateInput(String source, String target) {
        if (source == null || target == null) {
            throw new IllegalArgumentException("Source and target must not be null");
        }
    }

    public static void main(String[] args) {
        System.out.println(minWindow("ADOBECODEBANC", "ABC"));
        // Expected: BANC

        System.out.println(minWindow("a", "a"));
        // Expected: a

        System.out.println(minWindow("a", "aa"));
        // Expected: ""

        System.out.println(minWindow("aa", "aa"));
        // Expected: aa

        System.out.println(minWindow("aaflslflsldkalskaaa", "aaa"));
        // Expected: aaa
    }
}
