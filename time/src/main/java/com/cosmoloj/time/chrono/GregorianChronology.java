package com.cosmoloj.time.chrono;

import com.cosmoloj.time.GregorianDate;
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

public final class GregorianChronology implements Chronology, Serializable {

    public static final GregorianChronology INSTANCE = new GregorianChronology();

    private GregorianChronology() {
    }

    @Override
    public String getId() {
        return "GRE";
    }

    @Override
    public String getCalendarType() {
        return "gregorian";
    }

    @Override
    public GregorianDate date(final int prolepticYear, final int month, final int dayOfMonth) {
        return GregorianDate.of(prolepticYear, month, dayOfMonth);
    }

    @Override
    public GregorianDate dateYearDay(final int prolepticYear, final int dayOfYear) {
        return GregorianDate.ofYearDay(prolepticYear, dayOfYear);
    }

    @Override
    public GregorianDate dateEpochDay(final long epochDay) {
        return GregorianDate.ofEpochDay(epochDay);
    }

    @Override
    public GregorianDate date(final TemporalAccessor temporal) {
        return GregorianDate.from(temporal);
    }

    @Override
    public boolean isLeapYear(final long prolepticYear) {
        return ((prolepticYear & 3) == 0) && ((prolepticYear % 100) != 0 || (prolepticYear % 400) == 0);
    }

    @Override
    public int prolepticYear(final Era era, final int yearOfEra) {
        if (!(era instanceof ChristianEra)) {
            throw new ClassCastException("Era must be ChristianEra");
        }
        return (era == ChristianEra.AD ? yearOfEra : 1 - yearOfEra);
    }

    @Override
    public ChristianEra eraOf(final int eraValue) {
        return ChristianEra.of(eraValue);
    }

    @Override
    public List<Era> eras() {
        return List.of(ChristianEra.values());
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
