package com.suyash.data_structures.hashSet.OpenAddressing;

import com.suyash.data_structures.hashSet.Set;

public class HashSet<E> implements Set<E> {
    private static final int DEFAULT_CAPACITY = 16;
    private static final double LOAD_FACTOR_THRESHOLD = 0.75;
    private static final double RESIZE_FACTOR = 2.0;

    private Object[] table;
    private int size;

    public HashSet() {
        this(DEFAULT_CAPACITY);
    }

    public HashSet(int initialCapacity) {
        this.table = new Object[initialCapacity];
        this.size = 0;
    }

    @Override
    public boolean add(E element) {
        if (contains(element)) {
            return false;
        }

        ensureCapacity();
        int index = findIndex(element);
        table[index] = element;
        size++;
        return true;
    }

    @Override
    public boolean remove(E element) {
        int index = findIndex(element);
        if (table[index] != null && table[index].equals(element)) {
            table[index] = null;
            size--;
            return true;
        }
        return false;
    }

    @Override
    public boolean contains(E element) {
        return table[findIndex(element)] != null;
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
        if ((double) size / table.length >= LOAD_FACTOR_THRESHOLD) {
            int newCapacity = (int) (table.length * RESIZE_FACTOR);
            Object[] newTable = new Object[newCapacity];
            for (Object obj : table) {
                if (obj != null) {
                    int index = findIndex((E) obj, newTable);
                    newTable[index] = obj;
                }
            }
            table = newTable;
        }
    }

    private int findIndex(E element) {
        return findIndex(element, table);
    }

    private int findIndex(E element, Object[] array) {
        int index = Math.abs(element.hashCode() % array.length);
        while (array[index] != null && !array[index].equals(element)) {
            index = (index + 1) % array.length;
        }
        return index;
    }
}

