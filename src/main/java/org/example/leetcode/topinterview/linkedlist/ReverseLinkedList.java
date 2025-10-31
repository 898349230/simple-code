package org.example.leetcode.topinterview.linkedlist;

/**
 * https://leetcode.cn/problems/reverse-linked-list/description/
 * 反转链表
 */
public class ReverseLinkedList {
    public static void main(String[] args) {
        ListNode head = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5, null)))));
        System.out.println(reverseList(head));
    }

    public static ListNode reverseList(ListNode head) {
        ListNode pre = null;
        ListNode cur = head;
        while (cur != null) {
            // 临时存储下一个节点
            ListNode next = cur.next;
            // cur 和原 next 节点断开
            cur.next = pre;
            // pre 和 cur 都向前移动
            pre = cur;
            cur = next;
        }
        return pre;
    }
}
