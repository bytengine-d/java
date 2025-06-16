package cn.bytengine.d.lang;

import cn.bytengine.d.collection.TransIter;

import java.util.Iterator;
import java.util.function.Function;

/**
 * TODO
 *
 * @author Ban Tenio
 * @version 1.0
 */
public abstract class IteratorTools {
    private IteratorTools() {
    }

    public static <F, T> Iterator<T> trans(Iterator<F> iterator, Function<? super F, ? extends T> function) {
        return new TransIter<>(iterator, function);
    }
}
