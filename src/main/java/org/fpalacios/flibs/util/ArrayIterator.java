package org.fpalacios.flibs.util;

import java.util.Iterator;

public class ArrayIterator<T> implements Iterator<T> {

    private T[] array;

    private int pos = 0;

    public ArrayIterator(T[] array) {
        this.array = array;
    }

    public boolean hasNext() {
        return array.length > pos;
    }

    public T next() {
        return array[pos++];
    }

    public void remove() {
        throw new UnsupportedOperationException("Cannot remove an element of an array.");
    }
}
