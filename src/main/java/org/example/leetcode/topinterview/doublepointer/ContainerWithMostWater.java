package org.example.leetcode.topinterview.doublepointer;

/**
 * https://leetcode.cn/problems/container-with-most-water/description/?envType=study-plan-v2&envId=top-interview-150
 * 盛最多水的容器
 * 给定一个长度为 n 的整数数组 height 。有 n 条垂线，第 i 条线的两个端点是 (i, 0) 和 (i, height[i]) 。
 * 找出其中的两条线，使得它们与 x 轴共同构成的容器可以容纳最多的水。
 * 返回容器可以储存的最大水量。
 */
public class ContainerWithMostWater {

    public static void main(String[] args) {
        int[] height = new int[]{1,8,6,2,5,4,8,3,7};
        System.out.println(maxArea(height));
    }

    public static int maxArea(int[] height) {
        int i = 0;
        int j = height.length - 1;
        int result = 0;
        while (i < j) {
            // 底乘以高
            result = Math.max(result, (j-i)* Math.min(height[i],height[j]));
            // 移动短的
            if (height[i] > height[j]) {
                j--;
            } else {
                i++;
            }
        }
        return result;
    }
}
