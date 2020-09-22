package ca.jrvs.apps.practice.dataStructure;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JBSTreeTest {

    @Test
    void insert() {
        JBSTree<Integer> tree = new JBSTree<>(Integer::compareTo);
        tree.insert(1);
        tree.insert(2);
        List<Integer> ints = tree.inOrder();
        assertTrue(ints.contains(1));
        assertTrue(ints.contains(2));
    }

    @Test
    void search() {
        JBSTree<Integer> tree = new JBSTree<>(Integer::compareTo);
        tree.insert(3);
        tree.insert(1);
        tree.insert(2);
        tree.insert(4);
        tree.insert(5);
        assertEquals(tree.search(1),1 );
        assertEquals(tree.search(2),2 );
        assertEquals(tree.search(3),3 );
        assertEquals(tree.search(4),4 );
        assertEquals(tree.search(0),null);
    }

    @Test
    void remove() {
        JBSTree<Integer> tree = new JBSTree<>(Integer::compareTo);
        tree.insert(3);
        tree.insert(1);
        tree.insert(2);
        tree.insert(4);
        tree.insert(5);
        tree.remove(3);
        tree.remove(4);
        assertEquals(tree.search(1),1 );
        assertEquals(tree.search(2),2 );
        assertEquals(tree.search(3),null );
        assertEquals(tree.search(4),null );
        assertThrows(IllegalArgumentException.class, ()->tree.remove(4));

    }

    @Test
    void preOrder() {
        JBSTree<Integer> tree = new JBSTree<>(Integer::compareTo);
        tree.insert(4);
        tree.insert(2);
        tree.insert(6);
        tree.insert(1);
        tree.insert(3);
        tree.insert(5);
    //         4
    //      2     6
    //     1  3  5
        List<Integer> ints = new ArrayList<>();
        ints.add(4);
        ints.add(2);
        ints.add(1);
        ints.add(3);
        ints.add(6);
        ints.add(5);
        assertEquals(ints.toString(), tree.preOrder().toString());
    }

    @Test
    void inOrder() {
        JBSTree<Integer> tree = new JBSTree<>(Integer::compareTo);
        tree.insert(4);
        tree.insert(2);
        tree.insert(6);
        tree.insert(1);
        tree.insert(3);
        tree.insert(5);
        //         4
        //      2     6
        //     1  3  5
        List<Integer> ints = new ArrayList<>();
        ints.add(1);
        ints.add(2);
        ints.add(3);
        ints.add(4);
        ints.add(5);
        ints.add(6);
        assertEquals(ints.toString(), tree.inOrder().toString());
    }

    @Test
    void postOrder() {
        JBSTree<Integer> tree = new JBSTree<>(Integer::compareTo);
        tree.insert(4);
        tree.insert(2);
        tree.insert(6);
        tree.insert(1);
        tree.insert(3);
        tree.insert(5);
        //         4
        //      2     6
        //     1  3  5
        List<Integer> ints = new ArrayList<>();
        ints.add(1);
        ints.add(3);
        ints.add(2);
        ints.add(5);
        ints.add(6);
        ints.add(4);

        assertEquals(ints.toString(), tree.postOrder().toString());
    }

    @Test
    void findHeight() {
        JBSTree<Integer> tree = new JBSTree<>(Integer::compareTo);
        tree.insert(4);
        tree.insert(2);
        tree.insert(6);
        tree.insert(1);
        tree.insert(3);
        tree.insert(5);
        //         4
        //      2     6
        //     1  3  5
        assertEquals(tree.findHeight(),3);
        tree.remove(4);
        //
        //      2
        //     1  3
        //          6
        //         5
        assertEquals(tree.findHeight(),4);
    }
}