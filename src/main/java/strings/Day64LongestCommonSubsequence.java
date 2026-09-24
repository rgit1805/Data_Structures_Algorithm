package strings;

public class Day64LongestCommonSubsequence {

    /*
     * Problem:
     * Find the length of the Longest Common Subsequence (LCS)
     * of two strings.
     *
     * A subsequence keeps the relative order of characters,
     * but characters do not have to be contiguous.
     *
     * Example:
     * "abcde" and "ace" -> 3 ("ace")
     *
     * DP state:
     * dp[i][j] = LCS length between the first i characters of text1
     *            and the first j characters of text2.
     */

    public static int lcsRecursive(String text1, String text2) {
        validate(text1, text2);
        return solveRecursive(text1, text2, text1.length(), text2.length());
    }

    private static int solveRecursive(
            String text1,
            String text2,
            int i,
            int j) {

        if (i == 0 || j == 0) {
            return 0;
        }

        if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
            return 1 + solveRecursive(text1, text2, i - 1, j - 1);
        }

        return Math.max(
                solveRecursive(text1, text2, i - 1, j),
                solveRecursive(text1, text2, i, j - 1)
        );
    }

    /*
     * Bottom-up DP.
     *
     * If characters match:
     *     dp[i][j] = 1 + dp[i - 1][j - 1]
     *
     * Otherwise:
     *     dp[i][j] = max(dp[i - 1][j], dp[i][j - 1])
     */
    public static int lcs(String text1, String text2) {
        validate(text1, text2);

        int m = text1.length();
        int n = text2.length();

        int[][] dp = new int[m + 1][n + 1];

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                    dp[i][j] = 1 + dp[i - 1][j - 1];
                } else {
                    dp[i][j] = Math.max(
                            dp[i - 1][j],
                            dp[i][j - 1]
                    );
                }
            }
        }

        return dp[m][n];
    }

    /*
     * Space-optimized LCS.
     *
     * Only the previous row is required to calculate the current row.
     */
    public static int lcsOptimized(String text1, String text2) {
        validate(text1, text2);

        // Use the shorter string for the DP array.
        if (text2.length() > text1.length()) {
            return lcsOptimized(text2, text1);
        }

        int m = text1.length();
        int n = text2.length();

        int[] previous = new int[n + 1];
        int[] current = new int[n + 1];

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                    current[j] = 1 + previous[j - 1];
                } else {
                    current[j] = Math.max(
                            previous[j],
                            current[j - 1]
                    );
                }
            }

            int[] temp = previous;
            previous = current;
            current = temp;
        }

        return previous[n];
    }

    /*
     * Reconstruct one actual LCS, not just its length.
     */
    public static String getLcs(String text1, String text2) {
        validate(text1, text2);

        int m = text1.length();
        int n = text2.length();

        int[][] dp = new int[m + 1][n + 1];

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                    dp[i][j] = 1 + dp[i - 1][j - 1];
                } else {
                    dp[i][j] = Math.max(
                            dp[i - 1][j],
                            dp[i][j - 1]
                    );
                }
            }
        }

        StringBuilder lcs = new StringBuilder();

        int i = m;
        int j = n;

        while (i > 0 && j > 0) {
            if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                lcs.append(text1.charAt(i - 1));
                i--;
                j--;
            } else if (dp[i - 1][j] >= dp[i][j - 1]) {
                i--;
            } else {
                j--;
            }
        }

        return lcs.reverse().toString();
    }

    private static void validate(String text1, String text2) {
        if (text1 == null || text2 == null) {
            throw new IllegalArgumentException(
                    "Input strings must not be null."
            );
        }
    }

    public static void main(String[] args) {
        System.out.println("LCS length (abcde, ace): "
                + lcs("abcde", "ace")); // 3

        System.out.println("LCS: "
                + getLcs("abcde", "ace")); // ace

        System.out.println("LCS length (abc, abc): "
                + lcs("abc", "abc")); // 3

        System.out.println("LCS length (abc, def): "
                + lcs("abc", "def")); // 0

        System.out.println("LCS length (AGGTAB, GXTXAYB): "
                + lcsOptimized("AGGTAB", "GXTXAYB")); // 4

        System.out.println("One LCS (AGGTAB, GXTXAYB): "
                + getLcs("AGGTAB", "GXTXAYB")); // GTAB
    }
}
