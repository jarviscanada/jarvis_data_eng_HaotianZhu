package ca.jrvs.apps.practice.search;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class QuickSortTest {

    @Test
    void quickSort() {
        QuickSort quickSort = new QuickSort();
        List<Integer> arr = new ArrayList<>(Arrays.asList(new Integer[]{4,3,2,1,5,7,8,6,9}));
        quickSort.quickSort(arr,0, arr.size());
        assertEquals(arr.get(0), 1);
        assertEquals(arr.get(1), 2);
        assertEquals(arr.get(7), 8);
    }
}