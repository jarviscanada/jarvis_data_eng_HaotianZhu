package ca.jrvs.apps.practice;


import ca.jrvs.apps.practice.dataStructure.LinkedJList;

import java.util.LinkedList;

public class NthNodeFromEndOfLinkedList implements Solution {

    static <E> E solve(LinkedList<E> list, Integer n){
        // // LinkedList didn't provide method to access node
        // Node head = list.get(0) //get first node
        // Node tail = list.get(0) //get first node
        // Integer diff = 0;
        // while(diff < n){
        //     tail = tail.next()
        //     diff += 1;
        // }
        // if(diff != n){
        //  return null;
        // }
        // // the difference between the head and tail is n
        // while(tail != null){
        //  tail = tail.next()
        //  head = head.next()
        // }
        //

        return list.get(list.size()-n);
    }
}
