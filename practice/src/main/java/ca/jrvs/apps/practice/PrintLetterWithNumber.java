package ca.jrvs.apps.practice;

public class PrintLetterWithNumber implements Solution {
    public String solution(String input){
        char a = 'a';
        char bigA = 'A';
        StringBuilder stringBuilder = new StringBuilder();
        for(char e : input.toCharArray()){
            stringBuilder.append(e);
            if(e<a){
                stringBuilder.append(e - bigA + 27);
            } else {
                stringBuilder.append(e - a + 1);
            }
        }
        return stringBuilder.toString();
    }
}
