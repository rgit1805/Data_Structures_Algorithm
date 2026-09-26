package strings;

public class Day67WildcardMatching {

    /*
     * Wildcard Matching
     *
     * Pattern rules:
     *   '?' matches exactly one character.
     *   '*' matches any sequence of characters, including empty.
     *
     * Examples:
     *   "aa" / "a"     -> false
     *   "aa" / "*"     -> true
     *   "cb" / "?a"    -> false
     *   "adceb" / "*a*b" -> true
     *
     * DP state:
     * dp[i][j] = whether the first i characters of text
     *            match the first j characters of pattern.
     *
     * If pattern[j - 1] is a normal character:
     *     it must equal text[i - 1].
     *
     * If it is '?':
     *     it matches exactly one character.
     *
     * If it is '*':
     *     Two choices:
     *       1. '*' matches empty      -> dp[i][j - 1]
     *       2. '*' consumes one char  -> dp[i - 1][j]
     */

    public static boolean isMatchRecursive(String text, String pattern) {
        validate(text, pattern);
        return solveRecursive(text, pattern, text.length(), pattern.length());
    }

    private static boolean solveRecursive(
            String text,
            String pattern,
            int i,
            int j) {

        if (i == 0 && j == 0) {
            return true;
        }

        if (j == 0) {
            return false;
        }

        if (i == 0) {
            return allStars(pattern, j);
        }

        char current = pattern.charAt(j - 1);

        if (current == '*') {
            return solveRecursive(text, pattern, i, j - 1)
                    || solveRecursive(text, pattern, i - 1, j);
        }

        if (current == '?' || current == text.charAt(i - 1)) {
            return solveRecursive(text, pattern, i - 1, j - 1);
        }

        return false;
    }

    /*
     * Bottom-up DP.
     */
    public static boolean isMatch(String text, String pattern) {
        validate(text, pattern);

        int m = text.length();
        int n = pattern.length();

        boolean[][] dp = new boolean[m + 1][n + 1];
        dp[0][0] = true;

        // An empty text can only match a pattern consisting entirely of '*'.
        for (int j = 1; j <= n; j++) {
            dp[0][j] = dp[0][j - 1]
                    && pattern.charAt(j - 1) == '*';
        }

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {

                char current = pattern.charAt(j - 1);

                if (current == '*') {
                    // '*' matches empty OR consumes one text character.
                    dp[i][j] = dp[i][j - 1] || dp[i - 1][j];
                } else if (
                        current == '?'
                                || current == text.charAt(i - 1)
                ) {
                    dp[i][j] = dp[i - 1][j - 1];
                }
            }
        }

        return dp[m][n];
    }

    /*
     * Space-optimized DP.
     *
     * We only need the previous row and current row.
     */
    public static boolean isMatchOptimized(
            String text,
            String pattern) {

        validate(text, pattern);

        int m = text.length();
        int n = pattern.length();

        boolean[] previous = new boolean[n + 1];
        boolean[] current = new boolean[n + 1];

        previous[0] = true;

        for (int j = 1; j <= n; j++) {
            previous[j] = previous[j - 1]
                    && pattern.charAt(j - 1) == '*';
        }

        for (int i = 1; i <= m; i++) {
            current[0] = false;

            for (int j = 1; j <= n; j++) {
                char patternChar = pattern.charAt(j - 1);

                if (patternChar == '*') {
                    current[j] = current[j - 1] || previous[j];
                } else if (
                        patternChar == '?'
                                || patternChar == text.charAt(i - 1)
                ) {
                    current[j] = previous[j - 1];
                } else {
                    current[j] = false;
                }
            }

            boolean[] temp = previous;
            previous = current;
            current = temp;
        }

        return previous[n];
    }

    private static boolean allStars(String pattern, int length) {
        for (int j = 0; j < length; j++) {
            if (pattern.charAt(j) != '*') {
                return false;
            }
        }

        return true;
    }

    private static void validate(String text, String pattern) {
        if (text == null || pattern == null) {
            throw new IllegalArgumentException(
                    "Text and pattern must not be null."
            );
        }
    }

    public static void main(String[] args) {
        System.out.println(
                "aa / a: "
                        + isMatch("aa", "a")
        ); // false

        System.out.println(
                "aa / *: "
                        + isMatch("aa", "*")
        ); // true

        System.out.println(
                "cb / ?a: "
                        + isMatch("cb", "?a")
        ); // false

        System.out.println(
                "adceb / *a*b: "
                        + isMatch("adceb", "*a*b")
        ); // true

        System.out.println(
                "acdcb / a*c?b: "
                        + isMatch("acdcb", "a*c?b")
        ); // false

        System.out.println(
                "empty / ***: "
                        + isMatch("", "***")
        ); // true

        System.out.println(
                "optimized abcdef / a*?f: "
                        + isMatchOptimized("abcdef", "a*?f")
        ); // true
    }
}
