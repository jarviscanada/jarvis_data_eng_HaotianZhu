package ca.jrvs.apps.practice;

import java.util.Stack;

public class ValidParentheses implements Solution{
    public static Boolean check(String str){
        Stack<Character> stack = new Stack<>();
        stack.add('h');
        for(Integer i=0; i<str.length(); i++){
            if(str.charAt(i) == '[' || str.charAt(i) == '(' || str.charAt(i) == '{'){
                stack.add(str.charAt(i));
            } else if(str.charAt(i) == ']'){
                if(stack.pop() != '['){
                    return Boolean.FALSE;
                }
            } else if(str.charAt(i) == ')'){
                if(stack.pop() != '('){
                    return Boolean.FALSE;
                }
            } else if(str.charAt(i) == '}'){
                if(stack.pop() != '{'){
                    return Boolean.FALSE;
                }
            }
        }

        return Boolean.TRUE;

    }
}
