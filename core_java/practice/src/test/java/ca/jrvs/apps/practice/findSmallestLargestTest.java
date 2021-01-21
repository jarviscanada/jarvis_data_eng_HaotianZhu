package ca.jrvs.apps.practice;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FindSmallestLargestTest {

    int[] arr1 = new int[0];
    int[] arr2 = new int[]{1,4,3,2,7,-2,9,101};

    @Test
    void loopSolution() {
        FindSmallestLargest findSmallestLargest = new FindSmallestLargest();
        int[] r = findSmallestLargest.loopSolution(arr1);
        assertNull(r);
        r = findSmallestLargest.loopSolution(arr2);
        assertEquals(r[0], -2);
        assertEquals(r[1], 101);
    }

    @Test
    void streamSolution() {
        FindSmallestLargest findSmallestLargest = new FindSmallestLargest();
        int[] r = findSmallestLargest.streamSolution(arr1);
        assertNull(r);
        r = findSmallestLargest.loopSolution(arr2);
        assertEquals(r[0], -2);
        assertEquals(r[1], 101);
    }

    @Test
    void collectionSolution() {
        FindSmallestLargest findSmallestLargest = new FindSmallestLargest();
        int[] r = findSmallestLargest.collectionSolution(arr1);
        assertNull(r);
        r = findSmallestLargest.loopSolution(arr2);
        assertEquals(r[0], -2);
        assertEquals(r[1], 101);
    }
}