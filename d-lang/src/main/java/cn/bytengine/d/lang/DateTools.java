package cn.bytengine.d.lang;

import java.time.LocalDateTime;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

/**
 * 日期时间工具类
 *
 * @author Ban Tenio
 * @version 1.0
 */
public abstract class DateTools {
    private DateTools() {
    }

    /**
     * 格式化日期间隔输出，精确到毫秒
     *
     * @param betweenMs 日期间隔
     * @return XX天XX小时XX分XX秒XX毫秒
     */
    public static String formatBetween(long betweenMs) {
        return new BetweenFormatter(betweenMs, BetweenFormatter.Level.MILLISECOND).format();
    }

    /**
     * {@link TemporalAccessor}类型时间转为{@link Date}<br>
     * 始终根据已有{@link TemporalAccessor} 产生新的{@link Date}对象
     *
     * @param temporalAccessor {@link TemporalAccessor},常用子类： {@link LocalDateTime}、 LocalDate，如果传入{@code null}，返回{@code null}
     * @return 时间对象
     */
    public static Date date(TemporalAccessor temporalAccessor) {
        if (temporalAccessor == null) {
            return null;
        }
        return new Date(TemporalTools.toInstant(temporalAccessor).toEpochMilli());
    }

    /**
     * Long类型时间转为{@link Date}<br>
     * 只支持毫秒级别时间戳，如果需要秒级别时间戳，请自行×1000
     *
     * @param date Long类型Date（Unix时间戳）
     * @return 时间对象
     */
    public static Date date(long date) {
        return new Date(date);
    }
}
