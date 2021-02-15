package ca.jrvs.apps.practice.dataStructure;

public class LinkedJList<E> implements JList<E> {
    protected Node<E> tail;
    protected Node<E> head;
    protected Integer size = 0;

    protected class Node<E>{
        private Node<E> prev = null;
        private Node<E> next = null;
        private E value;

        Node(E val){
            this.value = val;
        }

        public E getValue() {
            return value;
        }

        public void setNext(Node<E> next) {
            this.next = next;
        }

        public void setPrev(Node<E> prev) {
            this.prev = prev;
        }

        public Node<E> getNext() {
            return next;
        }

        public Node<E> getPrev() {
            return prev;
        }
    }

    @Override
    public boolean add(E e) {
        Node<E> node = new Node<>(e);
        if(size > 0) {
            this.tail.setNext(node);
            node.setPrev(this.tail);
            this.tail = node;
        }else{
            this.tail = node;
            this.head = node;
        }
        size += 1;
        return Boolean.TRUE;
    }

    @Override
    public Object[] toArray() {
        Object[] arr = new Object[this.size];
        Node<E> current = head;
        Integer index = 0;
        while(current != null){
            arr[index] = current.getValue();
            current = current.getNext();
            index += 1;
        }
        return arr;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public int indexOf(Object o) {
        Node<E> current = head;
        Integer index = 0;
        while(current != null){
            if((current.getValue()== null && o==null) || (current.getValue().equals(o)) ){
                return index;
            }
            current = current.getNext();
            index += 1;
        }
        return -1;
    }

    @Override
    public boolean contains(Object o) {

        return this.indexOf(o) != -1;
    }

    private Node<E> getNode(int index){
        if(index < 0 || index >= this.size){
            throw new IndexOutOfBoundsException();
        }

        Node<E> current = head;
        Integer i = 0;
        while(i<index){
            current = current.getNext();
            index += 1;
        }
        return current;
    }

    @Override
    public E get(int index) {
        return this.getNode(index).getValue();
    }

    @Override
    public E remove(int index) {
        if(index < 0 || index >= this.size){
            throw new IndexOutOfBoundsException();
        }

        Node<E> target;
        if(index == 0){
            target = this.head;
            this.head = this.head.getNext();
            if(this.head != null) {
                this.head.setPrev(null);
            }
        } else{
            target = this.getNode(index);
            Node<E> prev = target.getPrev();
            Node<E> next = target.getNext();
            prev.setNext(next);
            next.setPrev(prev);
        }
        size -= 1;
        return target.getValue();
    }

    @Override
    public void clear() {
        this.tail = null;
        this.head = null;
        this.size = 0;
    }
}
