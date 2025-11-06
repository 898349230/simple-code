package org.example.leetcode.topinterview.tree;

import java.util.*;

/**
 * https://leetcode.cn/problems/symmetric-tree/description/?envType=study-plan-v2&envId=top-interview-150
 * 对称二叉树
 * 给你一个二叉树的根节点 root ， 检查它是否轴对称。
 */
public class SymmetricTree {
    public static void main(String[] args) {
        TreeNode root = new TreeNode(1,
                new TreeNode(2, new TreeNode(3), new TreeNode(4)),
                new TreeNode(2, new TreeNode(4), new TreeNode(3)));
        System.out.println(root);
        System.out.println(isSymmetric(root));
    }

    public static boolean isSymmetric(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            List<Integer> list = new ArrayList<>();
            // 层序遍历
            for (int i = 0; i < size; i++) {
                TreeNode current = queue.poll();
                if (current != null) {
                    list.add(current.val);
                    queue.offer(current.left);
                    queue.offer(current.right);
                } else {
                    list.add(null);
                }

            }

            // 每一层的数值是否对称
            for (int i = 0; i < list.size() / 2; i++) {
                Integer i1 = list.get(i);
                Integer i2 = list.get(list.size() - i - 1);
                if (i1 != null && i2 != null) {
                    if (!i1.equals(i2)) return false;
                } else if (i1 != null || i2 != null) {
                    return false;
                }
            }

        }
        return true;
    }

}
