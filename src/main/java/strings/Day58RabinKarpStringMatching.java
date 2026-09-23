package strings;

import java.util.Arrays;

public class Day58RabinKarpStringMatching {

    private static final long BASE = 256;
    private static final long MOD = 1_000_000_007L;

    public static int bruteForce(String text, String pattern) {
        validate(text, pattern);

        if (pattern.isEmpty()) {
            return 0;
        }
        if (pattern.length() > text.length()) {
            return -1;
        }

        for (int start = 0; start <= text.length() - pattern.length(); start++) {
            int j = 0;

            while (j < pattern.length()
                    && text.charAt(start + j) == pattern.charAt(j)) {
                j++;
            }

            if (j == pattern.length()) {
                return start;
            }
        }

        return -1;
    }

    public static int rabinKarp(String text, String pattern) {
        validate(text, pattern);

        if (pattern.isEmpty()) {
            return 0;
        }
        if (pattern.length() > text.length()) {
            return -1;
        }

        int m = pattern.length();

        long patternHash = 0;
        long windowHash = 0;
        long highestPower = 1;

        for (int i = 0; i < m; i++) {
            patternHash = appendHash(patternHash, pattern.charAt(i));
            windowHash = appendHash(windowHash, text.charAt(i));

            if (i < m - 1) {
                highestPower = (highestPower * BASE) % MOD;
            }
        }

        for (int start = 0; start <= text.length() - m; start++) {
            if (patternHash == windowHash
                    && matchesAt(text, pattern, start)) {
                return start;
            }

            if (start < text.length() - m) {
                windowHash = removeLeadingAndAppend(
                        windowHash,
                        text.charAt(start),
                        text.charAt(start + m),
                        highestPower
                );
            }
        }

        return -1;
    }

    public static int[] findAllOccurrences(String text, String pattern) {
        validate(text, pattern);

        if (pattern.isEmpty()) {
            int[] result = new int[text.length() + 1];
            for (int i = 0; i <= text.length(); i++) {
                result[i] = i;
            }
            return result;
        }

        if (pattern.length() > text.length()) {
            return new int[0];
        }

        int m = pattern.length();
        long patternHash = 0;
        long windowHash = 0;
        long highestPower = 1;

        for (int i = 0; i < m; i++) {
            patternHash = appendHash(patternHash, pattern.charAt(i));
            windowHash = appendHash(windowHash, text.charAt(i));

            if (i < m - 1) {
                highestPower = (highestPower * BASE) % MOD;
            }
        }

        int count = 0;

        for (int start = 0; start <= text.length() - m; start++) {
            if (patternHash == windowHash
                    && matchesAt(text, pattern, start)) {
                count++;
            }

            if (start < text.length() - m) {
                windowHash = removeLeadingAndAppend(
                        windowHash,
                        text.charAt(start),
                        text.charAt(start + m),
                        highestPower
                );
            }
        }

        int[] result = new int[count];
        int index = 0;

        patternHash = 0;
        windowHash = 0;

        for (int i = 0; i < m; i++) {
            patternHash = appendHash(patternHash, pattern.charAt(i));
            windowHash = appendHash(windowHash, text.charAt(i));
        }

        for (int start = 0; start <= text.length() - m; start++) {
            if (patternHash == windowHash
                    && matchesAt(text, pattern, start)) {
                result[index++] = start;
            }

            if (start < text.length() - m) {
                windowHash = removeLeadingAndAppend(
                        windowHash,
                        text.charAt(start),
                        text.charAt(start + m),
                        highestPower
                );
            }
        }

        return result;
    }

    private static long appendHash(long hash, char ch) {
        return (hash * BASE + ch) % MOD;
    }

    private static long removeLeadingAndAppend(
            long hash,
            char outgoing,
            char incoming,
            long highestPower) {

        long updated = (hash - outgoing * highestPower) % MOD;

        if (updated < 0) {
            updated += MOD;
        }

        return (updated * BASE + incoming) % MOD;
    }

    private static boolean matchesAt(String text, String pattern, int start) {
        for (int j = 0; j < pattern.length(); j++) {
            if (text.charAt(start + j) != pattern.charAt(j)) {
                return false;
            }
        }
        return true;
    }

    private static void validate(String text, String pattern) {
        if (text == null || pattern == null) {
            throw new IllegalArgumentException("Text and pattern must not be null.");
        }
    }

    public static void main(String[] args) {
        System.out.println("Brute force: "
                + bruteForce("sadbutsad", "sad")); // 0

        System.out.println("Rabin-Karp: "
                + rabinKarp("sadbutsad", "sad")); // 0

        System.out.println("Rabin-Karp: "
                + rabinKarp("mississippi", "issip")); // 4

        System.out.println("Rabin-Karp: "
                + rabinKarp("leetcode", "leeto")); // -1

        System.out.println("All occurrences: "
                + Arrays.toString(
                findAllOccurrences("aaaaa", "aa")
        )); // [0, 1, 2, 3]

        System.out.println("All occurrences: "
                + Arrays.toString(
                findAllOccurrences("abababab", "abab")
        )); // [0, 2, 4]
    }
}
