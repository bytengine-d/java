package cn.bytengine.d.ctx;

import java.time.Instant;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

import static java.time.format.DateTimeFormatter.ISO_INSTANT;

/**
 * 逻辑执行上下文
 *
 * @author Ban Tenio
 * @version 1.0
 */
public interface Ctx {
    /**
     * 获取上下文参数
     *
     * @param key 参数名
     * @return 参数值
     */
    Object get(String key);

    /**
     * 设置上下文参数
     *
     * @param key   参数名
     * @param value 参数值
     * @return 当前上下文对象
     */
    Ctx set(String key, Object value);

    /**
     * 上下文是否包含指定参数
     *
     * @param key 判断参数名
     * @return 是否包含参数名
     */
    boolean has(String key);

    /**
     * 删除上下文指定参数
     *
     * @param key 指定参数名
     * @return 当前上下文对象
     */
    Ctx remove(String key);

    /**
     * 根据指定类型获取指定参数
     *
     * @param key  指定参数名
     * @param type 参数值返回类型
     * @param <T>  参数值返回类型
     * @return 参数值，根据指定类型返回
     */
    default <T> T getByType(String key, Class<T> type) {
        return type.cast(get(key));
    }

    /**
     * 根据指定类型获取指定参数，如果该参数不存在，返回指定默认值
     *
     * @param key        指定参数名
     * @param type       参数值返回类型
     * @param defaultVal 无参数使用默认值
     * @param <T>        参数值返回类型
     * @return 参数值，根据指定类型返回
     */
    default <T> T getByTypeWithDefault(String key, Class<T> type, T defaultVal) {
        if (has(key)) {
            return getByType(key, type);
        }
        set(key, defaultVal);
        return defaultVal;
    }

    /**
     * 根据指定类型获取指定参数，如果该参数不存在，调用默认值提供函数
     *
     * @param key                指定参数名
     * @param type               参数值返回类型
     * @param defaultValSupplier 无参数调用默认值提供函数
     * @param <T>                参数值返回类型
     * @return 参数值，根据指定类型返回
     */
    default <T> T getByType(String key, Class<T> type, Supplier<T> defaultValSupplier) {
        if (has(key)) {
            return getByType(key, type);
        }
        T defaultVal = defaultValSupplier.get();
        set(key, defaultVal);
        return defaultVal;
    }

    /**
     * 获取字符串类型参数
     *
     * @param key 指定参数名
     * @return 字符串参数值
     */
    default String getString(String key) {
        Objects.requireNonNull(key);
        Object val = get(key);
        if (val == null) {
            return null;
        }
        if (val instanceof Instant) {
            return ISO_INSTANT.format((Instant) val);
        } else if (val instanceof byte[]) {
            return new String((byte[]) val);
        } else if (val instanceof Enum) {
            return ((Enum) val).name();
        } else {
            return val.toString();
        }
    }

    /**
     * 获取字符串类型参数，如果该参数不存在，返回默认值
     *
     * @param key        指定参数名
     * @param defaultVal 无参数使用默认值
     * @return 参数值
     */
    default String getString(String key, String defaultVal) {
        if (has(key)) {
            return getString(key);
        }
        set(key, defaultVal);
        return defaultVal;
    }

    /**
     * 获取字符串类型参数，如果该参数不存在，调用默认值提供函数
     *
     * @param key                指定参数名
     * @param defaultValSupplier 无参数调用默认值提供函数
     * @return 参数值
     */
    default String getString(String key, Supplier<String> defaultValSupplier) {
        if (has(key)) {
            return getString(key);
        }
        String defaultVal = defaultValSupplier.get();
        set(key, defaultVal);
        return defaultVal;
    }

    /**
     * 获取字符类型参数
     *
     * @param key 指定参数名
     * @return 数值参数值
     */
    default Character getChar(String key) {
        Objects.requireNonNull(key);
        return getByType(key, Character.class);
    }

    /**
     * 获取字符类型参数，如果该参数不存在，返回默认值
     *
     * @param key        指定参数名
     * @param defaultVal 无参数使用默认值
     * @return 参数值
     */
    default Character getChar(String key, Character defaultVal) {
        if (has(key)) {
            return getChar(key);
        }
        set(key, defaultVal);
        return defaultVal;
    }

    /**
     * 获取字符类型参数，如果该参数不存在，调用默认值提供函数
     *
     * @param key                指定参数名
     * @param defaultValSupplier 无参数调用默认值提供函数
     * @return 参数值
     */
    default Character getChar(String key, Supplier<Character> defaultValSupplier) {
        if (has(key)) {
            return getChar(key);
        }
        Character defaultVal = defaultValSupplier.get();
        set(key, defaultVal);
        return defaultVal;
    }

    /**
     * 获取数值类型参数
     *
     * @param key 指定参数名
     * @return 数值参数值
     */
    default Number getNumber(String key) {
        Objects.requireNonNull(key);
        return getByType(key, Number.class);
    }

    /**
     * 获取数值类型参数，如果该参数不存在，返回默认值
     *
     * @param key        指定参数名
     * @param defaultVal 无参数使用默认值
     * @return 参数值
     */
    default Number getNumber(String key, Number defaultVal) {
        if (has(key)) {
            return getNumber(key);
        }
        set(key, defaultVal);
        return defaultVal;
    }

    /**
     * 获取数值类型参数，如果该参数不存在，调用默认值提供函数
     *
     * @param key                指定参数名
     * @param defaultValSupplier 无参数调用默认值提供函数
     * @return 参数值
     */
    default Number getNumber(String key, Supplier<Number> defaultValSupplier) {
        if (has(key)) {
            return getNumber(key);
        }
        Number defaultVal = defaultValSupplier.get();
        set(key, defaultVal);
        return defaultVal;
    }

    /**
     * 获取字节参数
     *
     * @param key 指定参数名
     * @return 整形字节参数值
     */
    default Byte getByte(String key) {
        Objects.requireNonNull(key);
        Number number = getByType(key, Number.class);
        if (number == null) {
            return null;
        } else if (number instanceof Byte) {
            return (Byte) number;  // Avoids unnecessary unbox/box
        } else {
            return number.byteValue();
        }
    }

    /**
     * 获取字节参数，如果该参数不存在，返回默认值
     *
     * @param key        指定参数名
     * @param defaultVal 无参数使用默认值
     * @return 参数值
     */
    default Byte getByte(String key, Byte defaultVal) {
        if (has(key)) {
            return getByte(key);
        }
        set(key, defaultVal);
        return defaultVal;
    }

    /**
     * 获取字节参数，如果该参数不存在，调用默认值提供函数
     *
     * @param key                指定参数名
     * @param defaultValSupplier 无参数调用默认值提供函数
     * @return 参数值
     */
    default Byte getByte(String key, Supplier<Byte> defaultValSupplier) {
        if (has(key)) {
            return getByte(key);
        }
        Byte defaultVal = defaultValSupplier.get();
        set(key, defaultVal);
        return defaultVal;
    }

    /**
     * 获取短整形数字参数
     *
     * @param key 指定参数名
     * @return 短整形数字参数值
     */
    default Short getShort(String key) {
        Objects.requireNonNull(key);
        Number number = getByType(key, Number.class);
        if (number == null) {
            return null;
        } else if (number instanceof Short) {
            return (Short) number;  // Avoids unnecessary unbox/box
        } else {
            return number.shortValue();
        }
    }

    /**
     * 获取短整形数字参数，如果该参数不存在，返回默认值
     *
     * @param key        指定参数名
     * @param defaultVal 无参数使用默认值
     * @return 参数值
     */
    default Short getShort(String key, Short defaultVal) {
        if (has(key)) {
            return getShort(key);
        }
        set(key, defaultVal);
        return defaultVal;
    }

    /**
     * 获取短整形数字参数，如果该参数不存在，调用默认值提供函数
     *
     * @param key                指定参数名
     * @param defaultValSupplier 无参数调用默认值提供函数
     * @return 参数值
     */
    default Short getShort(String key, Supplier<Short> defaultValSupplier) {
        if (has(key)) {
            return getShort(key);
        }
        Short defaultVal = defaultValSupplier.get();
        set(key, defaultVal);
        return defaultVal;
    }

    /**
     * 获取整形数字参数
     *
     * @param key 指定参数名
     * @return 整形数字参数值
     */
    default Integer getInteger(String key) {
        Objects.requireNonNull(key);
        Number number = getByType(key, Number.class);
        if (number == null) {
            return null;
        } else if (number instanceof Integer) {
            return (Integer) number;  // Avoids unnecessary unbox/box
        } else {
            return number.intValue();
        }
    }

    /**
     * 获取整形数字参数，如果该参数不存在，返回默认值
     *
     * @param key        指定参数名
     * @param defaultVal 无参数使用默认值
     * @return 参数值
     */
    default Integer getInteger(String key, Integer defaultVal) {
        if (has(key)) {
            return getInteger(key);
        }
        set(key, defaultVal);
        return defaultVal;
    }

    /**
     * 获取整形数字参数，如果该参数不存在，调用默认值提供函数
     *
     * @param key                指定参数名
     * @param defaultValSupplier 无参数调用默认值提供函数
     * @return 参数值
     */
    default Integer getInteger(String key, Supplier<Integer> defaultValSupplier) {
        if (has(key)) {
            return getInteger(key);
        }
        Integer defaultVal = defaultValSupplier.get();
        set(key, defaultVal);
        return defaultVal;
    }

    /**
     * 获取长整型数字参数
     *
     * @param key 指定参数名
     * @return 长整形数字参数值
     */
    default Long getLong(String key) {
        Objects.requireNonNull(key);
        Number number = getByType(key, Number.class);
        if (number == null) {
            return null;
        } else if (number instanceof Long) {
            return (Long) number;  // Avoids unnecessary unbox/box
        } else {
            return number.longValue();
        }
    }

    /**
     * 获取长整型数字参数，如果该参数不存在，返回默认值
     *
     * @param key        指定参数名
     * @param defaultVal 无参数使用默认值
     * @return 参数值
     */
    default Long getLong(String key, Long defaultVal) {
        if (has(key)) {
            return getLong(key);
        }
        set(key, defaultVal);
        return defaultVal;
    }

    /**
     * 获取长整型数字参数，如果该参数不存在，调用默认值提供函数
     *
     * @param key                指定参数名
     * @param defaultValSupplier 无参数调用默认值提供函数
     * @return 参数值
     */
    default Long getLong(String key, Supplier<Long> defaultValSupplier) {
        if (has(key)) {
            return getLong(key);
        }
        Long defaultVal = defaultValSupplier.get();
        set(key, defaultVal);
        return defaultVal;
    }

    /**
     * 获取双精度浮点参数
     *
     * @param key 指定参数名
     * @return 双精度浮点参数值
     */
    default Double getDouble(String key) {
        Objects.requireNonNull(key);
        Number number = getByType(key, Number.class);
        if (number == null) {
            return null;
        } else if (number instanceof Double) {
            return (Double) number;  // Avoids unnecessary unbox/box
        } else {
            return number.doubleValue();
        }
    }

    /**
     * 获取双精度浮点参数，如果该参数不存在，返回默认值
     *
     * @param key        指定参数名
     * @param defaultVal 无参数使用默认值
     * @return 参数值
     */
    default Double getDouble(String key, Double defaultVal) {
        if (has(key)) {
            return getDouble(key);
        }
        set(key, defaultVal);
        return defaultVal;
    }

    /**
     * 获取双精度浮点参数，如果该参数不存在，调用默认值提供函数
     *
     * @param key                指定参数名
     * @param defaultValSupplier 无参数调用默认值提供函数
     * @return 参数值
     */
    default Double getDouble(String key, Supplier<Double> defaultValSupplier) {
        if (has(key)) {
            return getDouble(key);
        }
        Double defaultVal = defaultValSupplier.get();
        set(key, defaultVal);
        return defaultVal;
    }

    /**
     * 获取浮点参数
     *
     * @param key 指定参数名
     * @return 浮点参数值
     */
    default Float getFloat(String key) {
        Objects.requireNonNull(key);
        Number number = getByType(key, Number.class);
        if (number == null) {
            return null;
        } else if (number instanceof Float) {
            return (Float) number;  // Avoids unnecessary unbox/box
        } else {
            return number.floatValue();
        }
    }

    /**
     * 获取浮点参数，如果该参数不存在，返回默认值
     *
     * @param key        指定参数名
     * @param defaultVal 无参数使用默认值
     * @return 参数值
     */
    default Float getFloat(String key, Float defaultVal) {
        if (has(key)) {
            return getFloat(key);
        }
        set(key, defaultVal);
        return defaultVal;
    }

    /**
     * 获取浮点参数，如果该参数不存在，调用默认值提供函数
     *
     * @param key                指定参数名
     * @param defaultValSupplier 无参数调用默认值提供函数
     * @return 参数值
     */
    default Float getFloat(String key, Supplier<Float> defaultValSupplier) {
        if (has(key)) {
            return getFloat(key);
        }
        Float defaultVal = defaultValSupplier.get();
        set(key, defaultVal);
        return defaultVal;
    }

    /**
     * 获取布尔参数
     *
     * @param key 指定参数名
     * @return 布尔参数值
     */
    default Boolean getBoolean(String key) {
        Objects.requireNonNull(key);
        return getByType(key, Boolean.class);
    }

    /**
     * 获取布尔参数，如果该参数不存在，返回默认值
     *
     * @param key        指定参数名
     * @param defaultVal 无参数使用默认值
     * @return 参数值
     */
    default Boolean getBoolean(String key, Boolean defaultVal) {
        if (has(key)) {
            return getBoolean(key);
        }
        set(key, defaultVal);
        return defaultVal;
    }

    /**
     * 获取布尔参数，如果该参数不存在，调用默认值提供函数
     *
     * @param key                指定参数名
     * @param defaultValSupplier 无参数调用默认值提供函数
     * @return 参数值
     */
    default Boolean getBoolean(String key, Supplier<Boolean> defaultValSupplier) {
        if (has(key)) {
            return getBoolean(key);
        }
        Boolean defaultVal = defaultValSupplier.get();
        set(key, defaultVal);
        return defaultVal;
    }

    /**
     * 获取时间类型参数
     *
     * @param key 指定参数名
     * @return 时间参数值
     */
    default Instant getInstant(String key) {
        Objects.requireNonNull(key);
        Object val = get(key);
        if (val == null) {
            return null;
        }
        if (val instanceof Instant) {
            return (Instant) val;
        }
        String encoded = (String) val;
        return Instant.from(ISO_INSTANT.parse(encoded));
    }

    /**
     * 获取时间参数，如果该参数不存在，返回默认值
     *
     * @param key        指定参数名
     * @param defaultVal 无参数使用默认值
     * @return 参数值
     */
    default Instant getInstant(String key, Instant defaultVal) {
        if (has(key)) {
            return getInstant(key);
        }
        set(key, defaultVal);
        return defaultVal;
    }

    /**
     * 获取时间参数，如果该参数不存在，调用默认值提供函数
     *
     * @param key                指定参数名
     * @param defaultValSupplier 无参数调用默认值提供函数
     * @return 参数值
     */
    default Instant getInstant(String key, Supplier<Instant> defaultValSupplier) {
        if (has(key)) {
            return getInstant(key);
        }
        Instant defaultVal = defaultValSupplier.get();
        set(key, defaultVal);
        return defaultVal;
    }

    /**
     * 获取上下文类型参数
     *
     * @param key 指定参数名
     * @return 上下文参数，如果该参数不是上下文视图类型，返回null
     */
    default Ctx getCtx(String key) {
        Objects.requireNonNull(key);
        Object val = get(key);
        if (val == null) {
            return null;
        }
        if (val instanceof Ctx) {
            return (Ctx) val;
        } else if (val instanceof Map) {
            Ctxs.space(this, (Map<String, Object>) val);
        }
        return null;
    }

    /**
     * 获取时间参数，如果该参数不存在，返回默认值
     *
     * @param key        指定参数名
     * @param defaultVal 无参数使用默认值
     * @return 参数值
     */
    default Ctx getCtx(String key, Ctx defaultVal) {
        if (has(key)) {
            return getCtx(key);
        }
        set(key, defaultVal);
        return defaultVal;
    }

    /**
     * 获取时间参数，如果该参数不存在，调用默认值提供函数
     *
     * @param key                指定参数名
     * @param defaultValSupplier 无参数调用默认值提供函数
     * @return 参数值
     */
    default Ctx getCtx(String key, Supplier<Ctx> defaultValSupplier) {
        if (has(key)) {
            return getCtx(key);
        }
        Ctx defaultVal = defaultValSupplier.get();
        set(key, defaultVal);
        return defaultVal;
    }

    /**
     * 获得访问父级上下文
     *
     * @return 父级上下文，如果没有返回null
     */
    Ctx getParent();

    /**
     * 是否包含父级上下文
     *
     * @return true表示包含，反之返回false
     */
    default boolean hasParent() {
        return getParent() != null;
    }
}
