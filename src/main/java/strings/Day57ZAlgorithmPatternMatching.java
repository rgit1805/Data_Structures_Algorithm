package strings;

public class Day57ZAlgorithmPatternMatching {

    public static int[] buildZArray(String text) {
        if (text == null) {
            throw new IllegalArgumentException("Text must not be null.");
        }

        int n = text.length();
        int[] z = new int[n];

        int left = 0;
        int right = 0;

        for (int i = 1; i < n; i++) {
            if (i <= right) {
                z[i] = Math.min(right - i + 1, z[i - left]);
            }

            while (i + z[i] < n
                    && text.charAt(z[i]) == text.charAt(i + z[i])) {
                z[i]++;
            }

            if (i + z[i] - 1 > right) {
                left = i;
                right = i + z[i] - 1;
            }
        }

        return z;
    }

    public static int firstOccurrence(String text, String pattern) {
        validate(text, pattern);

        if (pattern.isEmpty()) {
            return 0;
        }
        if (pattern.length() > text.length()) {
            return -1;
        }

        String combined = pattern + "#" + text;
        int[] z = buildZArray(combined);
        int patternLength = pattern.length();

        for (int i = patternLength + 1; i < combined.length(); i++) {
            if (z[i] >= patternLength) {
                return i - patternLength - 1;
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

        String combined = pattern + "#" + text;
        int[] z = buildZArray(combined);

        int count = 0;
        for (int i = pattern.length() + 1; i < combined.length(); i++) {
            if (z[i] >= pattern.length()) {
                count++;
            }
        }

        int[] result = new int[count];
        int index = 0;

        for (int i = pattern.length() + 1; i < combined.length(); i++) {
            if (z[i] >= pattern.length()) {
                result[index++] = i - pattern.length() - 1;
            }
        }

        return result;
    }

    private static void validate(String text, String pattern) {
        if (text == null || pattern == null) {
            throw new IllegalArgumentException("Text and pattern must not be null.");
        }
    }

    public static void main(String[] args) {
        System.out.println("Z-array for aaaaa: ");
        printArray(buildZArray("aaaaa")); // [0, 4, 3, 2, 1]

        System.out.println("Z-array for aabxaabxcaabxaabxay: ");
        printArray(buildZArray("aabxaabxcaabxaabxay"));

        System.out.println("First occurrence: "
                + firstOccurrence("sadbutsad", "sad")); // 0

        System.out.println("First occurrence: "
                + firstOccurrence("mississippi", "issip")); // 4

        System.out.println("First occurrence: "
                + firstOccurrence("leetcode", "leeto")); // -1

        System.out.println("All occurrences: ");
        printArray(findAllOccurrences("aaaaa", "aa")); // [0, 1, 2, 3]

        System.out.println("All occurrences: ");
        printArray(findAllOccurrences("abababab", "abab")); // [0, 2, 4]
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
