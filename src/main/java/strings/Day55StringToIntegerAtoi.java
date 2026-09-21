package strings;

/**
 * Day 55: String to Integer (atoi).
 *
 * Convert a string into a signed 32-bit integer while following
 * strict parsing rules:
 * 1. Ignore leading whitespace.
 * 2. Read an optional '+' or '-' sign.
 * 3. Read consecutive decimal digits.
 * 4. Stop at the first non-digit after the number.
 * 5. Clamp overflow to Integer.MIN_VALUE / Integer.MAX_VALUE.
 */
public class Day55StringToIntegerAtoi {

    /**
     * Straightforward parser using a long accumulator.
     *
     * Long gives us enough room to detect an int overflow after
     * accumulating the next digit.
     *
     * Time: O(n)
     * Extra Space: O(1)
     */
    public static int parseUsingLong(String text) {
        validateInput(text);

        int index = 0;
        int n = text.length();

        while (index < n && Character.isWhitespace(text.charAt(index))) {
            index++;
        }

        int sign = 1;

        if (index < n && (text.charAt(index) == '+' || text.charAt(index) == '-')) {
            sign = text.charAt(index) == '-' ? -1 : 1;
            index++;
        }

        long value = 0;

        while (index < n && isDigit(text.charAt(index))) {
            int digit = text.charAt(index) - '0';
            value = value * 10 + digit;

            long signedValue = value * sign;

            if (signedValue > Integer.MAX_VALUE) {
                return Integer.MAX_VALUE;
            }

            if (signedValue < Integer.MIN_VALUE) {
                return Integer.MIN_VALUE;
            }

            index++;
        }

        return (int) (value * sign);
    }

    /**
     * Production-style parser that detects overflow BEFORE performing
     * the multiplication/addition that could overflow an int.
     *
     * For positive values:
     *     value <= (MAX_VALUE - digit) / 10
     *
     * If this condition fails, the next operation would overflow.
     *
     * For negative values we use a long limit during the check so that
     * Integer.MIN_VALUE (-2147483648) is handled correctly.
     *
     * Time: O(n)
     * Extra Space: O(1)
     */
    public static int parse(String text) {
        validateInput(text);

        int index = 0;
        int n = text.length();

        while (index < n && Character.isWhitespace(text.charAt(index))) {
            index++;
        }

        int sign = 1;

        if (index < n && (text.charAt(index) == '+' || text.charAt(index) == '-')) {
            sign = text.charAt(index) == '-' ? -1 : 1;
            index++;
        }

        int value = 0;

        while (index < n && isDigit(text.charAt(index))) {
            int digit = text.charAt(index) - '0';

            if (willOverflow(value, digit, sign)) {
                return sign == 1 ? Integer.MAX_VALUE : Integer.MIN_VALUE;
            }

            value = value * 10 + digit;
            index++;
        }

        return sign * value;
    }

    private static boolean willOverflow(int value, int digit, int sign) {
        long signedValue = (long) value * sign;
        long nextValue = signedValue * 10 + (long) sign * digit;

        return nextValue > Integer.MAX_VALUE || nextValue < Integer.MIN_VALUE;
    }

    private static boolean isDigit(char ch) {
        return ch >= '0' && ch <= '9';
    }

    private static void validateInput(String text) {
        if (text == null) {
            throw new IllegalArgumentException("Input string must not be null");
        }
    }

    public static void main(String[] args) {
        System.out.println(parse("42"));
        // Expected: 42

        System.out.println(parse("   -42"));
        // Expected: -42

        System.out.println(parse("4193 with words"));
        // Expected: 4193

        System.out.println(parse("words and 987"));
        // Expected: 0

        System.out.println(parse("-91283472332"));
        // Expected: -2147483648

        System.out.println(parse("91283472332"));
        // Expected: 2147483647

        System.out.println(parse("+1"));
        // Expected: 1

        System.out.println(parse("+-12"));
        // Expected: 0
    }
}
