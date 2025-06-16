package cn.bytengine.d.lang;

/**
 * TODO
 *
 * @author Ban Tenio
 * @version 1.0
 */
public class TimeInterval extends GroupTimeInterval {
    private static final String DEFAULT_ID = CharSequenceTools.EMPTY;

    public TimeInterval() {
        this(false);
    }

    public TimeInterval(boolean isNano) {
        super(isNano);
        start();
    }

    public long start() {
        return start(DEFAULT_ID);
    }

    public long intervalRestart() {
        return intervalRestart(DEFAULT_ID);
    }

    public TimeInterval restart() {
        start(DEFAULT_ID);
        return this;
    }

    public long interval() {
        return interval(DEFAULT_ID);
    }

    public String intervalPretty() {
        return intervalPretty(DEFAULT_ID);
    }

    public long intervalMs() {
        return intervalMs(DEFAULT_ID);
    }

    public long intervalSecond() {
        return intervalSecond(DEFAULT_ID);
    }

    public long intervalMinute() {
        return intervalMinute(DEFAULT_ID);
    }

    public long intervalHour() {
        return intervalHour(DEFAULT_ID);
    }

    public long intervalDay() {
        return intervalDay(DEFAULT_ID);
    }

    public long intervalWeek() {
        return intervalWeek(DEFAULT_ID);
    }
}
