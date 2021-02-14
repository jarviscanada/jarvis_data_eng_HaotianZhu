package ca.jrvs.apps.practice.search;

import java.util.ArrayList;
import java.util.List;

public class MergeSort {

    public static void mergeSort(List<Integer> arr) {
        List<Integer> r = divideMerge(arr);
        for(Integer i=0; i< arr.size(); i++){
            arr.set(i, r.get(i));
        }
    }

    private static List<Integer> merge(List<Integer> left, List<Integer> right){
        List<Integer> mergedArray = new ArrayList<>(left.size()+right.size());
        Integer leftIndex = 0;
        Integer rightIndex = 0;
        while(leftIndex < left.size() && rightIndex < right.size()){
            if(left.get(leftIndex) < right.get(rightIndex)){
                mergedArray.add(left.get(leftIndex));
                leftIndex += 1;
            } else {
                mergedArray.add(right.get(rightIndex));
                rightIndex += 1;
            }
        }

        while(leftIndex < left.size()){
            mergedArray.add(left.get(leftIndex));
            leftIndex += 1;
        }
        while(rightIndex < right.size()){
            mergedArray.add(right.get(rightIndex));
            rightIndex += 1;
        }
        return mergedArray;
    }

    private static List<Integer> divideMerge(List<Integer> arr){
        if(arr.size() == 1){
            return arr;
        }
        List<Integer> left = new ArrayList<>();
        List<Integer> right = new ArrayList<>();
        Integer m = (arr.size() + 0)/2;
        for(Integer i =0; i< m; i++){
            left.add(arr.get(i));
        }
        for(Integer i =m; i< arr.size(); i++){
            right.add(arr.get(i));
        }
        left = divideMerge(left);
        right = divideMerge(right);
        return merge(left, right);
    }
}
