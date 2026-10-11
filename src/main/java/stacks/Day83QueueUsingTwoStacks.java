package stacks;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.NoSuchElementException;

/**
 * Day 83: Implement a Queue Using Two Stacks.
 *
 * Queue behavior is FIFO (first in, first out), while each stack is LIFO.
 * Two stacks can simulate a queue by reversing item order when needed.
 *
 * incoming: newly enqueued items
 * outgoing: items ready to be dequeued in FIFO order
 *
 * Amortized enqueue: O(1)
 * Amortized dequeue/peek: O(1)
 * Worst-case dequeue/peek: O(n) when transferring items
 * Space: O(n)
 */
public class Day83QueueUsingTwoStacks<T> {

    private final Deque<T> incoming = new ArrayDeque<>();
    private final Deque<T> outgoing = new ArrayDeque<>();

    /**
     * Adds an item to the back of the queue.
     * Null values are rejected because ArrayDeque does not support null.
     */
    public void enqueue(T value) {
        if (value == null) {
            throw new IllegalArgumentException("Queue does not accept null values.");
        }
        incoming.push(value);
    }

    /**
     * Removes and returns the front item.
     */
    public T dequeue() {
        moveIncomingToOutgoingIfNeeded();

        if (outgoing.isEmpty()) {
            throw new NoSuchElementException("Cannot dequeue from an empty queue.");
        }

        return outgoing.pop();
    }

    /**
     * Returns the front item without removing it.
     */
    public T peek() {
        moveIncomingToOutgoingIfNeeded();

        if (outgoing.isEmpty()) {
            throw new NoSuchElementException("Cannot peek into an empty queue.");
        }

        return outgoing.peek();
    }

    public boolean isEmpty() {
        return incoming.isEmpty() && outgoing.isEmpty();
    }

    public int size() {
        return incoming.size() + outgoing.size();
    }

    public void clear() {
        incoming.clear();
        outgoing.clear();
    }

    /**
     * Transfer only when outgoing is empty. Transferring earlier would reverse
     * the order of older elements relative to newer ones.
     */
    private void moveIncomingToOutgoingIfNeeded() {
        if (outgoing.isEmpty()) {
            while (!incoming.isEmpty()) {
                outgoing.push(incoming.pop());
            }
        }
    }

    public static void main(String[] args) {
        Day83QueueUsingTwoStacks<Integer> queue =
                new Day83QueueUsingTwoStacks<>();

        queue.enqueue(10);
        queue.enqueue(20);
        queue.enqueue(30);

        System.out.println("Front: " + queue.peek());       // 10
        System.out.println("Removed: " + queue.dequeue());  // 10
        System.out.println("Removed: " + queue.dequeue());  // 20

        queue.enqueue(40);

        System.out.println("Front: " + queue.peek());       // 30
        System.out.println("Size: " + queue.size());        // 2
        System.out.println("Removed: " + queue.dequeue());  // 30
        System.out.println("Removed: " + queue.dequeue());  // 40
        System.out.println("Empty: " + queue.isEmpty());    // true

        // Expected output:
        // Front: 10
        // Removed: 10
        // Removed: 20
        // Front: 30
        // Size: 2
        // Removed: 30
        // Removed: 40
        // Empty: true
    }
}
