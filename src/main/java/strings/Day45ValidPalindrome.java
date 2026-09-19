package strings;

/**
 * Day 45: Valid Palindrome.
 *
 * Ignore non-alphanumeric characters and compare letters case-insensitively.
 */
public class Day45ValidPalindrome {

    /**
     * Two-pointer solution.
     *
     * left  -> moves forward
     * right -> moves backward
     *
     * Time: O(n)
     * Extra Space: O(1)
     */
    public static boolean isPalindrome(String text) {
        validateInput(text);

        int left = 0;
        int right = text.length() - 1;

        while (left < right) {
            while (left < right && !Character.isLetterOrDigit(text.charAt(left))) {
                left++;
            }

            while (left < right && !Character.isLetterOrDigit(text.charAt(right))) {
                right--;
            }

            if (Character.toLowerCase(text.charAt(left))
                    != Character.toLowerCase(text.charAt(right))) {
                return false;
            }

            left++;
            right--;
        }

        return true;
    }

    /**
     * Simpler approach using a normalized string.
     *
     * Time: O(n)
     * Extra Space: O(n)
     */
    public static boolean isPalindromeWithNormalization(String text) {
        validateInput(text);

        StringBuilder normalized = new StringBuilder();

        for (char ch : text.toCharArray()) {
            if (Character.isLetterOrDigit(ch)) {
                normalized.append(Character.toLowerCase(ch));
            }
        }

        String value = normalized.toString();

        int left = 0;
        int right = value.length() - 1;

        while (left < right) {
            if (value.charAt(left) != value.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }

        return true;
    }

    private static void validateInput(String text) {
        if (text == null) {
            throw new IllegalArgumentException("String must not be null");
        }
    }

    public static void main(String[] args) {
        System.out.println(isPalindrome("A man, a plan, a canal: Panama"));
        // Expected: true

        System.out.println(isPalindrome("race a car"));
        // Expected: false

        System.out.println(isPalindrome(" "));
        // Expected: true
    }
}
