package ca.jrvs.apps.practice.search;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class BinarySearchTest {
    public Integer[] arr = new Integer[]{3,5,6,8,10,11,21,35,44,77};
    public BinarySearch binarySearch = new BinarySearch();
    @Test
    void binarySearchRecursion() {
        assertEquals(binarySearch.binarySearchRecursion(arr, 0), Optional.empty());
        assertEquals(binarySearch.binarySearchRecursion(arr, 8).get(),3);
        assertEquals(binarySearch.binarySearchRecursion(arr, 21).get(),6);
    }

    @Test
    void binarySearchIteration() {
        assertEquals(binarySearch.binarySearchIteration(arr, 0), Optional.empty());
        assertEquals(binarySearch.binarySearchIteration(arr, 8).get(),3);
        assertEquals(binarySearch.binarySearchIteration(arr, 21).get(),6);
    }
}