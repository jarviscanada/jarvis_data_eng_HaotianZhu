package ca.jrvs.apps.practice;

import ca.jrvs.apps.practice.Solution;

import java.util.ArrayList;
import java.util.Arrays;


public class ReverseLinkedList implements Solution {

    public class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }

    public ListNode recursive(ListNode head){
        if(head == null){
            return null;
        }
        ArrayList<ListNode> r =  _recursive(head);
        ListNode newHead = r.get(0);
        ListNode parent = r.get(1);
        parent.next = null;
        return newHead;
    }

    public ArrayList<ListNode> _recursive(ListNode node){
        ListNode parent = node;
        ListNode child = node.next;
        ArrayList<ListNode> r;
        if(child == null){
            // reverse head
            r = new ArrayList<>();
            r.add(parent);
            r.add(parent);
        } else {
            r = _recursive(child); // r[0] head, r[1] parent
            r.get(1).next = parent;
            r.set(1, parent);
        }
        return r;
    }

    public ListNode loop(ListNode head){
        if(head == null){
            return null;
        }
        ListNode current = head;
        ListNode next = head.next;
        ListNode nextNext;
        while(next != null){
            nextNext = next.next;
            next.next = current;
            current = next;
            next = nextNext;
        }
        head.next = null;
        return current;
    }
}
