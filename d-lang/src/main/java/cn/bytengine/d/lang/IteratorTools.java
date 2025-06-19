package cn.bytengine.d.lang;

import cn.bytengine.d.collection.TransIter;

import java.util.Iterator;
import java.util.function.Function;

/**
 * {@link Iterable} 和 {@link Iterator} 相关工具类
 *
 * @author Ban Tenio
 * @version 1.0
 */
public abstract class IteratorTools {
    private IteratorTools() {
    }

    /**
     * 按照给定函数，转换{@link Iterator}为另一种类型的{@link Iterator}
     *
     * @param <F>      源元素类型
     * @param <T>      目标元素类型
     * @param iterator 源{@link Iterator}
     * @param function 转换函数
     * @return 转换后的{@link Iterator}
     */
    public static <F, T> Iterator<T> trans(Iterator<F> iterator, Function<? super F, ? extends T> function) {
        return new TransIter<>(iterator, function);
    }
}
