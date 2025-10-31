package org.example.leetcode.topinterview.array;

/**
 * https://leetcode.cn/problems/best-time-to-buy-and-sell-stock/description/?envType=study-plan-v2&envId=top-interview-150
 * 买卖股票最佳时机
 * 给定一个数组 prices ，它的第 i 个元素 prices[i] 表示一支给定股票第 i 天的价格。
 * 你只能选择 某一天 买入这只股票，并选择在 未来的某一个不同的日子 卖出该股票。设计一个算法来计算你所能获取的最大利润。
 * 返回你可以从这笔交易中获取的最大利润。如果你不能获取任何利润，返回 0 。
 */
public class BestTimeToBuyAndSellStock {

    public static void main(String[] args) {
        int[] prices = new int[]{7,1,5,3,6,4};
        int i = maxProfit(prices);
        System.out.println(i);

    }

    public static int maxProfit(int[] prices) {
        int[][] dp = new int[2][prices.length];
        // dp[0][i] 是第i天不持有， dp[1][i]是第i天持有
        dp[0][0] = 0;
        dp[1][0] = -prices[0];
        for (int i = 1; i < prices.length; i++) {
//            不持有 = 前一天不持有 或者 前一天持有当天卖掉
            dp[0][i] = Math.max(dp[0][i-1], dp[1][i-1] + prices[i]);
//            持有 = 前一天持有 或者 前一天不持有今天买了， -prices[i] 是因为只能购买一次
            dp[1][i] = Math.max(dp[1][i-1], -prices[i]);
        }
        return Math.max(dp[0][prices.length-1], dp[1][prices.length-1]);
    }
}
