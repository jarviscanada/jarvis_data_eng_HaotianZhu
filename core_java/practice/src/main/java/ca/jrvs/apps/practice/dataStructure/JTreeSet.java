package ca.jrvs.apps.practice.dataStructure;

import java.util.TreeMap;

public class JTreeSet<E> implements JSet<E>{
    TreeMap<E,Integer> treeMap = new TreeMap<>();
    Integer insertOrder = 0;

    @Override
    public int size() {
        return treeMap.size();
    }

    @Override
    public boolean contains(Object o) {
        return treeMap.containsKey((E) o);
    }

    @Override
    public boolean add(E e) {
        if(e == null){
            throw new NullPointerException("value is null");
        }
        if(treeMap.containsKey(e)){
            return Boolean.FALSE;
        }
        treeMap.put(e, insertOrder);
        insertOrder+=1;
        return Boolean.TRUE;
    }

    @Override
    public boolean remove(Object o) {
        if(o == null){
            throw new NullPointerException("value is null");
        }
        if(treeMap.containsKey((E) o)){
            treeMap.remove((E) o);
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    @Override
    public void clear() {
        treeMap.clear();
    }
}
