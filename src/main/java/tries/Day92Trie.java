package tries;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

/**
 * Day 92: Trie (Prefix Tree).
 *
 * Supports insertion, exact-word search, prefix search, deletion, counting
 * words sharing a prefix, and listing words under a prefix.
 *
 * This implementation supports arbitrary Java strings (not only lowercase
 * English letters). TreeMap children make prefix listings deterministic.
 */
public class Day92Trie {

    private static final class Node {
        private final Map<Character, Node> children = new TreeMap<>();
        private boolean isWord;
        private int wordsInSubtree;
    }

    private final Node root = new Node();
    private int size;

    /**
     * Inserts a word. Duplicate inserts do not change the word count.
     * Time: O(L log A), where L is word length and A is the branching factor.
     */
    public void insert(String word) {
        Objects.requireNonNull(word, "Word must not be null");

        Node current = root;
        current.wordsInSubtree++;

        for (int i = 0; i < word.length(); i++) {
            char character = word.charAt(i);
            current = current.children.computeIfAbsent(
                    character, ignored -> new Node());
            current.wordsInSubtree++;
        }

        if (!current.isWord) {
            current.isWord = true;
            size++;
        } else {
            // A duplicate word should not increase subtree counts.
            root.wordsInSubtree--;
            current = root;
            for (int i = 0; i < word.length(); i++) {
                current = current.children.get(word.charAt(i));
                current.wordsInSubtree--;
            }
        }
    }

    /** Returns true only when the complete word exists. */
    public boolean search(String word) {
        Objects.requireNonNull(word, "Word must not be null");
        Node node = findNode(word);
        return node != null && node.isWord;
    }

    /** Returns true when at least one stored word starts with the prefix. */
    public boolean startsWith(String prefix) {
        Objects.requireNonNull(prefix, "Prefix must not be null");
        return findNode(prefix) != null;
    }

    /**
     * Deletes a word if present. Nodes are pruned when they no longer lead
     * to any stored word. Returns false if the word was absent.
     */
    public boolean delete(String word) {
        Objects.requireNonNull(word, "Word must not be null");
        if (!search(word)) {
            return false;
        }

        root.wordsInSubtree--;
        Node current = root;

        for (int i = 0; i < word.length(); i++) {
            current = current.children.get(word.charAt(i));
            current.wordsInSubtree--;
        }
        current.isWord = false;
        size--;

        // Remove unused suffix nodes, walking back from the final character.
        Node[] path = new Node[word.length() + 1];
        path[0] = root;
        current = root;
        for (int i = 0; i < word.length(); i++) {
            current = current.children.get(word.charAt(i));
            path[i + 1] = current;
        }

        for (int i = word.length() - 1; i >= 0; i--) {
            Node child = path[i + 1];
            if (child.wordsInSubtree == 0) {
                path[i].children.remove(word.charAt(i));
            } else {
                break;
            }
        }
        return true;
    }

    /** Number of distinct words stored in the trie. */
    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    /** Number of stored words beginning with the given prefix. */
    public int countWordsWithPrefix(String prefix) {
        Objects.requireNonNull(prefix, "Prefix must not be null");
        Node node = findNode(prefix);
        return node == null ? 0 : node.wordsInSubtree;
    }

    /**
     * Returns all words beginning with prefix in lexicographic order.
     * Time: O(P + S), excluding map traversal overhead, where P is prefix
     * length and S is the total size of returned text.
     */
    public List<String> wordsWithPrefix(String prefix) {
        Objects.requireNonNull(prefix, "Prefix must not be null");
        Node node = findNode(prefix);
        List<String> result = new ArrayList<>();
        if (node != null) {
            collectWords(node, new StringBuilder(prefix), result);
        }
        return result;
    }

    public void clear() {
        root.children.clear();
        root.isWord = false;
        root.wordsInSubtree = 0;
        size = 0;
    }

    private Node findNode(String text) {
        Node current = root;
        for (int i = 0; i < text.length(); i++) {
            current = current.children.get(text.charAt(i));
            if (current == null) {
                return null;
            }
        }
        return current;
    }

    private void collectWords(Node node, StringBuilder prefix,
                              List<String> result) {
        if (node.isWord) {
            result.add(prefix.toString());
        }

        for (Map.Entry<Character, Node> entry : node.children.entrySet()) {
            prefix.append(entry.getKey());
            collectWords(entry.getValue(), prefix, result);
            prefix.deleteCharAt(prefix.length() - 1);
        }
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    public static void main(String[] args) {
        Day92Trie trie = new Day92Trie();

        trie.insert("app");
        trie.insert("apple");
        trie.insert("apply");
        trie.insert("bat");
        trie.insert("batch");
        trie.insert(""); // The empty string is supported as a word.

        check(trie.search("app"), "'app' should exist");
        check(!trie.search("ap"), "'ap' is only a prefix, not a word");
        check(trie.startsWith("ap"), "Prefix 'ap' should exist");
        check(!trie.startsWith("cat"), "Prefix 'cat' should not exist");
        check(trie.countWordsWithPrefix("app") == 3,
                "Three words should start with 'app'");
        check(trie.wordsWithPrefix("app").toString()
                        .equals("[app, apple, apply]"),
                "Unexpected prefix results: " + trie.wordsWithPrefix("app"));
        check(trie.size() == 6, "Trie should contain six distinct words");

        trie.insert("app"); // Duplicate insertion is a no-op.
        check(trie.size() == 6, "Duplicate insert must not change size");

        check(trie.delete("apple"), "Existing word should be deleted");
        check(!trie.search("apple"), "'apple' should no longer exist");
        check(trie.search("app"), "'app' should remain after deleting 'apple'");
        check(!trie.delete("missing"), "Deleting a missing word should fail");
        check(trie.countWordsWithPrefix("app") == 2,
                "Two words should remain under 'app'");

        check(trie.delete(""), "Empty word should be deletable");
        check(!trie.search(""), "Empty word should no longer exist");

        trie.clear();
        check(trie.isEmpty(), "Trie should be empty after clear");
        check(trie.wordsWithPrefix("a").isEmpty(),
                "No results should remain after clear");

        System.out.println("All trie checks passed.");
    }
}
