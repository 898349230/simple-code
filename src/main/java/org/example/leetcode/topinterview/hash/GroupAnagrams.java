package org.example.leetcode.topinterview.hash;

import java.util.*;

/**
 * https://leetcode.cn/problems/group-anagrams/?envType=study-plan-v2&envId=top-interview-150
 * 字母异位词分组
 * 给你一个字符串数组，请你将 字母异位词 组合在一起。可以按任意顺序返回结果列表。
 * 输入: strs = ["eat", "tea", "tan", "ate", "nat", "bat"]
 * 输出: [["bat"],["nat","tan"],["ate","eat","tea"]]
 */
public class GroupAnagrams {

    public static void main(String[] args) {
        String[] strs = new String[]{"eat", "tea", "tan", "ate", "nat", "bat"};
        System.out.println(groupAnagrams(strs));
    }

    public static List<List<String>> groupAnagrams(String[] strs) {
        List<List<String>> result = new ArrayList<>();
        // key 是每个字符和字符出现数量拼一起的字符串， 如果是value是 eat 则 key是 a-1e-1t-1
        Map<String, List<String>> map = new HashMap<>();
        for (String str : strs) {
            char[] charArray = str.toCharArray();
            int[] arr = new int[26];
            // 字符出现次数
            for (int i = 0; i < charArray.length; i++) {
               arr[charArray[i] - 'a']++;
            }
            String key = "";
            for (int i = 0; i < arr.length; i++) {
                // 当前字母存在
                if (arr[i] > 0) {
                    key = key + (char) (i + 'a');
                    key = key + arr[i];
                }
            }
            List<String> list = map.getOrDefault(key, new ArrayList<>());
            list.add(str);
            map.put(key, list);
        }
        return map.values().stream().toList();
    }
}
