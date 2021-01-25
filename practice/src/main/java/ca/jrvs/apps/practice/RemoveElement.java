package ca.jrvs.apps.practice;

public class RemoveElement implements Solution{
    public int solution(int[] nums, int val){
        int left = 0;
        int right = nums.length-1;

        while(right >= 0 && nums[right] == val){
            right --;
        }

        while(left<right){
            if(nums[left] == val){
                // remove element
                nums[left] = nums[right];
            }

            while(nums[right] == val){
                right --;
            }

            left++;
        }
        return left;
    }
}
