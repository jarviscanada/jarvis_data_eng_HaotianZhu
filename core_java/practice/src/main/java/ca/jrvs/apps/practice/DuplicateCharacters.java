package ca.jrvs.apps.practice;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class DuplicateCharacters implements Solution {
    public Character[] solution(String str){
        HashSet<Character> first = new HashSet<>();
        HashSet<Character> second = new HashSet<>();
        for(Character character: str.toCharArray()){
            if(character >= 'A' && character<='z'){
                if(first.contains(character)){
                    second.add(character);
                } else{
                    first.add(character);
                }
            }
        }
        List<Character> arr = new ArrayList<>();
        for(Character character: str.toCharArray()) {
            if(second.contains(character)){
                arr.add(character);
            }
        }
        return arr.toArray(new Character[arr.size()]);
    }
}
