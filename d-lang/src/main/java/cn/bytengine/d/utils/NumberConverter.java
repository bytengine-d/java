package cn.bytengine.d.utils;

import cn.bytengine.d.lang.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.temporal.TemporalAccessor;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.DoubleAdder;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.Function;

/**
 * TODO
 *
 * @author Ban Tenio
 * @version 1.0
 */
public class NumberConverter extends AbstractConverter<Number> {
    private final Class<? extends Number> targetType;

    public NumberConverter() {
        this.targetType = Number.class;
    }

    public NumberConverter(Class<? extends Number> clazz) {
        this.targetType = (null == clazz) ? Number.class : clazz;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Class<Number> getTargetType() {
        return (Class<Number>) this.targetType;
    }

    @Override
    protected Number convertInternal(Object value) {
        return convert(value, this.targetType, this::convertToStr);
    }

    @Override
    protected String convertToStr(Object value) {
        final String result = CharSequenceTools.trim(super.convertToStr(value));
        if (null != result && result.length() > 1) {
            final char c = Character.toUpperCase(result.charAt(result.length() - 1));
            if (c == 'D' || c == 'L' || c == 'F') {
                // 类型标识形式（例如123.6D）
                return CharSequenceTools.subPre(result, -1);
            }
        }

        return result;
    }

    protected static Number convert(Object value, Class<? extends Number> targetType, Function<Object, String> toStrFunc) {
        // 枚举转换为数字默认为其顺序
        if (value instanceof Enum) {
            return convert(((Enum<?>) value).ordinal(), targetType, toStrFunc);
        }

        // since 5.7.18
        if (value instanceof byte[]) {
            return ByteTools.bytesToNumber((byte[]) value, targetType, ByteTools.DEFAULT_ORDER);
        }

        if (Byte.class == targetType) {
            if (value instanceof Number) {
                return ((Number) value).byteValue();
            } else if (value instanceof Boolean) {
                return BooleanTools.toByteObj((Boolean) value);
            }
            final String valueStr = toStrFunc.apply(value);
            try {
                return CharSequenceTools.isBlank(valueStr) ? null : Byte.valueOf(valueStr);
            } catch (NumberFormatException e) {
                return NumberTools.parseNumber(valueStr).byteValue();
            }
        } else if (Short.class == targetType) {
            if (value instanceof Number) {
                return ((Number) value).shortValue();
            } else if (value instanceof Boolean) {
                return BooleanTools.toShortObj((Boolean) value);
            }
            final String valueStr = toStrFunc.apply((value));
            try {
                return CharSequenceTools.isBlank(valueStr) ? null : Short.valueOf(valueStr);
            } catch (NumberFormatException e) {
                return NumberTools.parseNumber(valueStr).shortValue();
            }
        } else if (Integer.class == targetType) {
            if (value instanceof Number) {
                return ((Number) value).intValue();
            } else if (value instanceof Boolean) {
                return BooleanTools.toInteger((Boolean) value);
            } else if (value instanceof Date) {
                return (int) ((Date) value).getTime();
            } else if (value instanceof Calendar) {
                return (int) ((Calendar) value).getTimeInMillis();
            } else if (value instanceof TemporalAccessor) {
                return (int) TemporalTools.toInstant((TemporalAccessor) value).toEpochMilli();
            }
            final String valueStr = toStrFunc.apply((value));
            return CharSequenceTools.isBlank(valueStr) ? null : NumberTools.parseInt(valueStr);
        } else if (AtomicInteger.class == targetType) {
            final Number number = convert(value, Integer.class, toStrFunc);
            if (null != number) {
                return new AtomicInteger(number.intValue());
            }
        } else if (Long.class == targetType) {
            if (value instanceof Number) {
                return ((Number) value).longValue();
            } else if (value instanceof Boolean) {
                return BooleanTools.toLongObj((Boolean) value);
            } else if (value instanceof Date) {
                return ((Date) value).getTime();
            } else if (value instanceof Calendar) {
                return ((Calendar) value).getTimeInMillis();
            } else if (value instanceof TemporalAccessor) {
                return TemporalTools.toInstant((TemporalAccessor) value).toEpochMilli();
            }
            final String valueStr = toStrFunc.apply((value));
            return CharSequenceTools.isBlank(valueStr) ? null : NumberTools.parseLong(valueStr);
        } else if (AtomicLong.class == targetType) {
            final Number number = convert(value, Long.class, toStrFunc);
            if (null != number) {
                return new AtomicLong(number.longValue());
            }
        } else if (LongAdder.class == targetType) {
            //jdk8 新增
            final Number number = convert(value, Long.class, toStrFunc);
            if (null != number) {
                final LongAdder longValue = new LongAdder();
                longValue.add(number.longValue());
                return longValue;
            }
        } else if (Float.class == targetType) {
            if (value instanceof Number) {
                return ((Number) value).floatValue();
            } else if (value instanceof Boolean) {
                return BooleanTools.toFloatObj((Boolean) value);
            }
            final String valueStr = toStrFunc.apply((value));
            return CharSequenceTools.isBlank(valueStr) ? null : NumberTools.parseFloat(valueStr);
        } else if (Double.class == targetType) {
            if (value instanceof Number) {
                return NumberTools.toDouble((Number) value);
            } else if (value instanceof Boolean) {
                return BooleanTools.toDoubleObj((Boolean) value);
            }
            final String valueStr = toStrFunc.apply((value));
            return CharSequenceTools.isBlank(valueStr) ? null : NumberTools.parseDouble(valueStr);
        } else if (DoubleAdder.class == targetType) {
            //jdk8 新增
            final Number number = convert(value, Double.class, toStrFunc);
            if (null != number) {
                final DoubleAdder doubleAdder = new DoubleAdder();
                doubleAdder.add(number.doubleValue());
                return doubleAdder;
            }
        } else if (BigDecimal.class == targetType) {
            return toBigDecimal(value, toStrFunc);
        } else if (BigInteger.class == targetType) {
            return toBigInteger(value, toStrFunc);
        } else if (Number.class == targetType) {
            if (value instanceof Number) {
                return (Number) value;
            } else if (value instanceof Boolean) {
                return BooleanTools.toInteger((Boolean) value);
            }
            final String valueStr = toStrFunc.apply((value));
            return CharSequenceTools.isBlank(valueStr) ? null : NumberTools.parseNumber(valueStr);
        }

        throw new UnsupportedOperationException(CharSequenceTools.format("Unsupport Number type: {}", targetType.getName()));
    }

    private static BigDecimal toBigDecimal(Object value, Function<Object, String> toStrFunc) {
        if (value instanceof Number) {
            return NumberTools.toBigDecimal((Number) value);
        } else if (value instanceof Boolean) {
            return ((boolean) value) ? BigDecimal.ONE : BigDecimal.ZERO;
        }

        //对于Double类型，先要转换为String，避免精度问题
        return NumberTools.toBigDecimal(toStrFunc.apply(value));
    }

    private static BigInteger toBigInteger(Object value, Function<Object, String> toStrFunc) {
        if (value instanceof Long) {
            return BigInteger.valueOf((Long) value);
        } else if (value instanceof Boolean) {
            return (boolean) value ? BigInteger.ONE : BigInteger.ZERO;
        }

        return NumberTools.toBigInteger(toStrFunc.apply(value));
    }
}
