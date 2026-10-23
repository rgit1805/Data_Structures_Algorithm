package trees;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Day 101: Construct a binary tree from inorder and postorder traversals.
 *
 * Assumptions:
 * - Values are unique.
 * - Both traversals describe the same tree.
 * - Inputs are non-null and have equal lengths.
 *
 * Postorder visits left, right, root, so reconstruction consumes postorder
 * from right to left and builds the right subtree before the left subtree.
 */
public class Day101BuildTreeFromInorderPostorder {

    public static final class TreeNode {
        public final int value;
        public TreeNode left;
        public TreeNode right;

        public TreeNode(int value) {
            this.value = value;
        }
    }

    public static TreeNode buildTree(int[] inorder, int[] postorder) {
        if (inorder == null || postorder == null) {
            throw new IllegalArgumentException("Traversals must not be null");
        }
        if (inorder.length != postorder.length) {
            throw new IllegalArgumentException(
                    "Inorder and postorder lengths must match");
        }
        if (inorder.length == 0) {
            return null;
        }

        Map<Integer, Integer> inorderIndex = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) {
            if (inorderIndex.put(inorder[i], i) != null) {
                throw new IllegalArgumentException(
                        "Duplicate values are not supported");
            }
        }

        Map<Integer, Boolean> seen = new HashMap<>();
        for (int value : postorder) {
            if (!inorderIndex.containsKey(value) || seen.put(value, true) != null) {
                throw new IllegalArgumentException(
                        "Traversals must contain the same unique values");
            }
        }

        int[] cursor = {postorder.length - 1};
        TreeNode root = build(inorder, postorder, 0, inorder.length - 1,
                cursor, inorderIndex);

        if (cursor[0] != -1) {
            throw new IllegalArgumentException("Inconsistent traversals");
        }
        return root;
    }

    private static TreeNode build(
            int[] inorder,
            int[] postorder,
            int inLeft,
            int inRight,
            int[] cursor,
            Map<Integer, Integer> inorderIndex) {

        if (inLeft > inRight) {
            return null;
        }
        if (cursor[0] < 0) {
            throw new IllegalArgumentException("Inconsistent traversals");
        }

        int rootValue = postorder[cursor[0]--];
        Integer rootIndex = inorderIndex.get(rootValue);
        if (rootIndex == null || rootIndex < inLeft || rootIndex > inRight) {
            throw new IllegalArgumentException("Inconsistent traversals");
        }

        TreeNode root = new TreeNode(rootValue);
        // Postorder is consumed backwards: root, right, left.
        root.right = build(inorder, postorder, rootIndex + 1, inRight,
                cursor, inorderIndex);
        root.left = build(inorder, postorder, inLeft, rootIndex - 1,
                cursor, inorderIndex);
        return root;
    }

    public static int[] preorderOf(TreeNode root) {
        int[] result = new int[countNodes(root)];
        writePreorder(root, result, new int[]{0});
        return result;
    }

    public static int[] inorderOf(TreeNode root) {
        int[] result = new int[countNodes(root)];
        writeInorder(root, result, new int[]{0});
        return result;
    }

    public static int[] postorderOf(TreeNode root) {
        int[] result = new int[countNodes(root)];
        writePostorder(root, result, new int[]{0});
        return result;
    }

    private static void writePreorder(TreeNode node, int[] out, int[] cursor) {
        if (node == null) return;
        out[cursor[0]++] = node.value;
        writePreorder(node.left, out, cursor);
        writePreorder(node.right, out, cursor);
    }

    private static void writeInorder(TreeNode node, int[] out, int[] cursor) {
        if (node == null) return;
        writeInorder(node.left, out, cursor);
        out[cursor[0]++] = node.value;
        writeInorder(node.right, out, cursor);
    }

    private static void writePostorder(TreeNode node, int[] out, int[] cursor) {
        if (node == null) return;
        writePostorder(node.left, out, cursor);
        writePostorder(node.right, out, cursor);
        out[cursor[0]++] = node.value;
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
        int[] inorder = {9, 3, 15, 20, 7};
        int[] postorder = {9, 15, 7, 20, 3};
        TreeNode root = buildTree(inorder, postorder);

        check(Arrays.equals(inorder, inorderOf(root)),
                "Reconstructed inorder should match input");
        check(Arrays.equals(postorder, postorderOf(root)),
                "Reconstructed postorder should match input");
        check(Arrays.equals(new int[]{3, 9, 20, 15, 7}, preorderOf(root)),
                "Unexpected preorder traversal");

        check(buildTree(new int[0], new int[0]) == null,
                "Empty traversals should return null");
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

        System.out.println("All inorder/postorder construction checks passed.");
    }
}
