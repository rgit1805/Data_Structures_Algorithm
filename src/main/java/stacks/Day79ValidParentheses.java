package stacks;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Day 79: Valid Parentheses
 *
 * Determine whether every opening bracket has the correct closing bracket
 * in the correct order.
 *
 * Supported brackets:
 *   ()
 *   []
 *   {}
 *
 * Examples:
 *   "()"       -> true
 *   "()[]{}"   -> true
 *   "(]"       -> false
 *   "([{}])"   -> true
 *   "([)]"     -> false
 *
 * Core pattern:
 * Use a stack because the most recently opened bracket must be the first
 * bracket that gets closed (LIFO ordering).
 */
public class Day79ValidParentheses {

    /**
     * Stack of opening brackets.
     *
     * Time: O(n)
     * Space: O(n)
     */
    public static boolean isValid(String text) {
        validateInput(text);

        Deque<Character> stack = new ArrayDeque<>();

        for (char current : text.toCharArray()) {
            if (isOpeningBracket(current)) {
                stack.push(current);
                continue;
            }

            if (!isClosingBracket(current)) {
                throw new IllegalArgumentException(
                        "Input contains unsupported character: " + current
                );
            }

            if (stack.isEmpty()) {
                return false;
            }

            char opening = stack.pop();

            if (!matches(opening, current)) {
                return false;
            }
        }

        return stack.isEmpty();
    }

    /**
     * Alternative implementation using expected closing brackets.
     *
     * Instead of storing the opening bracket, push the closing bracket
     * that we expect to see later.
     *
     * This makes validation at a closing bracket very direct:
     * current closing bracket must equal stack.pop().
     *
     * Time: O(n)
     * Space: O(n)
     */
    public static boolean isValidWithExpectedClosings(String text) {
        validateInput(text);

        Deque<Character> expectedClosings = new ArrayDeque<>();

        for (char current : text.toCharArray()) {
            switch (current) {
                case '(':
                    expectedClosings.push(')');
                    break;

                case '[':
                    expectedClosings.push(']');
                    break;

                case '{':
                    expectedClosings.push('}');
                    break;

                case ')':
                case ']':
                case '}':
                    if (expectedClosings.isEmpty()
                            || expectedClosings.pop() != current) {
                        return false;
                    }
                    break;

                default:
                    throw new IllegalArgumentException(
                            "Input contains unsupported character: " + current
                    );
            }
        }

        return expectedClosings.isEmpty();
    }

    /**
     * Supports bracket validation inside a larger text.
     *
     * Non-bracket characters are ignored.
     *
     * Useful when validating source-like text where identifiers,
     * operators, or whitespace can appear between brackets.
     *
     * Time: O(n)
     * Space: O(n)
     */
    public static boolean areBracketsBalanced(String text) {
        if (text == null) {
            throw new IllegalArgumentException("Input cannot be null.");
        }

        Deque<Character> stack = new ArrayDeque<>();

        for (char current : text.toCharArray()) {
            if (isOpeningBracket(current)) {
                stack.push(current);
            } else if (isClosingBracket(current)) {
                if (stack.isEmpty()) {
                    return false;
                }

                if (!matches(stack.pop(), current)) {
                    return false;
                }
            }
        }

        return stack.isEmpty();
    }

    private static boolean isOpeningBracket(char value) {
        return value == '(' || value == '[' || value == '{';
    }

    private static boolean isClosingBracket(char value) {
        return value == ')' || value == ']' || value == '}';
    }

    private static boolean matches(char opening, char closing) {
        return (opening == '(' && closing == ')')
                || (opening == '[' && closing == ']')
                || (opening == '{' && closing == '}');
    }

    private static void validateInput(String text) {
        if (text == null) {
            throw new IllegalArgumentException(
                    "Input cannot be null."
            );
        }
    }

    public static void main(String[] args) {
        String[] examples = {
                "()",
                "()[]{}",
                "(]",
                "([{}])",
                "([)]",
                "{[]}",
                "(",
                ")",
                "",
                "((()))",
                "{[()]}",
                "([{}])()",
                "hello (world [from {java}])!"
        };

        for (String example : examples) {
            System.out.println(
                    """ + example + "" -> "
                            + isValid(example)
            );
        }

        System.out.println("\nExpected:");
        System.out.println(""()" -> true");
        System.out.println(""()[]{}" -> true");
        System.out.println(""(]" -> false");
        System.out.println(""([{}])" -> true");
        System.out.println(""([)]" -> false");
        System.out.println(""{[]}" -> true");
        System.out.println(""(" -> false");
        System.out.println("")" -> false");
        System.out.println(""" -> true");

        System.out.println(
                "\nSource-like text -> "
                        + areBracketsBalanced(
                                "if (a[0] > b[0]) { return true; }"
                        )
        );
    }
}
