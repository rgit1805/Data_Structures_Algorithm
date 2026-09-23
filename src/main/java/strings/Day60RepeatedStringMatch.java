package strings;

import java.util.Arrays;

public class Day60RepeatedStringMatch {

    /*
     * Problem:
     * Find the minimum number of times string 'a' must be repeated so that
     * string 'b' becomes a substring of the repeated string.
     *
     * Example:
     * a = "abcd", b = "cdabcdab" -> 3
     *
     * Key observation:
     * If b can occur, we only need enough copies of a to cover b.
     * At most one additional copy is needed because a match can cross
     * the boundary between two copies.
     */

    public static int bruteForce(String a, String b) {
        validate(a, b);

        if (b.isEmpty()) {
            return 0;
        }

        StringBuilder repeated = new StringBuilder();
        int repeats = 0;

        while (repeated.length() < b.length()) {
            repeated.append(a);
            repeats++;
        }

        if (containsBruteForce(repeated.toString(), b)) {
            return repeats;
        }

        repeated.append(a);

        if (containsBruteForce(repeated.toString(), b)) {
            return repeats + 1;
        }

        return -1;
    }

    /*
     * KMP-based solution.
     *
     * Instead of constructing a potentially large repeated String,
     * KMP accesses the repeated string virtually:
     *
     * repeated[i] = a[i % a.length()]
     *
     * This avoids unnecessary memory allocation.
     */
    public static int repeatedStringMatch(String a, String b) {
        validate(a, b);

        if (b.isEmpty()) {
            return 0;
        }

        int repeats = (b.length() + a.length() - 1) / a.length();

        if (kmpInRepeatedString(a, b, repeats)) {
            return repeats;
        }

        if (kmpInRepeatedString(a, b, repeats + 1)) {
            return repeats + 1;
        }

        return -1;
    }

    private static boolean containsBruteForce(String text, String pattern) {
        if (pattern.length() > text.length()) {
            return false;
        }

        for (int start = 0; start <= text.length() - pattern.length(); start++) {
            int j = 0;

            while (j < pattern.length()
                    && text.charAt(start + j) == pattern.charAt(j)) {
                j++;
            }

            if (j == pattern.length()) {
                return true;
            }
        }

        return false;
    }

    private static boolean kmpInRepeatedString(
            String a,
            String pattern,
            int repeats) {

        int[] lps = buildLps(pattern);
        int patternIndex = 0;
        int totalLength = repeats * a.length();

        for (int textIndex = 0; textIndex < totalLength; textIndex++) {
            char current = a.charAt(textIndex % a.length());

            while (patternIndex > 0
                    && current != pattern.charAt(patternIndex)) {
                patternIndex = lps[patternIndex - 1];
            }

            if (current == pattern.charAt(patternIndex)) {
                patternIndex++;

                if (patternIndex == pattern.length()) {
                    return true;
                }
            }
        }

        return false;
    }

    private static int[] buildLps(String pattern) {
        int[] lps = new int[pattern.length()];

        int length = 0;
        int i = 1;

        while (i < pattern.length()) {
            if (pattern.charAt(i) == pattern.charAt(length)) {
                lps[i] = ++length;
                i++;
            } else if (length > 0) {
                length = lps[length - 1];
            } else {
                lps[i] = 0;
                i++;
            }
        }

        return lps;
    }

    private static void validate(String a, String b) {
        if (a == null || b == null) {
            throw new IllegalArgumentException("Strings must not be null.");
        }

        if (a.isEmpty()) {
            throw new IllegalArgumentException("String 'a' must not be empty.");
        }
    }

    public static void main(String[] args) {
        System.out.println("Brute force: "
                + bruteForce("abcd", "cdabcdab")); // 3

        System.out.println("KMP: "
                + repeatedStringMatch("abcd", "cdabcdab")); // 3

        System.out.println("KMP: "
                + repeatedStringMatch("a", "aa")); // 2

        System.out.println("KMP: "
                + repeatedStringMatch("a", "a")); // 1

        System.out.println("KMP: "
                + repeatedStringMatch("abc", "cabca")); // 3

        System.out.println("KMP: "
                + repeatedStringMatch("abc", "xyz")); // -1

        System.out.println("LPS for ababaca: "
                + Arrays.toString(buildLps("ababaca")));
    }
}
