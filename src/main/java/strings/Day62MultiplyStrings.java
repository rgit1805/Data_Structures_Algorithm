package strings;

public class Day62MultiplyStrings {

    /*
     * Problem:
     * Multiply two non-negative integers represented as strings
     * without converting them directly to primitive numeric types.
     *
     * Example:
     * "123" * "456" = "56088"
     *
     * Core idea:
     * Simulate the same multiplication we do by hand.
     *
     * For digits at positions i and j:
     *     product = digitA * digitB
     *
     * The result is accumulated in:
     *     result[i + j + 1]
     *     result[i + j]
     */

    public static String multiply(String num1, String num2) {
        validate(num1, num2);

        if (num1.equals("0") || num2.equals("0")) {
            return "0";
        }

        int[] result = new int[num1.length() + num2.length()];

        for (int i = num1.length() - 1; i >= 0; i--) {
            int digit1 = num1.charAt(i) - '0';

            for (int j = num2.length() - 1; j >= 0; j--) {
                int digit2 = num2.charAt(j) - '0';

                int positionLow = i + j + 1;
                int positionHigh = i + j;

                int product = digit1 * digit2 + result[positionLow];

                result[positionLow] = product % 10;
                result[positionHigh] += product / 10;
            }
        }

        StringBuilder answer = new StringBuilder();

        int index = 0;

        while (index < result.length && result[index] == 0) {
            index++;
        }

        while (index < result.length) {
            answer.append(result[index]);
            index++;
        }

        return answer.length() == 0 ? "0" : answer.toString();
    }

    /*
     * Adds two non-negative integer strings.
     * This reinforces digit-by-digit arithmetic without overflow.
     */
    public static String addStrings(String num1, String num2) {
        validate(num1, num2);

        int i = num1.length() - 1;
        int j = num2.length() - 1;
        int carry = 0;

        StringBuilder result = new StringBuilder();

        while (i >= 0 || j >= 0 || carry > 0) {
            int digit1 = i >= 0 ? num1.charAt(i) - '0' : 0;
            int digit2 = j >= 0 ? num2.charAt(j) - '0' : 0;

            int sum = digit1 + digit2 + carry;

            result.append(sum % 10);
            carry = sum / 10;

            i--;
            j--;
        }

        return result.reverse().toString();
    }

    private static void validate(String num1, String num2) {
        if (num1 == null || num2 == null
                || num1.isEmpty() || num2.isEmpty()) {
            throw new IllegalArgumentException(
                    "Numbers must be non-empty strings."
            );
        }

        validateNumber(num1);
        validateNumber(num2);
    }

    private static void validateNumber(String number) {
        for (int i = 0; i < number.length(); i++) {
            if (!Character.isDigit(number.charAt(i))) {
                throw new IllegalArgumentException(
                        "Numbers must contain only digits."
                );
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("123 * 456 = "
                + multiply("123", "456")); // 56088

        System.out.println("2 * 3 = "
                + multiply("2", "3")); // 6

        System.out.println("999 * 999 = "
                + multiply("999", "999")); // 998001

        System.out.println("0 * 123456 = "
                + multiply("0", "123456")); // 0

        System.out.println("123456789 * 987654321 = "
                + multiply("123456789", "987654321")); // 121932631112635269

        System.out.println("Add 999 + 1 = "
                + addStrings("999", "1")); // 1000

        System.out.println("Add 123456789 + 987654321 = "
                + addStrings("123456789", "987654321")); // 1111111110
    }
}
