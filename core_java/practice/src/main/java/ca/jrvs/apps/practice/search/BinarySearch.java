package ca.jrvs.apps.practice.search;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class BinarySearch {

    /**
     * find the the target index in a sorted array
     *
     * @param arr input arry is sorted
     * @param target value to be searched
     * @return target index or Optional.empty() if not ound
     */
    public <E extends Comparable> Optional<Integer> binarySearchRecursion(E[] arr, E target) {
        List<E> list = new ArrayList<E>(Arrays.asList(arr));
        return Optional.ofNullable(_binarySearchRecursion(list, target, 0, list.size()-1));
    }
    private <E extends Comparable> Integer _binarySearchRecursion(List<E> arr, E target, Integer l, Integer r) {
        if(l == r){
            if(target.compareTo(arr.get(l)) == 0 ) {
                return l;
            }else{
                return null;
            }
        }

        Integer m = (l+r)/2;
        if(target.compareTo(arr.get(m)) < 0 ){
            // left
            return this._binarySearchRecursion(arr, target, l, m);
        } else if(target.compareTo(arr.get(m)) > 0 ) {
            return this._binarySearchRecursion(arr, target, m, r);
        } else {
            return m;
        }
    }

    /**
     * find the the target index in a sorted array
     *
     * @param arr input arry is sorted
     * @param target value to be searched
     * @return target index or Optional.empty() if not ound
     */
    public  <E extends Comparable> Optional<Integer> binarySearchIteration(E[] arr, E target) {
        Integer l = 0;
        Integer r = arr.length-1;
        Integer m = (l+r)/2;
        Boolean found = Boolean.FALSE;
        while(!found && m < arr.length){
            if(target.compareTo(arr[m]) < 0){
                r = m;
            } else if(target.compareTo(arr[m]) > 0){
                l = m;
            } else {
                found = Boolean.TRUE;
            }
            if(l == r){
                break;
            }
            m = (l+r)/2;
        }
        return found?Optional.of(m):Optional.empty();
    }
}