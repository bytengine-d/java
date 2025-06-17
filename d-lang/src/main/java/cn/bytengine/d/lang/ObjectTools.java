package cn.bytengine.d.lang;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * TODO
 *
 * @author Ban Tenio
 * @version 1.0
 */
public abstract class ObjectTools {
    private ObjectTools() {
    }

    public static boolean isNull(Object obj) {
        //noinspection ConstantConditions
        return null == obj || obj.equals(null);
    }

    public static boolean isNotNull(Object obj) {
        //noinspection ConstantConditions
        return null != obj && false == obj.equals(null);
    }

    public static <T> T defaultIfNull(final T object, final T defaultValue) {
        return isNull(object) ? defaultValue : object;
    }

    public static <T> T defaultIfNull(T source, Supplier<? extends T> defaultValueSupplier) {
        if (isNull(source)) {
            return defaultValueSupplier.get();
        }
        return source;
    }

    public static <T> T defaultIfNull(T source, Function<T, ? extends T> defaultValueSupplier) {
        if (isNull(source)) {
            return defaultValueSupplier.apply(null);
        }
        return source;
    }

    public static <T, R> T defaultIfNull(R source, Function<R, ? extends T> handle, final T defaultValue) {
        if (isNotNull(source)) {
            return handle.apply(source);
        }
        return defaultValue;
    }

    public static boolean equals(Object obj1, Object obj2) {
        return equal(obj1, obj2);
    }

    public static boolean equal(Object obj1, Object obj2) {
        if (obj1 instanceof Number && obj2 instanceof Number) {
            return NumberTools.equals((Number) obj1, (Number) obj2);
        }
        return Objects.equals(obj1, obj2);
    }
}
