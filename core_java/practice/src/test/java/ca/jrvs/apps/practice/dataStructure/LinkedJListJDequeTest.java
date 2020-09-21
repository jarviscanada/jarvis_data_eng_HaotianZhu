package ca.jrvs.apps.practice.dataStructure;

import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class LinkedJListJDequeTest {

    @Test
    void add() {
        LinkedJListJDeque<Integer> listJDeque = new LinkedJListJDeque<>();
        listJDeque.add(1);
        listJDeque.add(2);
        listJDeque.add(3);
        assertEquals(listJDeque.remove(), 1);
        assertEquals(listJDeque.remove(), 2);
        assertEquals(listJDeque.remove(), 3);
    }

    @Test
    void push() {
        LinkedJListJDeque<Integer> listJDeque = new LinkedJListJDeque<>();
        listJDeque.push(1);
        listJDeque.push(2);
        listJDeque.push(3);
        assertEquals(listJDeque.remove(), 3);
        assertEquals(listJDeque.remove(), 2);
        assertEquals(listJDeque.remove(), 1);
        assertThrows(NoSuchElementException.class, ()-> listJDeque.remove());
    }

    @Test
    void peek() {
        LinkedJListJDeque<Integer> listJDeque = new LinkedJListJDeque<>();
        listJDeque.add(1);
        listJDeque.add(2);
        listJDeque.add(3);
        assertEquals(listJDeque.peek(), listJDeque.remove());
        assertEquals(listJDeque.peek(), listJDeque.remove());
        assertEquals(listJDeque.peek(), listJDeque.remove());

    }
}