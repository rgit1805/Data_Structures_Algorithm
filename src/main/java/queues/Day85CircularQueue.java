package queues;

import java.util.Arrays;

/**
 * Day 85: Fixed-capacity circular queue implemented with an array.
 *
 * The front index points to the first element. The number of stored elements
 * is tracked separately, so empty and full states are never confused.
 */
public class Day85CircularQueue {
    private final int[] elements;
    private int frontIndex;
    private int size;

    public Day85CircularQueue(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be greater than zero");
        }
        elements = new int[capacity];
        frontIndex = 0;
        size = 0;
    }

    /**
     * Inserts at the logical rear.
     *
     * @return false when the queue is full; otherwise true.
     */
    public boolean enqueue(int value) {
        if (isFull()) {
            return false;
        }

        int rearIndex = (frontIndex + size) % elements.length;
        elements[rearIndex] = value;
        size++;
        return true;
    }

    /**
     * Removes the front element.
     *
     * @return false when empty; otherwise true.
     */
    public boolean dequeue() {
        if (isEmpty()) {
            return false;
        }

        frontIndex = (frontIndex + 1) % elements.length;
        size--;
        // Normalize the index when empty; not required for correctness,
        // but it keeps the internal state easy to inspect while debugging.
        if (size == 0) {
            frontIndex = 0;
        }
        return true;
    }

    /** Returns the front value, or -1 if empty. */
    public int front() {
        return isEmpty() ? -1 : elements[frontIndex];
    }

    /** Returns the rear value, or -1 if empty. */
    public int rear() {
        if (isEmpty()) {
            return -1;
        }
        int rearIndex = (frontIndex + size - 1) % elements.length;
        return elements[rearIndex];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean isFull() {
        return size == elements.length;
    }

    public int size() {
        return size;
    }

    public int capacity() {
        return elements.length;
    }

    /** Removes all elements without reallocating the backing array. */
    public void clear() {
        frontIndex = 0;
        size = 0;
        Arrays.fill(elements, 0);
    }

    /** Returns elements in logical front-to-rear order for inspection. */
    public int[] toArray() {
        int[] result = new int[size];
        for (int i = 0; i < size; i++) {
            result[i] = elements[(frontIndex + i) % elements.length];
        }
        return result;
    }

    public static void main(String[] args) {
        Day85CircularQueue queue = new Day85CircularQueue(3);

        System.out.println(queue.enqueue(10)); // true
        System.out.println(queue.enqueue(20)); // true
        System.out.println(queue.enqueue(30)); // true
        System.out.println(queue.enqueue(40)); // false: full
        System.out.println("Front: " + queue.front()); // 10
        System.out.println("Rear: " + queue.rear());   // 30

        queue.dequeue(); // removes 10; front advances
        queue.enqueue(40); // wraps around to the start of the array

        System.out.println("After wraparound: " + Arrays.toString(queue.toArray()));
        // [20, 30, 40]
        System.out.println("Front: " + queue.front()); // 20
        System.out.println("Rear: " + queue.rear());   // 40
        System.out.println("Full: " + queue.isFull()); // true
    }
}
