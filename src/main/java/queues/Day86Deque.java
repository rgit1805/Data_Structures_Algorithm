package queues;

import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * Day 86: Array-backed double-ended queue (deque).
 *
 * Supports insertion and removal at both ends. The circular buffer avoids
 * shifting elements; capacity grows geometrically when it becomes full.
 *
 * End operations are O(1) amortized and O(n) for a resizing operation.
 * Space is O(n) for the current backing capacity.
 */
public class Day86Deque<T> {
    private static final int DEFAULT_CAPACITY = 8;

    private Object[] elements;
    private int head;
    private int size;

    public Day86Deque() {
        this(DEFAULT_CAPACITY);
    }

    public Day86Deque(int initialCapacity) {
        if (initialCapacity <= 0) {
            throw new IllegalArgumentException("Initial capacity must be greater than zero");
        }
        elements = new Object[initialCapacity];
    }

    public void addFirst(T value) {
        requireNonNull(value);
        ensureCapacity();
        head = (head - 1 + elements.length) % elements.length;
        elements[head] = value;
        size++;
    }

    public void addLast(T value) {
        requireNonNull(value);
        ensureCapacity();
        int tail = (head + size) % elements.length;
        elements[tail] = value;
        size++;
    }

    public T removeFirst() {
        ensureNotEmpty();
        T value = elementAt(head);
        elements[head] = null;
        head = (head + 1) % elements.length;
        size--;
        normalizeIfEmpty();
        return value;
    }

    public T removeLast() {
        ensureNotEmpty();
        int tail = (head + size - 1) % elements.length;
        T value = elementAt(tail);
        elements[tail] = null;
        size--;
        normalizeIfEmpty();
        return value;
    }

    public T peekFirst() {
        ensureNotEmpty();
        return elementAt(head);
    }

    public T peekLast() {
        ensureNotEmpty();
        return elementAt((head + size - 1) % elements.length);
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public int capacity() {
        return elements.length;
    }

    public void clear() {
        Arrays.fill(elements, null);
        head = 0;
        size = 0;
    }

    /** Returns a snapshot ordered from front to rear. */
    public Object[] toArray() {
        Object[] result = new Object[size];
        for (int i = 0; i < size; i++) {
            result[i] = elements[(head + i) % elements.length];
        }
        return result;
    }

    private void ensureCapacity() {
        if (size < elements.length) {
            return;
        }

        int newCapacity = elements.length <= Integer.MAX_VALUE / 2
                ? elements.length * 2
                : Integer.MAX_VALUE;

        if (newCapacity <= elements.length) {
            throw new IllegalStateException("Deque has reached maximum array capacity");
        }

        Object[] expanded = new Object[newCapacity];
        for (int i = 0; i < size; i++) {
            expanded[i] = elements[(head + i) % elements.length];
        }
        elements = expanded;
        head = 0;
    }

    @SuppressWarnings("unchecked")
    private T elementAt(int index) {
        return (T) elements[index];
    }

    private void ensureNotEmpty() {
        if (isEmpty()) {
            throw new NoSuchElementException("Deque is empty");
        }
    }

    private void normalizeIfEmpty() {
        if (size == 0) {
            head = 0;
        }
    }

    private static void requireNonNull(Object value) {
        if (value == null) {
            throw new IllegalArgumentException("Null values are not supported");
        }
    }

    public static void main(String[] args) {
        Day86Deque<Integer> deque = new Day86Deque<>(3);
        deque.addLast(20);   // [20]
        deque.addLast(30);   // [20, 30]
        deque.addFirst(10);  // [10, 20, 30]
        deque.addFirst(5);   // grows capacity; [5, 10, 20, 30]

        System.out.println("Deque front-to-rear: " + Arrays.toString(deque.toArray()));
        System.out.println("Front: " + deque.peekFirst()); // 5
        System.out.println("Rear: " + deque.peekLast());   // 30
        System.out.println("Remove front: " + deque.removeFirst()); // 5
        System.out.println("Remove rear: " + deque.removeLast());   // 30
        System.out.println("Remaining: " + Arrays.toString(deque.toArray()));
        System.out.println("Size: " + deque.size()); // 2

        deque.clear();
        System.out.println("Empty after clear: " + deque.isEmpty()); // true
    }
}
