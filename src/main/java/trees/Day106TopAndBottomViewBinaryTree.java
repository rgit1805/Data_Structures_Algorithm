package trees;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;

/**
 * Day 106: Top view and bottom view of a binary tree.
 *
 * BFS visits nodes level by level. Each node is assigned a horizontal distance:
 * left child = distance - 1, right child = distance + 1.
 * Top view keeps the first node seen at each distance; bottom view overwrites
 * the value so the last node seen at each distance is retained.
 */
public class Day106TopAndBottomViewBinaryTree {

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
        private final int horizontalDistance;

        private Entry(TreeNode node, int horizontalDistance) {
            this.node = node;
            this.horizontalDistance = horizontalDistance;
        }
    }

    /** Returns the first BFS node encountered at each horizontal distance. */
    public static List<Integer> topView(TreeNode root) {
        Map<Integer, Integer> view = new TreeMap<>();
        traverseView(root, view, true);
        return new ArrayList<>(view.values());
    }

    /** Returns the last BFS node encountered at each horizontal distance. */
    public static List<Integer> bottomView(TreeNode root) {
        Map<Integer, Integer> view = new TreeMap<>();
        traverseView(root, view, false);
        return new ArrayList<>(view.values());
    }

    private static void traverseView(
            TreeNode root, Map<Integer, Integer> view, boolean keepFirst) {
        if (root == null) {
            return;
        }

        Queue<Entry> queue = new ArrayDeque<>();
        queue.offer(new Entry(root, 0));

        while (!queue.isEmpty()) {
            Entry entry = queue.poll();
            int distance = entry.horizontalDistance;

            if (!keepFirst || !view.containsKey(distance)) {
                view.put(distance, entry.node.value);
            }

            if (entry.node.left != null) {
                queue.offer(new Entry(entry.node.left, distance - 1));
            }
            if (entry.node.right != null) {
                queue.offer(new Entry(entry.node.right, distance + 1));
            }
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
        //        \   /
        //         4 5
        //        / \
        //       6   7
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.right = new TreeNode(4);
        root.right.left = new TreeNode(5);
        root.left.right.left = new TreeNode(6);
        root.left.right.right = new TreeNode(7);

        check(topView(root).equals(List.of(2, 1, 3)),
                "Unexpected top view: " + topView(root));
        check(bottomView(root).equals(List.of(6, 5, 7)),
                "Unexpected bottom view: " + bottomView(root));
        check(topView(null).isEmpty(), "Empty tree top view should be empty");
        check(bottomView(null).isEmpty(), "Empty tree bottom view should be empty");

        TreeNode single = new TreeNode(42);
        check(topView(single).equals(List.of(42)), "Single-node top view failed");
        check(bottomView(single).equals(List.of(42)), "Single-node bottom view failed");

        System.out.println("All top and bottom view checks passed.");
    }
}
