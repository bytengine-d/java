package cn.bytengine.d.lang;

import cn.bytengine.d.collection.ArrayIter;
import cn.bytengine.d.fn.CloneSupport;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * TODO
 *
 * @author Ban Tenio
 * @version 1.0
 */
public class Tuple extends CloneSupport<Tuple> implements Iterable<Object>, Serializable {

    private final Object[] members;
    private int hashCode;
    private boolean cacheHash;

    public Tuple(Object... members) {
        this.members = members;
    }

    @SuppressWarnings("unchecked")
    public <T> T get(int index) {
        return (T) members[index];
    }

    public Object[] getMembers() {
        return this.members;
    }

    public final List<Object> toList() {
        return CollectionTools.toList(this.members);
    }

    public Tuple setCacheHash(boolean cacheHash) {
        this.cacheHash = cacheHash;
        return this;
    }

    public int size() {
        return this.members.length;
    }

    public boolean contains(Object value) {
        return ArrayTools.contains(this.members, value);
    }

    public final Stream<Object> stream() {
        return Arrays.stream(this.members);
    }

    public final Stream<Object> parallelStream() {
        return StreamSupport.stream(spliterator(), true);
    }

    public final Tuple sub(final int start, final int end) {
        return new Tuple(ArrayTools.sub(this.members, start, end));
    }

    @Override
    public int hashCode() {
        if (this.cacheHash && 0 != this.hashCode) {
            return this.hashCode;
        }
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.deepHashCode(members);
        if (this.cacheHash) {
            this.hashCode = result;
        }
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Tuple other = (Tuple) obj;
        return false != Arrays.deepEquals(members, other.members);
    }

    @Override
    public String toString() {
        return Arrays.toString(members);
    }

    @Override
    public Iterator<Object> iterator() {
        return new ArrayIter<>(members);
    }

    @Override
    public final Spliterator<Object> spliterator() {
        return Spliterators.spliterator(this.members, Spliterator.ORDERED);
    }
}
