package ca.jrvs.apps.practice;

import java.util.HashMap;
import java.util.HashSet;

public class MissingNumber implements Solution {
    public int sumSolution(int[] nums){
        Long total = (long) ((nums.length + 1) * nums.length / 2);

        for(int x: nums){
            total-=x;

        }
        return total.intValue();
    }

    public int hashSolution(int[] nums){
        HashSet<Integer> map = new HashSet<>();
        for(int num: nums){
            map.add(num);
        }
        for(Integer i=0; i<nums.length+1 ;i++){
            if(!map.contains(i)){
                return i;
            }
        }
        return -1;
    }
}
