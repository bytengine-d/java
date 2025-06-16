package cn.bytengine.d.lang;

import java.time.temporal.TemporalAccessor;
import java.util.Date;

/**
 * TODO
 *
 * @author Ban Tenio
 * @version 1.0
 */
public abstract class DateTools {
    private DateTools() {
    }

    public static String formatBetween(long betweenMs) {
        return new BetweenFormatter(betweenMs, BetweenFormatter.Level.MILLISECOND).format();
    }

    public static Date date(TemporalAccessor temporalAccessor) {
        if (temporalAccessor == null) {
            return null;
        }
        return new Date(TemporalTools.toInstant(temporalAccessor).toEpochMilli());
    }
}
