package com.suyash.data_structures.hashSet.Chaining;

import com.suyash.data_structures.hashSet.Set;
import java.util.LinkedList;

public class HashSet<E> implements Set<E> {
    private static final int DEFAULT_CAPACITY = 16;
    private static final double LOAD_FACTOR_THRESHOLD = 0.75;

    private LinkedList<E>[] buckets;
    private int size;

    public HashSet() {
        this(DEFAULT_CAPACITY);
    }

    public HashSet(int initialCapacity) {
        this.buckets = new LinkedList[initialCapacity];
        this.size = 0;
    }

    @Override
    public boolean add(E element) {
        if (contains(element)) {
            return false;
        }

        ensureCapacity();
        int index = getIndex(element);
        if (buckets[index] == null) {
            buckets[index] = new LinkedList<>();
        }
        buckets[index].add(element);
        size++;
        return true;
    }

    @Override
    public boolean remove(E element) {
        int index = getIndex(element);
        if (buckets[index] != null) {
            boolean removed = buckets[index].remove(element);
            if (removed) {
                size--;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean contains(E element) {
        int index = getIndex(element);
        return buckets[index] != null && buckets[index].contains(element);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private void ensureCapacity() {
        if ((double) size / buckets.length >= LOAD_FACTOR_THRESHOLD) {
            int newCapacity = buckets.length * 2;
            LinkedList<E>[] newBuckets = new LinkedList[newCapacity];
            for (LinkedList<E> bucket : buckets) {
                if (bucket != null) {
                    for (E element : bucket) {
                        int index = getIndex(element, newCapacity);
                        if (newBuckets[index] == null) {
                            newBuckets[index] = new LinkedList<>();
                        }
                        newBuckets[index].add(element);
                    }
                }
            }
            buckets = newBuckets;
        }
    }

    private int getIndex(E element) {
        return getIndex(element, buckets.length);
    }

    private int getIndex(E element, int capacity) {
        return Math.abs(element.hashCode() % capacity);
    }
}
