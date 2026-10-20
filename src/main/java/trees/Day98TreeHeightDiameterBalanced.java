package trees;

/**
 * Day 98: Binary tree height, diameter, and balance check.
 *
 * Height is measured in edges: an empty tree has height -1 and a leaf has
 * height 0. Diameter is measured in edges along the longest path between
 * any two nodes. A tree is height-balanced when every node's subtree heights
 * differ by at most one.
 */
public class Day98TreeHeightDiameterBalanced {

    public static final class TreeNode {
        public final int value;
        public TreeNode left;
        public TreeNode right;

        public TreeNode(int value) {
            this.value = value;
        }
    }

    /** Returns tree height measured in edges. Time O(n), space O(h). */
    public static int height(TreeNode root) {
        if (root == null) {
            return -1;
        }
        return 1 + Math.max(height(root.left), height(root.right));
    }

    /**
     * Returns the diameter measured in edges.
     * The helper computes height and updates the best diameter in one pass.
     * Time O(n), auxiliary recursion space O(h).
     */
    public static int diameter(TreeNode root) {
        int[] best = {0};
        heightAndUpdateDiameter(root, best);
        return best[0];
    }

    private static int heightAndUpdateDiameter(TreeNode node, int[] best) {
        if (node == null) {
            return -1;
        }

        int leftHeight = heightAndUpdateDiameter(node.left, best);
        int rightHeight = heightAndUpdateDiameter(node.right, best);

        // Path through this node: left edges + right edges.
        best[0] = Math.max(best[0], leftHeight + rightHeight + 2);
        return 1 + Math.max(leftHeight, rightHeight);
    }

    /**
     * Returns whether the tree is height-balanced.
     * Uses -2 as an imbalance sentinel, since valid heights are >= -1.
     * Time O(n), auxiliary recursion space O(h).
     */
    public static boolean isBalanced(TreeNode root) {
        return balancedHeight(root) != -2;
    }

    private static int balancedHeight(TreeNode node) {
        if (node == null) {
            return -1;
        }

        int leftHeight = balancedHeight(node.left);
        if (leftHeight == -2) {
            return -2;
        }

        int rightHeight = balancedHeight(node.right);
        if (rightHeight == -2) {
            return -2;
        }

        if (Math.abs(leftHeight - rightHeight) > 1) {
            return -2;
        }

        return 1 + Math.max(leftHeight, rightHeight);
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    public static void main(String[] args) {
        //       1
        //      / \
        //     2   3
        //    / \
        //   4   5
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);

        check(height(root) == 2, "Height should be 2 edges");
        check(diameter(root) == 3, "Diameter should be 3 edges");
        check(isBalanced(root), "Example tree should be balanced");

        check(height(null) == -1, "Empty tree height should be -1");
        check(diameter(null) == 0, "Empty tree diameter should be 0");
        check(isBalanced(null), "Empty tree is balanced");

        TreeNode leaf = new TreeNode(9);
        check(height(leaf) == 0, "Leaf height should be 0");
        check(diameter(leaf) == 0, "Leaf diameter should be 0");
        check(isBalanced(leaf), "Leaf should be balanced");

        TreeNode skewed = new TreeNode(1);
        skewed.left = new TreeNode(2);
        skewed.left.left = new TreeNode(3);
        skewed.left.left.left = new TreeNode(4);
        check(height(skewed) == 3, "Skewed tree height should be 3");
        check(diameter(skewed) == 3, "Skewed tree diameter should be 3");
        check(!isBalanced(skewed), "Skewed tree should not be balanced");

        System.out.println("All height, diameter, and balance checks passed.");
    }
}
