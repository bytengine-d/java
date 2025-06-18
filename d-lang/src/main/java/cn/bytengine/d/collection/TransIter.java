package cn.bytengine.d.collection;

import cn.bytengine.d.lang.AssertTools;

import java.util.Iterator;
import java.util.function.Function;

/**
 * 使用给定的转换函数，转换源{@link Iterator}为新类型的{@link Iterator}
 *
 * @param <F> 源元素类型
 * @param <T> 目标元素类型
 * @author Ban Tenio
 * @version 1.0
 */
public class TransIter<F, T> implements Iterator<T> {
    private final Iterator<? extends F> backingIterator;
    private final Function<? super F, ? extends T> func;

    /**
     * 构造
     *
     * @param backingIterator 源{@link Iterator}
     * @param func            转换函数
     */
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
