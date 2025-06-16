package cn.bytengine.d.utils;

/**
 * TODO
 *
 * @author Ban Tenio
 * @version 1.0
 */
public interface Converter<T> {
    T convert(Object value, T defaultValue) throws IllegalArgumentException;

    default T convertWithCheck(Object value, T defaultValue, boolean quietly) {
        try {
            return convert(value, defaultValue);
        } catch (Exception e) {
            if (quietly) {
                return defaultValue;
            }
            throw e;
        }
    }
}
