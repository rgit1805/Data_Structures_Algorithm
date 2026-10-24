package trees;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;

/**
 * Day 105: Binary tree vertical order traversal.
 *
 * Assign each node a horizontal column: root is column 0, left children are
 * column - 1, and right children are column + 1. BFS preserves top-to-bottom
 * order and, for nodes at the same depth and column, left-to-right queue order.
 */
public class Day105BinaryTreeVerticalOrderTraversal {

    public static final class TreeNode {
        public final int value;
        public TreeNode left;
        public TreeNode right;

        public TreeNode(int value) {
            this.value = value;
        }
    }

    private static final class Entry {
        private final TreeNode node;
        private final int column;

        private Entry(TreeNode node, int column) {
            this.node = node;
            this.column = column;
        }
    }

    /**
     * Returns columns from leftmost to rightmost. Nodes in each column appear
     * top-to-bottom; ties at the same level are encountered left-to-right.
     *
     * Time: O(n log c), where c is the number of columns.
     * Space: O(n) for the queue and output.
     */
    public static List<List<Integer>> verticalOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        Map<Integer, List<Integer>> columns = new TreeMap<>();
        Queue<Entry> queue = new ArrayDeque<>();
        queue.offer(new Entry(root, 0));

        while (!queue.isEmpty()) {
            Entry entry = queue.poll();
            columns.computeIfAbsent(entry.column, ignored -> new ArrayList<>())
                    .add(entry.node.value);

            if (entry.node.left != null) {
                queue.offer(new Entry(entry.node.left, entry.column - 1));
            }
            if (entry.node.right != null) {
                queue.offer(new Entry(entry.node.right, entry.column + 1));
            }
        }

        result.addAll(columns.values());
        return result;
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    public static void main(String[] args) {
        //           1
        //         /   \
        //        2     3
        //       / \   / \
        //      4   5 6   7
        //         /     /
        //        8     9
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
        root.right.left = new TreeNode(6);
        root.right.right = new TreeNode(7);
        root.left.right.left = new TreeNode(8);
        root.right.right.left = new TreeNode(9);

        List<List<Integer>> expected = List.of(
                List.of(4),
                List.of(2),
                List.of(1, 5, 6),
                List.of(8, 3, 9),
                List.of(7)
        );
        check(verticalOrder(root).equals(expected),
                "Unexpected vertical order: " + verticalOrder(root));
        check(verticalOrder(null).isEmpty(),
                "Empty tree should return no columns");

        TreeNode single = new TreeNode(42);
        check(verticalOrder(single).equals(List.of(List.of(42))),
                "Single-node case failed");

        System.out.println("All vertical order traversal checks passed.");
    }
}
