package ca.jrvs.apps.practice.dataStructure;

public class ArrayJList<E> implements JList<E> {
    private static final int DEFAULT_CAPACITY = 10;
    protected Object[] data;
    private Integer tail = 0;


    public ArrayJList(int initialCapacity) {
        if (initialCapacity > 0) {
            this.data = new Object[initialCapacity];
        } else {
            throw new IllegalArgumentException("Illegal Capacity: " + initialCapacity);
        }
    }

    public ArrayJList(){
        this.data = new Object[this.DEFAULT_CAPACITY];
    }

    private void doubleSizeAndUpdate(){
        Object[] tmp = new Object[data.length*2];
        for(Integer i=0; i<data.length; i++){
            tmp[i] = data[i];
        }
        this.data = tmp;
    }


    @Override
    public boolean add(E o) {
        this.data[tail] = o;
        tail += 1;
        if(tail == data.length){
            this.doubleSizeAndUpdate();

        }
        return true;
    }

    @Override
    public Object[] toArray() {
        return this.data.clone();
    }

    @Override
    public int size() {
        return this.tail;
    }

    @Override
    public boolean isEmpty() {
        return (this.tail == 0);
    }

    @Override
    public int indexOf(Object o) {
        for(Integer i =0; i<this.tail; i++){
            if(data[i] == null && o == null){
                return i;
            } else if(data[i] != null && data[i].equals(o)){
                return i;
            }
        }
        return -1;
    }

    @Override
    public boolean contains(Object o) {
        return indexOf(o) != -1;
    }

    @Override
    public E get(int index) {
        if(index<0 || index>=this.tail){
            throw new IndexOutOfBoundsException();
        }
        return (E) this.data[index];
    }

    @Override
    public E remove(int index) {
        if(index<0 || index>=this.tail){
            throw new IndexOutOfBoundsException();
        }
        Object removeObj = this.data[index];
        for(Integer i= index; i<this.tail-1; i++){
            this.data[i] = this.data[i+1];
            this.data[i+1] = null;
        }
        this.tail-=1;
        return (E) removeObj;
    }

    @Override
    public void clear() {
        for(Integer i= 0; i<this.tail; i++){
            this.data[i] = null;
        }
        this.tail=0;
    }
}
