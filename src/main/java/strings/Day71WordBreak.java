package strings;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Day 71: Word Break
 *
 * Given a string and a dictionary of words, determine whether the string
 * can be segmented into one or more dictionary words.
 *
 * Example:
 *   "leetcode", ["leet", "code"] -> true
 *   "applepenapple", ["apple", "pen"] -> true
 *   "catsandog", ["cats", "dog", "sand", "and", "cat"] -> false
 *
 * Approaches:
 * 1. Recursive: explores every possible split.
 * 2. Memoized recursion: caches suffix results.
 * 3. Bottom-up DP: dp[i] means text[0..i) can be segmented.
 *
 * Engineering note:
 * Using a HashSet gives average O(1) membership lookup, but creating
 * substrings still costs time and memory. The bottom-up solution therefore
 * optionally limits checks using the maximum dictionary word length.
 */
public class Day71WordBreak {

    /**
     * Brute-force recursive solution.
     *
     * Time: O(2^n) in the worst case.
     * Space: O(n) recursion depth.
     */
    public static boolean wordBreakRecursive(String text, List<String> wordDict) {
        validateInput(text, wordDict);

        Set<String> dictionary = new HashSet<>(wordDict);
        return canBreakRecursive(text, 0, dictionary);
    }

    private static boolean canBreakRecursive(
            String text,
            int start,
            Set<String> dictionary
    ) {
        if (start == text.length()) {
            return true;
        }

        for (int end = start + 1; end <= text.length(); end++) {
            if (dictionary.contains(text.substring(start, end))
                    && canBreakRecursive(text, end, dictionary)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Top-down dynamic programming using memoization.
     *
     * Time: O(n^2) typical with HashSet lookups and substring creation.
     * Space: O(n) memo + O(n) recursion depth.
     */
    public static boolean wordBreakMemoized(String text, List<String> wordDict) {
        validateInput(text, wordDict);

        Set<String> dictionary = new HashSet<>(wordDict);
        Boolean[] memo = new Boolean[text.length() + 1];

        return canBreakMemoized(text, 0, dictionary, memo);
    }

    private static boolean canBreakMemoized(
            String text,
            int start,
            Set<String> dictionary,
            Boolean[] memo
    ) {
        if (start == text.length()) {
            return true;
        }

        if (memo[start] != null) {
            return memo[start];
        }

        for (int end = start + 1; end <= text.length(); end++) {
            String candidate = text.substring(start, end);

            if (dictionary.contains(candidate)
                    && canBreakMemoized(text, end, dictionary, memo)) {
                memo[start] = true;
                return true;
            }
        }

        memo[start] = false;
        return false;
    }

    /**
     * Bottom-up DP.
     *
     * dp[i] = true means text.substring(0, i) can be segmented.
     *
     * Time: O(n * L) substring/membership checks, where L is the maximum
     * dictionary word length (bounded by n).
     * Space: O(n) DP + dictionary storage.
     */
    public static boolean wordBreak(String text, List<String> wordDict) {
        validateInput(text, wordDict);

        Set<String> dictionary = new HashSet<>(wordDict);

        int maxWordLength = 0;
        for (String word : dictionary) {
            maxWordLength = Math.max(maxWordLength, word.length());
        }

        boolean[] dp = new boolean[text.length() + 1];
        dp[0] = true;

        for (int end = 1; end <= text.length(); end++) {
            int startMin = Math.max(0, end - maxWordLength);

            for (int start = startMin; start < end; start++) {
                if (!dp[start]) {
                    continue;
                }

                if (dictionary.contains(text.substring(start, end))) {
                    dp[end] = true;
                    break;
                }
            }
        }

        return dp[text.length()];
    }

    private static void validateInput(String text, List<String> wordDict) {
        if (text == null) {
            throw new IllegalArgumentException("Text cannot be null.");
        }

        if (wordDict == null) {
            throw new IllegalArgumentException("Dictionary cannot be null.");
        }

        for (String word : wordDict) {
            if (word == null || word.isEmpty()) {
                throw new IllegalArgumentException(
                        "Dictionary cannot contain null or empty words."
                );
            }
        }
    }

    public static void main(String[] args) {
        List<String> dictionary1 = List.of("leet", "code");
        List<String> dictionary2 = List.of("apple", "pen");
        List<String> dictionary3 = List.of("cats", "dog", "sand", "and", "cat");

        System.out.println("Recursive:");
        System.out.println(wordBreakRecursive("leetcode", dictionary1)); // true
        System.out.println(wordBreakRecursive("catsandog", dictionary3)); // false

        System.out.println("\nMemoized:");
        System.out.println(wordBreakMemoized("leetcode", dictionary1)); // true
        System.out.println(wordBreakMemoized("applepenapple", dictionary2)); // true

        System.out.println("\nBottom-up DP:");
        System.out.println(wordBreak("leetcode", dictionary1)); // true
        System.out.println(wordBreak("applepenapple", dictionary2)); // true
        System.out.println(wordBreak("catsandog", dictionary3)); // false
        System.out.println(wordBreak("", List.of("a"))); // true
    }
}
