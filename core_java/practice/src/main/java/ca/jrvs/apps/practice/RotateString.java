package ca.jrvs.apps.practice;

public class RotateString implements Solution {

    static Boolean check(String a, String b){
        if(a.length() != b.length()){
            return Boolean.FALSE;
        }

        String longString = a+a;
        return longString.contains(b);
    }
}
