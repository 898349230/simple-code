package org.example.leetcode.topinterview.tree;

public class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode() {}
    TreeNode(int val) { this.val = val; }
    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        return toStringHelper(this, "", true);
    }

    private String toStringHelper(TreeNode node, String prefix, boolean isTail) {
        if (node == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(prefix).append(isTail ? "└── " : "├── ").append(node.val).append("\n");
        String newPrefix = prefix + (isTail ? "    " : "│   ");
        if (node.left != null || node.right != null) {
            sb.append(toStringHelper(node.left, newPrefix, node.right == null));
            sb.append(toStringHelper(node.right, newPrefix, true));
        }
        return sb.toString();
    }
}
