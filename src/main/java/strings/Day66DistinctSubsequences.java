package strings;

public class Day66DistinctSubsequences {

    /*
     * Distinct Subsequences
     *
     * Given source and target, count how many distinct subsequences
     * of source equal target.
     *
     * Example:
     * source = "rabbbit"
     * target = "rabbit"
     * answer = 3
     *
     * DP state:
     * dp[i][j] = number of ways to form the first j characters
     *            of target using the first i characters of source.
     *
     * If source[i - 1] == target[j - 1]:
     *
     *     We have two choices:
     *     1. Use source[i - 1]  -> dp[i - 1][j - 1]
     *     2. Skip source[i - 1] -> dp[i - 1][j]
     *
     *     dp[i][j] = dp[i - 1][j - 1] + dp[i - 1][j]
     *
     * If they differ:
     *
     *     dp[i][j] = dp[i - 1][j]
     *
     * Important:
     * The answer can become very large, so long is used.
     */

    public static long countDistinctSubsequencesRecursive(
            String source,
            String target) {

        validate(source, target);
        return solveRecursive(
                source,
                target,
                source.length(),
                target.length()
        );
    }

    private static long solveRecursive(
            String source,
            String target,
            int i,
            int j) {

        // Empty target can always be formed by choosing nothing.
        if (j == 0) {
            return 1;
        }

        // Non-empty target cannot be formed from an empty source.
        if (i == 0) {
            return 0;
        }

        if (source.charAt(i - 1) == target.charAt(j - 1)) {
            long useCurrent = solveRecursive(
                    source, target, i - 1, j - 1
            );

            long skipCurrent = solveRecursive(
                    source, target, i - 1, j
            );

            return useCurrent + skipCurrent;
        }

        return solveRecursive(source, target, i - 1, j);
    }

    /*
     * Bottom-up DP.
     *
     * dp[0][0] = 1
     * dp[i][0] = 1 for every i
     * dp[0][j] = 0 for j > 0
     */
    public static long countDistinctSubsequences(
            String source,
            String target) {

        validate(source, target);

        int m = source.length();
        int n = target.length();

        long[][] dp = new long[m + 1][n + 1];

        for (int i = 0; i <= m; i++) {
            dp[i][0] = 1;
        }

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {

                // Skip source[i - 1].
                dp[i][j] = dp[i - 1][j];

                // Optionally use source[i - 1].
                if (source.charAt(i - 1) == target.charAt(j - 1)) {
                    dp[i][j] += dp[i - 1][j - 1];
                }
            }
        }

        return dp[m][n];
    }

    /*
     * Space-optimized DP.
     *
     * j must move from right to left.
     * This preserves dp[j - 1] from the previous row
     * before it gets overwritten.
     */
    public static long countDistinctSubsequencesOptimized(
            String source,
            String target) {

        validate(source, target);

        // Keep target as the shorter dimension for lower memory usage.
        if (target.length() > source.length()) {
            return 0;
        }

        int m = source.length();
        int n = target.length();

        long[] dp = new long[n + 1];
        dp[0] = 1;

        for (int i = 1; i <= m; i++) {
            for (int j = n; j >= 1; j--) {
                if (source.charAt(i - 1) == target.charAt(j - 1)) {
                    dp[j] += dp[j - 1];
                }
            }
        }

        return dp[n];
    }

    private static void validate(String source, String target) {
        if (source == null || target == null) {
            throw new IllegalArgumentException(
                    "Source and target must not be null."
            );
        }
    }

    public static void main(String[] args) {
        System.out.println(
                "rabbbit -> rabbit: "
                        + countDistinctSubsequences(
                                "rabbbit",
                                "rabbit"
                        )
        ); // 3

        System.out.println(
                "babgbag -> bag: "
                        + countDistinctSubsequences(
                                "babgbag",
                                "bag"
                        )
        ); // 5

        System.out.println(
                "abc -> abc: "
                        + countDistinctSubsequences(
                                "abc",
                                "abc"
                        )
        ); // 1

        System.out.println(
                "abc -> : "
                        + countDistinctSubsequences(
                                "abc",
                                ""
                        )
        ); // 1

        System.out.println(
                "abc -> d: "
                        + countDistinctSubsequences(
                                "abc",
                                "d"
                        )
        ); // 0

        System.out.println(
                "optimized babgbag -> bag: "
                        + countDistinctSubsequencesOptimized(
                                "babgbag",
                                "bag"
                        )
        ); // 5
    }
}
