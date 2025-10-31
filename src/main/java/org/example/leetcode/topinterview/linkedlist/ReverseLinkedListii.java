package org.example.leetcode.topinterview.linkedlist;

/**
 * https://leetcode.cn/problems/reverse-linked-list-ii/?envType=study-plan-v2&envId=top-interview-150
 * 反转链表 II
 * 给你单链表的头指针 head 和两个整数 left 和 right ，其中 left <= right 。
 * 请你反转从位置 left 到位置 right 的链表节点，返回 反转后的链表 。
 */
public class ReverseLinkedListii {
    public static void main(String[] args) {
        ListNode head = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5, null)))));
        System.out.println(reverseBetween(head, 2, 4));
    }

    public static ListNode reverseBetween(ListNode head, int left, int right) {
        ListNode current = head;
        int i = 1;
        while (current != null) {
            if (i >= left) {

            }
        }
        return head;
    }
}
