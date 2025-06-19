package cn.bytengine.d.lang;

import java.time.*;
import java.time.chrono.Era;
import java.time.format.DateTimeFormatter;
import java.time.temporal.*;
import java.util.concurrent.TimeUnit;

/**
 * {@link Temporal} 工具类封装
 *
 * @author Ban Tenio
 * @version 1.0
 */
public abstract class TemporalTools {
    private TemporalTools() {
    }

    /**
     * 获取两个日期的差，如果结束时间早于开始时间，获取结果为负。
     * <p>
     * 返回结果为{@link Duration}对象，通过调用toXXX方法返回相差单位
     *
     * @param startTimeInclude 开始时间（包含）
     * @param endTimeExclude   结束时间（不包含）
     * @return 时间差 {@link Duration}对象
     */
    public static Duration between(Temporal startTimeInclude, Temporal endTimeExclude) {
        return Duration.between(startTimeInclude, endTimeExclude);
    }

    /**
     * 获取两个日期的差，如果结束时间早于开始时间，获取结果为负。
     * <p>
     * 返回结果为时间差的long值
     *
     * @param startTimeInclude 开始时间（包括）
     * @param endTimeExclude   结束时间（不包括）
     * @param unit             时间差单位
     * @return 时间差
     */
    public static long between(Temporal startTimeInclude, Temporal endTimeExclude, ChronoUnit unit) {
        return unit.between(startTimeInclude, endTimeExclude);
    }

    /**
     * 将 {@link TimeUnit} 转换为 {@link ChronoUnit}.
     *
     * @param unit 被转换的{@link TimeUnit}单位，如果为{@code null}返回{@code null}
     * @return {@link ChronoUnit}
     */
    public static ChronoUnit toChronoUnit(TimeUnit unit) throws IllegalArgumentException {
        if (null == unit) {
            return null;
        }
        switch (unit) {
            case NANOSECONDS:
                return ChronoUnit.NANOS;
            case MICROSECONDS:
                return ChronoUnit.MICROS;
            case MILLISECONDS:
                return ChronoUnit.MILLIS;
            case SECONDS:
                return ChronoUnit.SECONDS;
            case MINUTES:
                return ChronoUnit.MINUTES;
            case HOURS:
                return ChronoUnit.HOURS;
            case DAYS:
                return ChronoUnit.DAYS;
            default:
                throw new IllegalArgumentException("Unknown TimeUnit constant");
        }
    }

    /**
     * 转换 {@link ChronoUnit} 到 {@link TimeUnit}.
     *
     * @param unit {@link ChronoUnit}，如果为{@code null}返回{@code null}
     * @return {@link TimeUnit}
     * @throws IllegalArgumentException 如果{@link TimeUnit}没有对应单位抛出
     */
    public static TimeUnit toTimeUnit(ChronoUnit unit) throws IllegalArgumentException {
        if (null == unit) {
            return null;
        }
        switch (unit) {
            case NANOS:
                return TimeUnit.NANOSECONDS;
            case MICROS:
                return TimeUnit.MICROSECONDS;
            case MILLIS:
                return TimeUnit.MILLISECONDS;
            case SECONDS:
                return TimeUnit.SECONDS;
            case MINUTES:
                return TimeUnit.MINUTES;
            case HOURS:
                return TimeUnit.HOURS;
            case DAYS:
                return TimeUnit.DAYS;
            default:
                throw new IllegalArgumentException("ChronoUnit cannot be converted to TimeUnit: " + unit);
        }
    }

    /**
     * 日期偏移,根据field不同加不同值（偏移会修改传入的对象）
     *
     * @param <T>    日期类型，如LocalDate或LocalDateTime
     * @param time   {@link Temporal}
     * @param number 偏移量，正数为向后偏移，负数为向前偏移
     * @param field  偏移单位，见{@link ChronoUnit}，不能为null
     * @return 偏移后的日期时间
     */
    @SuppressWarnings("unchecked")
    public static <T extends Temporal> T offset(T time, long number, TemporalUnit field) {
        if (null == time) {
            return null;
        }

        return (T) time.plus(number, field);
    }

    /**
     * 偏移到指定的周几
     *
     * @param temporal   日期或者日期时间
     * @param dayOfWeek  周几
     * @param <T>        日期类型，如LocalDate或LocalDateTime
     * @param isPrevious 是否向前偏移，{@code true}向前偏移，{@code false}向后偏移。
     * @return 偏移后的日期
     */
    @SuppressWarnings("unchecked")
    public <T extends Temporal> T offset(T temporal, DayOfWeek dayOfWeek, boolean isPrevious) {
        return (T) temporal.with(isPrevious ? TemporalAdjusters.previous(dayOfWeek) : TemporalAdjusters.next(dayOfWeek));
    }

    /**
     * 安全获取时间的某个属性，属性不存在返回最小值，一般为0<br>
     * 注意请谨慎使用此方法，某些{@link TemporalAccessor#isSupported(TemporalField)}为{@code false}的方法返回最小值
     *
     * @param temporalAccessor 需要获取的时间对象
     * @param field            需要获取的属性
     * @return 时间的值，如果无法获取则获取最小值，一般为0
     */
    public static int get(TemporalAccessor temporalAccessor, TemporalField field) {
        if (temporalAccessor.isSupported(field)) {
            return temporalAccessor.get(field);
        }

        return (int) field.range().getMinimum();
    }

    /**
     * 格式化日期时间为指定格式<br>
     * 如果为{@link Month}，调用{@link Month#toString()}
     *
     * @param time      {@link TemporalAccessor}
     * @param formatter 日期格式化器，预定义的格式见：{@link DateTimeFormatter}
     * @return 格式化后的字符串
     */
    public static String format(TemporalAccessor time, DateTimeFormatter formatter) {
        if (null == time) {
            return null;
        }

        if (time instanceof Month) {
            return time.toString();
        }

        if (null == formatter) {
            formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        }

        try {
            return formatter.format(time);
        } catch (UnsupportedTemporalTypeException e) {
            if (time instanceof LocalDate && e.getMessage().contains("HourOfDay")) {
                // 用户传入LocalDate，但是要求格式化带有时间部分，转换为LocalDateTime重试
                return formatter.format(((LocalDate) time).atStartOfDay());
            } else if (time instanceof LocalTime && e.getMessage().contains("YearOfEra")) {
                // 用户传入LocalTime，但是要求格式化带有日期部分，转换为LocalDateTime重试
                return formatter.format(((LocalTime) time).atDate(LocalDate.now()));
            } else if (time instanceof Instant) {
                // 时间戳没有时区信息，赋予默认时区
                return formatter.format(((Instant) time).atZone(ZoneId.systemDefault()));
            }
            throw e;
        }
    }

    /**
     * 格式化日期时间为指定格式<br>
     * 如果为{@link Month}，调用{@link Month#toString()}
     *
     * @param time   {@link TemporalAccessor}
     * @param format 日期格式
     * @return 格式化后的字符串
     */
    public static String format(TemporalAccessor time, String format) {
        if (null == time) {
            return null;
        }

        if (time instanceof DayOfWeek || time instanceof java.time.Month || time instanceof Era || time instanceof MonthDay) {
            return time.toString();
        }

        // 检查自定义格式
        if (GlobalCustomFormat.isCustomFormat(format)) {
            return GlobalCustomFormat.format(time, format);
        }

        final DateTimeFormatter formatter = CharSequenceTools.isBlank(format)
                ? null : DateTimeFormatter.ofPattern(format);

        return format(time, formatter);
    }

    /**
     * {@link TemporalAccessor}转换为 时间戳（从1970-01-01T00:00:00Z开始的毫秒数）<br>
     * 如果为{@link Month}，调用{@link Month#getValue()}
     *
     * @param temporalAccessor Date对象
     * @return {@link Instant}对象
     */
    public static long toEpochMilli(TemporalAccessor temporalAccessor) {
        if (temporalAccessor instanceof Month) {
            return ((Month) temporalAccessor).getValue();
        } else if (temporalAccessor instanceof DayOfWeek) {
            return ((DayOfWeek) temporalAccessor).getValue();
        } else if (temporalAccessor instanceof Era) {
            return ((Era) temporalAccessor).getValue();
        }
        return toInstant(temporalAccessor).toEpochMilli();
    }

    /**
     * {@link TemporalAccessor}转换为 {@link Instant}对象
     *
     * @param temporalAccessor Date对象
     * @return {@link Instant}对象
     */
    public static Instant toInstant(TemporalAccessor temporalAccessor) {
        if (null == temporalAccessor) {
            return null;
        }

        Instant result;
        if (temporalAccessor instanceof Instant) {
            result = (Instant) temporalAccessor;
        } else if (temporalAccessor instanceof LocalDateTime) {
            result = ((LocalDateTime) temporalAccessor).atZone(ZoneId.systemDefault()).toInstant();
        } else if (temporalAccessor instanceof ZonedDateTime) {
            result = ((ZonedDateTime) temporalAccessor).toInstant();
        } else if (temporalAccessor instanceof OffsetDateTime) {
            result = ((OffsetDateTime) temporalAccessor).toInstant();
        } else if (temporalAccessor instanceof LocalDate) {
            result = ((LocalDate) temporalAccessor).atStartOfDay(ZoneId.systemDefault()).toInstant();
        } else if (temporalAccessor instanceof LocalTime) {
            // 指定本地时间转换 为Instant，取当天日期
            result = ((LocalTime) temporalAccessor).atDate(LocalDate.now()).atZone(ZoneId.systemDefault()).toInstant();
        } else if (temporalAccessor instanceof OffsetTime) {
            // 指定本地时间转换 为Instant，取当天日期
            result = ((OffsetTime) temporalAccessor).atDate(LocalDate.now()).toInstant();
        } else {
            // issue#1891@Github
            // Instant.from不能完成日期转换
            //result = Instant.from(temporalAccessor);
            result = toInstant(LocalDateTimeTools.of(temporalAccessor));
        }

        return result;
    }

    /**
     * 当前日期是否在日期指定范围内<br>
     * 起始日期和结束日期可以互换
     *
     * @param date      被检查的日期
     * @param beginDate 起始日期（包含）
     * @param endDate   结束日期（包含）
     * @return 是否在范围内
     */
    public static boolean isIn(TemporalAccessor date, TemporalAccessor beginDate, TemporalAccessor endDate) {
        return isIn(date, beginDate, endDate, true, true);
    }

    /**
     * 当前日期是否在日期指定范围内<br>
     * 起始日期和结束日期可以互换<br>
     * 通过includeBegin, includeEnd参数控制日期范围区间是否为开区间，例如：传入参数：includeBegin=true, includeEnd=false，
     * 则本方法会判断 date ∈ (beginDate, endDate] 是否成立
     *
     * @param date         被检查的日期
     * @param beginDate    起始日期
     * @param endDate      结束日期
     * @param includeBegin 时间范围是否包含起始日期
     * @param includeEnd   时间范围是否包含结束日期
     * @return 是否在范围内
     */
    public static boolean isIn(TemporalAccessor date, TemporalAccessor beginDate, TemporalAccessor endDate,
                               boolean includeBegin, boolean includeEnd) {
        if (date == null || beginDate == null || endDate == null) {
            throw new IllegalArgumentException("参数不可为null");
        }

        final long thisMills = toEpochMilli(date);
        final long beginMills = toEpochMilli(beginDate);
        final long endMills = toEpochMilli(endDate);
        final long rangeMin = Math.min(beginMills, endMills);
        final long rangeMax = Math.max(beginMills, endMills);

        // 先判断是否满足 date ∈ (beginDate, endDate)
        boolean isIn = rangeMin < thisMills && thisMills < rangeMax;

        // 若不满足，则再判断是否在时间范围的边界上
        if (!isIn && includeBegin) {
            isIn = thisMills == rangeMin;
        }

        if (!isIn && includeEnd) {
            isIn = thisMills == rangeMax;
        }

        return isIn;
    }
}
