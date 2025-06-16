package cn.bytengine.d.lang;

import java.time.Duration;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalUnit;

/**
 * TODO
 *
 * @author Ban Tenio
 * @version 1.0
 */
public enum ChronoUnit implements TemporalUnit {
    NANOS("Nanos", Duration.ofNanos(1)),
    MICROS("Micros", Duration.ofNanos(1000)),
    MILLIS("Millis", Duration.ofNanos(1000_000)),
    SECONDS("Seconds", Duration.ofSeconds(1)),
    MINUTES("Minutes", Duration.ofSeconds(60)),
    HOURS("Hours", Duration.ofSeconds(3600)),
    HALF_DAYS("HalfDays", Duration.ofSeconds(43200)),
    DAYS("Days", Duration.ofSeconds(86400)),
    WEEKS("Weeks", Duration.ofSeconds(7 * 86400L)),
    MONTHS("Months", Duration.ofSeconds(31556952L / 12)),
    YEARS("Years", Duration.ofSeconds(31556952L)),
    DECADES("Decades", Duration.ofSeconds(31556952L * 10L)),
    CENTURIES("Centuries", Duration.ofSeconds(31556952L * 100L)),
    MILLENNIA("Millennia", Duration.ofSeconds(31556952L * 1000L)),
    ERAS("Eras", Duration.ofSeconds(31556952L * 1000_000_000L)),
    FOREVER("Forever", Duration.ofSeconds(Long.MAX_VALUE, 999_999_999));

    private final String name;
    private final Duration duration;

    private ChronoUnit(String name, Duration estimatedDuration) {
        this.name = name;
        this.duration = estimatedDuration;
    }

    @Override
    public Duration getDuration() {
        return duration;
    }

    @Override
    public boolean isDurationEstimated() {
        return this.compareTo(DAYS) >= 0;
    }

    @Override
    public boolean isDateBased() {
        return this.compareTo(DAYS) >= 0 && this != FOREVER;
    }

    @Override
    public boolean isTimeBased() {
        return this.compareTo(DAYS) < 0;
    }

    @Override
    public boolean isSupportedBy(Temporal temporal) {
        return temporal.isSupported(this);
    }

    @Override
    public <R extends Temporal> R addTo(R temporal, long amount) {
        return (R) temporal.plus(amount, this);
    }

    @Override
    public long between(Temporal temporal1Inclusive, Temporal temporal2Exclusive) {
        return temporal1Inclusive.until(temporal2Exclusive, this);
    }

    @Override
    public String toString() {
        return name;
    }
}
