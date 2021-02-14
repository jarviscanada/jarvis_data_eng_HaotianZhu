package ca.jrvs.apps.practice;

import ca.jrvs.apps.practice.Solution;

public class RemoveDuplicatesFromSortedArray implements Solution {
    public int solution(int[] nums){
        int len = 0;
        for(int current=0; current< nums.length; current++){
            if(nums[len] != nums[current]){
                len += 1;
                nums[len] = nums[current];
            }
        }
        return len+1;
    }
}
