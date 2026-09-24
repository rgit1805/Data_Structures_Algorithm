package strings;

public class Day63EditDistance {

    /*
     * Problem:
     * Find the minimum number of insertions, deletions, and replacements
     * needed to transform word1 into word2.
     *
     * Example:
     * "horse" -> "ros" = 3
     * "intention" -> "execution" = 5
     *
     * DP state:
     * dp[i][j] = minimum operations needed to transform
     *            first i characters of word1 into first j characters of word2.
     */

    public static int editDistanceRecursive(String word1, String word2) {
        validate(word1, word2);
        return solveRecursive(word1, word2, word1.length(), word2.length());
    }

    private static int solveRecursive(
            String word1,
            String word2,
            int i,
            int j) {

        if (i == 0) {
            return j;
        }

        if (j == 0) {
            return i;
        }

        if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
            return solveRecursive(word1, word2, i - 1, j - 1);
        }

        int insert = solveRecursive(word1, word2, i, j - 1);
        int delete = solveRecursive(word1, word2, i - 1, j);
        int replace = solveRecursive(word1, word2, i - 1, j - 1);

        return 1 + Math.min(insert, Math.min(delete, replace));
    }

    /*
     * Bottom-up dynamic programming.
     *
     * Transition when characters differ:
     *
     * dp[i][j] = 1 + min(
     *     dp[i][j - 1],     // insert
     *     dp[i - 1][j],     // delete
     *     dp[i - 1][j - 1]  // replace
     * )
     */
    public static int editDistance(String word1, String word2) {
        validate(word1, word2);

        int m = word1.length();
        int n = word2.length();

        int[][] dp = new int[m + 1][n + 1];

        for (int i = 0; i <= m; i++) {
            dp[i][0] = i;
        }

        for (int j = 0; j <= n; j++) {
            dp[0][j] = j;
        }

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {

                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    int insert = dp[i][j - 1];
                    int delete = dp[i - 1][j];
                    int replace = dp[i - 1][j - 1];

                    dp[i][j] = 1 + Math.min(
                            insert,
                            Math.min(delete, replace)
                    );
                }
            }
        }

        return dp[m][n];
    }

    /*
     * Space-optimized DP.
     *
     * Only the previous row and current row are required.
     */
    public static int editDistanceOptimized(String word1, String word2) {
        validate(word1, word2);

        // Keep word2 as the shorter string to reduce memory usage.
        if (word2.length() > word1.length()) {
            return editDistanceOptimized(word2, word1);
        }

        int m = word1.length();
        int n = word2.length();

        int[] previous = new int[n + 1];
        int[] current = new int[n + 1];

        for (int j = 0; j <= n; j++) {
            previous[j] = j;
        }

        for (int i = 1; i <= m; i++) {
            current[0] = i;

            for (int j = 1; j <= n; j++) {
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    current[j] = previous[j - 1];
                } else {
                    int insert = current[j - 1];
                    int delete = previous[j];
                    int replace = previous[j - 1];

                    current[j] = 1 + Math.min(
                            insert,
                            Math.min(delete, replace)
                    );
                }
            }

            int[] temp = previous;
            previous = current;
            current = temp;
        }

        return previous[n];
    }

    private static void validate(String word1, String word2) {
        if (word1 == null || word2 == null) {
            throw new IllegalArgumentException(
                    "Input strings must not be null."
            );
        }
    }

    public static void main(String[] args) {
        System.out.println("horse -> ros: "
                + editDistance("horse", "ros")); // 3

        System.out.println("intention -> execution: "
                + editDistance("intention", "execution")); // 5

        System.out.println("same -> same: "
                + editDistance("same", "same")); // 0

        System.out.println(" -> abc: "
                + editDistance("", "abc")); // 3

        System.out.println("abc -> : "
                + editDistance("abc", "")); // 3

        System.out.println("kitten -> sitting (optimized): "
                + editDistanceOptimized("kitten", "sitting")); // 3

        System.out.println("flaw -> lawn (optimized): "
                + editDistanceOptimized("flaw", "lawn")); // 2
    }
}
