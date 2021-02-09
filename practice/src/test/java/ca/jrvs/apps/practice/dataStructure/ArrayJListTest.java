package ca.jrvs.apps.practice.dataStructure;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArrayJListTest {
    private Integer size = 4;
    @Test
    void add() {
        ArrayJList<Integer> arr = new ArrayJList<Integer>(size);
        arr.add(100);
        assertEquals(arr.get(0), 100);
    }

    @Test
    void toArray() {
        ArrayJList<Integer> arr = new ArrayJList<>(size);
        arr.add(1);
        arr.add(2);
        arr.add(3);
        arr.add(4);
        arr.add(5);
        Object[] newArr = arr.toArray();
        assertTrue(arr.contains(1));
        assertFalse(arr.contains(0));
        assertEquals((int) newArr[0], 1);
        assertEquals((int) newArr[1], 2);
    }

    @Test
    void size() {
        ArrayJList<Integer> arr = new ArrayJList<>(size);
        arr.add(100);
        assertEquals(arr.size(), 1);
        arr.add(1);
        arr.add(1);
        arr.add(1);
        arr.add(1);
        assertEquals(arr.size(), 5);
    }

    @Test
    void isEmpty() {
        ArrayJList<Integer> arr = new ArrayJList<Integer>(size);
        assertTrue(arr.isEmpty());
        arr.add(1);
        assertFalse(arr.isEmpty());
    }

    @Test
    void indexOf() {
        ArrayJList<Integer> arr = new ArrayJList<Integer>(size);
        assertTrue(arr.isEmpty());
        arr.add(1);
        assertFalse(arr.isEmpty());
        arr.remove(0);
        assertTrue(arr.isEmpty());
    }

    @Test
    void contains() {
        ArrayJList<Integer> arr = new ArrayJList<Integer>(size);
        arr.add(1);
        assertTrue(arr.contains(1));
        assertFalse(arr.contains(100));
        assertFalse(arr.contains(null));
        arr.add(null);
        assertTrue(arr.contains(null));
    }

    @Test
    void get() {
        ArrayJList<Integer> arr = new ArrayJList<Integer>(size);
        arr.add(1);
        assertEquals(arr.get(0), 1);
        assertThrows(IndexOutOfBoundsException.class, ()->arr.get(1));
    }

    @Test
    void remove() {
        ArrayJList<Integer> arr = new ArrayJList<Integer>(size);
        arr.add(1);
        assertThrows(IndexOutOfBoundsException.class, ()->arr.remove(1));
        assertEquals(arr.remove(0), 1);
    }

    @Test
    void clear() {
        ArrayJList<Integer> arr = new ArrayJList<Integer>(size);
        arr.add(1);
        arr.add(2);
        arr.add(3);
        arr.add(4);
        arr.add(5);
        assertEquals(arr.size(), 5);
        arr.clear();
        assertEquals(arr.size(), 0);
        assertThrows(IndexOutOfBoundsException.class, ()->arr.get(0));
    }
}