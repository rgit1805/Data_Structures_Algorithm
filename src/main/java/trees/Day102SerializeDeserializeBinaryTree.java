package trees;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Day 102: Serialize and deserialize a binary tree.
 *
 * Serialization uses preorder traversal with a dedicated null marker ("#")
 * and comma delimiters. Including null children makes the representation
 * unambiguous even when different tree shapes have the same values.
 */
public class Day102SerializeDeserializeBinaryTree {

    public static final class TreeNode {
        public final int value;
        public TreeNode left;
        public TreeNode right;

        public TreeNode(int value) {
            this.value = value;
        }
    }

    /** Converts a tree to a preorder string in O(n) time and O(n) space. */
    public static String serialize(TreeNode root) {
        StringBuilder output = new StringBuilder();
        writePreorder(root, output);
        return output.toString();
    }

    private static void writePreorder(TreeNode node, StringBuilder output) {
        if (node == null) {
            output.append("#,");
            return;
        }

        output.append(node.value).append(',');
        writePreorder(node.left, output);
        writePreorder(node.right, output);
    }

    /**
     * Reconstructs a tree from serialize() output.
     * Rejects null, empty, truncated, malformed, or trailing-token data.
     */
    public static TreeNode deserialize(String data) {
        if (data == null || data.isEmpty()) {
            throw new IllegalArgumentException("Serialized data must not be null or empty");
        }

        String[] tokens = data.split(",", -1);
        // serialize() always appends one trailing comma.
        if (!tokens[tokens.length - 1].isEmpty()) {
            throw new IllegalArgumentException("Serialized data must end with a delimiter");
        }

        Deque<String> remaining = new ArrayDeque<>();
        for (int i = 0; i < tokens.length - 1; i++) {
            if (tokens[i].isEmpty()) {
                throw new IllegalArgumentException("Serialized data contains an empty token");
            }
            remaining.addLast(tokens[i]);
        }

        TreeNode root = readPreorder(remaining);
        if (!remaining.isEmpty()) {
            throw new IllegalArgumentException("Serialized data contains trailing tokens");
        }
        return root;
    }

    private static TreeNode readPreorder(Deque<String> tokens) {
        if (tokens.isEmpty()) {
            throw new IllegalArgumentException("Serialized data ended unexpectedly");
        }

        String token = tokens.removeFirst();
        if (token.equals("#")) {
            return null;
        }

        final int value;
        try {
            value = Integer.parseInt(token);
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("Invalid node value: " + token, exception);
        }

        TreeNode node = new TreeNode(value);
        node.left = readPreorder(tokens);
        node.right = readPreorder(tokens);
        return node;
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
        //        /
        //       4
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.right.left = new TreeNode(4);

        String encoded = serialize(root);
        check(encoded.equals("1,2,#,#,3,4,#,#,#,"),
                "Unexpected serialization: " + encoded);

        TreeNode decoded = deserialize(encoded);
        check(serialize(decoded).equals(encoded),
                "Serialization should be preserved after round-trip");

        check(serialize(null).equals("#,"), "Null tree serialization is incorrect");
        check(deserialize("#,") == null, "Null tree should deserialize to null");

        TreeNode single = new TreeNode(-42);
        check(serialize(deserialize(serialize(single))).equals(serialize(single)),
                "Single-node tree round-trip failed");

        check(throwsIllegalArgument(() -> deserialize(null)),
                "Null serialized data should be rejected");
        check(throwsIllegalArgument(() -> deserialize("1,#,")),
                "Truncated tree data should be rejected");
        check(throwsIllegalArgument(() -> deserialize("x,#,#,")),
                "Non-integer node token should be rejected");
        check(throwsIllegalArgument(() -> deserialize("#,#,")),
                "Trailing tokens should be rejected");

        System.out.println("All serialization/deserialization checks passed.");
    }
}
