package com.cosmoloj.time.temporal;

import com.cosmoloj.time.GregorianDate;
import com.cosmoloj.time.JulianDayDate;
import com.cosmoloj.time.PetrinianDate;
import com.cosmoloj.time.RomaicDate;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalQuery;

/**
 *
 * @author Samuel Andrés
 */
public final class TemporalQueries {

    private TemporalQueries() {
    }

    public static TemporalQuery<GregorianDate> gregorianDate() {
        return GREGORIAN_DATE;
    }

    public static TemporalQuery<PetrinianDate> petrinianDate() {
        return PETRINIAN_DATE;
    }

    public static TemporalQuery<RomaicDate> romaicDate() {
        return ROMAIC_DATE;
    }

    public static TemporalQuery<JulianDayDate> julianDayDate() {
        return JULIAN_DAY_DATE;
    }

    private static final TemporalQuery<GregorianDate> GREGORIAN_DATE = (temporal) -> {
        if (temporal.isSupported(ChronoField.EPOCH_DAY)) {
            return GregorianDate.ofEpochDay(temporal.getLong(ChronoField.EPOCH_DAY));
        }
        return null;
    };

    private static final TemporalQuery<PetrinianDate> PETRINIAN_DATE = (temporal) -> {
        if (temporal.isSupported(ChronoField.EPOCH_DAY)) {
            return PetrinianDate.ofEpochDay(temporal.getLong(ChronoField.EPOCH_DAY));
        }
        return null;
    };

    private static final TemporalQuery<RomaicDate> ROMAIC_DATE = (temporal) -> {
        if (temporal.isSupported(ChronoField.EPOCH_DAY)) {
            return RomaicDate.ofEpochDay(temporal.getLong(ChronoField.EPOCH_DAY));
        }
        return null;
    };

    private static final TemporalQuery<JulianDayDate> JULIAN_DAY_DATE = (temporal) -> {
        if (temporal.isSupported(ChronoField.EPOCH_DAY)) {
            return JulianDayDate.ofEpochDay(temporal.getLong(ChronoField.EPOCH_DAY));
        }
        return null;
    };
}
