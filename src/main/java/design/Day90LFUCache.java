package design;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Day 90: LFU (Least Frequently Used) Cache.
 *
 * Evicts the key with the lowest access frequency. When multiple keys share
 * that frequency, the least recently used among them is evicted.
 *
 * Three maps provide average O(1) operations:
 * - values: key -> cached value
 * - frequencies: key -> access frequency
 * - keysByFrequency: frequency -> keys ordered from least to most recent
 */
public class Day90LFUCache {

    public static final class LFUCache<K, V> {
        private final int capacity;
        private int minimumFrequency;

        private final Map<K, V> values = new HashMap<>();
        private final Map<K, Integer> frequencies = new HashMap<>();
        private final Map<Integer, LinkedHashSet<K>> keysByFrequency =
                new HashMap<>();

        public LFUCache(int capacity) {
            if (capacity < 1) {
                throw new IllegalArgumentException("Capacity must be at least 1");
            }
            this.capacity = capacity;
        }

        /**
         * Returns the cached value, or null when absent.
         * A successful get increases the key's frequency.
         * Average time: O(1).
         */
        public V get(K key) {
            Objects.requireNonNull(key, "Key must not be null");
            if (!values.containsKey(key)) {
                return null;
            }

            increaseFrequency(key);
            return values.get(key);
        }

        /**
         * Inserts or updates a key. Updating an existing key counts as use.
         * Evicts LFU; ties are broken by least-recently-used order.
         * Average time: O(1).
         */
        public void put(K key, V value) {
            Objects.requireNonNull(key, "Key must not be null");
            Objects.requireNonNull(value, "Value must not be null");

            if (values.containsKey(key)) {
                values.put(key, value);
                increaseFrequency(key);
                return;
            }

            if (values.size() == capacity) {
                Set<K> leastFrequentKeys =
                        keysByFrequency.get(minimumFrequency);
                K victim = leastFrequentKeys.iterator().next();
                leastFrequentKeys.remove(victim);

                if (leastFrequentKeys.isEmpty()) {
                    keysByFrequency.remove(minimumFrequency);
                }

                values.remove(victim);
                frequencies.remove(victim);
            }

            values.put(key, value);
            frequencies.put(key, 1);
            keysByFrequency.computeIfAbsent(1, ignored -> new LinkedHashSet<>())
                    .add(key);
            minimumFrequency = 1;
        }

        /** Removes a key explicitly. Average time: O(1). */
        public boolean remove(K key) {
            Objects.requireNonNull(key, "Key must not be null");
            Integer frequency = frequencies.remove(key);
            if (frequency == null) {
                return false;
            }

            values.remove(key);
            LinkedHashSet<K> bucket = keysByFrequency.get(frequency);
            bucket.remove(key);

            if (bucket.isEmpty()) {
                keysByFrequency.remove(frequency);
                if (frequency == minimumFrequency) {
                    refreshMinimumFrequency();
                }
            }
            return true;
        }

        public int size() {
            return values.size();
        }

        public int capacity() {
            return capacity;
        }

        public boolean containsKey(K key) {
            Objects.requireNonNull(key, "Key must not be null");
            return values.containsKey(key);
        }

        public boolean isEmpty() {
            return values.isEmpty();
        }

        /** Clears the cache. Time: O(n). */
        public void clear() {
            values.clear();
            frequencies.clear();
            keysByFrequency.clear();
            minimumFrequency = 0;
        }

        /** Returns a key's frequency, or 0 if it is not cached. */
        public int frequencyOf(K key) {
            Objects.requireNonNull(key, "Key must not be null");
            return frequencies.getOrDefault(key, 0);
        }

        private void increaseFrequency(K key) {
            int oldFrequency = frequencies.get(key);
            LinkedHashSet<K> oldBucket = keysByFrequency.get(oldFrequency);
            oldBucket.remove(key);

            if (oldBucket.isEmpty()) {
                keysByFrequency.remove(oldFrequency);
                if (minimumFrequency == oldFrequency) {
                    minimumFrequency++;
                }
            }

            int newFrequency = oldFrequency + 1;
            frequencies.put(key, newFrequency);
            keysByFrequency
                    .computeIfAbsent(newFrequency,
                            ignored -> new LinkedHashSet<>())
                    .add(key);
        }

        private void refreshMinimumFrequency() {
            minimumFrequency = 0;
            for (int frequency : keysByFrequency.keySet()) {
                if (minimumFrequency == 0 || frequency < minimumFrequency) {
                    minimumFrequency = frequency;
                }
            }
        }
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    public static void main(String[] args) {
        LFUCache<Integer, String> cache = new LFUCache<>(2);
        cache.put(1, "one");
        cache.put(2, "two");

        check("one".equals(cache.get(1)), "Key 1 should be present");
        check(cache.frequencyOf(1) == 2, "Key 1 frequency should be 2");

        cache.put(3, "three"); // Evicts key 2 (frequency 1).
        check(cache.get(2) == null, "Key 2 should have been evicted");
        check("three".equals(cache.get(3)), "Key 3 should be present");

        // Keys 1 and 3 now both have frequency 2. Key 1 entered that
        // frequency bucket first, so it is the least recently used tie.
        cache.put(4, "four");
        check(cache.get(1) == null, "Key 1 should lose the LFU/LRU tie-break");
        check("three".equals(cache.get(3)), "Key 3 should remain present");
        check("four".equals(cache.get(4)), "Key 4 should be present");

        check(cache.remove(3), "Existing key should be removed");
        check(!cache.remove(99), "Missing key should not be removed");
        check(cache.size() == 1, "Cache should contain one entry");

        cache.clear();
        check(cache.isEmpty(), "Cache should be empty after clear");

        System.out.println("All LFU cache checks passed.");
    }
}
