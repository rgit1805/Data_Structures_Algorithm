package strings;

public class Day69InterleavingString {

    /*
     * Interleaving String
     *
     * Given s1, s2 and s3, determine whether s3 can be formed by
     * interleaving s1 and s2 while preserving the relative order
     * of characters from each original string.
     *
     * Example:
     * s1 = "aabcc"
     * s2 = "dbbca"
     * s3 = "aadbbcbcac" -> true
     *
     * DP state:
     * dp[i][j] = whether the first i characters of s1 and the
     *            first j characters of s2 can form the first i+j
     *            characters of s3.
     *
     * At each state we have at most two choices:
     *
     * 1. Take the next character from s1.
     * 2. Take the next character from s2.
     *
     * We never reorder characters inside s1 or s2.
     */

    public static boolean isInterleaveRecursive(
            String s1,
            String s2,
            String s3) {

        validate(s1, s2, s3);

        if (s1.length() + s2.length() != s3.length()) {
            return false;
        }

        return solveRecursive(s1, s2, s3, 0, 0);
    }

    private static boolean solveRecursive(
            String s1,
            String s2,
            String s3,
            int i,
            int j) {

        int k = i + j;

        if (k == s3.length()) {
            return true;
        }

        boolean takeFromS1 =
                i < s1.length()
                        && s1.charAt(i) == s3.charAt(k)
                        && solveRecursive(s1, s2, s3, i + 1, j);

        if (takeFromS1) {
            return true;
        }

        return j < s2.length()
                && s2.charAt(j) == s3.charAt(k)
                && solveRecursive(s1, s2, s3, i, j + 1);
    }

    /*
     * Bottom-up 2D DP.
     */
    public static boolean isInterleave(
            String s1,
            String s2,
            String s3) {

        validate(s1, s2, s3);

        if (s1.length() + s2.length() != s3.length()) {
            return false;
        }

        int m = s1.length();
        int n = s2.length();

        boolean[][] dp = new boolean[m + 1][n + 1];
        dp[0][0] = true;

        for (int i = 1; i <= m; i++) {
            dp[i][0] =
                    dp[i - 1][0]
                            && s1.charAt(i - 1) == s3.charAt(i - 1);
        }

        for (int j = 1; j <= n; j++) {
            dp[0][j] =
                    dp[0][j - 1]
                            && s2.charAt(j - 1) == s3.charAt(j - 1);
        }

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {

                int k = i + j - 1;

                boolean fromS1 =
                        dp[i - 1][j]
                                && s1.charAt(i - 1) == s3.charAt(k);

                boolean fromS2 =
                        dp[i][j - 1]
                                && s2.charAt(j - 1) == s3.charAt(k);

                dp[i][j] = fromS1 || fromS2;
            }
        }

        return dp[m][n];
    }

    /*
     * Space-optimized DP.
     *
     * dp[j] represents the current row.
     */
    public static boolean isInterleaveOptimized(
            String s1,
            String s2,
            String s3) {

        validate(s1, s2, s3);

        if (s1.length() + s2.length() != s3.length()) {
            return false;
        }

        // Keep s2 as the shorter string to reduce memory.
        if (s2.length() > s1.length()) {
            return isInterleaveOptimized(s2, s1, s3);
        }

        int m = s1.length();
        int n = s2.length();

        boolean[] dp = new boolean[n + 1];
        dp[0] = true;

        for (int j = 1; j <= n; j++) {
            dp[j] =
                    dp[j - 1]
                            && s2.charAt(j - 1) == s3.charAt(j - 1);
        }

        for (int i = 1; i <= m; i++) {
            dp[0] =
                    dp[0]
                            && s1.charAt(i - 1) == s3.charAt(i - 1);

            for (int j = 1; j <= n; j++) {
                int k = i + j - 1;

                boolean fromS1 =
                        dp[j]
                                && s1.charAt(i - 1) == s3.charAt(k);

                boolean fromS2 =
                        dp[j - 1]
                                && s2.charAt(j - 1) == s3.charAt(k);

                dp[j] = fromS1 || fromS2;
            }
        }

        return dp[n];
    }

    private static void validate(
            String s1,
            String s2,
            String s3) {

        if (s1 == null || s2 == null || s3 == null) {
            throw new IllegalArgumentException(
                    "Input strings must not be null."
            );
        }
    }

    public static void main(String[] args) {
        System.out.println(
                "aabcc + dbbca -> aadbbcbcac: "
                        + isInterleave(
                                "aabcc",
                                "dbbca",
                                "aadbbcbcac"
                        )
        ); // true

        System.out.println(
                "aabcc + dbbca -> aadbbbaccc: "
                        + isInterleave(
                                "aabcc",
                                "dbbca",
                                "aadbbbaccc"
                        )
        ); // false

        System.out.println(
                "a + b -> ab: "
                        + isInterleave("a", "b", "ab")
        ); // true

        System.out.println(
                "a + b -> ba: "
                        + isInterleave("a", "b", "ba")
        ); // true

        System.out.println(
                "abc + def -> abdecf (optimized): "
                        + isInterleaveOptimized(
                                "abc",
                                "def",
                                "abdecf"
                        )
        ); // true

        System.out.println(
                "abc + def -> abdfec (optimized): "
                        + isInterleaveOptimized(
                                "abc",
                                "def",
                                "abdfec"
                        )
        ); // false
    }
}
