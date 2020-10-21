package ca.jrvs.apps.practice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class twoSumTest {
    ArrayList<Integer> arr;
    Integer target;
    twoSum solution = new twoSum();
    @BeforeEach
    void setUp(){
        arr = new ArrayList<>();
        arr.add(3);
        arr.add(7);
        arr.add(4);
        arr.add(2);
        arr.add(1);
        target = 8;
    }

    @Test
    void bruteForce() {
        ArrayList<Integer> r = solution.bruteForce(arr, target);
        assertTrue(r.contains(1));
        assertTrue(r.contains(4));
    }

    @Test
    void useSort() {
        ArrayList<Integer> r = solution.bruteForce(arr, target);
        assertTrue(r.contains(1));
        assertTrue(r.contains(4));
    }

    @Test
    void useHashMap() {
        ArrayList<Integer> r = solution.bruteForce(arr, target);
        assertTrue(r.contains(1));
        assertTrue(r.contains(4));
    }
}