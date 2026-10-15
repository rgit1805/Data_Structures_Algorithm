package design;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Day 89: LRU (Least Recently Used) Cache.
 *
 * A HashMap provides O(1) average-time lookup, while a doubly linked list
 * tracks recency. Most-recently-used entries stay near the head; the
 * least-recently-used entry stays near the tail.
 *
 * Null keys and values are rejected so get(key) returning null unambiguously
 * means that the key is not cached.
 */
public class Day89LRUCache {

    public static final class LRUCache<K, V> {
        private final int capacity;
        private final Map<K, Node<K, V>> entries;

        // Sentinel nodes simplify insertion and removal at both ends.
        private final Node<K, V> head = new Node<>(null, null);
        private final Node<K, V> tail = new Node<>(null, null);

        private static final class Node<K, V> {
            private final K key;
            private V value;
            private Node<K, V> previous;
            private Node<K, V> next;

            private Node(K key, V value) {
                this.key = key;
                this.value = value;
            }
        }

        public LRUCache(int capacity) {
            if (capacity < 1) {
                throw new IllegalArgumentException("Capacity must be at least 1");
            }
            this.capacity = capacity;
            this.entries = new HashMap<>();
            head.next = tail;
            tail.previous = head;
        }

        /**
         * Returns the value and marks the entry as most recently used.
         * Returns null when the key is absent.
         * Average time: O(1).
         */
        public V get(K key) {
            Objects.requireNonNull(key, "Key must not be null");
            Node<K, V> node = entries.get(key);
            if (node == null) {
                return null;
            }
            moveToMostRecent(node);
            return node.value;
        }

        /**
         * Inserts or updates an entry and evicts the least-recently-used entry
         * if capacity is exceeded. Average time: O(1).
         */
        public void put(K key, V value) {
            Objects.requireNonNull(key, "Key must not be null");
            Objects.requireNonNull(value, "Value must not be null");

            Node<K, V> existing = entries.get(key);
            if (existing != null) {
                existing.value = value;
                moveToMostRecent(existing);
                return;
            }

            Node<K, V> node = new Node<>(key, value);
            entries.put(key, node);
            addAfterHead(node);

            if (entries.size() > capacity) {
                Node<K, V> leastRecent = tail.previous;
                unlink(leastRecent);
                entries.remove(leastRecent.key);
            }
        }

        /** Removes a key without affecting other entries. Average time: O(1). */
        public boolean remove(K key) {
            Objects.requireNonNull(key, "Key must not be null");
            Node<K, V> node = entries.remove(key);
            if (node == null) {
                return false;
            }
            unlink(node);
            return true;
        }

        public int size() {
            return entries.size();
        }

        public int capacity() {
            return capacity;
        }

        public boolean containsKey(K key) {
            Objects.requireNonNull(key, "Key must not be null");
            return entries.containsKey(key);
        }

        public boolean isEmpty() {
            return entries.isEmpty();
        }

        /** Removes all cached entries. Time: O(n). */
        public void clear() {
            entries.clear();
            head.next = tail;
            tail.previous = head;
        }

        /** Returns keys from most recently used to least recently used. */
        public String recencyOrder() {
            StringBuilder result = new StringBuilder("[");
            Node<K, V> current = head.next;
            while (current != tail) {
                if (current != head.next) {
                    result.append(", ");
                }
                result.append(current.key);
                current = current.next;
            }
            return result.append(']').toString();
        }

        private void moveToMostRecent(Node<K, V> node) {
            unlink(node);
            addAfterHead(node);
        }

        private void addAfterHead(Node<K, V> node) {
            node.previous = head;
            node.next = head.next;
            head.next.previous = node;
            head.next = node;
        }

        private void unlink(Node<K, V> node) {
            node.previous.next = node.next;
            node.next.previous = node.previous;
            node.previous = null;
            node.next = null;
        }
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    public static void main(String[] args) {
        LRUCache<Integer, String> cache = new LRUCache<>(2);

        cache.put(1, "one");
        cache.put(2, "two");
        check("one".equals(cache.get(1)), "Key 1 should be present");
        cache.put(3, "three"); // Evicts key 2, the least recently used.
        check(cache.get(2) == null, "Key 2 should have been evicted");
        check("three".equals(cache.get(3)), "Key 3 should be present");

        cache.put(1, "ONE"); // Updating also makes key 1 most recently used.
        check("ONE".equals(cache.get(1)), "Key 1 should be updated");
        check("[1, 3]".equals(cache.recencyOrder()),
                "Unexpected recency order: " + cache.recencyOrder());

        check(cache.remove(1), "Existing key should be removed");
        check(!cache.remove(99), "Missing key should not be removed");
        check(cache.size() == 1, "Cache should contain one entry");

        cache.clear();
        check(cache.isEmpty(), "Cache should be empty after clear");

        System.out.println("All LRU cache checks passed.");
    }
}
