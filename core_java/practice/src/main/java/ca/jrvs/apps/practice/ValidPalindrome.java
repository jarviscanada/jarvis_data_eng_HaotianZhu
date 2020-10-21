package ca.jrvs.apps.practice;

public class ValidPalindrome implements Solution {

    public static Boolean twoPoints(String s){
        Integer l = 0;
        Integer r = s.length() - 1;

        while(l<r){
            if(s.charAt(l) != s.charAt(r)){
                return  Boolean.FALSE;
            }
            l++;
            r--;
        }
        return  Boolean.TRUE;
    }

    public static Boolean recursive(String s){
        if(s.length() <= 1){
            return  Boolean.TRUE;
        }
        return  (s.charAt(0) == s.charAt(s.length()-1)) & recursive(s.substring(1, s.length()-1));
    }


}
