package com.cosmoloj.time.chrono;

import com.cosmoloj.time.JulianDayDate;
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
public final class JulianDayChronology implements Chronology, Serializable {

    public static final JulianDayChronology INSTANCE = new JulianDayChronology();

    private JulianDayChronology() {
    }

    @Override
    public String getId() {
        return "JULD";
    }

    @Override
    public String getCalendarType() {
        return "julianday";
    }

    @Override
    public JulianDayDate date(final int prolepticYear, final int month, final int dayOfMonth) {
        throw new UnsupportedOperationException();
    }

    @Override
    public JulianDayDate dateYearDay(final int prolepticYear, final int dayOfYear) {
        throw new UnsupportedOperationException();
    }

    @Override
    public JulianDayDate dateEpochDay(final long epochDay) {
        return JulianDayDate.ofEpochDay(epochDay);
    }

    @Override
    public JulianDayDate date(final TemporalAccessor temporal) {
        return JulianDayDate.from(temporal);
    }

    @Override
    public boolean isLeapYear(final long prolepticYear) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int prolepticYear(final Era era, final int yearOfEra) {
        throw new UnsupportedOperationException();
    }

    @Override
    public JulianDayEra eraOf(final int eraValue) {
        return JulianDayEra.of(eraValue);
    }

    @Override
    public List<Era> eras() {
        return List.of(JulianDayEra.values());
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
