package org.example.leetcode.topinterview.doublepointer;

/**
 * https://leetcode.cn/problems/valid-palindrome/description/?envType=study-plan-v2&envId=top-interview-150
 * 验证回文串
 * 如果在将所有大写字符转换为小写字符、并移除所有非字母数字字符之后，短语正着读和反着读都一样。则可以认为该短语是一个 回文串 。
 * 字母和数字都属于字母数字字符。
 * 给你一个字符串 s，如果它是 回文串 ，返回 true ；否则，返回 false 。
 *
 */
public class ValidPalindrome {

    public static void main(String[] args) {
//        String s = "A man, a plan, a canal: Panama";
        String s = ".,";
        System.out.println(isPalindrome(s));
    }

    public static boolean isPalindrome(String s) {
        String str = "";
        s = s.toLowerCase();
        for (char c : s.toCharArray()) {
            if (Character.isLetterOrDigit(c)) {
                str+=c;
            }
        }

        int i = 0;
        int j = str.length() - 1;
        while (i<j) {
            if (str.charAt(i++) != str.charAt(j--)) {
                return false;
            }
        }
        return true;
    }

    //  String s = ".,";  这种情况过不去
    public static boolean isPalindrome2(String s) {
        int i = 0;
        int j = s.length() - 1;
        s = s.toLowerCase();
        while (i < j) {
            while (i < j && !Character.isLetterOrDigit(s.charAt(i)) || s.charAt(i) == ' ') {
                i++;
            }
            while (i < j && !Character.isLetterOrDigit(s.charAt(j)) || s.charAt(j) == ' ') {
                j--;
            }
            if (s.charAt(i) != s.charAt(j)) {
                return false;
            }
            i++;
            j--;
        }
        return true;
    }
}
