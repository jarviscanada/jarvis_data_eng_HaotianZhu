package ca.jrvs.apps.practice;

import ca.jrvs.apps.practice.Solution;

public class MergeSortedArray implements Solution {
    public void solution(int[] nums1, int m, int[] nums2, int n) {

        int index1 = m-1;
        int index2 = n-1;
        int pos = m+n-1;
        while(index1 >=0 && index2 >= 0){
            if(nums1[index1] >= nums2[index2]){
                nums1[pos] = nums1[index1];
                index1 --;
            } else{
                nums1[pos] = nums2[index2];
                index2 --;
            }
            pos --;
        }

        while(index1 >= 0){
            nums1[pos] = nums1[index1];
            pos --;
            index1--;
        }
        while(index2 >= 0){
            nums1[pos] = nums2[index2];
            pos --;
            index2--;
        }

    }
}
