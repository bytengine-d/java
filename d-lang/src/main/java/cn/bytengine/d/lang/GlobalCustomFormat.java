package cn.bytengine.d.lang;

import cn.bytengine.d.collection.SafeConcurrentHashMap;

import java.time.temporal.TemporalAccessor;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

/**
 * TODO
 *
 * @author Ban Tenio
 * @version 1.0
 */
public abstract class GlobalCustomFormat {
    private GlobalCustomFormat() {
    }

    public static final String FORMAT_SECONDS = "#sss";
    public static final String FORMAT_MILLISECONDS = "#SSS";

    private static final Map<CharSequence, Function<Date, String>> formatterMap;
    private static final Map<CharSequence, Function<CharSequence, Date>> parserMap;

    static {
        formatterMap = new SafeConcurrentHashMap<>();
        parserMap = new SafeConcurrentHashMap<>();

        // Hutool预设的几种自定义格式
        putFormatter(FORMAT_SECONDS, (date) -> String.valueOf(floorDiv(date.getTime(), 1000)));
        putParser(FORMAT_SECONDS, (dateStr) -> DateTools.date(multiplyExact(Long.parseLong(dateStr.toString()), 1000)));

        putFormatter(FORMAT_MILLISECONDS, (date) -> String.valueOf(date.getTime()));
        putParser(FORMAT_MILLISECONDS, (dateStr) -> DateTools.date(Long.parseLong(dateStr.toString())));
    }

    public static long multiplyExact(long x, int y) {
        return multiplyExact(x, (long) y);
    }

    public static long multiplyExact(long x, long y) {
        long r = x * y;
        long ax = Math.abs(x);
        long ay = Math.abs(y);
        if (((ax | ay) >>> 31 != 0)) {
            // Some bits greater than 2^31 that might cause overflow
            // Check the result using the divide operator
            // and check for the special case of Long.MIN_VALUE * -1
            if (((y != 0) && (r / y != x)) ||
                    (x == Long.MIN_VALUE && y == -1)) {
                throw new ArithmeticException("long overflow");
            }
        }
        return r;
    }

    private static long floorDiv(long x, int y) {
        return floorDiv(x, (long) y);
    }

    private static long floorDiv(long x, long y) {
        long r = x / y;
        // if the signs are different and modulo not zero, round down
        if ((x ^ y) < 0 && (r * y != x)) {
            r--;
        }
        return r;
    }

    public static void putFormatter(String format, Function<Date, String> func) {
        AssertTools.notNull(format, "Format must be not null !");
        AssertTools.notNull(func, "Function must be not null !");
        formatterMap.put(format, func);
    }

    public static void putParser(String format, Function<CharSequence, Date> func) {
        AssertTools.notNull(format, "Format must be not null !");
        AssertTools.notNull(func, "Function must be not null !");
        parserMap.put(format, func);
    }

    public static boolean isCustomFormat(String format) {
        return formatterMap.containsKey(format);
    }

    public static String format(Date date, CharSequence format) {
        if (null != formatterMap) {
            final Function<Date, String> func = formatterMap.get(format);
            if (null != func) {
                return func.apply(date);
            }
        }

        return null;
    }

    public static String format(TemporalAccessor temporalAccessor, CharSequence format) {
        return format(DateTools.date(temporalAccessor), format);
    }

    public static Date parse(CharSequence dateStr, String format) {
        if (null != parserMap) {
            final Function<CharSequence, Date> func = parserMap.get(format);
            if (null != func) {
                return func.apply(dateStr);
            }
        }

        return null;
    }
}
