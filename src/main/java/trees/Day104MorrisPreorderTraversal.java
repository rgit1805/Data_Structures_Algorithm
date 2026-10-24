package trees;

import java.util.ArrayList;
import java.util.List;

/**
 * Day 104: Morris preorder traversal.
 *
 * Visits nodes in root-left-right order without recursion or an explicit stack.
 * Temporary predecessor threads allow the traversal to return from a left
 * subtree; every thread is removed before the traversal finishes.
 */
public class Day104MorrisPreorderTraversal {

    public static final class TreeNode {
        public final int value;
        public TreeNode left;
        public TreeNode right;

        public TreeNode(int value) {
            this.value = value;
        }
    }

    /**
     * Time: O(n). Auxiliary traversal space: O(1), excluding the returned list.
     * The tree is temporarily modified, then restored during a successful run.
     */
    public static List<Integer> morrisPreorder(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        TreeNode current = root;

        while (current != null) {
            if (current.left == null) {
                result.add(current.value);
                current = current.right;
            } else {
                TreeNode predecessor = current.left;
                while (predecessor.right != null && predecessor.right != current) {
                    predecessor = predecessor.right;
                }

                if (predecessor.right == null) {
                    // Preorder visits the current node before its left subtree.
                    result.add(current.value);
                    predecessor.right = current; // Create a temporary thread.
                    current = current.left;
                } else {
                    predecessor.right = null; // Restore the original pointer.
                    current = current.right;
                }
            }
        }

        return result;
    }

    /** Recursive reference implementation for checking expected output. */
    public static List<Integer> recursivePreorder(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        appendPreorder(root, result);
        return result;
    }

    private static void appendPreorder(TreeNode node, List<Integer> result) {
        if (node == null) {
            return;
        }
        result.add(node.value);
        appendPreorder(node.left, result);
        appendPreorder(node.right, result);
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    public static void main(String[] args) {
        //          1
        //        /   \
        //       2     5
        //      / \   /
        //     3   4 6
        TreeNode root = new TreeNode(1);
        TreeNode two = new TreeNode(2);
        TreeNode five = new TreeNode(5);
        TreeNode three = new TreeNode(3);
        TreeNode four = new TreeNode(4);
        TreeNode six = new TreeNode(6);
        root.left = two;
        root.right = five;
        two.left = three;
        two.right = four;
        five.left = six;

        List<Integer> expected = List.of(1, 2, 3, 4, 5, 6);
        check(morrisPreorder(root).equals(expected), "Unexpected preorder result");
        check(recursivePreorder(root).equals(expected), "Reference preorder mismatch");

        // Verify key pointers and run a second traversal after thread cleanup.
        check(root.left == two && root.right == five, "Root pointers were not restored");
        check(two.left == three && two.right == four, "Left subtree was not restored");
        check(five.left == six && five.right == null, "Right subtree was not restored");
        check(morrisPreorder(root).equals(expected), "Second traversal failed");
        check(morrisPreorder(null).isEmpty(), "Empty tree should return an empty list");

        TreeNode single = new TreeNode(99);
        check(morrisPreorder(single).equals(List.of(99)), "Single-node case failed");

        System.out.println("All Morris preorder traversal checks passed.");
    }
}
