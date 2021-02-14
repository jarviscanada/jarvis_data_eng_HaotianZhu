package ca.jrvs.apps.practice.dataStructure;

import java.util.NoSuchElementException;

public class LinkedJListJDeque<E> extends LinkedJList<E> implements JDeque<E> {

    public LinkedJListJDeque(){
        super();
    }


    @Override
    public E remove() {
        return this.pop();
    }

    @Override
    public E pop() {
        if(this.size == 0){
            throw new NoSuchElementException();
        }
        Node<E> target = tail;
        Node<E> prev = tail.getPrev();
        if(prev == null){
            tail = null;
            head = null;
            size = 0;
            return target.getValue();
        }
        prev.setNext(null);
        tail = prev;
        size -= 1;
        return target.getValue();
    }

    @Override
    public boolean add(E e) {
        Node<E> node = new Node<E>(e);
        if(size > 0) {
            node.setNext(head);
            head.setPrev(node);
            this.head = node;
        }else{
            this.head = node;
            this.tail = node;
        }
        size += 1;
        return  Boolean.TRUE;
    }

    @Override
    public void push(E e) {
        Node<E> node = new Node<E>(e);
        if(size > 0) {
            this.tail.setNext(node);
            node.setPrev(this.tail);
            this.tail = node;
        }else{
            this.head = node;
            this.tail = node;
        }

        size += 1;
    }

    @Override
    public E peek() {
        return this.tail.getValue();
    }
}
