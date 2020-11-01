package com.cosmoloj.time.chrono;

import com.cosmoloj.time.RomaicDate;
import java.io.Serializable;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.Chronology;
import java.time.chrono.Era;
import java.time.format.ResolverStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalField;
import java.time.temporal.ValueRange;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Samuel Andrés
 */
public final class RomaicChronology implements Chronology, Serializable {

    public static final RomaicChronology INSTANCE = new RomaicChronology();

    private RomaicChronology() {
    }

    @Override
    public String getId() {
        return "ROM";
    }

    @Override
    public String getCalendarType() {
        return "romaic";
    }

    @Override
    public RomaicDate date(final int prolepticYear, final int month, final int dayOfMonth) {
        return RomaicDate.of(month, month, dayOfMonth);
    }

    @Override
    public RomaicDate dateYearDay(final int prolepticYear, final int dayOfYear) {
        return RomaicDate.ofYearDay(prolepticYear, dayOfYear);
    }

    @Override
    public RomaicDate dateEpochDay(final long epochDay) {
        return RomaicDate.ofEpochDay(epochDay);
    }

    @Override
    public RomaicDate date(final TemporalAccessor temporal) {
        return RomaicDate.from(temporal);
    }

    @Override
    public boolean isLeapYear(final long prolepticYear) {
        return (prolepticYear & 3) == 0;
    }

    @Override
    public int prolepticYear(final Era era, final int yearOfEra) {
        if (!(era instanceof RomaicEra)) {
            throw new ClassCastException("Era must be RomaicEra");
        }
        return (era == RomaicEra.AM ? yearOfEra : 1 - yearOfEra);
    }

    @Override
    public RomaicEra eraOf(final int eraValue) {
        return RomaicEra.of(eraValue);
    }

    @Override
    public List<Era> eras() {
        return List.of(RomaicEra.values());
    }

    @Override
    public ValueRange range(final ChronoField field) {
        return field.range();
    }

    @Override
    public ChronoLocalDate resolveDate(final Map<TemporalField, Long> fieldValues, final ResolverStyle resolverStyle) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int compareTo(final Chronology other) {
        return getId().compareTo(other.getId());
    }
}
