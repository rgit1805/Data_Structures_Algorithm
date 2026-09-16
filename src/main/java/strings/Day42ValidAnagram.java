package strings;

import java.util.HashMap;
import java.util.Map;

/**
 * Day 42: Valid Anagram.
 *
 * Two strings are anagrams when they contain the same characters
 * with exactly the same frequencies.
 */
public class Day42ValidAnagram {

    /**
     * Frequency-array solution for lowercase English letters.
     *
     * Time: O(n)
     * Extra Space: O(1)
     */
    public static boolean isAnagramLowercase(String first, String second) {
        validateStrings(first, second);

        if (first.length() != second.length()) {
            return false;
        }

        int[] frequency = new int[26];

        for (int i = 0; i < first.length(); i++) {
            char firstChar = first.charAt(i);
            char secondChar = second.charAt(i);

            validateLowercase(firstChar);
            validateLowercase(secondChar);

            frequency[firstChar - 'a']++;
            frequency[secondChar - 'a']--;
        }

        for (int count : frequency) {
            if (count != 0) {
                return false;
            }
        }

        return true;
    }

    /**
     * General character solution using HashMap.
     *
     * Time: O(n) average
     * Extra Space: O(k), where k is the number of distinct characters.
     */
    public static boolean isAnagram(String first, String second) {
        validateStrings(first, second);

        if (first.length() != second.length()) {
            return false;
        }

        Map<Character, Integer> frequency = new HashMap<>();

        for (char ch : first.toCharArray()) {
            frequency.put(ch, frequency.getOrDefault(ch, 0) + 1);
        }

        for (char ch : second.toCharArray()) {
            Integer count = frequency.get(ch);

            if (count == null) {
                return false;
            }

            if (count == 1) {
                frequency.remove(ch);
            } else {
                frequency.put(ch, count - 1);
            }
        }

        return frequency.isEmpty();
    }

    private static void validateStrings(String first, String second) {
        if (first == null || second == null) {
            throw new IllegalArgumentException("Strings must not be null");
        }
    }

    private static void validateLowercase(char ch) {
        if (ch < 'a' || ch > 'z') {
            throw new IllegalArgumentException(
                    "Input must contain only lowercase English letters");
        }
    }

    public static void main(String[] args) {
        System.out.println(isAnagramLowercase("listen", "silent"));
        // Expected: true

        System.out.println(isAnagramLowercase("rat", "car"));
        // Expected: false

        System.out.println(isAnagram("conversation", "voicesranton"));
        // Expected: true
    }
}
