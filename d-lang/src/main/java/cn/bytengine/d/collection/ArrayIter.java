package cn.bytengine.d.collection;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * TODO
 *
 * @author Ban Tenio
 * @version 1.0
 */
public class ArrayIter<E> implements Iterable<E>, Iterator<E>, Serializable {
    private final Object array;
    private int startIndex;
    private int endIndex;
    private int index;

    public ArrayIter(E[] array) {
        this((Object) array);
    }

    public ArrayIter(Object array) {
        this(array, 0);
    }

    public ArrayIter(Object array, int startIndex) {
        this(array, startIndex, -1);
    }

    public ArrayIter(final Object array, final int startIndex, final int endIndex) {
        this.endIndex = Array.getLength(array);
        if (endIndex > 0 && endIndex < this.endIndex) {
            this.endIndex = endIndex;
        }

        if (startIndex >= 0 && startIndex < this.endIndex) {
            this.startIndex = startIndex;
        }
        this.array = array;
        this.index = this.startIndex;
    }

    @Override
    public boolean hasNext() {
        return (index < endIndex);
    }

    @Override
    @SuppressWarnings("unchecked")
    public E next() {
        if (hasNext() == false) {
            throw new NoSuchElementException();
        }
        return (E) Array.get(array, index++);
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("remove() method is not supported");
    }

    public Object getArray() {
        return array;
    }

    public void reset() {
        this.index = this.startIndex;
    }

    public Iterator<E> iterator() {
        return this;
    }
}
