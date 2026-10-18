package tries;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Day 93: Word Search II using a Trie and backtracking.
 *
 * Finds all distinct dictionary words that can be formed by moving
 * horizontally or vertically through adjacent board cells. A cell may be
 * used at most once in a single word path.
 */
public class Day93WordSearchII {

    private static final class TrieNode {
        private final Map<Character, TrieNode> children = new HashMap<>();
        private String word;
    }

    /**
     * Builds a trie from the dictionary, then explores the board from each
     * starting cell. Found terminal words are cleared to avoid duplicate
     * results when a word can be formed by multiple paths.
     */
    public static List<String> findWords(char[][] board, String[] words) {
        validateBoard(board);
        Objects.requireNonNull(words, "Words array must not be null");

        List<String> result = new ArrayList<>();
        if (board.length == 0 || board[0].length == 0 || words.length == 0) {
            return result;
        }

        TrieNode root = new TrieNode();
        Set<String> uniqueWords = new HashSet<>();

        for (String word : words) {
            Objects.requireNonNull(word, "Dictionary word must not be null");
            if (word.isEmpty() || !uniqueWords.add(word)) {
                continue;
            }

            TrieNode node = root;
            for (int i = 0; i < word.length(); i++) {
                char ch = word.charAt(i);
                node = node.children.computeIfAbsent(ch, ignored -> new TrieNode());
            }
            node.word = word;
        }

        boolean[][] visited = new boolean[board.length][board[0].length];
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[0].length; col++) {
                backtrack(board, row, col, root, visited, result);
            }
        }

        return result;
    }

    private static void backtrack(char[][] board, int row, int col,
                                  TrieNode parent, boolean[][] visited,
                                  List<String> result) {
        if (row < 0 || row >= board.length
                || col < 0 || col >= board[0].length
                || visited[row][col]) {
            return;
        }

        TrieNode node = parent.children.get(board[row][col]);
        if (node == null) {
            return; // No dictionary word has this prefix.
        }

        if (node.word != null) {
            result.add(node.word);
            node.word = null; // Report each distinct word only once.
        }

        visited[row][col] = true;
        backtrack(board, row - 1, col, node, visited, result);
        backtrack(board, row + 1, col, node, visited, result);
        backtrack(board, row, col - 1, node, visited, result);
        backtrack(board, row, col + 1, node, visited, result);
        visited[row][col] = false;

        // Optional pruning: remove a trie branch once it has no remaining
        // word and no descendants. This can reduce later search work.
        if (node.word == null && node.children.isEmpty()) {
            parent.children.remove(board[row][col]);
        }
    }

    private static void validateBoard(char[][] board) {
        Objects.requireNonNull(board, "Board must not be null");
        if (board.length == 0) {
            return;
        }
        if (board[0] == null) {
            throw new IllegalArgumentException("Board rows must not be null");
        }

        int columns = board[0].length;
        for (char[] row : board) {
            if (row == null || row.length != columns) {
                throw new IllegalArgumentException(
                        "Board must be rectangular and rows must not be null");
            }
        }
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    public static void main(String[] args) {
        char[][] board = {
                {'o', 'a', 'a', 'n'},
                {'e', 't', 'a', 'e'},
                {'i', 'h', 'k', 'r'},
                {'i', 'f', 'l', 'v'}
        };

        String[] words = {
                "oath", "pea", "eat", "rain", "oath", "hklf", "hf"
        };

        List<String> found = findWords(board, words);
        Set<String> foundSet = new HashSet<>(found);

        check(foundSet.equals(Set.of("oath", "eat", "hklf", "hf")),
                "Unexpected words found: " + found);
        check(found.size() == foundSet.size(),
                "Results must not contain duplicate words");

        // A cell cannot be reused within a path.
        char[][] oneCell = {{'a'}};
        check(findWords(oneCell, new String[]{"aa", "a"})
                        .equals(List.of("a")),
                "A single cell must not be reused");

        check(findWords(new char[0][0], new String[]{"word"}).isEmpty(),
                "Empty board should produce no results");

        System.out.println("Words found: " + found);
        System.out.println("All Word Search II checks passed.");
    }
}
