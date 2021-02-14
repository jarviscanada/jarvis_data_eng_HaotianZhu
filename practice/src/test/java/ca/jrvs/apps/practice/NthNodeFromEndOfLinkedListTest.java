package ca.jrvs.apps.practice;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class NthNodeFromEndOfLinkedListTest {

    @Test
    void solve() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(0);
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        assertEquals(NthNodeFromEndOfLinkedList.solve(list, 1), 4);
        assertEquals(NthNodeFromEndOfLinkedList.solve(list, 2), 3);
        assertThrows( IndexOutOfBoundsException.class, ()-> NthNodeFromEndOfLinkedList.solve(list, 7));
    }
}