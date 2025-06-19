package cn.bytengine.d.lang;

import cn.bytengine.d.collection.TransSpliterator;

import java.util.Spliterator;
import java.util.function.Function;

/**
 * {@link Spliterator}相关工具类
 *
 * @author Ban Tenio
 * @version 1.0
 */
public abstract class SpliteratorTools {
    private SpliteratorTools() {
    }

    /**
     * 使用给定的转换函数，转换源{@link Spliterator}为新类型的{@link Spliterator}
     *
     * @param <F>             源元素类型
     * @param <T>             目标元素类型
     * @param fromSpliterator 源{@link Spliterator}
     * @param function        转换函数
     * @return 新类型的{@link Spliterator}
     */
    public static <F, T> Spliterator<T> trans(Spliterator<F> fromSpliterator, Function<? super F, ? extends T> function) {
        return new TransSpliterator<>(fromSpliterator, function);
    }
}
