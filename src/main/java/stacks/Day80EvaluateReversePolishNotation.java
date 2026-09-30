package stacks;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Day 80: Evaluate Reverse Polish Notation (RPN)
 *
 * Evaluate an arithmetic expression written in postfix notation.
 *
 * In Reverse Polish Notation, operators come AFTER their operands.
 *
 * Example:
 *   ["2", "1", "+", "3", "*"]
 *
 * Means:
 *   (2 + 1) * 3
 *   = 9
 *
 * Core pattern:
 * Use a stack of operands.
 *
 * Operand  -> push onto stack
 * Operator -> pop right operand, pop left operand,
 *             calculate left operator right,
 *             push result back
 *
 * Time: O(n)
 * Space: O(n)
 */
public class Day80EvaluateReversePolishNotation {

    /**
     * Evaluates an RPN expression using a stack.
     *
     * Important:
     * For subtraction and division, operand order matters.
     *
     * If expression is:
     *   ["5", "2", "-"]
     *
     * We pop:
     *   right = 2
     *   left  = 5
     *
     * Then calculate:
     *   left - right = 5 - 2 = 3
     *
     * not:
     *   right - left
     */
    public static int evaluate(String[] tokens) {
        validateTokens(tokens);

        Deque<Integer> stack = new ArrayDeque<>();

        for (String token : tokens) {
            if (isNumber(token)) {
                stack.push(parseInteger(token));
                continue;
            }

            if (!isOperator(token)) {
                throw new IllegalArgumentException(
                        "Unsupported token: " + token
                );
            }

            if (stack.size() < 2) {
                throw new IllegalArgumentException(
                        "Invalid RPN expression: insufficient operands for "
                                + token
                );
            }

            int right = stack.pop();
            int left = stack.pop();

            int result = applyOperator(left, right, token);
            stack.push(result);
        }

        if (stack.size() != 1) {
            throw new IllegalArgumentException(
                    "Invalid RPN expression: leftover operands."
            );
        }

        return stack.pop();
    }

    /**
     * Evaluates the expression using long internally.
     *
     * This version is useful when the caller wants to reduce the risk of
     * intermediate int overflow. The final result is still returned as int,
     * so the final value must fit inside Java's int range.
     */
    public static int evaluateSafely(String[] tokens) {
        validateTokens(tokens);

        Deque<Long> stack = new ArrayDeque<>();

        for (String token : tokens) {
            if (isNumber(token)) {
                stack.push(Long.parseLong(token));
                continue;
            }

            if (!isOperator(token)) {
                throw new IllegalArgumentException(
                        "Unsupported token: " + token
                );
            }

            if (stack.size() < 2) {
                throw new IllegalArgumentException(
                        "Invalid RPN expression: insufficient operands for "
                                + token
                );
            }

            long right = stack.pop();
            long left = stack.pop();

            long result;

            switch (token) {
                case "+":
                    result = left + right;
                    break;

                case "-":
                    result = left - right;
                    break;

                case "*":
                    result = left * right;
                    break;

                case "/":
                    if (right == 0) {
                        throw new ArithmeticException(
                                "Division by zero."
                        );
                    }
                    result = left / right;
                    break;

                default:
                    throw new IllegalArgumentException(
                            "Unsupported operator: " + token
                    );
            }

            if (result < Integer.MIN_VALUE || result > Integer.MAX_VALUE) {
                throw new ArithmeticException(
                        "Result exceeds integer range: " + result
                );
            }

            stack.push(result);
        }

        if (stack.size() != 1) {
            throw new IllegalArgumentException(
                    "Invalid RPN expression: expression must leave exactly "
                            + "one result."
            );
        }

        return Math.toIntExact(stack.pop());
    }

    private static int applyOperator(int left, int right, String operator) {
        switch (operator) {
            case "+":
                return left + right;

            case "-":
                return left - right;

            case "*":
                return left * right;

            case "/":
                if (right == 0) {
                    throw new ArithmeticException(
                            "Division by zero."
                    );
                }

                // Java integer division truncates toward zero.
                return left / right;

            default:
                throw new IllegalArgumentException(
                        "Unsupported operator: " + operator
                );
        }
    }

    private static boolean isOperator(String token) {
        return token.equals("+")
                || token.equals("-")
                || token.equals("*")
                || token.equals("/");
    }

    private static boolean isNumber(String token) {
        if (token == null || token.isEmpty()) {
            return false;
        }

        int start = (token.charAt(0) == '-'
                || token.charAt(0) == '+') ? 1 : 0;

        if (start == token.length()) {
            return false;
        }

        for (int i = start; i < token.length(); i++) {
            if (!Character.isDigit(token.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    private static int parseInteger(String token) {
        try {
            return Integer.parseInt(token);
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException(
                    "Integer is outside the supported range: " + token,
                    exception
            );
        }
    }

    private static void validateTokens(String[] tokens) {
        if (tokens == null || tokens.length == 0) {
            throw new IllegalArgumentException(
                    "Expression must contain at least one token."
            );
        }

        for (String token : tokens) {
            if (token == null || token.isEmpty()) {
                throw new IllegalArgumentException(
                        "Expression contains a null or empty token."
                );
            }
        }
    }

    public static void main(String[] args) {
        String[][] examples = {
                {"2", "1", "+", "3", "*"},
                {"4", "13", "5", "/", "+"},
                {"10", "6", "9", "3", "/", "-", "*"},
                {"5", "2", "-"},
                {"8", "2", "/"},
                {"-4", "2", "/"},
                {"3", "4", "+", "2", "*", "7", "/"}
        };

        System.out.println("RPN Evaluation:");

        for (String[] expression : examples) {
            System.out.println(
                    String.join(" ", expression)
                            + " -> "
                            + evaluate(expression)
            );
        }

        System.out.println("\nExpected:");
        System.out.println("2 1 + 3 * -> 9");
        System.out.println("4 13 5 / + -> 6");
        System.out.println("10 6 9 3 / - * -> -30");
        System.out.println("5 2 - -> 3");
        System.out.println("8 2 / -> 4");
        System.out.println("-4 2 / -> -2");
        System.out.println("3 4 + 2 * 7 / -> 2");
    }
}
