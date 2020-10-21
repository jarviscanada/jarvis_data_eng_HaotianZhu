package ca.jrvs.apps.practice.dataStructure;

import java.util.HashMap;

public class JHashSet<E> implements JSet<E>{

    private HashMap<E,Integer> map = new HashMap<E,Integer>();
    Integer insertOrder = 0;
    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean contains(Object o) {
        return map.containsKey((E) o);
    }

    @Override
    public boolean add(E e) {
        if(e == null){
            throw new NullPointerException("value is null");
        }

        if(map.containsKey(e)){
            return Boolean.FALSE;
        }

        map.put(e,insertOrder);
        insertOrder+=1;
        return Boolean.TRUE;
    }

    @Override
    public boolean remove(Object o) {
        if(o == null){
            throw new NullPointerException("value is null");
        }
        if(map.containsKey((E) o)){
            map.remove((E) o);
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    @Override
    public void clear() {
        map.clear();
    }
}
