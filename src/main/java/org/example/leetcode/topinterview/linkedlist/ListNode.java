package org.example.leetcode.topinterview.linkedlist;

public class ListNode {

    public int val;
    public ListNode next;
     public ListNode() {}
    public ListNode(int val) { this.val = val; }
     public ListNode(int val, ListNode next) {
         this.val = val;
         this.next = next;
     }

    public void setVal(int val) {
        this.val = val;
    }


    public void setNext(ListNode next) {
        this.next = next;
    }

    @Override
    public String toString() {
         StringBuilder result = new StringBuilder(this.val + "");
         ListNode temp = this.next;
        while (temp != null) {
            result.append(" -> ").append(temp.val);
            temp = temp.next;
        }
        return result.toString();
    }
}
