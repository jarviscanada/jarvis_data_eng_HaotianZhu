package ca.jrvs.apps.practice;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StringToIntegerAtoi implements Solution{

    public Integer withBuildInParsing(String str){
        Integer end = str.length();
        for(Integer i=0; i<str.length(); i++){
            char ch = str.charAt(i);
            if(ch != '+' && ch != '-' && !(ch>=48 && ch <=57)){
                end = i;
                break;
            }
        }
         String newStr = str.substring(0,end);
        return Integer.parseInt(newStr);
    }

    public Integer withOutBuildInParsing(String str){
        List<Integer> arr = str.chars().boxed().collect(Collectors.toCollection(ArrayList::new));

        Integer positive = 0;
        List<Integer> result = new ArrayList<>();
        for(Integer i = 0; i<arr.size(); i++){
            Integer ch = arr.get(i);
            if(ch == '+'){
                if(positive == 0){
                    positive = 1;
                } else{
                    return 0;
                }
            }else if(ch == '-'){
                if(positive == 0){
                    positive = -1;
                } else{
                    return 0;
                }
            } else if(ch>=48 && ch <=57){
                result.add(ch-48);
                if(positive == 0){
                    positive = 1;
                }
            } else {
                break;
            }
        }
        Integer r = 0;
        for(Integer i=0; i<result.size(); i++){
            r*=10;
            r+=result.get(i);
        }
        return positive*r;
    }
}
