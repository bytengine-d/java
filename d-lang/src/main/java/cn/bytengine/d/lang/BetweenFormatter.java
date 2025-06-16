package cn.bytengine.d.lang;

import java.io.Serializable;
import java.util.function.Function;

/**
 * TODO
 *
 * @author Ban Tenio
 * @version 1.0
 */
public class BetweenFormatter implements Serializable {

    private long betweenMs;
    private Level level;
    private final int levelMaxCount;
    private Function<Level, String> levelFormatter = Level::getName;
    private String separator = CharSequenceTools.EMPTY;

    public BetweenFormatter(long betweenMs, BetweenFormatter.Level level) {
        this(betweenMs, level, 0);
    }

    public BetweenFormatter(long betweenMs, Level level, int levelMaxCount) {
        this.betweenMs = betweenMs;
        this.level = level;
        this.levelMaxCount = levelMaxCount;
    }

    public String format() {
        final StringBuilder sb = new StringBuilder();
        if (betweenMs > 0) {
            long day = betweenMs / DateUnit.DAY.getMillis();
            long hour = betweenMs / DateUnit.HOUR.getMillis() - day * 24;
            long minute = betweenMs / DateUnit.MINUTE.getMillis() - day * 24 * 60 - hour * 60;

            final long BetweenOfSecond = ((day * 24 + hour) * 60 + minute) * 60;
            long second = betweenMs / DateUnit.SECOND.getMillis() - BetweenOfSecond;
            long millisecond = betweenMs - (BetweenOfSecond + second) * 1000;

            final int level = this.level.ordinal();
            int levelCount = 0;

            if (isLevelCountValid(levelCount) && day > 0) {
                sb.append(day).append(levelFormatter.apply(Level.DAY)).append(separator);
                levelCount++;
            }
            if (isLevelCountValid(levelCount) && 0 != hour && level >= Level.HOUR.ordinal()) {
                sb.append(hour).append(levelFormatter.apply(Level.HOUR)).append(separator);
                levelCount++;
            }
            if (isLevelCountValid(levelCount) && 0 != minute && level >= Level.MINUTE.ordinal()) {
                sb.append(minute).append(levelFormatter.apply(Level.MINUTE)).append(separator);
                levelCount++;
            }
            if (isLevelCountValid(levelCount) && 0 != second && level >= Level.SECOND.ordinal()) {
                sb.append(second).append(levelFormatter.apply(Level.SECOND)).append(separator);
                levelCount++;
            }
            if (isLevelCountValid(levelCount) && 0 != millisecond && level >= Level.MILLISECOND.ordinal()) {
                sb.append(millisecond).append(levelFormatter.apply(Level.MILLISECOND)).append(separator);
                // levelCount++;
            }
        }

        if (CharSequenceTools.isEmpty(sb)) {
            sb.append(0).append(levelFormatter.apply(this.level));
        } else {
            if (CharSequenceTools.isNotEmpty(separator)) {
                sb.delete(sb.length() - separator.length(), sb.length());
            }
        }
        return sb.toString();
    }

    public long getBetweenMs() {
        return betweenMs;
    }

    public void setBetweenMs(long betweenMs) {
        this.betweenMs = betweenMs;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public BetweenFormatter setLevelFormatter(Function<Level, String> levelFormatter) {
        this.levelFormatter = levelFormatter;
        return this;
    }

    public BetweenFormatter setSeparator(String separator) {
        this.separator = CharSequenceTools.nullToEmpty(separator);
        return this;
    }

    public enum Level {
        DAY("天"),
        HOUR("小时"),
        MINUTE("分"),
        SECOND("秒"),
        MILLISECOND("毫秒");
        private final String name;

        Level(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }
    }

    @Override
    public String toString() {
        return format();
    }

    private boolean isLevelCountValid(int levelCount) {
        return this.levelMaxCount <= 0 || levelCount < this.levelMaxCount;
    }
}
