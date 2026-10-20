package trees;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Objects;
import java.util.Queue;

/**
 * Day 97: Level-order traversal, zigzag level-order traversal, and right-side
 * view of a binary tree.
 *
 * All three algorithms use breadth-first search (BFS). Each node is visited
 * once, so each operation takes O(n) time and O(w) queue space, where w is
 * the maximum width of the tree.
 */
public class Day97BinaryTreeBFS {

    public static final class TreeNode {
        public final int value;
        public TreeNode left;
        public TreeNode right;

        public TreeNode(int value) {
            this.value = value;
        }
    }

    /** Returns each level from left to right. */
    public static List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            List<Integer> level = new ArrayList<>(levelSize);

            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.remove();
                level.add(node.value);
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
            result.add(level);
        }
        return result;
    }

    /**
     * Returns levels alternating direction: left-to-right, then
     * right-to-left, and so on. The queue order remains standard BFS;
     * only the order in which values are written changes.
     */
    public static List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);
        boolean leftToRight = true;

        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            Deque<Integer> level = new ArrayDeque<>();

            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.remove();

                if (leftToRight) {
                    level.addLast(node.value);
                } else {
                    level.addFirst(node.value);
                }

                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }

            result.add(new ArrayList<>(level));
            leftToRight = !leftToRight;
        }
        return result;
    }

    /** Returns the value visible at each level when viewed from the right. */
    public static List<Integer> rightSideView(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            int levelSize = queue.size();

            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.remove();

                if (i == levelSize - 1) {
                    result.add(node.value);
                }

                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
        }
        return result;
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
        //        \     \
        //         5     4
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.right = new TreeNode(5);
        root.right.right = new TreeNode(4);

        check(levelOrder(root).toString()
                        .equals("[[1], [2, 3], [5, 4]]"),
                "Unexpected level-order traversal: " + levelOrder(root));

        check(zigzagLevelOrder(root).toString()
                        .equals("[[1], [3, 2], [5, 4]]"),
                "Unexpected zigzag traversal: " + zigzagLevelOrder(root));

        check(rightSideView(root).equals(List.of(1, 3, 4)),
                "Unexpected right-side view: " + rightSideView(root));

        check(levelOrder(null).isEmpty(), "Empty tree should have no levels");
        check(zigzagLevelOrder(null).isEmpty(),
                "Empty tree should have no zigzag levels");
        check(rightSideView(null).isEmpty(),
                "Empty tree should have an empty right-side view");

        TreeNode single = new TreeNode(42);
        check(levelOrder(single).equals(List.of(List.of(42))),
                "Single-node level order is incorrect");
        check(zigzagLevelOrder(single).equals(List.of(List.of(42))),
                "Single-node zigzag is incorrect");
        check(rightSideView(single).equals(List.of(42)),
                "Single-node right-side view is incorrect");

        System.out.println("All binary-tree BFS checks passed.");
    }
}
