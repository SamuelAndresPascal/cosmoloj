package com.cosmoloj.time;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.chrono.ChronoLocalDate;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.time.temporal.UnsupportedTemporalTypeException;

/**
 *
 * @author Samuel Andrés
 */
public final class TemporalUtil {

    private TemporalUtil() {
    }

    public static long daysBetween(final ChronoLocalDate start, final ChronoLocalDate end) {
        return end.toEpochDay() - start.toEpochDay();
    }

    public static long monthsBetween(final WeekDate start, final WeekDate end) {
        final long packed1 = JulianUtil.getProlepticMonth(start.getYear(), start.getMonthValue()) * 32L
                + start.getDayOfMonth();
        final long packed2 = JulianUtil.getProlepticMonth(end.getYear(), end.getMonthValue()) * 32L
                + end.getDayOfMonth();
        return (packed2 - packed1) / 32;
    }

    public static <S extends WeekDate & ChronoLocalDate, E extends WeekDate & ChronoLocalDate> long between(
            final S startInclusive, final E endExclusive, final TemporalUnit unit) {
        if (unit instanceof ChronoUnit chrono) {
            return switch (chrono) {
                case DAYS -> TemporalUtil.daysBetween(startInclusive, endExclusive);
                case WEEKS -> TemporalUtil.daysBetween(startInclusive, endExclusive) / 7;
                case MONTHS -> TemporalUtil.monthsBetween(startInclusive, endExclusive);
                case YEARS -> TemporalUtil.monthsBetween(startInclusive, endExclusive) / 12;
                case DECADES -> TemporalUtil.monthsBetween(startInclusive, endExclusive) / 120;
                case CENTURIES -> TemporalUtil.monthsBetween(startInclusive, endExclusive) / 1200;
                case MILLENNIA -> TemporalUtil.monthsBetween(startInclusive, endExclusive) / 12000;
                case ERAS -> endExclusive.getLong(ChronoField.ERA) - startInclusive.getLong(ChronoField.ERA);
                default -> throw new UnsupportedTemporalTypeException("Unsupported unit: " + unit);
            };
        }
        return unit.between(startInclusive, endExclusive);
    }

    public static int compare(final YearMonthDayDate firstDate, final YearMonthDayDate otherDate) {
        int cmp = (firstDate.getYear() - otherDate.getYear());
        if (cmp == 0) {
            cmp = (firstDate.getMonthValue() - otherDate.getMonthValue());
            if (cmp == 0) {
                cmp = (firstDate.getDayOfMonth() - otherDate.getDayOfMonth());
            }
        }
        return cmp;
    }

    public static long instantEpoch(final Clock clock) {
        final Instant now = clock.instant();
        final ZoneOffset offset = clock.getZone().getRules().getOffset(now);
        final long epochSec = now.getEpochSecond() + offset.getTotalSeconds();
        return Math.floorDiv(epochSec, 24 * 60 * 60);
    }
}
