package cn.bytengine.d.lang;

import cn.hutool.core.date.format.GlobalCustomFormat;

import java.time.*;
import java.time.chrono.Era;
import java.time.format.DateTimeFormatter;
import java.time.temporal.*;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

/**
 * TODO
 *
 * @author Ban Tenio
 * @version 1.0
 */
public abstract class TemporalTools {
    private TemporalTools() {
    }

    public static Duration between(Temporal startTimeInclude, Temporal endTimeExclude) {
        return Duration.between(startTimeInclude, endTimeExclude);
    }

    public static long between(Temporal startTimeInclude, Temporal endTimeExclude, ChronoUnit unit) {
        return unit.between(startTimeInclude, endTimeExclude);
    }

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

    @SuppressWarnings("unchecked")
    public static <T extends Temporal> T offset(T time, long number, TemporalUnit field) {
        if (null == time) {
            return null;
        }

        return (T) time.plus(number, field);
    }

    @SuppressWarnings("unchecked")
    public <T extends Temporal> T offset(T temporal, DayOfWeek dayOfWeek, boolean isPrevious) {
        return (T) temporal.with(isPrevious ? TemporalAdjusters.previous(dayOfWeek) : TemporalAdjusters.next(dayOfWeek));
    }

    public static int get(TemporalAccessor temporalAccessor, TemporalField field) {
        if (temporalAccessor.isSupported(field)) {
            return temporalAccessor.get(field);
        }

        return (int) field.range().getMinimum();
    }

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

    public static boolean isIn(TemporalAccessor date, TemporalAccessor beginDate, TemporalAccessor endDate) {
        return isIn(date, beginDate, endDate, true, true);
    }

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
