package cn.bytengine.d.collection;

import cn.bytengine.d.lang.AssertTools;
import cn.bytengine.d.lang.IteratorTools;
import cn.bytengine.d.lang.SpliteratorTools;

import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * TODO
 *
 * @author Ban Tenio
 * @version 1.0
 */
public class TransCollection<F, T> extends AbstractCollection<T> {
    private final Collection<F> fromCollection;
    private final Function<? super F, ? extends T> function;

    public TransCollection(Collection<F> fromCollection, Function<? super F, ? extends T> function) {
        this.fromCollection = AssertTools.notNull(fromCollection);
        this.function = AssertTools.notNull(function);
    }

    @Override
    public Iterator<T> iterator() {
        return IteratorTools.trans(fromCollection.iterator(), function);
    }

    @Override
    public void clear() {
        fromCollection.clear();
    }

    @Override
    public boolean isEmpty() {
        return fromCollection.isEmpty();
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        AssertTools.notNull(action);
        fromCollection.forEach((f) -> action.accept(function.apply(f)));
    }

    @Override
    public boolean removeIf(Predicate<? super T> filter) {
        AssertTools.notNull(filter);
        return fromCollection.removeIf(element -> filter.test(function.apply(element)));
    }

    @Override
    public Spliterator<T> spliterator() {
        return SpliteratorTools.trans(fromCollection.spliterator(), function);
    }

    @Override
    public int size() {
        return fromCollection.size();
    }
}
