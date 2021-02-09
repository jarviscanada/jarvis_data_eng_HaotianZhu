package ca.jrvs.apps.practice;

import java.util.ArrayList;
import java.util.Stack;

public class QueueUsingStacks<E> implements Solution{
    private QueueStack<E> queue = new QueueStack<>();

    public QueueStack<E> getQueue(){
        return queue;
    }

    public class QueueStack<E> {
        private Stack<E> inStack = new Stack<E>();
        private Stack<E> outStack = new Stack<E>();

        void add(E obj){
            inStack.push(obj);
        }

        E remove(){
            if(outStack.isEmpty()){
                while(!inStack.isEmpty()){
                    outStack.push(inStack.pop());
                }
            }
            return outStack.pop();
        }
    }
}
