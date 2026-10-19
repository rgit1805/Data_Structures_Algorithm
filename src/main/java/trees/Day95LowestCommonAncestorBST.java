package trees;

import java.util.Objects;

/**
 * Day 95: Lowest Common Ancestor (LCA) in a Binary Search Tree.
 *
 * The LCA of two values is the deepest node that is an ancestor of both.
 * This class builds a BST and finds the LCA using the BST ordering property.
 * Duplicate values are ignored.
 */
public class Day95LowestCommonAncestorBST<T extends Comparable<? super T>> {

    private static final class Node<T> {
        private final T value;
        private Node<T> left;
        private Node<T> right;

        private Node(T value) {
            this.value = value;
        }
    }

    private Node<T> root;
    private int size;

    public boolean insert(T value) {
        Objects.requireNonNull(value, "Value must not be null");

        if (root == null) {
            root = new Node<>(value);
            size++;
            return true;
        }

        Node<T> current = root;
        while (true) {
            int comparison = value.compareTo(current.value);
            if (comparison == 0) {
                return false;
            } else if (comparison < 0) {
                if (current.left == null) {
                    current.left = new Node<>(value);
                    size++;
                    return true;
                }
                current = current.left;
            } else {
                if (current.right == null) {
                    current.right = new Node<>(value);
                    size++;
                    return true;
                }
                current = current.right;
            }
        }
    }

    public boolean contains(T value) {
        Objects.requireNonNull(value, "Value must not be null");
        Node<T> current = root;

        while (current != null) {
            int comparison = value.compareTo(current.value);
            if (comparison == 0) {
                return true;
            }
            current = comparison < 0 ? current.left : current.right;
        }
        return false;
    }

    /**
     * Returns the LCA value, or null if either requested value is absent.
     * Average time is O(h); worst-case time is O(n) for a skewed tree.
     */
    public T lowestCommonAncestor(T first, T second) {
        Objects.requireNonNull(first, "First value must not be null");
        Objects.requireNonNull(second, "Second value must not be null");

        if (!contains(first) || !contains(second)) {
            return null;
        }

        T smaller = first.compareTo(second) <= 0 ? first : second;
        T larger = first.compareTo(second) <= 0 ? second : first;

        Node<T> current = root;
        while (current != null) {
            if (larger.compareTo(current.value) < 0) {
                current = current.left;
            } else if (smaller.compareTo(current.value) > 0) {
                current = current.right;
            } else {
                // The values split across the node, or one value is this node.
                return current.value;
            }
        }

        return null; // Unreachable when both values are present.
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        root = null;
        size = 0;
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    public static void main(String[] args) {
        Day95LowestCommonAncestorBST<Integer> bst =
                new Day95LowestCommonAncestorBST<>();

        for (int value : new int[]{6, 2, 8, 0, 4, 7, 9, 3, 5}) {
            check(bst.insert(value), "New values should be inserted");
        }

        check(Integer.valueOf(2).equals(bst.lowestCommonAncestor(2, 4)),
                "A node can be the LCA of itself and a descendant");
        check(Integer.valueOf(6).equals(bst.lowestCommonAncestor(2, 8)),
                "Root should be the LCA when values split across it");
        check(Integer.valueOf(4).equals(bst.lowestCommonAncestor(3, 5)),
                "Expected 4 as the LCA of 3 and 5");
        check(Integer.valueOf(6).equals(bst.lowestCommonAncestor(0, 9)),
                "Expected root as the LCA of opposite-side values");
        check(bst.lowestCommonAncestor(2, 100) == null,
                "Missing values should produce null");
        check(bst.lowestCommonAncestor(100, 101) == null,
                "Two missing values should produce null");
        check(bst.size() == 9, "Tree should contain nine values");

        bst.clear();
        check(bst.isEmpty(), "Clear should empty the tree");

        System.out.println("All BST LCA checks passed.");
    }
}
