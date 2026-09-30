package stacks;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Day 78: Min Stack
 *
 * Design a stack that supports:
 *   - push
 *   - pop
 *   - top
 *   - getMin
 *
 * All operations should run in O(1).
 *
 * Example:
 *   push(5)
 *   push(3)
 *   push(7)
 *   getMin() -> 3
 *   pop()
 *   getMin() -> 3
 *   pop()
 *   getMin() -> 5
 *
 * This problem demonstrates how to maintain additional derived state
 * without repeatedly scanning the entire data structure.
 */
public class Day78MinStack {

    /**
     * Approach 1: Store the minimum alongside every value.
     *
     * Each entry stores:
     *   value
     *   minimum value in the stack up to this entry
     *
     * Time:
     *   push    O(1)
     *   pop     O(1)
     *   top     O(1)
     *   getMin  O(1)
     *
     * Space: O(n)
     */
    public static class MinStackWithPair {

        private final Deque<Entry> stack = new ArrayDeque<>();

        public void push(int value) {
            int currentMin = stack.isEmpty()
                    ? value
                    : Math.min(value, stack.peek().minimum);

            stack.push(new Entry(value, currentMin));
        }

        public int pop() {
            ensureNotEmpty();
            return stack.pop().value;
        }

        public int top() {
            ensureNotEmpty();
            return stack.peek().value;
        }

        public int getMin() {
            ensureNotEmpty();
            return stack.peek().minimum;
        }

        public boolean isEmpty() {
            return stack.isEmpty();
        }

        private void ensureNotEmpty() {
            if (stack.isEmpty()) {
                throw new IllegalStateException("Stack is empty.");
            }
        }

        private static class Entry {
            private final int value;
            private final int minimum;

            private Entry(int value, int minimum) {
                this.value = value;
                this.minimum = minimum;
            }
        }
    }

    /**
     * Approach 2: Use two stacks.
     *
     * The normal stack stores values.
     * The min stack stores only the minimum values needed to restore
     * previous minimums after a pop.
     *
     * Time: O(1) per operation.
     * Space: O(n).
     */
    public static class MinStackWithTwoStacks {

        private final Deque<Integer> values = new ArrayDeque<>();
        private final Deque<Integer> minimums = new ArrayDeque<>();

        public void push(int value) {
            values.push(value);

            if (minimums.isEmpty()
                    || value <= minimums.peek()) {
                minimums.push(value);
            }
        }

        public int pop() {
            ensureNotEmpty();

            int value = values.pop();

            if (value == minimums.peek()) {
                minimums.pop();
            }

            return value;
        }

        public int top() {
            ensureNotEmpty();
            return values.peek();
        }

        public int getMin() {
            ensureNotEmpty();
            return minimums.peek();
        }

        public boolean isEmpty() {
            return values.isEmpty();
        }

        private void ensureNotEmpty() {
            if (values.isEmpty()) {
                throw new IllegalStateException("Stack is empty.");
            }
        }
    }

    /**
     * Approach 3: Single-stack encoded representation.
     *
     * Instead of storing a second minimum structure, encode information
     * about the previous minimum inside the stack value.
     *
     * Important:
     * long is used internally to avoid integer overflow during encoding.
     *
     * Time: O(1) per operation.
     * Space: O(n).
     *
     * This approach saves the second stack but makes the implementation
     * more subtle and less straightforward to maintain.
     */
    public static class MinStackEncoded {

        private final Deque<Long> stack = new ArrayDeque<>();
        private long minimum;

        public void push(int value) {
            if (stack.isEmpty()) {
                stack.push((long) value);
                minimum = value;
                return;
            }

            if (value >= minimum) {
                stack.push((long) value);
            } else {
                long encoded = 2L * value - minimum;
                stack.push(encoded);
                minimum = value;
            }
        }

        public int pop() {
            ensureNotEmpty();

            long top = stack.pop();

            if (top >= minimum) {
                return (int) top;
            }

            int currentMinimum = (int) minimum;

            // Recover the previous minimum:
            // encoded = 2 * newMin - oldMin
            minimum = 2L * minimum - top;

            return currentMinimum;
        }

        public int top() {
            ensureNotEmpty();

            long top = stack.peek();

            if (top >= minimum) {
                return (int) top;
            }

            // Encoded value represents the current minimum.
            return (int) minimum;
        }

        public int getMin() {
            ensureNotEmpty();
            return (int) minimum;
        }

        public boolean isEmpty() {
            return stack.isEmpty();
        }

        private void ensureNotEmpty() {
            if (stack.isEmpty()) {
                throw new IllegalStateException("Stack is empty.");
            }
        }
    }

    private static void testPairImplementation() {
        MinStackWithPair stack = new MinStackWithPair();

        stack.push(5);
        stack.push(3);
        stack.push(7);
        stack.push(2);
        stack.push(2);
        stack.push(6);

        System.out.println("Pair implementation:");
        System.out.println("Min = " + stack.getMin()); // 2
        System.out.println("Pop = " + stack.pop());    // 6
        System.out.println("Min = " + stack.getMin()); // 2
        System.out.println("Pop = " + stack.pop());    // 2
        System.out.println("Min = " + stack.getMin()); // 2
        System.out.println("Pop = " + stack.pop());    // 2
        System.out.println("Min = " + stack.getMin()); // 3
        System.out.println("Top = " + stack.top());    // 3
    }

    private static void testTwoStackImplementation() {
        MinStackWithTwoStacks stack = new MinStackWithTwoStacks();

        stack.push(10);
        stack.push(4);
        stack.push(8);
        stack.push(1);

        System.out.println("\nTwo-stack implementation:");
        System.out.println("Min = " + stack.getMin()); // 1
        System.out.println("Pop = " + stack.pop());    // 1
        System.out.println("Min = " + stack.getMin()); // 4
        System.out.println("Top = " + stack.top());    // 8
    }

    private static void testEncodedImplementation() {
        MinStackEncoded stack = new MinStackEncoded();

        stack.push(10);
        stack.push(4);
        stack.push(8);
        stack.push(1);

        System.out.println("\nEncoded implementation:");
        System.out.println("Min = " + stack.getMin()); // 1
        System.out.println("Top = " + stack.top());    // 1
        System.out.println("Pop = " + stack.pop());    // 1
        System.out.println("Min = " + stack.getMin()); // 4
        System.out.println("Pop = " + stack.pop());    // 8
        System.out.println("Min = " + stack.getMin()); // 4
    }

    public static void main(String[] args) {
        testPairImplementation();
        testTwoStackImplementation();
        testEncodedImplementation();

        System.out.println("\nAll Min Stack operations are O(1).");
    }
}
