package stacks;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Day 82: Evaluate Infix Expression using Two Stacks.
 *
 * Supports integer literals, +, -, *, /, %, parentheses, and unary minus
 * before integer literals (for example, -5 + 2 * 3).
 *
 * Time: O(n)
 * Space: O(n)
 */
public class Day82EvaluateInfixExpression {

    public static long evaluate(String expression) {
        if (expression == null || expression.isBlank()) {
            throw new IllegalArgumentException("Expression cannot be blank.");
        }

        Deque<Long> values = new ArrayDeque<>();
        Deque<Character> operators = new ArrayDeque<>();
        boolean expectOperand = true;

        for (int i = 0; i < expression.length();) {
            char ch = expression.charAt(i);

            if (Character.isWhitespace(ch)) {
                i++;
                continue;
            }

            if (ch == '(') {
                if (!expectOperand) {
                    throw new IllegalArgumentException("Missing operator before '('.");
                }
                operators.push(ch);
                expectOperand = true;
                i++;
                continue;
            }

            if (ch == ')') {
                if (expectOperand) {
                    throw new IllegalArgumentException("Unexpected ')' or missing operand.");
                }
                while (!operators.isEmpty() && operators.peek() != '(') {
                    applyTop(values, operators);
                }
                if (operators.isEmpty()) {
                    throw new IllegalArgumentException("Mismatched parentheses.");
                }
                operators.pop();
                expectOperand = false;
                i++;
                continue;
            }

            boolean unaryMinus = ch == '-' && expectOperand;
            if (Character.isDigit(ch) || unaryMinus) {
                if (unaryMinus) {
                    i++;
                    while (i < expression.length()
                            && Character.isWhitespace(expression.charAt(i))) {
                        i++;
                    }
                    if (i >= expression.length()
                            || !Character.isDigit(expression.charAt(i))) {
                        throw new IllegalArgumentException(
                                "Unary minus must precede an integer literal."
                        );
                    }
                }

                long number = 0;
                while (i < expression.length()
                        && Character.isDigit(expression.charAt(i))) {
                    number = Math.addExact(
                            Math.multiplyExact(number, 10),
                            expression.charAt(i) - '0'
                    );
                    i++;
                }
                values.push(unaryMinus ? Math.negateExact(number) : number);
                expectOperand = false;
                continue;
            }

            if (isOperator(ch)) {
                if (expectOperand) {
                    throw new IllegalArgumentException(
                            "Operator '" + ch + "' is missing a left operand."
                    );
                }
                while (!operators.isEmpty()
                        && operators.peek() != '('
                        && precedence(operators.peek()) >= precedence(ch)) {
                    applyTop(values, operators);
                }
                operators.push(ch);
                expectOperand = true;
                i++;
                continue;
            }

            throw new IllegalArgumentException(
                    "Unsupported character '" + ch + "' at index " + i
            );
        }

        if (expectOperand) {
            throw new IllegalArgumentException("Expression ends with an operator.");
        }

        while (!operators.isEmpty()) {
            if (operators.peek() == '(') {
                throw new IllegalArgumentException("Mismatched parentheses.");
            }
            applyTop(values, operators);
        }

        if (values.size() != 1) {
            throw new IllegalArgumentException("Invalid expression.");
        }
        return values.pop();
    }

    private static void applyTop(
            Deque<Long> values,
            Deque<Character> operators
    ) {
        if (values.size() < 2 || operators.isEmpty()) {
            throw new IllegalArgumentException("Insufficient operands.");
        }

        char operator = operators.pop();
        long right = values.pop();
        long left = values.pop();

        long result;
        switch (operator) {
            case '+':
                result = Math.addExact(left, right);
                break;
            case '-':
                result = Math.subtractExact(left, right);
                break;
            case '*':
                result = Math.multiplyExact(left, right);
                break;
            case '/':
                if (right == 0) {
                    throw new ArithmeticException("Division by zero.");
                }
                result = left / right; // Java truncates integer division toward zero.
                break;
            case '%':
                if (right == 0) {
                    throw new ArithmeticException("Modulo by zero.");
                }
                result = left % right;
                break;
            default:
                throw new IllegalArgumentException("Unsupported operator: " + operator);
        }
        values.push(result);
    }

    private static boolean isOperator(char ch) {
        return ch == '+' || ch == '-' || ch == '*' || ch == '/' || ch == '%';
    }

    private static int precedence(char operator) {
        switch (operator) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
            case '%':
                return 2;
            default:
                throw new IllegalArgumentException("Unsupported operator: " + operator);
        }
    }

    public static void main(String[] args) {
        String[] expressions = {
                "2 + 3 * 4",
                "(2 + 3) * 4",
                "10 - 2 * 3",
                "100 / 5 / 2",
                "18 % 5 + 2",
                "-5 + 2 * 3",
                "10 + (2 * (3 + 4))",
                "50 - (10 + 5) * 2",
                "8 + 2 * 3 - 4 / 2"
        };

        for (String expression : expressions) {
            System.out.println(expression + " -> " + evaluate(expression));
        }

        // Expected results: 14, 20, 4, 10, 5, 1, 24, 20, 12.
    }
}
