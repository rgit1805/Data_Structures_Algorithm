package strings;

public class Day70PalindromePartitioningII {

    /*
     * Palindrome Partitioning II
     *
     * Find the minimum number of cuts needed to partition a string
     * so that every resulting substring is a palindrome.
     *
     * Examples:
     *   "aab"   -> 1  ("aa" | "b")
     *   "a"     -> 0
     *   "ab"    -> 1
     *   "aba"   -> 0
     *
     * Core DP idea:
     *
     * pal[i][j] = whether text[i..j] is a palindrome.
     *
     * Then:
     * cuts[i] = minimum cuts needed for text[0..i].
     *
     * If text[j..i] is a palindrome:
     *   - if j == 0, no cut is needed;
     *   - otherwise cuts[i] = min(cuts[i], cuts[j - 1] + 1).
     *
     * This problem combines two DP layers:
     *   1. Palindrome recognition.
     *   2. Minimum partition optimization.
     */

    public static int minCutRecursive(String text) {
        validate(text);

        if (text.length() <= 1) {
            return 0;
        }

        Boolean[][] palindromeMemo =
                new Boolean[text.length()][text.length()];

        Integer[] cutsMemo =
                new Integer[text.length()];

        return solveRecursive(
                text,
                0,
                palindromeMemo,
                cutsMemo
        );
    }

    private static int solveRecursive(
            String text,
            int start,
            Boolean[][] palindromeMemo,
            Integer[] cutsMemo) {

        if (start == text.length()) {
            return -1;
        }

        if (cutsMemo[start] != null) {
            return cutsMemo[start];
        }

        int best = Integer.MAX_VALUE;

        for (int end = start; end < text.length(); end++) {
            if (isPalindrome(
                    text,
                    start,
                    end,
                    palindromeMemo)) {

                int cutsAfter =
                        solveRecursive(
                                text,
                                end + 1,
                                palindromeMemo,
                                cutsMemo
                        );

                best = Math.min(best, cutsAfter + 1);
            }
        }

        cutsMemo[start] = best;
        return best;
    }

    /*
     * Bottom-up DP.
     */
    public static int minCut(String text) {
        validate(text);

        int n = text.length();

        if (n <= 1) {
            return 0;
        }

        boolean[][] palindrome =
                buildPalindromeTable(text);

        int[] cuts = new int[n];

        for (int i = 0; i < n; i++) {
            cuts[i] = i;

            for (int start = 0; start <= i; start++) {
                if (palindrome[start][i]) {
                    if (start == 0) {
                        cuts[i] = 0;
                    } else {
                        cuts[i] = Math.min(
                                cuts[i],
                                cuts[start - 1] + 1
                        );
                    }
                }
            }
        }

        return cuts[n - 1];
    }

    /*
     * O(n) auxiliary-space DP.
     *
     * We expand around every possible palindrome center.
     * Whenever text[left..right] is a palindrome, we know a valid
     * partition ending at 'right'.
     *
     * This avoids storing the complete O(n^2) palindrome table.
     */
    public static int minCutOptimized(String text) {
        validate(text);

        int n = text.length();

        if (n <= 1) {
            return 0;
        }

        int[] cuts = new int[n];

        for (int i = 0; i < n; i++) {
            cuts[i] = i;
        }

        for (int center = 0; center < n; center++) {
            expandAndUpdateCuts(
                    text,
                    center,
                    center,
                    cuts
            );

            expandAndUpdateCuts(
                    text,
                    center,
                    center + 1,
                    cuts
            );
        }

        return cuts[n - 1];
    }

    private static void expandAndUpdateCuts(
            String text,
            int left,
            int right,
            int[] cuts) {

        while (left >= 0
                && right < text.length()
                && text.charAt(left) == text.charAt(right)) {

            if (left == 0) {
                cuts[right] = 0;
            } else {
                cuts[right] = Math.min(
                        cuts[right],
                        cuts[left - 1] + 1
                );
            }

            left--;
            right++;
        }
    }

    private static boolean[][] buildPalindromeTable(
            String text) {

        int n = text.length();

        boolean[][] palindrome =
                new boolean[n][n];

        for (int length = 1; length <= n; length++) {
            for (int start = 0;
                 start + length <= n;
                 start++) {

                int end = start + length - 1;

                if (text.charAt(start)
                        == text.charAt(end)
                        && (length <= 2
                        || palindrome[start + 1][end - 1])) {

                    palindrome[start][end] = true;
                }
            }
        }

        return palindrome;
    }

    private static boolean isPalindrome(
            String text,
            int left,
            int right,
            Boolean[][] memo) {

        if (left >= right) {
            return true;
        }

        if (memo[left][right] != null) {
            return memo[left][right];
        }

        memo[left][right] =
                text.charAt(left) == text.charAt(right)
                        && isPalindrome(
                                text,
                                left + 1,
                                right - 1,
                                memo
                        );

        return memo[left][right];
    }

    private static void validate(String text) {
        if (text == null) {
            throw new IllegalArgumentException(
                    "Input string must not be null."
            );
        }
    }

    public static void main(String[] args) {
        System.out.println(
                "aab: " + minCut("aab")
        ); // 1

        System.out.println(
                "a: " + minCut("a")
        ); // 0

        System.out.println(
                "ab: " + minCut("ab")
        ); // 1

        System.out.println(
                "aba: " + minCut("aba")
        ); // 0

        System.out.println(
                "aab: optimized = "
                        + minCutOptimized("aab")
        ); // 1

        System.out.println(
                "aabbc: optimized = "
                        + minCutOptimized("aabbc")
        ); // 2
    }
}
