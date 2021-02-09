package ca.jrvs.apps.practice;

import java.util.HashSet;

public class FindDuplicateNumber implements Solution{
    public int solution(int[] nums){
        // nums.length = n+1
        int first = nums[0];
        int second = nums[0];

        first = nums[first];
        second = nums[nums[second]];

        while(first != second){
            first = nums[first];
            second = nums[nums[second]];
        }

        first = nums[0];
        while(first != second){
            first = nums[first];
            second = nums[second];
        }
        return first;

    }
}
