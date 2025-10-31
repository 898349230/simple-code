package org.example.leetcode.topinterview.array;

/**
 * https://leetcode.cn/problems/jump-game/description/?envType=study-plan-v2&envId=top-interview-150
 * 跳跃游戏
 * 给你一个非负整数数组 nums ，你最初位于数组的 第一个下标 。数组中的每个元素代表你在该位置可以跳跃的最大长度。
 * 判断你是否能够到达最后一个下标，如果可以，返回 true ；否则，返回 false 。
 */
public class JumpGame {

    public static void main(String[] args) {
        int[] nums = new int[]{2,3,1,1,4};
        System.out.println(canJump(nums));
    }

    public static boolean canJump(int[] nums) {
        // 当前位置（能跳跃的最大位置）
        int p = 0;
        // 遍历每一个元素
        for (int i = 0; i < nums.length; i++) {
            // 如果
            if (i <= p) {
                // 当前位置可以跳到的最大位置，i + nums[i] 是从 i 位置开始跳（i位置上一次循环肯定可以跳到）的最大位置
                p = Math.max(p, i + nums[i]);
                if (p >= nums.length - 1) {
                    return true;
                }
            }
        }
        return false;
    }
}
