package ca.jrvs.apps.practice;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class FindSmallestLargest implements Solution {
    public int[] loopSolution(int[] arr){
        if(arr.length == 0){
            return null;
        }

        int smallest = arr[0];
        int largest = arr[0];

        for(int x : arr){
            if(x < smallest){
                smallest = x;
            }
            if(x > largest){
                largest = x;
            }
        }
        return new int[]{smallest, largest};
    }

    public int[] streamSolution(int[] arr){
        if(arr.length == 0){
            return null;
        }

        IntStream intStream = Arrays.stream(arr);

        int largest = intStream.max().getAsInt();
        int smallest = intStream.min().getAsInt();
        return new int[]{smallest, largest};
    }


    public int[] collectionSolution(int[] arr){
        if(arr.length == 0){
            return null;
        }
        int smallest = arr[0];
        int largest = arr[0];

        List<Integer> list = Arrays.stream(arr).boxed().collect(Collectors.toList());
        largest = Collections.max(list, Comparator.comparingInt(a -> a)).intValue();
        smallest =Collections.min(list, Comparator.comparingInt(a -> a));
        return new int[]{smallest, largest};
    }
}
