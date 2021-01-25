package ca.jrvs.apps.practice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ValidAnagram implements Solution {

    static Boolean checkWithSort(String a, String b){
        if(a == b){
            return Boolean.TRUE;
        }

        if(a.length() != b.length()){
            return  Boolean.FALSE;
        }

        char[] aCharList =  a.toCharArray();
        Arrays.sort(aCharList);
        char[] bCharList =  b.toCharArray();
        Arrays.sort(bCharList);

        for(Integer i =0; i<aCharList.length; i++){
            if(aCharList[i] != bCharList[i]){
                return Boolean.FALSE;
            }
        }
        return Boolean.TRUE;
    }

    static Boolean check(String a, String b){
        if(a == b){
            return Boolean.TRUE;
        }

        if(a.length() != b.length()){
            return  Boolean.FALSE;
        }


        HashMap<Character, Integer> map = new HashMap<>();

        for(Integer i=0; i<a.length(); i++){
             Character ch = new Character(a.charAt(i));
             if(map.containsKey(ch)){
                 map.put(ch, map.get(ch)+1);
             } else{
                 map.put(ch,1);
             }
        }

        for(Integer i=0; i<b.length(); i++){
            Character ch = new Character(b.charAt(i));
            if(map.containsKey(ch)){
                map.put(ch, map.get(ch)-1);
                if(map.get(ch) < 0){
                    return Boolean.FALSE;
                }
            } else{
                return Boolean.FALSE;
            }
        }
        return  Boolean.TRUE;
    }
}
