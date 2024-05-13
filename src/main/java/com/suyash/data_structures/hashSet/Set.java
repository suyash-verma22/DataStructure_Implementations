package com.suyash.data_structures.hashSet;

public interface Set <T>{

    boolean add(T item);

    boolean remove(T item);

    boolean contains(T item);

    boolean isEmpty();

    int size();
}
