package cn.bytengine.d.lang;

import cn.bytengine.d.collection.TransSpliterator;

import java.util.Spliterator;
import java.util.function.Function;

/**
 * TODO
 *
 * @author Ban Tenio
 * @version 1.0
 */
public abstract class SpliteratorTools {
    private SpliteratorTools() {
    }

    public static <F, T> Spliterator<T> trans(Spliterator<F> fromSpliterator, Function<? super F, ? extends T> function) {
        return new TransSpliterator<>(fromSpliterator, function);
    }
}
