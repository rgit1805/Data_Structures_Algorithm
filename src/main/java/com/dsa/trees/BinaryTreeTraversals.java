package com.dsa.trees;

import java.util.ArrayList;
import java.util.List;

/**
 * Classic binary tree traversals: inorder, preorder, postorder.
 * Time: O(n) | Space: O(h) recursion stack
 */
public class BinaryTreeTraversals {

    public static class TreeNode {
        int val;
        TreeNode left, right;
        TreeNode(int val) { this.val = val; }
    }

    public List<Integer> inorder(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        inorderHelper(root, result);
        return result;
    }

    private void inorderHelper(TreeNode node, List<Integer> result) {
        if (node == null) return;
        inorderHelper(node.left, result);
        result.add(node.val);
        inorderHelper(node.right, result);
    }
}
