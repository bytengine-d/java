package cn.bytengine.d.lang;

import cn.bytengine.d.collection.SafeConcurrentHashMap;

import java.io.Serializable;
import java.util.Map;

/**
 * TODO
 *
 * @author Ban Tenio
 * @version 1.0
 */
public class GroupTimeInterval implements Serializable {
    private static final long serialVersionUID = 1L;

    private final boolean isNano;
    protected final Map<String, Long> groupMap;

    public GroupTimeInterval(boolean isNano) {
        this.isNano = isNano;
        groupMap = new SafeConcurrentHashMap<>();
    }

    public GroupTimeInterval clear() {
        this.groupMap.clear();
        return this;
    }

    public long start(String id) {
        final long time = getTime();
        this.groupMap.put(id, time);
        return time;
    }

    public long intervalRestart(String id) {
        final long now = getTime();
        return now - ObjectTools.defaultIfNull(this.groupMap.put(id, now), now);
    }

    public long interval(String id) {
        final Long lastTime = this.groupMap.get(id);
        if (null == lastTime) {
            return 0;
        }
        return getTime() - lastTime;
    }

    public long interval(String id, DateUnit dateUnit) {
        final long intervalMs = isNano ? interval(id) / 1000000L : interval(id);
        if (DateUnit.MS == dateUnit) {
            return intervalMs;
        }
        return intervalMs / dateUnit.getMillis();
    }

    public long intervalMs(String id) {
        return interval(id, DateUnit.MS);
    }

    public long intervalSecond(String id) {
        return interval(id, DateUnit.SECOND);
    }

    public long intervalMinute(String id) {
        return interval(id, DateUnit.MINUTE);
    }

    public long intervalHour(String id) {
        return interval(id, DateUnit.HOUR);
    }

    public long intervalDay(String id) {
        return interval(id, DateUnit.DAY);
    }

    public long intervalWeek(String id) {
        return interval(id, DateUnit.WEEK);
    }

    public String intervalPretty(String id) {
        return DateTools.formatBetween(intervalMs(id));
    }

    private long getTime() {
        return this.isNano ? System.nanoTime() : System.currentTimeMillis();
    }
}
