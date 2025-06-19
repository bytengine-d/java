package cn.bytengine.d.lang;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.util.Date;
import java.util.TimeZone;

/**
 * JDK8+中的{@link LocalDateTime} 工具类封装
 *
 * @author Ban Tenio
 * @version 1.0
 */
public abstract class LocalDateTimeTools {
    private LocalDateTimeTools() {
    }

    /**
     * 当前时间，默认时区
     *
     * @return {@link LocalDateTime}
     */
    public static LocalDateTime now() {
        return LocalDateTime.now();
    }

    /**
     * {@link Instant}转{@link LocalDateTime}，使用默认时区
     *
     * @param instant {@link Instant}
     * @return {@link LocalDateTime}
     */
    public static LocalDateTime of(Instant instant) {
        return of(instant, ZoneId.systemDefault());
    }

    /**
     * {@link Instant}转{@link LocalDateTime}，使用UTC时区
     *
     * @param instant {@link Instant}
     * @return {@link LocalDateTime}
     */
    public static LocalDateTime ofUTC(Instant instant) {
        return of(instant, ZoneId.of("UTC"));
    }

    /**
     * {@link ZonedDateTime}转{@link LocalDateTime}
     *
     * @param zonedDateTime {@link ZonedDateTime}
     * @return {@link LocalDateTime}
     */
    public static LocalDateTime of(ZonedDateTime zonedDateTime) {
        if (null == zonedDateTime) {
            return null;
        }
        return zonedDateTime.toLocalDateTime();
    }

    /**
     * {@link Instant}转{@link LocalDateTime}
     *
     * @param instant {@link Instant}
     * @param zoneId  时区
     * @return {@link LocalDateTime}
     */
    public static LocalDateTime of(Instant instant, ZoneId zoneId) {
        if (null == instant) {
            return null;
        }

        return LocalDateTime.ofInstant(instant, ObjectTools.defaultIfNull(zoneId, ZoneId::systemDefault));
    }

    /**
     * {@link Instant}转{@link LocalDateTime}
     *
     * @param instant  {@link Instant}
     * @param timeZone 时区
     * @return {@link LocalDateTime}
     */
    public static LocalDateTime of(Instant instant, TimeZone timeZone) {
        if (null == instant) {
            return null;
        }

        return of(instant, ObjectTools.defaultIfNull(timeZone, TimeZone::getDefault).toZoneId());
    }

    /**
     * 毫秒转{@link LocalDateTime}，使用默认时区
     *
     * <p>注意：此方法使用默认时区，如果非UTC，会产生时间偏移</p>
     *
     * @param epochMilli 从1970-01-01T00:00:00Z开始计数的毫秒数
     * @return {@link LocalDateTime}
     */
    public static LocalDateTime of(long epochMilli) {
        return of(Instant.ofEpochMilli(epochMilli));
    }

    /**
     * 毫秒转{@link LocalDateTime}，使用UTC时区
     *
     * @param epochMilli 从1970-01-01T00:00:00Z开始计数的毫秒数
     * @return {@link LocalDateTime}
     */
    public static LocalDateTime ofUTC(long epochMilli) {
        return ofUTC(Instant.ofEpochMilli(epochMilli));
    }

    /**
     * 毫秒转{@link LocalDateTime}，根据时区不同，结果会产生时间偏移
     *
     * @param epochMilli 从1970-01-01T00:00:00Z开始计数的毫秒数
     * @param zoneId     时区
     * @return {@link LocalDateTime}
     */
    public static LocalDateTime of(long epochMilli, ZoneId zoneId) {
        return of(Instant.ofEpochMilli(epochMilli), zoneId);
    }

    /**
     * 毫秒转{@link LocalDateTime}，结果会产生时间偏移
     *
     * @param epochMilli 从1970-01-01T00:00:00Z开始计数的毫秒数
     * @param timeZone   时区
     * @return {@link LocalDateTime}
     */
    public static LocalDateTime of(long epochMilli, TimeZone timeZone) {
        return of(Instant.ofEpochMilli(epochMilli), timeZone);
    }

    /**
     * {@link Date}转{@link LocalDateTime}，使用默认时区
     *
     * @param date Date对象
     * @return {@link LocalDateTime}
     */
    public static LocalDateTime of(Date date) {
        if (null == date) {
            return null;
        }
        return of(date.toInstant());
    }

    /**
     * {@link TemporalAccessor}转{@link LocalDateTime}，使用默认时区
     *
     * @param temporalAccessor {@link TemporalAccessor}
     * @return {@link LocalDateTime}
     */
    public static LocalDateTime of(TemporalAccessor temporalAccessor) {
        if (null == temporalAccessor) {
            return null;
        }

        if (temporalAccessor instanceof LocalDate) {
            return ((LocalDate) temporalAccessor).atStartOfDay();
        } else if (temporalAccessor instanceof Instant) {
            return LocalDateTime.ofInstant((Instant) temporalAccessor, ZoneId.systemDefault());
        }

        // issue#3301
        try {
            return LocalDateTime.from(temporalAccessor);
        } catch (final Exception ignore) {
            //ignore
        }

        try {
            return ZonedDateTime.from(temporalAccessor).toLocalDateTime();
        } catch (final Exception ignore) {
            //ignore
        }

        try {
            return LocalDateTime.ofInstant(Instant.from(temporalAccessor), ZoneId.systemDefault());
        } catch (final Exception ignore) {
            //ignore
        }

        return LocalDateTime.of(
                TemporalTools.get(temporalAccessor, ChronoField.YEAR),
                TemporalTools.get(temporalAccessor, ChronoField.MONTH_OF_YEAR),
                TemporalTools.get(temporalAccessor, ChronoField.DAY_OF_MONTH),
                TemporalTools.get(temporalAccessor, ChronoField.HOUR_OF_DAY),
                TemporalTools.get(temporalAccessor, ChronoField.MINUTE_OF_HOUR),
                TemporalTools.get(temporalAccessor, ChronoField.SECOND_OF_MINUTE),
                TemporalTools.get(temporalAccessor, ChronoField.NANO_OF_SECOND)
        );
    }

    /**
     * {@link TemporalAccessor}转{@link LocalDate}，使用默认时区
     *
     * @param temporalAccessor {@link TemporalAccessor}
     * @return {@link LocalDate}
     */
    public static LocalDate ofDate(TemporalAccessor temporalAccessor) {
        if (null == temporalAccessor) {
            return null;
        }

        if (temporalAccessor instanceof LocalDateTime) {
            return ((LocalDateTime) temporalAccessor).toLocalDate();
        } else if (temporalAccessor instanceof Instant) {
            return of(temporalAccessor).toLocalDate();
        }

        return LocalDate.of(
                TemporalTools.get(temporalAccessor, ChronoField.YEAR),
                TemporalTools.get(temporalAccessor, ChronoField.MONTH_OF_YEAR),
                TemporalTools.get(temporalAccessor, ChronoField.DAY_OF_MONTH)
        );
    }

    /**
     * 解析日期时间字符串为{@link LocalDateTime}，仅支持yyyy-MM-dd'T'HH:mm:ss格式，例如：2007-12-03T10:15:30<br>
     * 即{@link DateTimeFormatter#ISO_LOCAL_DATE_TIME}
     *
     * @param text 日期时间字符串
     * @return {@link LocalDateTime}
     */
    public static LocalDateTime parse(CharSequence text) {
        return parse(text, (DateTimeFormatter) null);
    }

    /**
     * 解析日期时间字符串为{@link LocalDateTime}，格式支持日期时间、日期、时间<br>
     * 如果formatter为{@code null}，则使用{@link DateTimeFormatter#ISO_LOCAL_DATE_TIME}
     *
     * @param text      日期时间字符串
     * @param formatter 日期格式化器，预定义的格式见：{@link DateTimeFormatter}
     * @return {@link LocalDateTime}
     */
    public static LocalDateTime parse(CharSequence text, DateTimeFormatter formatter) {
        if (CharSequenceTools.isBlank(text)) {
            return null;
        }
        if (null == formatter) {
            return LocalDateTime.parse(text);
        }

        return of(formatter.parse(text));
    }

    /**
     * 解析日期时间字符串为{@link LocalDateTime}
     *
     * @param text   日期时间字符串
     * @param format 日期格式，类似于yyyy-MM-dd HH:mm:ss,SSS
     * @return {@link LocalDateTime}
     */
    public static LocalDateTime parse(CharSequence text, String format) {
        if (CharSequenceTools.isBlank(text)) {
            return null;
        }

        if (GlobalCustomFormat.isCustomFormat(format)) {
            return of(GlobalCustomFormat.parse(text, format));
        }

        DateTimeFormatter formatter = null;
        if (CharSequenceTools.isNotBlank(format)) {
            // 修复yyyyMMddHHmmssSSS格式不能解析的问题
            // fix issue#1082
            //see https://stackoverflow.com/questions/22588051/is-java-time-failing-to-parse-fraction-of-second
            // jdk8 bug at: https://bugs.openjdk.java.net/browse/JDK-8031085
            if (CharSequenceTools.startWithIgnoreEquals(format, DatePatterns.PURE_DATETIME_PATTERN)) {
                final String fraction = CharSequenceTools.removePrefix(format, DatePatterns.PURE_DATETIME_PATTERN);
                if (RegexTools.isMatch("[S]{1,2}", fraction)) {
                    //将yyyyMMddHHmmssS、yyyyMMddHHmmssSS的日期统一替换为yyyyMMddHHmmssSSS格式，用0补
                    text += CharSequenceTools.repeat('0', 3 - fraction.length());
                }
                formatter = new DateTimeFormatterBuilder()
                        .appendPattern(DatePatterns.PURE_DATETIME_PATTERN)
                        .appendValue(ChronoField.MILLI_OF_SECOND, 3)
                        .toFormatter();
            } else {
                formatter = DateTimeFormatter.ofPattern(format);
            }
        }

        return parse(text, formatter);
    }
}
