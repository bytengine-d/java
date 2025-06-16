package cn.bytengine.d.collection;

import cn.bytengine.d.lang.AssertTools;

import java.util.Iterator;
import java.util.function.Function;

/**
 * TODO
 *
 * @author Ban Tenio
 * @version 1.0
 */
public class TransIter<F, T> implements Iterator<T> {
    private final Iterator<? extends F> backingIterator;
    private final Function<? super F, ? extends T> func;

    public TransIter(final Iterator<? extends F> backingIterator, final Function<? super F, ? extends T> func) {
        this.backingIterator = AssertTools.notNull(backingIterator);
        this.func = AssertTools.notNull(func);
    }

    @Override
    public final boolean hasNext() {
        return backingIterator.hasNext();
    }

    @Override
    public final T next() {
        return func.apply(backingIterator.next());
    }

    @Override
    public final void remove() {
        backingIterator.remove();
    }
}
