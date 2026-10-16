package design;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Day 91: Time-Based Key-Value Store.
 *
 * Each key maps to a list of values ordered by timestamp. get(key, timestamp)
 * uses binary search to return the value associated with the greatest
 * timestamp less than or equal to the requested timestamp.
 *
 * Contract: timestamps supplied to set() for a given key must be non-decreasing.
 * Multiple writes at the same timestamp are allowed; the latest write at that
 * timestamp is returned.
 */
public class Day91TimeBasedKeyValueStore {

    public static final class TimeMap {
        private static final class Entry {
            private final int timestamp;
            private final String value;

            private Entry(int timestamp, String value) {
                this.timestamp = timestamp;
                this.value = value;
            }
        }

        private final Map<String, List<Entry>> history = new HashMap<>();

        /**
         * Stores a value for a key at a timestamp.
         * Average time: O(1) amortized per write.
         */
        public void set(String key, String value, int timestamp) {
            Objects.requireNonNull(key, "Key must not be null");
            Objects.requireNonNull(value, "Value must not be null");

            List<Entry> entries = history.computeIfAbsent(
                    key, ignored -> new ArrayList<>());

            if (!entries.isEmpty()) {
                int lastTimestamp = entries.get(entries.size() - 1).timestamp;
                if (timestamp < lastTimestamp) {
                    throw new IllegalArgumentException(
                            "Timestamps for a key must be non-decreasing");
                }

                // A write at the same timestamp replaces the previous value.
                if (timestamp == lastTimestamp) {
                    entries.set(entries.size() - 1, new Entry(timestamp, value));
                    return;
                }
            }

            entries.add(new Entry(timestamp, value));
        }

        /**
         * Returns the value whose timestamp is the greatest timestamp <= query.
         * Returns an empty string when the key has no eligible value.
         * Time: O(log m), where m is the number of entries for this key.
         */
        public String get(String key, int timestamp) {
            Objects.requireNonNull(key, "Key must not be null");
            List<Entry> entries = history.get(key);

            if (entries == null || entries.isEmpty()) {
                return "";
            }

            int low = 0;
            int high = entries.size() - 1;
            int answer = -1;

            while (low <= high) {
                int mid = low + (high - low) / 2;

                if (entries.get(mid).timestamp <= timestamp) {
                    answer = mid;
                    low = mid + 1; // Search for a later eligible timestamp.
                } else {
                    high = mid - 1;
                }
            }

            return answer == -1 ? "" : entries.get(answer).value;
        }

        public int numberOfKeys() {
            return history.size();
        }

        public int numberOfVersions(String key) {
            Objects.requireNonNull(key, "Key must not be null");
            List<Entry> entries = history.get(key);
            return entries == null ? 0 : entries.size();
        }

        public void clear() {
            history.clear();
        }
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    public static void main(String[] args) {
        TimeMap timeMap = new TimeMap();

        timeMap.set("course", "Java", 1);
        timeMap.set("course", "DSA", 4);
        timeMap.set("course", "System Design", 9);

        check("Java".equals(timeMap.get("course", 1)),
                "Timestamp 1 should return Java");
        check("Java".equals(timeMap.get("course", 3)),
                "Timestamp 3 should return the most recent earlier value");
        check("DSA".equals(timeMap.get("course", 8)),
                "Timestamp 8 should return DSA");
        check("System Design".equals(timeMap.get("course", 10)),
                "Timestamp 10 should return System Design");
        check("".equals(timeMap.get("missing", 10)),
                "Missing keys should return an empty string");
        check("".equals(timeMap.get("course", 0)),
                "No earlier timestamp should return an empty string");

        // Same timestamp replaces the prior value rather than adding a version.
        timeMap.set("course", "Advanced DSA", 9);
        check("Advanced DSA".equals(timeMap.get("course", 9)),
                "Latest write at equal timestamp should be returned");
        check(timeMap.numberOfVersions("course") == 3,
                "Equal timestamp should replace, not append");

        boolean rejectedOutOfOrderTimestamp = false;
        try {
            timeMap.set("course", "Out of order", 7);
        } catch (IllegalArgumentException expected) {
            rejectedOutOfOrderTimestamp = true;
        }
        check(rejectedOutOfOrderTimestamp,
                "Out-of-order timestamp should be rejected");

        System.out.println("All time-based key-value store checks passed.");
    }
}
