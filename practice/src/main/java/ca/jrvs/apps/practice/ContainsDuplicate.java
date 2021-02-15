package ca.jrvs.apps.practice;

import java.util.HashSet;

public class ContainsDuplicate implements Solution {
    public boolean solution(int[] nums){
        HashSet<Integer> map = new HashSet<>();
        for(int x: nums){
            if(map.contains(x)){
                return true;
            }
            map.add(x);
        }
        return false;
    }
}
