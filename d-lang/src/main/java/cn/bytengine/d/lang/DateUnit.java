package cn.bytengine.d.lang;

import java.time.temporal.ChronoUnit;

/**
 * 日期时间单位，每个单位都是以毫秒为基数
 *
 * @author Ban Tenio
 * @version 1.0
 */
public enum DateUnit {
    /**
     * 一毫秒
     */
    MS(1),
    /**
     * 一秒的毫秒数
     */
    SECOND(1000),
    /**
     * 一分钟的毫秒数
     */
    MINUTE(SECOND.getMillis() * 60),
    /**
     * 一小时的毫秒数
     */
    HOUR(MINUTE.getMillis() * 60),
    /**
     * 一天的毫秒数
     */
    DAY(HOUR.getMillis() * 24),
    /**
     * 一周的毫秒数
     */
    WEEK(DAY.getMillis() * 7);

    private final long millis;

    DateUnit(long millis) {
        this.millis = millis;
    }

    /**
     * @return 单位对应的毫秒数
     */
    public long getMillis() {
        return this.millis;
    }

    /**
     * 单位兼容转换，将DateUnit转换为对应的{@link ChronoUnit}
     *
     * @return {@link ChronoUnit}
     */
    public ChronoUnit toChronoUnit() {
        return toChronoUnit(this);
    }

    /**
     * 单位兼容转换，将{@link ChronoUnit}转换为对应的DateUnit
     *
     * @param unit {@link ChronoUnit}
     * @return DateUnit，null表示不支持此单位
     */
    public static DateUnit of(ChronoUnit unit) {
        switch (unit) {
            case MICROS:
                return DateUnit.MS;
            case SECONDS:
                return DateUnit.SECOND;
            case MINUTES:
                return MINUTE;
            case HOURS:
                return HOUR;
            case DAYS:
                return DAY;
            case WEEKS:
                return WEEK;
        }
        return null;
    }

    /**
     * 单位兼容转换，将DateUnit转换为对应的{@link ChronoUnit}
     *
     * @param unit DateUnit
     * @return {@link ChronoUnit}
     */
    public static ChronoUnit toChronoUnit(DateUnit unit) {
        switch (unit) {
            case MS:
                return ChronoUnit.MICROS;
            case SECOND:
                return ChronoUnit.SECONDS;
            case MINUTE:
                return ChronoUnit.MINUTES;
            case HOUR:
                return ChronoUnit.HOURS;
            case DAY:
                return ChronoUnit.DAYS;
            case WEEK:
                return ChronoUnit.WEEKS;
        }
        return null;
    }
}
