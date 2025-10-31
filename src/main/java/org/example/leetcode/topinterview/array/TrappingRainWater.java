package org.example.leetcode.topinterview.array;

/**
 * https://leetcode.cn/problems/trapping-rain-water/description/?envType=study-plan-v2&envId=top-interview-150
 * 接雨水
 * 给定 n 个非负整数表示每个宽度为 1 的柱子的高度图，计算按此排列的柱子，下雨之后能接多少雨水。
 */
public class TrappingRainWater {

    public static void main(String[] args) {
        int[] height = new int[]{0,1,0,2,1,0,1,3,2,1,2,1};
        System.out.println(trap(height));
    }

    public static int trap(int[] height) {
        // leftMax[i] 表示 height[0]到height[i] 之间的最大值
        int[] leftMax = new int[height.length];
        // rightMax[i] 表示 到height[i] 到 height[n-1] 之间的最大值
        int[] rightMax = new int[height.length];
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < height.length; i++) {
            max = Math.max(max, height[i]);
            leftMax[i] = max;
        }
        max = Integer.MIN_VALUE;
        for (int j = height.length-1; j >= 0; j--) {
            max = Math.max(max, height[j]);
            rightMax[j] = max;
        }
        int result = 0;
        for (int i = 0; i < height.length; i++) {
            result += Math.min(leftMax[i], rightMax[i]) - height[i];
        }
        return result;
    }
}
