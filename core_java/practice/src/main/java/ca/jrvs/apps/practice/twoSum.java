package ca.jrvs.apps.practice;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 *
 * https://www.notion.so/e16f5158c43d4e1f8bb242fa4d7b9f2d?v=e7b12bd0067641989f651673d69cb5dc&p=a884c2a98f474110974ca4d0f3015fb3
 *  */
public class twoSum implements Solution{


    public ArrayList<Integer> bruteForce(ArrayList<Integer> sums, Integer target){
        ArrayList<Integer> arr = new ArrayList<>();
        for(Integer i = 0; i<sums.size()-1; i++){
            for(Integer j = i+1; j<sums.size(); j++){
                if(sums.get(i) + sums.get(j) == target){
                    arr.add(i);
                    arr.add(j);
                    return arr;
                }

            }
        }
        return arr;
    }

    public ArrayList<Integer> useSort(ArrayList<Integer> sums, Integer target){
        sums.sort(Integer::compareTo);
        ArrayList<Integer> arr = new ArrayList<>();
        Integer i = 0;
        Integer j = sums.size()-1;
        while(i<j){
            if(sums.get(i) + sums.get(j) == target){
                arr.add(i);
                arr.add(j);
                return arr;
            } else if(sums.get(i) + sums.get(j) < target){
                i ++;
            } else {
                j--;
            }
        }
        return arr;

    }

    public ArrayList<Integer> useHashMap(ArrayList<Integer> sums, Integer target){
        HashMap<Integer, Integer> map = new HashMap<>();
        ArrayList<Integer> arr = new ArrayList<>();

        for(Integer i=0; i<sums.size(); i++){
            map.put(sums.get(i), i);
        }
        for(Integer i=0; i<sums.size(); i++){
            if(map.containsKey(target - sums.get(i))){
                arr.add(i);
                arr.add(map.get(target - sums.get(i)));
                return arr;
            }
        }
        return arr;

    }
}
