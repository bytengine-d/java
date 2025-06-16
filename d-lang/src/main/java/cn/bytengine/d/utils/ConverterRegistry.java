package cn.bytengine.d.utils;

import cn.bytengine.d.collection.SafeConcurrentHashMap;
import cn.bytengine.d.lang.TypeReference;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.ConvertException;
import cn.hutool.core.convert.TypeConverter;
import cn.hutool.core.convert.impl.*;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.lang.Opt;
import cn.hutool.core.lang.Pair;
import cn.hutool.core.util.*;

import java.io.Serializable;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.time.*;
import java.time.temporal.TemporalAccessor;
import java.util.*;
import java.util.concurrent.atomic.*;

/**
 * TODO
 *
 * @author Ban Tenio
 * @version 1.0
 */
public class ConverterRegistry implements Serializable {
    private Map<Class<?>, Converter<?>> defaultConverterMap;
    private volatile Map<Type, Converter<?>> customConverterMap;

    private static class SingletonHolder {
        private static final ConverterRegistry INSTANCE = new ConverterRegistry();
    }

    public static ConverterRegistry getInstance() {
        return ConverterRegistry.SingletonHolder.INSTANCE;
    }

    public ConverterRegistry() {
        defaultConverter();
        putCustomBySpi();
    }

    private void putCustomBySpi() {
        ServiceLoaderUtil.load(Converter.class).forEach(converter -> {
            try {
                Type type = TypeUtil.getTypeArgument(ClassUtil.getClass(converter));
                if (null != type) {
                    putCustom(type, converter);
                }
            } catch (Exception e) {
                // 忽略注册失败的
            }
        });
    }

    public ConverterRegistry putCustom(Type type, Class<? extends Converter<?>> converterClass) {
        return putCustom(type, ReflectUtil.newInstance(converterClass));
    }

    public ConverterRegistry putCustom(Type type, Converter<?> converter) {
        if (null == customConverterMap) {
            synchronized (this) {
                if (null == customConverterMap) {
                    customConverterMap = new SafeConcurrentHashMap<>();
                }
            }
        }
        customConverterMap.put(type, converter);
        return this;
    }

    public <T> Converter<T> getConverter(Type type, boolean isCustomFirst) {
        Converter<T> converter;
        if (isCustomFirst) {
            converter = this.getCustomConverter(type);
            if (null == converter) {
                converter = this.getDefaultConverter(type);
            }
        } else {
            converter = this.getDefaultConverter(type);
            if (null == converter) {
                converter = this.getCustomConverter(type);
            }
        }
        return converter;
    }

    public <T> Converter<T> getDefaultConverter(Type type) {
        final Class<?> key = TypeUtil.getClass(type);
        return (null == defaultConverterMap || null == key) ? null : (Converter<T>) defaultConverterMap.get(key);
    }

    public <T> Converter<T> getCustomConverter(Type type) {
        return (null == customConverterMap) ? null : (Converter<T>) customConverterMap.get(type);
    }

    public <T> T convert(Type type, Object value, T defaultValue, boolean isCustomFirst) throws ConvertException {
        if (TypeUtil.isUnknown(type) && null == defaultValue) {
            // 对于用户不指定目标类型的情况，返回原值
            return (T) value;
        }
        if (ObjectUtil.isNull(value)) {
            return defaultValue;
        }
        if (TypeUtil.isUnknown(type)) {
            type = defaultValue.getClass();
        }

        // issue#I7WJHH，Opt和Optional处理
        if (value instanceof Opt) {
            value = ((Opt<T>) value).get();
            if (ObjUtil.isNull(value)) {
                return defaultValue;
            }
        }
        if (value instanceof Optional) {
            value = ((Optional<T>) value).orElse(null);
            if (ObjUtil.isNull(value)) {
                return defaultValue;
            }
        }

        if (type instanceof TypeReference) {
            type = ((TypeReference<?>) type).getType();
        }

        // 自定义对象转换
        if (value instanceof TypeConverter) {
            return ObjUtil.defaultIfNull((T) ((TypeConverter) value).convert(type, value), defaultValue);
        }

        // 标准转换器
        final Converter<T> converter = getConverter(type, isCustomFirst);
        if (null != converter) {
            return converter.convert(value, defaultValue);
        }

        Class<T> rowType = (Class<T>) TypeUtil.getClass(type);
        if (null == rowType) {
            if (null != defaultValue) {
                rowType = (Class<T>) defaultValue.getClass();
            } else {
                // 无法识别的泛型类型，按照Object处理
                return (T) value;
            }
        }

        // 特殊类型转换，包括Collection、Map、强转、Array等
        final T result = convertSpecial(type, rowType, value, defaultValue);
        if (null != result) {
            return result;
        }

        // 尝试转Bean
        if (BeanUtil.isBean(rowType)) {
            return new BeanConverter<T>(type).convert(value, defaultValue);
        }

        // 无法转换
        throw new ConvertException("Can not Converter from [{}] to [{}]", value.getClass().getName(), type.getTypeName());
    }

    public <T> T convert(Type type, Object value, T defaultValue) throws ConvertException {
        return convert(type, value, defaultValue, true);
    }

    public <T> T convert(Type type, Object value) throws ConvertException {
        return convert(type, value, null);
    }

    private <T> T convertSpecial(Type type, Class<T> rowType, Object value, T defaultValue) {
        if (null == rowType) {
            return null;
        }

        // 集合转换（不可以默认强转）
        if (Collection.class.isAssignableFrom(rowType)) {
            final CollectionConverter collectionConverter = new CollectionConverter(type);
            return (T) collectionConverter.convert(value, (Collection<?>) defaultValue);
        }

        // Map类型（不可以默认强转）
        if (Map.class.isAssignableFrom(rowType)) {
            final MapConverter mapConverter = new MapConverter(type);
            return (T) mapConverter.convert(value, (Map<?, ?>) defaultValue);
        }

        // Map类型（不可以默认强转）
        if (Map.Entry.class.isAssignableFrom(rowType)) {
            final EntryConverter mapConverter = new EntryConverter(type);
            return (T) mapConverter.convert(value, (Map.Entry<?, ?>) defaultValue);
        }

        // 默认强转
        if (rowType.isInstance(value)) {
            return (T) value;
        }

        // 枚举转换
        if (rowType.isEnum()) {
            return (T) new EnumConverter(rowType).convert(value, defaultValue);
        }

        // 数组转换
        if (rowType.isArray()) {
            final ArrayConverter arrayConverter = new ArrayConverter(rowType);
            return (T) arrayConverter.convert(value, defaultValue);
        }

        // issue#I7FQ29 Class
        if ("java.lang.Class".equals(rowType.getName())) {
            final ClassConverter converter = new ClassConverter();
            return (T) converter.convert(value, (Class<?>) defaultValue);
        }

        // 空值转空Bean
        if (ObjectUtil.isEmpty(value)) {
            // issue#3649 空值转空对象，则直接实例化
            return ReflectUtil.newInstanceIfPossible(rowType);
        }

        // 表示非需要特殊转换的对象
        return null;
    }

    private ConverterRegistry defaultConverter() {
        defaultConverterMap = new SafeConcurrentHashMap<>();

        // 原始类型转换器
        defaultConverterMap.put(int.class, new PrimitiveConverter(int.class));
        defaultConverterMap.put(long.class, new PrimitiveConverter(long.class));
        defaultConverterMap.put(byte.class, new PrimitiveConverter(byte.class));
        defaultConverterMap.put(short.class, new PrimitiveConverter(short.class));
        defaultConverterMap.put(float.class, new PrimitiveConverter(float.class));
        defaultConverterMap.put(double.class, new PrimitiveConverter(double.class));
        defaultConverterMap.put(char.class, new PrimitiveConverter(char.class));
        defaultConverterMap.put(boolean.class, new PrimitiveConverter(boolean.class));

        // 包装类转换器
        defaultConverterMap.put(Number.class, new NumberConverter());
        defaultConverterMap.put(Integer.class, new NumberConverter(Integer.class));
        defaultConverterMap.put(AtomicInteger.class, new NumberConverter(AtomicInteger.class));// since 3.0.8
        defaultConverterMap.put(Long.class, new NumberConverter(Long.class));
        defaultConverterMap.put(LongAdder.class, new NumberConverter(LongAdder.class));
        defaultConverterMap.put(AtomicLong.class, new NumberConverter(AtomicLong.class));// since 3.0.8
        defaultConverterMap.put(Byte.class, new NumberConverter(Byte.class));
        defaultConverterMap.put(Short.class, new NumberConverter(Short.class));
        defaultConverterMap.put(Float.class, new NumberConverter(Float.class));
        defaultConverterMap.put(Double.class, new NumberConverter(Double.class));
        defaultConverterMap.put(DoubleAdder.class, new NumberConverter(DoubleAdder.class));
        defaultConverterMap.put(Character.class, new CharacterConverter());
        defaultConverterMap.put(Boolean.class, new BooleanConverter());
        defaultConverterMap.put(AtomicBoolean.class, new AtomicBooleanConverter());// since 3.0.8
        defaultConverterMap.put(BigDecimal.class, new NumberConverter(BigDecimal.class));
        defaultConverterMap.put(BigInteger.class, new NumberConverter(BigInteger.class));
        defaultConverterMap.put(CharSequence.class, new StringConverter());
        defaultConverterMap.put(String.class, new StringConverter());

        // URI and URL
        defaultConverterMap.put(URI.class, new URIConverter());
        defaultConverterMap.put(URL.class, new URLConverter());

        // 日期时间
        defaultConverterMap.put(Calendar.class, new CalendarConverter());
        defaultConverterMap.put(java.util.Date.class, new DateConverter(java.util.Date.class));
        defaultConverterMap.put(DateTime.class, new DateConverter(DateTime.class));
        defaultConverterMap.put(java.sql.Date.class, new DateConverter(java.sql.Date.class));
        defaultConverterMap.put(java.sql.Time.class, new DateConverter(java.sql.Time.class));
        defaultConverterMap.put(java.sql.Timestamp.class, new DateConverter(java.sql.Timestamp.class));

        // 日期时间 JDK8+(since 5.0.0)
        defaultConverterMap.put(TemporalAccessor.class, new TemporalAccessorConverter(Instant.class));
        defaultConverterMap.put(Instant.class, new TemporalAccessorConverter(Instant.class));
        defaultConverterMap.put(LocalDateTime.class, new TemporalAccessorConverter(LocalDateTime.class));
        defaultConverterMap.put(LocalDate.class, new TemporalAccessorConverter(LocalDate.class));
        defaultConverterMap.put(LocalTime.class, new TemporalAccessorConverter(LocalTime.class));
        defaultConverterMap.put(ZonedDateTime.class, new TemporalAccessorConverter(ZonedDateTime.class));
        defaultConverterMap.put(OffsetDateTime.class, new TemporalAccessorConverter(OffsetDateTime.class));
        defaultConverterMap.put(OffsetTime.class, new TemporalAccessorConverter(OffsetTime.class));
        defaultConverterMap.put(DayOfWeek.class, new TemporalAccessorConverter(DayOfWeek.class));
        defaultConverterMap.put(Month.class, new TemporalAccessorConverter(Month.class));
        defaultConverterMap.put(MonthDay.class, new TemporalAccessorConverter(MonthDay.class));
        defaultConverterMap.put(Period.class, new PeriodConverter());
        defaultConverterMap.put(Duration.class, new DurationConverter());

        // Reference
        defaultConverterMap.put(WeakReference.class, new ReferenceConverter(WeakReference.class));// since 3.0.8
        defaultConverterMap.put(SoftReference.class, new ReferenceConverter(SoftReference.class));// since 3.0.8
        defaultConverterMap.put(AtomicReference.class, new AtomicReferenceConverter());// since 3.0.8

        //AtomicXXXArray，since 5.4.5
        defaultConverterMap.put(AtomicIntegerArray.class, new AtomicIntegerArrayConverter());
        defaultConverterMap.put(AtomicLongArray.class, new AtomicLongArrayConverter());

        // 其它类型
        defaultConverterMap.put(TimeZone.class, new TimeZoneConverter());
        defaultConverterMap.put(Locale.class, new LocaleConverter());
        defaultConverterMap.put(Charset.class, new CharsetConverter());
        defaultConverterMap.put(Path.class, new PathConverter());
        defaultConverterMap.put(Currency.class, new CurrencyConverter());// since 3.0.8
        defaultConverterMap.put(UUID.class, new UUIDConverter());// since 4.0.10
        defaultConverterMap.put(StackTraceElement.class, new StackTraceElementConverter());// since 4.5.2
        defaultConverterMap.put(Optional.class, new OptionalConverter());// since 5.0.0
        defaultConverterMap.put(Opt.class, new OptConverter());// since 5.7.16
        defaultConverterMap.put(Pair.class, new PairConverter(Pair.class));// since 5.8.17

        return this;
    }
}
