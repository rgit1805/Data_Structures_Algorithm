package strings;

import java.util.HashMap;
import java.util.Map;

/**
 * Day 41: Strings - Character Frequency.
 *
 * Pattern: character -> frequency.
 */
public class Day41CharacterFrequency {

    /**
     * Frequency counting for lowercase English letters.
     * Time: O(n), Extra Space: O(1).
     */
    public static int[] frequencyLowercase(String text) {
        validateInput(text);

        int[] frequency = new int[26];

        for (char ch : text.toCharArray()) {
            if (ch < 'a' || ch > 'z') {
                throw new IllegalArgumentException(
                        "Input must contain only lowercase English letters");
            }
            frequency[ch - 'a']++;
        }

        return frequency;
    }

    /**
     * General character frequency using a HashMap.
     * Time: O(n) average, Extra Space: O(k).
     */
    public static Map<Character, Integer> frequency(String text) {
        validateInput(text);

        Map<Character, Integer> frequency = new HashMap<>();

        for (char ch : text.toCharArray()) {
            frequency.put(ch, frequency.getOrDefault(ch, 0) + 1);
        }

        return frequency;
    }

    /**
     * Returns the first character whose frequency is exactly one.
     * Time: O(n) average, Extra Space: O(k).
     */
    public static char firstNonRepeatingCharacter(String text) {
        validateInput(text);

        Map<Character, Integer> frequency = frequency(text);

        for (char ch : text.toCharArray()) {
            if (frequency.get(ch) == 1) {
                return ch;
            }
        }

        return '\0';
    }

    private static void validateInput(String text) {
        if (text == null) {
            throw new IllegalArgumentException("String must not be null");
        }
    }

    public static void main(String[] args) {
        String text = "programming";

        System.out.println("Frequency map: " + frequency(text));
        System.out.println("First non-repeating character: "
                + firstNonRepeatingCharacter(text));

        int[] lowercaseFrequency = frequencyLowercase("banana");
        System.out.println("Count of 'a' in banana: "
                + lowercaseFrequency['a' - 'a']);
        // Expected: 3
    }
}
