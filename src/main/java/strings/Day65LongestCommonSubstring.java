package strings;

public class Day65LongestCommonSubstring {

    /*
     * Longest Common Substring
     *
     * Important distinction:
     * - Substring: characters must be contiguous.
     * - Subsequence: characters only need to preserve order.
     *
     * Example:
     * text1 = "abcde"
     * text2 = "abfce"
     *
     * Longest Common Substring = "ab", length 2.
     *
     * DP state:
     * dp[i][j] = length of the longest common substring
     *            ending exactly at text1[i - 1] and text2[j - 1].
     *
     * If characters match:
     *     dp[i][j] = 1 + dp[i - 1][j - 1]
     *
     * If they do not match:
     *     dp[i][j] = 0
     *
     * The reset to 0 is what makes this different from LCS.
     */

    public static int longestCommonSubstring(String text1, String text2) {
        validate(text1, text2);

        int m = text1.length();
        int n = text2.length();

        int[][] dp = new int[m + 1][n + 1];
        int maxLength = 0;

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                    dp[i][j] = 1 + dp[i - 1][j - 1];
                    maxLength = Math.max(maxLength, dp[i][j]);
                }
            }
        }

        return maxLength;
    }

    /*
     * Space-optimized version.
     *
     * We process j from right to left so that previous[j - 1]
     * still represents the previous row when calculating current[j].
     */
    public static int longestCommonSubstringOptimized(
            String text1,
            String text2) {

        validate(text1, text2);

        // Use the shorter string for the DP array.
        if (text2.length() > text1.length()) {
            return longestCommonSubstringOptimized(text2, text1);
        }

        int m = text1.length();
        int n = text2.length();

        int[] dp = new int[n + 1];
        int maxLength = 0;

        for (int i = 1; i <= m; i++) {
            for (int j = n; j >= 1; j--) {
                if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                    dp[j] = 1 + dp[j - 1];
                    maxLength = Math.max(maxLength, dp[j]);
                } else {
                    dp[j] = 0;
                }
            }
        }

        return maxLength;
    }

    /*
     * Reconstruct one actual longest common substring.
     */
    public static String getLongestCommonSubstring(
            String text1,
            String text2) {

        validate(text1, text2);

        int m = text1.length();
        int n = text2.length();

        int[][] dp = new int[m + 1][n + 1];

        int maxLength = 0;
        int endingIndex = 0;

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                    dp[i][j] = 1 + dp[i - 1][j - 1];

                    if (dp[i][j] > maxLength) {
                        maxLength = dp[i][j];
                        endingIndex = i;
                    }
                }
            }
        }

        return text1.substring(
                endingIndex - maxLength,
                endingIndex
        );
    }

    private static void validate(String text1, String text2) {
        if (text1 == null || text2 == null) {
            throw new IllegalArgumentException(
                    "Input strings must not be null."
            );
        }
    }

    public static void main(String[] args) {
        System.out.println(
                "abcde / abfce: "
                        + longestCommonSubstring("abcde", "abfce")
        ); // 2

        System.out.println(
                "abc / abc: "
                        + longestCommonSubstring("abc", "abc")
        ); // 3

        System.out.println(
                "abc / def: "
                        + longestCommonSubstring("abc", "def")
        ); // 0

        System.out.println(
                "abcdxyz / xyzabcd: "
                        + getLongestCommonSubstring("abcdxyz", "xyzabcd")
        ); // abcd or xyz

        System.out.println(
                "GeeksforGeeks / GeeksQuiz (optimized): "
                        + longestCommonSubstringOptimized(
                                "GeeksforGeeks",
                                "GeeksQuiz"
                        )
        ); // 5
    }
}
