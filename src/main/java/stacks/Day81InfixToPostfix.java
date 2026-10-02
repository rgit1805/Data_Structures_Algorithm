package stacks;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * Day 81: Infix to Postfix Conversion
 *
 * Converts an arithmetic expression from infix notation to postfix
 * (Reverse Polish Notation).
 *
 * Infix:
 *   2 + 3 * 4
 *
 * Postfix:
 *   2 3 4 * +
 *
 * Why a stack?
 * Operators have precedence and parentheses can temporarily change that
 * precedence. The stack stores operators until they can safely be emitted.
 *
 * Supported:
 *   operands: letters, digits, and underscore
 *   operators: + - * / ^
 *   parentheses: ( )
 *
 * Multi-character operands can be separated by whitespace.
 *
 * Time: O(n)
 * Space: O(n)
 */
public class Day81InfixToPostfix {

    /**
     * Converts an infix expression to a space-separated postfix expression.
     *
     * Example:
     *   "A + B * C" -> "A B C * +"
     *
     * Operators with higher precedence are emitted first.
     * Operators with equal precedence follow associativity rules.
     */
    public static String convert(String expression) {
        if (expression == null || expression.isBlank()) {
            throw new IllegalArgumentException(
                    "Expression cannot be null or blank."
            );
        }

        List<String> tokens = tokenize(expression);
        Deque<String> operators = new ArrayDeque<>();
        List<String> output = new ArrayList<>();

        boolean expectOperand = true;

        for (String token : tokens) {
            if (isOperand(token)) {
                output.add(token);
                expectOperand = false;
                continue;
            }

            if (token.equals("(")) {
                if (!expectOperand) {
                    throw new IllegalArgumentException(
                            "Missing operator before '('"
                    );
                }

                operators.push(token);
                expectOperand = true;
                continue;
            }

            if (token.equals(")")) {
                if (expectOperand) {
                    throw new IllegalArgumentException(
                            "Unexpected ')' or missing operand."
                    );
                }

                boolean foundOpening = false;

                while (!operators.isEmpty()) {
                    String top = operators.pop();

                    if (top.equals("(")) {
                        foundOpening = true;
                        break;
                    }

                    output.add(top);
                }

                if (!foundOpening) {
                    throw new IllegalArgumentException(
                            "Mismatched parentheses."
                    );
                }

                expectOperand = false;
                continue;
            }

            if (!isOperator(token)) {
                throw new IllegalArgumentException(
                        "Unsupported token: " + token
                );
            }

            if (expectOperand) {
                throw new IllegalArgumentException(
                        "Operator '" + token + "' is missing a left operand."
                );
            }

            while (!operators.isEmpty()
                    && isOperator(operators.peek())
                    && shouldPopBeforePush(operators.peek(), token)) {
                output.add(operators.pop());
            }

            operators.push(token);
            expectOperand = true;
        }

        if (expectOperand) {
            throw new IllegalArgumentException(
                    "Expression ends with an operator or missing operand."
            );
        }

        while (!operators.isEmpty()) {
            String top = operators.pop();

            if (top.equals("(") || top.equals(")")) {
                throw new IllegalArgumentException(
                        "Mismatched parentheses."
                );
            }

            output.add(top);
        }

        return String.join(" ", output);
    }

    /**
     * Determines whether the operator already on the stack should be emitted
     * before the incoming operator.
     *
     * For left-associative operators (+, -, *, /), equal precedence means
     * the existing operator is emitted first.
     *
     * Exponentiation (^) is right-associative, so equal precedence does not
     * cause the existing ^ to be popped.
     */
    private static boolean shouldPopBeforePush(
            String stackOperator,
            String incomingOperator
    ) {
        int stackPrecedence = precedence(stackOperator);
        int incomingPrecedence = precedence(incomingOperator);

        if (stackPrecedence > incomingPrecedence) {
            return true;
        }

        if (stackPrecedence < incomingPrecedence) {
            return false;
        }

        return !isRightAssociative(incomingOperator);
    }

    private static int precedence(String operator) {
        switch (operator) {
            case "+":
            case "-":
                return 1;

            case "*":
            case "/":
                return 2;

            case "^":
                return 3;

            default:
                throw new IllegalArgumentException(
                        "Not an operator: " + operator
                );
        }
    }

    private static boolean isRightAssociative(String operator) {
        return operator.equals("^");
    }

    private static boolean isOperator(String token) {
        return token.equals("+")
                || token.equals("-")
                || token.equals("*")
                || token.equals("/")
                || token.equals("^");
    }

    private static boolean isOperand(String token) {
        if (token == null || token.isEmpty()) {
            return false;
        }

        for (char current : token.toCharArray()) {
            if (!Character.isLetterOrDigit(current) && current != '_') {
                return false;
            }
        }

        return true;
    }

    /**
     * Tokenizes both compact expressions such as:
     *   A+B*C
     *
     * and whitespace-separated expressions such as:
     *   total + tax * rate
     */
    private static List<String> tokenize(String expression) {
        List<String> tokens = new ArrayList<>();
        StringBuilder operand = new StringBuilder();

        for (int i = 0; i < expression.length(); i++) {
            char current = expression.charAt(i);

            if (Character.isWhitespace(current)) {
                flushOperand(operand, tokens);
                continue;
            }

            if (Character.isLetterOrDigit(current) || current == '_') {
                operand.append(current);
                continue;
            }

            flushOperand(operand, tokens);

            if (current == '('
                    || current == ')'
                    || current == '+'
                    || current == '-'
                    || current == '*'
                    || current == '/'
                    || current == '^') {
                tokens.add(String.valueOf(current));
            } else {
                throw new IllegalArgumentException(
                        "Unsupported character: " + current
                );
            }
        }

        flushOperand(operand, tokens);
        return tokens;
    }

    private static void flushOperand(
            StringBuilder operand,
            List<String> tokens
    ) {
        if (operand.length() > 0) {
            tokens.add(operand.toString());
            operand.setLength(0);
        }
    }

    public static void main(String[] args) {
        String[] examples = {
                "A + B * C",
                "(A + B) * C",
                "A + B * C - D",
                "A * (B + C) / D",
                "A + B ^ C ^ D",
                "2 + 3 * 4",
                "total + tax * rate",
                "((A+B)*C)"
        };

        System.out.println("Infix -> Postfix:");

        for (String example : examples) {
            System.out.println(
                    example + " -> " + convert(example)
            );
        }

        System.out.println("\nExpected:");
        System.out.println("A + B * C -> A B C * +");
        System.out.println("(A + B) * C -> A B + C *");
        System.out.println("A + B * C - D -> A B C * + D -");
        System.out.println("A * (B + C) / D -> A B C + * D /");
        System.out.println("A + B ^ C ^ D -> A B C D ^ ^ +");
        System.out.println("2 + 3 * 4 -> 2 3 4 * +");
    }
}
