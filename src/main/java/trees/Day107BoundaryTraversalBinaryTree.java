package trees;

import java.util.ArrayList;
import java.util.List;

/**
 * Day 107: Boundary traversal of a binary tree in anti-clockwise order.
 *
 * The boundary consists of the root, left boundary excluding leaves, all
 * leaves from left to right, and right boundary excluding leaves in reverse.
 * Excluding leaves from side boundaries avoids duplicate values.
 */
public class Day107BoundaryTraversalBinaryTree {

    public static final class TreeNode {
        public final int value;
        public TreeNode left;
        public TreeNode right;

        public TreeNode(int value) {
            this.value = value;
        }
    }

    public static List<Integer> boundaryTraversal(TreeNode root) {
        List<Integer> boundary = new ArrayList<>();
        if (root == null) {
            return boundary;
        }
        if (isLeaf(root)) {
            boundary.add(root.value);
            return boundary;
        }

        boundary.add(root.value);
        addLeftBoundary(root.left, boundary);
        addLeaves(root, boundary);

        List<Integer> rightBoundary = new ArrayList<>();
        addRightBoundary(root.right, rightBoundary);
        for (int i = rightBoundary.size() - 1; i >= 0; i--) {
            boundary.add(rightBoundary.get(i));
        }
        return boundary;
    }

    private static boolean isLeaf(TreeNode node) {
        return node != null && node.left == null && node.right == null;
    }

    private static void addLeftBoundary(TreeNode node, List<Integer> result) {
        TreeNode current = node;
        while (current != null) {
            if (!isLeaf(current)) {
                result.add(current.value);
            }
            current = current.left != null ? current.left : current.right;
        }
    }

    private static void addLeaves(TreeNode node, List<Integer> result) {
        if (node == null) {
            return;
        }
        if (isLeaf(node)) {
            result.add(node.value);
            return;
        }
        addLeaves(node.left, result);
        addLeaves(node.right, result);
    }

    private static void addRightBoundary(TreeNode node, List<Integer> result) {
        TreeNode current = node;
        while (current != null) {
            if (!isLeaf(current)) {
                result.add(current.value);
            }
            current = current.right != null ? current.right : current.left;
        }
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    public static void main(String[] args) {
        //          1
        //        /   \
        //       2     3
        //      / \     \
        //     4   5     6
        //        / \   /
        //       7   8 9
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
        root.right.right = new TreeNode(6);
        root.left.right.left = new TreeNode(7);
        root.left.right.right = new TreeNode(8);
        root.right.right.left = new TreeNode(9);

        List<Integer> expected = List.of(1, 2, 4, 7, 8, 9, 6, 3);
        check(boundaryTraversal(root).equals(expected),
                "Unexpected boundary: " + boundaryTraversal(root));
        check(boundaryTraversal(null).isEmpty(),
                "Empty tree should have an empty boundary");
        check(boundaryTraversal(new TreeNode(42)).equals(List.of(42)),
                "Single-node tree should appear once");

        TreeNode skewed = new TreeNode(10);
        skewed.right = new TreeNode(11);
        skewed.right.right = new TreeNode(12);
        check(boundaryTraversal(skewed).equals(List.of(10, 12, 11)),
                "Right-skewed tree boundary failed");

        System.out.println("All boundary traversal checks passed.");
    }
}
