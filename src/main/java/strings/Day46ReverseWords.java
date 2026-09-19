package strings;

/**
 * Day 46: Reverse Words in a String.
 *
 * Reverse the order of words, remove extra spaces, and keep each word intact.
 *
 * Example:
 *   "  the sky is blue  " -> "blue is sky the"
 */
public class Day46ReverseWords {

    /**
     * Split-based solution.
     *
     * Time: O(n)
     * Extra Space: O(n).
     */
    public static String reverseWords(String text) {
        validateInput(text);

        String trimmed = text.trim();

        if (trimmed.isEmpty()) {
            return "";
        }

        String[] words = trimmed.split("\\s+");
        StringBuilder result = new StringBuilder();

        for (int i = words.length - 1; i >= 0; i--) {
            if (result.length() > 0) {
                result.append(' ');
            }
            result.append(words[i]);
        }

        return result.toString();
    }

    /**
     * Manual traversal solution.
     *
     * Builds words while scanning from right to left. This makes the
     * whitespace handling explicit and avoids regex-based splitting.
     *
     * Time: O(n)
     * Extra Space: O(n) for the returned result.
     */
    public static String reverseWordsManual(String text) {
        validateInput(text);

        StringBuilder result = new StringBuilder();
        int index = text.length() - 1;

        while (index >= 0) {
            while (index >= 0 && Character.isWhitespace(text.charAt(index))) {
                index--;
            }

            if (index < 0) {
                break;
            }

            int end = index;

            while (index >= 0 && !Character.isWhitespace(text.charAt(index))) {
                index--;
            }

            if (result.length() > 0) {
                result.append(' ');
            }

            result.append(text, index + 1, end + 1);
        }

        return result.toString();
    }

    private static void validateInput(String text) {
        if (text == null) {
            throw new IllegalArgumentException("String must not be null");
        }
    }

    public static void main(String[] args) {
        System.out.println(reverseWords("the sky is blue"));
        // Expected: "blue is sky the"

        System.out.println(reverseWordsManual("  hello   world  "));
        // Expected: "world hello"

        System.out.println(reverseWords("a good   example"));
        // Expected: "example good a"
    }
}
