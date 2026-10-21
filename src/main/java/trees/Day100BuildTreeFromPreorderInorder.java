package trees;

import java.util.HashMap;
import java.util.Map;

/**
 * Day 100: Construct a binary tree from preorder and inorder traversals.
 *
 * Assumptions:
 * - Values are unique.
 * - Both traversals describe the same tree.
 * - Preorder and inorder arrays are non-null and have equal lengths.
 *
 * Preorder visits root, left, right. Inorder visits left, root, right.
 * A value-to-index map makes locating each root in inorder O(1) on average.
 */
public class Day100BuildTreeFromPreorderInorder {

    public static final class TreeNode {
        public final int value;
        public TreeNode left;
        public TreeNode right;

        public TreeNode(int value) {
            this.value = value;
        }
    }

    public static TreeNode buildTree(int[] preorder, int[] inorder) {
        if (preorder == null || inorder == null) {
            throw new IllegalArgumentException("Traversals must not be null");
        }
        if (preorder.length != inorder.length) {
            throw new IllegalArgumentException(
                    "Preorder and inorder lengths must match");
        }
        if (preorder.length == 0) {
            return null;
        }

        Map<Integer, Integer> inorderIndex = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) {
            if (inorderIndex.put(inorder[i], i) != null) {
                throw new IllegalArgumentException(
                        "Duplicate values are not supported");
            }
        }

        // Also verify preorder has unique values and matches inorder's values.
        Map<Integer, Boolean> seen = new HashMap<>();
        for (int value : preorder) {
            if (!inorderIndex.containsKey(value) || seen.put(value, true) != null) {
                throw new IllegalArgumentException(
                        "Traversals must contain the same unique values");
            }
        }

        int[] preorderCursor = {0};
        TreeNode root = build(preorder, 0, inorder.length - 1,
                preorderCursor, inorderIndex);

        if (preorderCursor[0] != preorder.length) {
            throw new IllegalArgumentException("Inconsistent traversals");
        }
        return root;
    }

    private static TreeNode build(
            int[] preorder,
            int inLeft,
            int inRight,
            int[] preorderCursor,
            Map<Integer, Integer> inorderIndex) {

        if (inLeft > inRight) {
            return null;
        }
        if (preorderCursor[0] >= preorder.length) {
            throw new IllegalArgumentException("Inconsistent traversals");
        }

        int rootValue = preorder[preorderCursor[0]++];
        Integer rootIndex = inorderIndex.get(rootValue);
        if (rootIndex == null || rootIndex < inLeft || rootIndex > inRight) {
            throw new IllegalArgumentException("Inconsistent traversals");
        }

        TreeNode root = new TreeNode(rootValue);
        root.left = build(preorder, inLeft, rootIndex - 1,
                preorderCursor, inorderIndex);
        root.right = build(preorder, rootIndex + 1, inRight,
                preorderCursor, inorderIndex);
        return root;
    }

    public static int[] preorderOf(TreeNode root) {
        int size = countNodes(root);
        int[] values = new int[size];
        int[] cursor = {0};
        writePreorder(root, values, cursor);
        return values;
    }

    private static void writePreorder(TreeNode node, int[] values, int[] cursor) {
        if (node == null) return;
        values[cursor[0]++] = node.value;
        writePreorder(node.left, values, cursor);
        writePreorder(node.right, values, cursor);
    }

    public static int[] inorderOf(TreeNode root) {
        int size = countNodes(root);
        int[] values = new int[size];
        int[] cursor = {0};
        writeInorder(root, values, cursor);
        return values;
    }

    private static void writeInorder(TreeNode node, int[] values, int[] cursor) {
        if (node == null) return;
        writeInorder(node.left, values, cursor);
        values[cursor[0]++] = node.value;
        writeInorder(node.right, values, cursor);
    }

    private static int countNodes(TreeNode node) {
        if (node == null) return 0;
        return 1 + countNodes(node.left) + countNodes(node.right);
    }

    private static void check(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }

    private static boolean throwsIllegalArgument(Runnable action) {
        try {
            action.run();
            return false;
        } catch (IllegalArgumentException expected) {
            return true;
        }
    }

    public static void main(String[] args) {
        int[] preorder = {3, 9, 20, 15, 7};
        int[] inorder = {9, 3, 15, 20, 7};

        TreeNode root = buildTree(preorder, inorder);
        check(java.util.Arrays.equals(preorder, preorderOf(root)),
                "Reconstructed preorder should match input");
        check(java.util.Arrays.equals(inorder, inorderOf(root)),
                "Reconstructed inorder should match input");

        check(buildTree(new int[0], new int[0]) == null,
                "Empty traversals should produce an empty tree");

        TreeNode single = buildTree(new int[]{42}, new int[]{42});
        check(single.value == 42 && single.left == null && single.right == null,
                "Single-node tree should be reconstructed");

        check(throwsIllegalArgument(
                        () -> buildTree(new int[]{1}, new int[]{1, 2})),
                "Mismatched lengths should be rejected");
        check(throwsIllegalArgument(
                        () -> buildTree(new int[]{1, 1}, new int[]{1, 1})),
                "Duplicate values should be rejected");
        check(throwsIllegalArgument(
                        () -> buildTree(new int[]{1, 2, 3}, new int[]{3, 1, 2})),
                "Inconsistent traversal orders should be rejected");

        System.out.println("All tree-construction checks passed.");
    }
}
