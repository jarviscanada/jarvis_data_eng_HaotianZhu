package ca.jrvs.apps.practice.search;

import java.util.List;

public class QuickSort {

    public void quickSort(List<Integer> arr, Integer begin, Integer end) {
        if (begin < end) {
            int partitionIndex = partition(arr, begin, end);
            quickSort(arr, begin, partitionIndex);
            quickSort(arr, partitionIndex+1, end);
        }
    }

    private Integer partition(List<Integer> arr, Integer begin, Integer end) {

        Integer pivotIndex = begin;
        Integer pivotVal = arr.get(end-1);

        while(arr.get(pivotIndex) < pivotVal){
            pivotIndex += 1;
        }

        for(Integer current = pivotIndex+1; current < arr.size(); current += 1){
            if(arr.get(current) < pivotVal){
                Integer tmp = arr.get(pivotIndex);
                arr.set(pivotIndex, arr.get(current));
                arr.set(current, tmp);
                pivotIndex += 1;
            }
        }

        arr.set(end-1, arr.get(pivotIndex));
        arr.set(pivotIndex, pivotVal);
        return pivotIndex;
    }
}
