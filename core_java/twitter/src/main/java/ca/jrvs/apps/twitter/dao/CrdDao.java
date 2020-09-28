package ca.jrvs.apps.twitter.dao;

public interface CrdDao<T> {

    T create(T obj);

    T getById(String id);

    T deleteById(String id);
}
