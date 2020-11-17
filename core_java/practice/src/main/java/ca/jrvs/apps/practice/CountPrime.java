package ca.jrvs.apps.practice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CountPrime implements Solution{
    public int solution(int n){
        if(n < 2){
            return 0;
        }
        Boolean[] isPrime = new Boolean[n];
        Arrays.fill(isPrime, Boolean.TRUE);
        isPrime[0] = Boolean.FALSE;
        isPrime[1] = Boolean.FALSE;


        for(int i=2; i< n ; i++){


            if(isPrime[i] == Boolean.TRUE){
                // if not prime
                for(int j=i+i; j<n; j+=i){
                    isPrime[j] = Boolean.FALSE;
                }
            }
        }

        return (int) Arrays.stream(isPrime).filter(x->x).count();
    }
}
