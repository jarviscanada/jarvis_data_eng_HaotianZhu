package ca.jrvs.apps.practice.dataStructure;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JHashSetTest {

    @Test
    void size() {
        JHashSet<Integer> set = new JHashSet<>();
        set.add(1);
        set.add(999);
        assertEquals(set.size(), 2);
        assertFalse(set.add(1));
    }

    @Test
    void contains() {
        JHashSet<Integer> set = new JHashSet<>();
        set.add(1);
        set.add(999);
        assertTrue(set.contains(999));
        assertFalse(set.contains(2));
    }


    @Test
    void remove() {
        JHashSet<Integer> set = new JHashSet<>();
        set.add(1);
        set.add(999);
        set.remove(1);
        assertFalse(set.contains(1));
        assertThrows(NullPointerException.class, ()->set.remove(null));
        assertFalse(set.remove(-1));
    }

    @Test
    void clear() {
        JHashSet<Integer> set = new JHashSet<>();
        set.add(1);
        set.add(999);
        set.clear();
        assertFalse(set.contains(1));
        assertFalse(set.contains(999));
    }
}