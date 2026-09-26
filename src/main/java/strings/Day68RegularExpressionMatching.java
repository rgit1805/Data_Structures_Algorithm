package strings;

public class Day68RegularExpressionMatching {

    /*
     * Regular Expression Matching
     *
     * Supported pattern rules:
     *   '.' matches exactly one character.
     *   '*' means zero or more occurrences of the character immediately before it.
     *
     * Examples:
     *   "aa" / "a"    -> false
     *   "aa" / "a*"   -> true
     *   "ab" / ".*"   -> true
     *   "aab" / "c*a*b" -> true
     *   "mississippi" / "mis*is*p*." -> false
     *
     * Important distinction from Day 67:
     *   Wildcard '*' stands alone and matches any sequence.
     *   Regex '*' modifies the previous pattern character.
     *
     * DP state:
     * dp[i][j] = whether first i text characters match
     *            first j pattern characters.
     */

    public static boolean isMatchRecursive(String text, String pattern) {
        validate(text, pattern);
        return solveRecursive(text, pattern, 0, 0);
    }

    private static boolean solveRecursive(
            String text,
            String pattern,
            int i,
            int j) {

        if (j == pattern.length()) {
            return i == text.length();
        }

        boolean firstMatch =
                i < text.length()
                        && (pattern.charAt(j) == '.'
                        || pattern.charAt(j) == text.charAt(i));

        // '*' can only appear as a modifier after another pattern character.
        if (j + 1 < pattern.length()
                && pattern.charAt(j + 1) == '*') {

            // Zero occurrences OR consume one matching character.
            return solveRecursive(text, pattern, i, j + 2)
                    || (firstMatch
                    && solveRecursive(text, pattern, i + 1, j));
        }

        return firstMatch
                && solveRecursive(text, pattern, i + 1, j + 1);
    }

    /*
     * Bottom-up DP.
     *
     * For x*:
     *
     * 1. Match zero copies:
     *      dp[i][j] = dp[i][j - 2]
     *
     * 2. If x matches current text character:
     *      dp[i][j] |= dp[i - 1][j]
     *
     * The second case keeps the same pattern so x* can consume
     * another character.
     */
    public static boolean isMatch(String text, String pattern) {
        validate(text, pattern);
        validatePattern(pattern);

        int m = text.length();
        int n = pattern.length();

        boolean[][] dp = new boolean[m + 1][n + 1];
        dp[0][0] = true;

        // Empty text can match patterns such as a*, a*b*, a*b*c*.
        for (int j = 2; j <= n; j += 2) {
            if (pattern.charAt(j - 1) == '*') {
                dp[0][j] = dp[0][j - 2];
            }
        }

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {

                char patternChar = pattern.charAt(j - 1);

                if (patternChar == '*') {
                    // '*' cannot act independently.
                    continue;
                }

                boolean firstMatch =
                        patternChar == '.'
                                || patternChar == text.charAt(i - 1);

                if (j < n && pattern.charAt(j) == '*') {
                    // Zero occurrences of pattern[j - 1].
                    dp[i][j + 1] = dp[i][j - 1];

                    // One or more occurrences.
                    if (firstMatch) {
                        dp[i][j + 1] |= dp[i - 1][j + 1];
                    }
                } else if (firstMatch) {
                    dp[i][j] = dp[i - 1][j - 1];
                }
            }
        }

        return dp[m][n];
    }

    /*
     * Space-optimized DP.
     *
     * The current row depends on the previous row and values
     * two columns behind for x*.
     */
    public static boolean isMatchOptimized(
            String text,
            String pattern) {

        validate(text, pattern);
        validatePattern(pattern);

        int m = text.length();
        int n = pattern.length();

        boolean[] previous = new boolean[n + 1];
        boolean[] current = new boolean[n + 1];

        previous[0] = true;

        for (int j = 2; j <= n; j += 2) {
            if (pattern.charAt(j - 1) == '*') {
                previous[j] = previous[j - 2];
            }
        }

        for (int i = 1; i <= m; i++) {
            current[0] = false;

            for (int j = 1; j <= n; j++) {

                char patternChar = pattern.charAt(j - 1);

                if (patternChar == '*') {
                    current[j] = false;
                    continue;
                }

                boolean firstMatch =
                        patternChar == '.'
                                || patternChar == text.charAt(i - 1);

                if (j < n && pattern.charAt(j) == '*') {
                    // This column represents x*.
                    current[j + 1] = current[j - 1];

                    if (firstMatch) {
                        current[j + 1] |= previous[j + 1];
                    }
                } else if (firstMatch) {
                    current[j] = previous[j - 1];
                }
            }

            boolean[] temp = previous;
            previous = current;
            current = temp;
        }

        return previous[n];
    }

    private static void validate(String text, String pattern) {
        if (text == null || pattern == null) {
            throw new IllegalArgumentException(
                    "Text and pattern must not be null."
            );
        }
    }

    private static void validatePattern(String pattern) {
        if (!pattern.isEmpty() && pattern.charAt(0) == '*') {
            throw new IllegalArgumentException(
                    "Pattern cannot start with '*'."
            );
        }

        for (int i = 0; i < pattern.length(); i++) {
            if (pattern.charAt(i) == '*'
                    && (i == 0 || pattern.charAt(i - 1) == '*')) {
                throw new IllegalArgumentException(
                        "Invalid pattern: '*' must modify a preceding character."
                );
            }
        }
    }

    public static void main(String[] args) {
        System.out.println(
                "aa / a: "
                        + isMatch("aa", "a")
        ); // false

        System.out.println(
                "aa / a*: "
                        + isMatch("aa", "a*")
        ); // true

        System.out.println(
                "ab / .*: "
                        + isMatch("ab", ".*")
        ); // true

        System.out.println(
                "aab / c*a*b: "
                        + isMatch("aab", "c*a*b")
        ); // true

        System.out.println(
                "mississippi / mis*is*p*.: "
                        + isMatch(
                                "mississippi",
                                "mis*is*p*."
                        )
        ); // false

        System.out.println(
                "empty / a*: "
                        + isMatch("", "a*")
        ); // true

        System.out.println(
                "optimized ab / .*: "
                        + isMatchOptimized("ab", ".*")
        ); // true
    }
}
