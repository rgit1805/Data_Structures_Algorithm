package strings;

/**
 * Day 44: Longest Common Prefix.
 *
 * Find the longest prefix shared by every string in an array.
 */
public class Day44LongestCommonPrefix {

    /**
     * Horizontal scanning:
     * Start with the first string as the prefix and shrink it until
     * every string starts with it.
     *
     * Time: O(S), where S is the total number of characters examined.
     * Extra Space: O(1).
     */
    public static String longestCommonPrefix(String[] words) {
        validateInput(words);

        if (words.length == 0) {
            return "";
        }

        if (words[0] == null) {
            throw new IllegalArgumentException("Strings must not be null");
        }

        String prefix = words[0];

        for (int i = 1; i < words.length; i++) {
            if (words[i] == null) {
                throw new IllegalArgumentException("Strings must not be null");
            }

            while (!words[i].startsWith(prefix)) {
                prefix = prefix.substring(0, prefix.length() - 1);

                if (prefix.isEmpty()) {
                    return "";
                }
            }
        }

        return prefix;
    }

    /**
     * Character-by-character approach.
     *
     * Compare the same index across all strings until a mismatch occurs.
     *
     * Time: O(S), where S is the total number of characters examined.
     * Extra Space: O(1).
     */
    public static String longestCommonPrefixByCharacters(String[] words) {
        validateInput(words);

        if (words.length == 0) {
            return "";
        }

        for (String word : words) {
            if (word == null) {
                throw new IllegalArgumentException("Strings must not be null");
            }
        }

        for (int index = 0; index < words[0].length(); index++) {
            char expected = words[0].charAt(index);

            for (int row = 1; row < words.length; row++) {
                if (index >= words[row].length()
                        || words[row].charAt(index) != expected) {
                    return words[0].substring(0, index);
                }
            }
        }

        return words[0];
    }

    private static void validateInput(String[] words) {
        if (words == null) {
            throw new IllegalArgumentException("Input array must not be null");
        }
    }

    public static void main(String[] args) {
        String[] words1 = {"flower", "flow", "flight"};
        System.out.println(longestCommonPrefix(words1));
        // Expected: "fl"

        String[] words2 = {"dog", "racecar", "car"};
        System.out.println(longestCommonPrefixByCharacters(words2));
        // Expected: ""

        String[] words3 = {"interspecies", "interstellar", "interstate"};
        System.out.println(longestCommonPrefix(words3));
        // Expected: "inters"
    }
}
