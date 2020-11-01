package com.cosmoloj.time.chrono;

import com.cosmoloj.time.PetrinianDate;
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
public final class PetrinianChronology implements Chronology, Serializable {

    public static final PetrinianChronology INSTANCE = new PetrinianChronology();

    private PetrinianChronology() {
    }

    @Override
    public String getId() {
        return "PET";
    }

    @Override
    public String getCalendarType() {
        return "petrinian";
    }

    @Override
    public PetrinianDate date(final int prolepticYear, final int month, final int dayOfMonth) {
        return PetrinianDate.of(month, month, dayOfMonth);
    }

    @Override
    public PetrinianDate dateYearDay(final int prolepticYear, final int dayOfYear) {
        return PetrinianDate.ofYearDay(prolepticYear, dayOfYear);
    }

    @Override
    public PetrinianDate dateEpochDay(final long epochDay) {
        return PetrinianDate.ofEpochDay(epochDay);
    }

    @Override
    public PetrinianDate date(final TemporalAccessor temporal) {
        return PetrinianDate.from(temporal);
    }

    @Override
    public boolean isLeapYear(final long prolepticYear) {
        return (prolepticYear & 3) == 0;
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
