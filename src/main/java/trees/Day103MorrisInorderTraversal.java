package trees;

import java.util.ArrayList;
import java.util.List;

/**
 * Day 103: Morris inorder traversal.
 *
 * Traverses a binary tree in inorder without recursion or an explicit stack.
 * Temporary predecessor threads are removed before moving on, so the original
 * tree structure is restored when the method returns.
 */
public class Day103MorrisInorderTraversal {

    public static final class TreeNode {
        public final int value;
        public TreeNode left;
        public TreeNode right;

        public TreeNode(int value) {
            this.value = value;
        }
    }

    /**
     * Returns the inorder sequence using O(1) auxiliary space (excluding output).
     * Time: O(n), because each edge/node is handled a constant number of times.
     */
    public static List<Integer> morrisInorder(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        TreeNode current = root;

        while (current != null) {
            if (current.left == null) {
                result.add(current.value);
                current = current.right;
            } else {
                // Find the rightmost node in current's left subtree.
                TreeNode predecessor = current.left;
                while (predecessor.right != null && predecessor.right != current) {
                    predecessor = predecessor.right;
                }

                if (predecessor.right == null) {
                    // Create a temporary thread back to current.
                    predecessor.right = current;
                    current = current.left;
                } else {
                    // The left subtree is done; remove the temporary thread.
                    predecessor.right = null;
                    result.add(current.value);
                    current = current.right;
                }
            }
        }

        return result;
    }

    /** Recursive reference implementation used only to validate examples. */
    public static List<Integer> recursiveInorder(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        appendInorder(root, result);
        return result;
    }

    private static void appendInorder(TreeNode node, List<Integer> result) {
        if (node == null) {
            return;
        }
        appendInorder(node.left, result);
        result.add(node.value);
        appendInorder(node.right, result);
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    public static void main(String[] args) {
        //          4
        //        /   \
        //       2     6
        //      / \   /
        //     1   3 5
        TreeNode root = new TreeNode(4);
        root.left = new TreeNode(2);
        root.right = new TreeNode(6);
        root.left.left = new TreeNode(1);
        root.left.right = new TreeNode(3);
        root.right.left = new TreeNode(5);

        List<Integer> expected = List.of(1, 2, 3, 4, 5, 6);
        check(morrisInorder(root).equals(expected),
                "Unexpected Morris inorder result");
        check(recursiveInorder(root).equals(expected),
                "Unexpected recursive reference result");

        // A second traversal verifies that temporary threads were removed.
        check(morrisInorder(root).equals(expected),
                "Tree structure should be restored after traversal");
        check(morrisInorder(null).isEmpty(),
                "Empty tree should produce an empty traversal");

        TreeNode single = new TreeNode(42);
        check(morrisInorder(single).equals(List.of(42)),
                "Single-node traversal failed");

        System.out.println("All Morris inorder traversal checks passed.");
    }
}
