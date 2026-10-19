package trees;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Objects;

/**
 * Day 96: Validate a BST and find its kth-smallest element.
 *
 * These algorithms operate on a general binary-tree node structure rather
 * than assuming the input already came from a BST insertion routine.
 * Duplicate values violate the strict BST ordering used here.
 */
public class Day96ValidateBSTAndKthSmallest {

    public static final class TreeNode {
        public final int value;
        public TreeNode left;
        public TreeNode right;

        public TreeNode(int value) {
            this.value = value;
        }
    }

    /**
     * Validates the entire tree using exclusive lower and upper bounds.
     * Time: O(n); auxiliary space: O(h) recursion stack.
     */
    public static boolean isValidBST(TreeNode root) {
        return validate(root, null, null);
    }

    private static boolean validate(TreeNode node, Long lower, Long upper) {
        if (node == null) {
            return true;
        }

        long value = node.value;
        if ((lower != null && value <= lower)
                || (upper != null && value >= upper)) {
            return false;
        }

        return validate(node.left, lower, value)
                && validate(node.right, value, upper);
    }

    /**
     * Returns the kth-smallest value (1-indexed) using iterative inorder
     * traversal. Throws IllegalArgumentException if k is invalid or the
     * tree has fewer than k nodes.
     *
     * Time: O(h + k) in the usual analysis; auxiliary space: O(h).
     */
    public static int kthSmallest(TreeNode root, int k) {
        if (k < 1) {
            throw new IllegalArgumentException("k must be at least 1");
        }

        Deque<TreeNode> stack = new ArrayDeque<>();
        TreeNode current = root;
        int visited = 0;

        while (current != null || !stack.isEmpty()) {
            while (current != null) {
                stack.push(current);
                current = current.left;
            }

            current = stack.pop();
            visited++;
            if (visited == k) {
                return current.value;
            }
            current = current.right;
        }

        throw new IllegalArgumentException(
                "k exceeds the number of nodes in the tree");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
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
        //        5
        //       / \
        //      3   7
        //     / \ / \
        //    2  4 6  8
        TreeNode root = new TreeNode(5);
        root.left = new TreeNode(3);
        root.right = new TreeNode(7);
        root.left.left = new TreeNode(2);
        root.left.right = new TreeNode(4);
        root.right.left = new TreeNode(6);
        root.right.right = new TreeNode(8);

        check(isValidBST(root), "Example tree should be a valid BST");
        check(kthSmallest(root, 1) == 2, "First smallest should be 2");
        check(kthSmallest(root, 4) == 5, "Fourth smallest should be 5");
        check(kthSmallest(root, 7) == 8, "Seventh smallest should be 8");

        // A local child can look valid but still violate an ancestor bound:
        // 5 -> right 7 -> left 4 is invalid because 4 is less than 5.
        TreeNode invalid = new TreeNode(5);
        invalid.right = new TreeNode(7);
        invalid.right.left = new TreeNode(4);
        check(!isValidBST(invalid),
                "A descendant violating an ancestor bound must be rejected");

        TreeNode duplicate = new TreeNode(5);
        duplicate.left = new TreeNode(5);
        check(!isValidBST(duplicate), "Duplicate values are not valid here");

        check(throwsIllegalArgument(() -> kthSmallest(root, 0)),
                "k = 0 should be rejected");
        check(throwsIllegalArgument(() -> kthSmallest(root, 8)),
                "k greater than tree size should be rejected");

        System.out.println("All BST validation and kth-smallest checks passed.");
    }
}
