package ca.jrvs.apps.practice;

public class SwapTwoNumbers implements Solution {
    public void bitOperation(int[] arr){
         arr[1] = arr[0]^arr[1];
         arr[0] = arr[0]^arr[1];
         arr[1] = arr[0]^arr[1];
    }

    public void arithmetic(int[] arr){
        arr[1] = arr[0]+arr[1];
        arr[0] = arr[1] - arr[0];
        arr[1] = arr[1] - arr[0];
    }
}
