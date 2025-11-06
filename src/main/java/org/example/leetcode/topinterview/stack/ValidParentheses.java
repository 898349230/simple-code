package org.example.leetcode.topinterview.stack;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * https://leetcode.cn/problems/valid-parentheses/description/?envType=study-plan-v2&envId=top-interview-150
 * 有效的括号
 * 给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串 s ，判断字符串是否有效。
 * 有效字符串需满足：
 * 左括号必须用相同类型的右括号闭合。
 * 左括号必须以正确的顺序闭合。
 * 每个右括号都有一个对应的相同类型的左括号。
 */
public class ValidParentheses {
    public static void main(String[] args) {
        String s = "([)[])";
        System.out.println(isValid(s));
    }
    public static boolean isValid(String s) {
        char[] charArray = s.toCharArray();
        Map<Character, Character> map = new HashMap<>();
        map.put('}', '{');
        map.put(']', '[');
        map.put(')', '(');
        Stack<Character> stack = new Stack<>();
        for (char c : charArray) {
            if (!stack.isEmpty() && stack.peek().equals(map.get(c))) {
                stack.pop();
            } else {
                stack.push(c);
            }

        }
        return stack.isEmpty();
    }
}
