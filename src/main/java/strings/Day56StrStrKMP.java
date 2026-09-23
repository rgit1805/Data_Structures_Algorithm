package strings;

public class Day56StrStrKMP {

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

    public static int kmp(String text, String pattern) {
        validate(text, pattern);

        if (pattern.isEmpty()) {
            return 0;
        }
        if (pattern.length() > text.length()) {
            return -1;
        }

        int[] lps = buildLps(pattern);
        int textIndex = 0;
        int patternIndex = 0;

        while (textIndex < text.length()) {
            if (text.charAt(textIndex) == pattern.charAt(patternIndex)) {
                textIndex++;
                patternIndex++;

                if (patternIndex == pattern.length()) {
                    return textIndex - patternIndex;
                }
            } else if (patternIndex > 0) {
                patternIndex = lps[patternIndex - 1];
            } else {
                textIndex++;
            }
        }

        return -1;
    }

    public static int[] buildLps(String pattern) {
        if (pattern == null) {
            throw new IllegalArgumentException("Pattern must not be null.");
        }

        int[] lps = new int[pattern.length()];
        int len = 0;
        int i = 1;

        while (i < pattern.length()) {
            if (pattern.charAt(i) == pattern.charAt(len)) {
                lps[i] = ++len;
                i++;
            } else if (len > 0) {
                len = lps[len - 1];
            } else {
                lps[i] = 0;
                i++;
            }
        }

        return lps;
    }

    private static void validate(String text, String pattern) {
        if (text == null || pattern == null) {
            throw new IllegalArgumentException("Text and pattern must not be null.");
        }
    }

    public static void main(String[] args) {
        System.out.println("Brute force: "
                + bruteForce("sadbutsad", "sad")); // 0

        System.out.println("KMP: "
                + kmp("sadbutsad", "sad")); // 0

        System.out.println("KMP: "
                + kmp("leetcode", "leeto")); // -1

        System.out.println("KMP: "
                + kmp("mississippi", "issip")); // 4

        System.out.println("KMP: "
                + kmp("ababababca", "abababca")); // 2

        System.out.print("LPS for ababaca: ");
        printArray(buildLps("ababaca")); // [0, 0, 1, 2, 3, 0, 1]
    }

    private static void printArray(int[] values) {
        System.out.print("[");
        for (int i = 0; i < values.length; i++) {
            if (i > 0) {
                System.out.print(", ");
            }
            System.out.print(values[i]);
        }
        System.out.println("]");
    }
}
