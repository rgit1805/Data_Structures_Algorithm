package trees;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;

/**
 * Day 94: Binary Search Tree (BST).
 *
 * Stores unique comparable values. For every node, all values in the left
 * subtree are smaller and all values in the right subtree are larger.
 * Duplicate insertions are rejected.
 *
 * Average operation time is O(log n) for a reasonably balanced tree; in the
 * worst case a skewed tree takes O(n).
 */
public class Day94BinarySearchTree<T extends Comparable<? super T>> {

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

    /** Inserts a value. Returns false when the value already exists. */
    public boolean insert(T value) {
        Objects.requireNonNull(value, "Value must not be null");
        if (contains(value)) {
            return false;
        }
        root = insertNode(root, value);
        size++;
        return true;
    }

    private Node<T> insertNode(Node<T> node, T value) {
        if (node == null) {
            return new Node<>(value);
        }
        if (value.compareTo(node.value) < 0) {
            node.left = insertNode(node.left, value);
        } else {
            node.right = insertNode(node.right, value);
        }
        return node;
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

    /** Deletes a value. Returns false when the value does not exist. */
    public boolean delete(T value) {
        Objects.requireNonNull(value, "Value must not be null");
        if (!contains(value)) {
            return false;
        }
        root = deleteNode(root, value);
        size--;
        return true;
    }

    private Node<T> deleteNode(Node<T> node, T value) {
        int comparison = value.compareTo(node.value);
        if (comparison < 0) {
            node.left = deleteNode(node.left, value);
        } else if (comparison > 0) {
            node.right = deleteNode(node.right, value);
        } else {
            if (node.left == null) {
                return node.right;
            }
            if (node.right == null) {
                return node.left;
            }

            // Two children: replace this node with its inorder successor.
            Node<T> successor = minimumNode(node.right);
            Node<T> replacement = new Node<>(successor.value);
            replacement.left = node.left;
            replacement.right = deleteMinimum(node.right);
            return replacement;
        }
        return node;
    }

    private Node<T> deleteMinimum(Node<T> node) {
        if (node.left == null) {
            return node.right;
        }
        node.left = deleteMinimum(node.left);
        return node;
    }

    private Node<T> minimumNode(Node<T> node) {
        Node<T> current = node;
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }

    public T min() {
        if (root == null) {
            throw new IllegalStateException("Tree is empty");
        }
        return minimumNode(root).value;
    }

    public T max() {
        if (root == null) {
            throw new IllegalStateException("Tree is empty");
        }
        Node<T> current = root;
        while (current.right != null) {
            current = current.right;
        }
        return current.value;
    }

    /** Returns values in ascending order. */
    public List<T> inorder() {
        List<T> result = new ArrayList<>();
        inorder(root, result);
        return result;
    }

    private void inorder(Node<T> node, List<T> result) {
        if (node == null) return;
        inorder(node.left, result);
        result.add(node.value);
        inorder(node.right, result);
    }

    public List<T> preorder() {
        List<T> result = new ArrayList<>();
        preorder(root, result);
        return result;
    }

    private void preorder(Node<T> node, List<T> result) {
        if (node == null) return;
        result.add(node.value);
        preorder(node.left, result);
        preorder(node.right, result);
    }

    public List<T> postorder() {
        List<T> result = new ArrayList<>();
        postorder(root, result);
        return result;
    }

    private void postorder(Node<T> node, List<T> result) {
        if (node == null) return;
        postorder(node.left, result);
        postorder(node.right, result);
        result.add(node.value);
    }

    public List<T> levelOrder() {
        List<T> result = new ArrayList<>();
        if (root == null) return result;

        Queue<Node<T>> queue = new ArrayDeque<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            Node<T> node = queue.remove();
            result.add(node.value);
            if (node.left != null) queue.add(node.left);
            if (node.right != null) queue.add(node.right);
        }
        return result;
    }

    /** Height is measured in edges; an empty tree has height -1. */
    public int height() {
        return height(root);
    }

    private int height(Node<T> node) {
        if (node == null) return -1;
        return 1 + Math.max(height(node.left), height(node.right));
    }

    public boolean isValidBST() {
        return isValidBST(root, null, null);
    }

    private boolean isValidBST(Node<T> node, T lower, T upper) {
        if (node == null) return true;
        if ((lower != null && node.value.compareTo(lower) <= 0)
                || (upper != null && node.value.compareTo(upper) >= 0)) {
            return false;
        }
        return isValidBST(node.left, lower, node.value)
                && isValidBST(node.right, node.value, upper);
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
        if (!condition) throw new AssertionError(message);
    }

    public static void main(String[] args) {
        Day94BinarySearchTree<Integer> bst = new Day94BinarySearchTree<>();
        for (int value : new int[]{8, 3, 10, 1, 6, 14, 4, 7, 13}) {
            check(bst.insert(value), "New value should be inserted");
        }

        check(!bst.insert(6), "Duplicate values should be rejected");
        check(bst.contains(7), "Tree should contain 7");
        check(!bst.contains(99), "Tree should not contain 99");
        check(bst.inorder().equals(
                        List.of(1, 3, 4, 6, 7, 8, 10, 13, 14)),
                "Inorder traversal should be sorted");
        check(bst.preorder().equals(
                        List.of(8, 3, 1, 6, 4, 7, 10, 14, 13)),
                "Unexpected preorder traversal");
        check(bst.postorder().equals(
                        List.of(1, 4, 7, 6, 3, 13, 14, 10, 8)),
                "Unexpected postorder traversal");
        check(bst.levelOrder().equals(
                        List.of(8, 3, 10, 1, 6, 14, 4, 7, 13)),
                "Unexpected level-order traversal");
        check(bst.min() == 1 && bst.max() == 14,
                "Minimum or maximum is incorrect");
        check(bst.isValidBST(), "BST properties should hold");

        check(bst.delete(1), "Leaf deletion should succeed");
        check(bst.delete(14), "One-child deletion should succeed");
        check(bst.delete(8), "Two-child deletion should succeed");
        check(!bst.delete(99), "Deleting a missing value should fail");
        check(bst.isValidBST(), "BST should remain valid after deletion");
        check(bst.inorder().equals(List.of(3, 4, 6, 7, 10, 13)),
                "Unexpected values after deletion: " + bst.inorder());
        check(bst.size() == 6, "Size should reflect deletions");

        bst.clear();
        check(bst.isEmpty() && bst.size() == 0, "Clear should empty the tree");
        System.out.println("All BST checks passed.");
    }
}
