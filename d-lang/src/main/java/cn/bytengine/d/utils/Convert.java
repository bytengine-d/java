package cn.bytengine.d.utils;

import cn.bytengine.d.lang.TypeReference;

import java.lang.reflect.Type;

/**
 * TODO
 *
 * @author Ban Tenio
 * @version 1.0
 */
public abstract class Convert {
    private Convert() {
    }

    public static <T> T convert(Class<T> type, Object value) throws ConvertException {
        return convert((Type) type, value);
    }

    public static <T> T convert(TypeReference<T> reference, Object value) throws ConvertException {
        return convert(reference.getType(), value, null);
    }

    public static <T> T convert(Type type, Object value) throws ConvertException {
        return convert(type, value, null);
    }

    public static <T> T convert(Class<T> type, Object value, T defaultValue) throws ConvertException {
        return convert((Type) type, value, defaultValue);
    }

    public static <T> T convert(Type type, Object value, T defaultValue) throws ConvertException {
        return convertWithCheck(type, value, defaultValue, false);
    }

    public static <T> T convertWithCheck(Type type, Object value, T defaultValue, boolean quietly) {
        final ConverterRegistry registry = ConverterRegistry.getInstance();
        try {
            return registry.convert(type, value, defaultValue);
        } catch (Exception e) {
            if (quietly) {
                return defaultValue;
            }
            throw e;
        }
    }
}
