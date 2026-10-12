package stacks;

import java.util.ArrayDeque;
import java.util.NoSuchElementException;
import java.util.Queue;

/**
 * Day 84: Implement a LIFO stack using FIFO queues.
 *
 * Both implementations keep the newest pushed element at the front of the
 * active queue, so pop() and peek() are O(1). They differ in how push works.
 */
public class Day84StackUsingQueues {

    /**
     * One-queue solution: after inserting an element, rotate the queue so
     * that the new element moves to the front.
     *
     * push: O(n); pop/peek: O(1); extra space: O(n).
     */
    public static final class OneQueueStack<T> {
        private final Queue<T> queue = new ArrayDeque<>();

        public void push(T value) {
            requireNonNull(value);
            queue.add(value);

            // Move every element that was behind the new value ahead of it.
            for (int i = 0, rotations = queue.size() - 1; i < rotations; i++) {
                queue.add(queue.remove());
            }
        }

        public T pop() {
            ensureNotEmpty();
            return queue.remove();
        }

        public T peek() {
            ensureNotEmpty();
            return queue.element();
        }

        public boolean isEmpty() {
            return queue.isEmpty();
        }

        public int size() {
            return queue.size();
        }

        public void clear() {
            queue.clear();
        }

        private void ensureNotEmpty() {
            if (queue.isEmpty()) {
                throw new NoSuchElementException("Stack is empty");
            }
        }
    }

    /**
     * Two-queue solution: build the new queue with the pushed value first,
     * then append the previous stack contents and swap the queues.
     *
     * push: O(n); pop/peek: O(1); extra space: O(n).
     */
    public static final class TwoQueueStack<T> {
        private Queue<T> primary = new ArrayDeque<>();
        private Queue<T> helper = new ArrayDeque<>();

        public void push(T value) {
            requireNonNull(value);
            helper.add(value);

            while (!primary.isEmpty()) {
                helper.add(primary.remove());
            }

            Queue<T> oldPrimary = primary;
            primary = helper;
            helper = oldPrimary;
        }

        public T pop() {
            ensureNotEmpty();
            return primary.remove();
        }

        public T peek() {
            ensureNotEmpty();
            return primary.element();
        }

        public boolean isEmpty() {
            return primary.isEmpty();
        }

        public int size() {
            return primary.size();
        }

        public void clear() {
            primary.clear();
            helper.clear();
        }

        private void ensureNotEmpty() {
            if (primary.isEmpty()) {
                throw new NoSuchElementException("Stack is empty");
            }
        }
    }

    private static void requireNonNull(Object value) {
        if (value == null) {
            throw new IllegalArgumentException("Null values are not supported");
        }
    }

    public static void main(String[] args) {
        demonstrateOneQueueStack();
        demonstrateTwoQueueStack();
    }

    private static void demonstrateOneQueueStack() {
        OneQueueStack<Integer> stack = new OneQueueStack<>();
        stack.push(10);
        stack.push(20);
        stack.push(30);

        System.out.println("One queue:");
        System.out.println("Top: " + stack.peek()); // 30
        System.out.println("Pop: " + stack.pop());  // 30
        stack.push(40);
        System.out.println("Pop: " + stack.pop());  // 40
        System.out.println("Pop: " + stack.pop());  // 20
        System.out.println("Size: " + stack.size()); // 1
    }

    private static void demonstrateTwoQueueStack() {
        TwoQueueStack<String> stack = new TwoQueueStack<>();
        stack.push("first");
        stack.push("second");
        stack.push("third");

        System.out.println("\nTwo queues:");
        System.out.println("Top: " + stack.peek()); // third
        while (!stack.isEmpty()) {
            System.out.println("Pop: " + stack.pop());
        }
    }
}
