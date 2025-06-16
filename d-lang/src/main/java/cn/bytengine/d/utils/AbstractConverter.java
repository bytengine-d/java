package cn.bytengine.d.utils;

import cn.bytengine.d.lang.ArrayTools;
import cn.bytengine.d.lang.CharSequenceTools;
import cn.bytengine.d.lang.CharTools;
import cn.bytengine.d.lang.ClassTools;

import java.io.Serializable;
import java.util.Map;

/**
 * TODO
 *
 * @author Ban Tenio
 * @version 1.0
 */
public abstract class AbstractConverter<T> implements Converter<T>, Serializable {
    public T convertQuietly(Object value, T defaultValue) {
        try {
            return convert(value, defaultValue);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    @Override
    public T convert(Object value, T defaultValue) {
        Class<T> targetType = getTargetType();
        if (null == targetType && null == defaultValue) {
            throw new NullPointerException(CharSequenceTools.format("[type] and [defaultValue] are both null for Converter [{}], we can not know what type to convert !", this.getClass().getName()));
        }
        if (null == targetType) {
            // 目标类型不确定时使用默认值的类型
            targetType = (Class<T>) defaultValue.getClass();
        }
        if (null == value) {
            return defaultValue;
        }

        if (null == defaultValue || targetType.isInstance(defaultValue)) {
            if (targetType.isInstance(value) && false == Map.class.isAssignableFrom(targetType)) {
                // 除Map外，已经是目标类型，不需要转换（Map类型涉及参数类型，需要单独转换）
                return targetType.cast(value);
            }
            final T result = convertInternal(value);
            return ((null == result) ? defaultValue : result);
        } else {
            throw new IllegalArgumentException(
                    CharSequenceTools.format("Default value [{}]({}) is not the instance of [{}]", defaultValue, defaultValue.getClass(), targetType));
        }
    }

    protected abstract T convertInternal(Object value);

    protected String convertToStr(Object value) {
        if (null == value) {
            return null;
        }
        if (value instanceof CharSequence) {
            return value.toString();
        } else if (ArrayTools.isArray(value)) {
            return ArrayTools.toString(value);
        } else if (CharTools.isChar(value)) {
            //对于ASCII字符使用缓存加速转换，减少空间创建
            return CharTools.toString((char) value);
        }
        return value.toString();
    }

    @SuppressWarnings("unchecked")
    public Class<T> getTargetType() {
        return (Class<T>) ClassTools.getTypeArgument(getClass());
    }
}
