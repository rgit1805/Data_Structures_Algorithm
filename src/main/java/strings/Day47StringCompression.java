package strings;

/**
 * Day 47: String Compression - Run-Length Encoding.
 *
 * Consecutive equal characters are represented as:
 * character + count
 *
 * Example:
 * "aaabbc" -> "a3b2c1"
 */
public class Day47StringCompression {

    /**
     * Compresses consecutive groups using run-length encoding.
     *
     * Time: O(n)
     * Extra Space: O(n) for the result.
     */
    public static String compress(String text) {
        validateInput(text);

        if (text.isEmpty()) {
            return "";
        }

        StringBuilder result = new StringBuilder();
        int index = 0;

        while (index < text.length()) {
            char current = text.charAt(index);
            int count = 0;

            while (index < text.length() && text.charAt(index) == current) {
                count++;
                index++;
            }

            result.append(current).append(count);
        }

        return result.toString();
    }

    /**
     * Compresses only when the encoded representation is shorter.
     *
     * Example:
     * "abc" -> "abc"
     * "aaabbc" -> "a3b2c1"
     *
     * Time: O(n)
     * Extra Space: O(n) for the result.
     */
    public static String compressIfShorter(String text) {
        validateInput(text);

        String compressed = compress(text);
        return compressed.length() < text.length() ? compressed : text;
    }

    /**
     * Returns the number of consecutive characters starting at start.
     *
     * This helper makes the group-counting pattern explicit.
     */
    public static int runLength(String text, int start) {
        validateInput(text);

        if (start < 0 || start >= text.length()) {
            throw new IllegalArgumentException("Start index out of range");
        }

        char current = text.charAt(start);
        int end = start;

        while (end < text.length() && text.charAt(end) == current) {
            end++;
        }

        return end - start;
    }

    private static void validateInput(String text) {
        if (text == null) {
            throw new IllegalArgumentException("String must not be null");
        }
    }

    public static void main(String[] args) {
        System.out.println(compress("aaabbc"));
        // Expected: "a3b2c1"

        System.out.println(compress("aabbcccc"));
        // Expected: "a2b2c4"

        System.out.println(compressIfShorter("abc"));
        // Expected: "abc"

        System.out.println(runLength("aaabbc", 0));
        // Expected: 3
    }
}
