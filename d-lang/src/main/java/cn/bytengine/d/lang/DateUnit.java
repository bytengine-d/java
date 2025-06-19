package cn.bytengine.d.lang;

import java.time.temporal.ChronoUnit;

/**
 * TODO
 *
 * @author Ban Tenio
 * @version 1.0
 */
public enum DateUnit {

    MS(1),
    SECOND(1000),
    MINUTE(SECOND.getMillis() * 60),
    HOUR(MINUTE.getMillis() * 60),
    DAY(HOUR.getMillis() * 24),
    WEEK(DAY.getMillis() * 7);

    private final long millis;

    DateUnit(long millis) {
        this.millis = millis;
    }

    public long getMillis() {
        return this.millis;
    }

    public ChronoUnit toChronoUnit() {
        return toChronoUnit(this);
    }

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
