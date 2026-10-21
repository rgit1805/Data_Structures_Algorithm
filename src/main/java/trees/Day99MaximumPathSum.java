package trees;

/**
 * Day 99: Maximum path sum in a binary tree.
 *
 * A path may start and end at any nodes, but it must follow parent-child
 * edges and cannot revisit a node. Node values may be negative.
 */
public class Day99MaximumPathSum {

    public static final class TreeNode {
        public final int value;
        public TreeNode left;
        public TreeNode right;

        public TreeNode(int value) {
            this.value = value;
        }
    }

    /**
     * Returns the maximum sum over all non-empty paths.
     *
     * Time: O(n), auxiliary space: O(h) for recursion.
     * Throws IllegalArgumentException for an empty tree because no non-empty
     * path exists.
     */
    public static int maxPathSum(TreeNode root) {
        if (root == null) {
            throw new IllegalArgumentException(
                    "Maximum path sum is undefined for an empty tree");
        }

        int[] best = {Integer.MIN_VALUE};
        maxGain(root, best);
        return best[0];
    }

    /**
     * Returns the best downward path gain starting at this node.
     * Negative child gains are ignored, but the global path may use both
     * positive child gains through the current node.
     */
    private static int maxGain(TreeNode node, int[] best) {
        if (node == null) {
            return 0;
        }

        int leftGain = Math.max(0, maxGain(node.left, best));
        int rightGain = Math.max(0, maxGain(node.right, best));

        // A candidate path can turn at this node.
        int pathThroughNode = node.value + leftGain + rightGain;
        best[0] = Math.max(best[0], pathThroughNode);

        // A parent can continue through only one child.
        return node.value + Math.max(leftGain, rightGain);
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
        //       1
        //      / \
        //     2   3
        TreeNode first = new TreeNode(1);
        first.left = new TreeNode(2);
        first.right = new TreeNode(3);
        check(maxPathSum(first) == 6,
                "Maximum path 2 -> 1 -> 3 should sum to 6");

        //       -10
        //       /  \
        //      9   20
        //          / \
        //         15  7
        TreeNode second = new TreeNode(-10);
        second.left = new TreeNode(9);
        second.right = new TreeNode(20);
        second.right.left = new TreeNode(15);
        second.right.right = new TreeNode(7);
        check(maxPathSum(second) == 42,
                "Maximum path 15 -> 20 -> 7 should sum to 42");

        TreeNode allNegative = new TreeNode(-3);
        allNegative.left = new TreeNode(-5);
        allNegative.right = new TreeNode(-1);
        check(maxPathSum(allNegative) == -1,
                "All-negative tree should return its largest node");

        TreeNode single = new TreeNode(8);
        check(maxPathSum(single) == 8,
                "Single-node maximum path sum should be its value");

        check(throwsIllegalArgument(() -> maxPathSum(null)),
                "Empty tree should be rejected");

        System.out.println("All maximum-path-sum checks passed.");
    }
}
