package cn.bytengine.d.utils;

import cn.bytengine.d.lang.CharSequenceTools;
import cn.bytengine.d.lang.ObjectTools;

import java.util.function.Function;

/**
 * TODO
 *
 * @author Ban Tenio
 * @version 1.0
 */
public class PrimitiveConverter extends AbstractConverter<Object> {

    private final Class<?> targetType;

    public PrimitiveConverter(Class<?> clazz) {
        if (null == clazz) {
            throw new NullPointerException("PrimitiveConverter not allow null target type!");
        } else if (false == clazz.isPrimitive()) {
            throw new IllegalArgumentException("[" + clazz + "] is not a primitive class!");
        }
        this.targetType = clazz;
    }

    @Override
    protected Object convertInternal(Object value) {
        return PrimitiveConverter.convert(value, this.targetType, this::convertToStr);
    }

    @Override
    protected String convertToStr(Object value) {
        return CharSequenceTools.trim(super.convertToStr(value));
    }

    @Override
    @SuppressWarnings("unchecked")
    public Class<Object> getTargetType() {
        return (Class<Object>) this.targetType;
    }

    protected static Object convert(Object value, Class<?> primitiveClass, Function<Object, String> toStringFunc) {
        if (byte.class == primitiveClass) {
            return ObjectTools.defaultIfNull(NumberConverter.convert(value, Byte.class, toStringFunc), 0);
        } else if (short.class == primitiveClass) {
            return ObjectTools.defaultIfNull(NumberConverter.convert(value, Short.class, toStringFunc), 0);
        } else if (int.class == primitiveClass) {
            return ObjectTools.defaultIfNull(NumberConverter.convert(value, Integer.class, toStringFunc), 0);
        } else if (long.class == primitiveClass) {
            return ObjectTools.defaultIfNull(NumberConverter.convert(value, Long.class, toStringFunc), 0);
        } else if (float.class == primitiveClass) {
            return ObjectTools.defaultIfNull(NumberConverter.convert(value, Float.class, toStringFunc), 0);
        } else if (double.class == primitiveClass) {
            return ObjectTools.defaultIfNull(NumberConverter.convert(value, Double.class, toStringFunc), 0);
        } else if (char.class == primitiveClass) {
            return Convert.convert(Character.class, value);
        } else if (boolean.class == primitiveClass) {
            return Convert.convert(Boolean.class, value);
        }

        throw new ConvertException("Unsupported target type: {}", primitiveClass);
    }
}
