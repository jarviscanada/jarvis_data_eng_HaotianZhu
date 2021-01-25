package ca.jrvs.apps.practice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FibonacciNumberAndClimbingStairs implements Solution{

    public Integer fibonacciNumber(Integer n){
        if(n == 0){
            return 0;
        }

        if(n == 1){
            return  1;
        }

        Integer prev = 0;
        Integer current = 1;
        Integer next;

        for(Integer i=2; i<n+1; i++){
            next = current + prev;
            prev = current;
            current = next;
        }
        return current;
    }

    public Integer climbingStairs(Integer n){
        List<Integer> arr = new ArrayList<>(Arrays.asList(new Integer[n+1]));
        arr.set(0,0);
        arr.set(1,1);
        arr.set(2,2);

        this._climbingStairs(n,arr);


        return arr.get(n);
    }

    private Integer _climbingStairs(Integer n,List<Integer> arr){

        if(arr.get(n-1) == null){
            _climbingStairs(n-1, arr);
        }

        if(arr.get(n-2) == null){
           _climbingStairs(n-2, arr);
        }

        return arr.set(n, arr.get(n-1) + arr.get(n-2));

    }

}
