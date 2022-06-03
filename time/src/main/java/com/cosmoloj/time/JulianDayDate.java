package com.cosmoloj.time;

import com.cosmoloj.time.chrono.JulianDayChronology;
import com.cosmoloj.time.temporal.TemporalQueries;
import java.io.Serializable;
import java.time.Clock;
import java.time.DateTimeException;
import java.time.ZoneId;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoPeriod;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalQuery;
import java.time.temporal.TemporalUnit;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.time.temporal.ValueRange;
import java.util.Objects;

/**
 *
 * @author Samuel Andrés
 */
public final class JulianDayDate implements Temporal, TemporalAdjuster, ChronoLocalDate, Serializable {

    public static final JulianDayDate MIN = JulianDayDate.of(Long.MIN_VALUE);
    public static final JulianDayDate MAX = JulianDayDate.of(Long.MAX_VALUE);

    static final int FIRST_JULIAN_DAY_TO_1970_GREGORIAN = 2440587; // .5

    private final long julianDay;

    private JulianDayDate(final long julianDay) {
        this.julianDay = julianDay;
    }

    public long getDay() {
        return this.julianDay;
    }

    @Override
    public boolean isSupported(final TemporalUnit unit) {
        return ChronoLocalDate.super.isSupported(unit);
    }

    @Override
    public long until(final Temporal endExclusive, final TemporalUnit unit) {
        if (ChronoUnit.DAYS.equals(unit)) {
            return TemporalUtil.daysBetween(this, JulianDayDate.from(endExclusive));
        }
        throw new UnsupportedTemporalTypeException("Unsupported unit: " + unit);
    }

    @Override
    public boolean isSupported(final TemporalField field) {
        return ChronoLocalDate.super.isSupported(field);
    }

    @Override
    public ValueRange range(final TemporalField field) {
        if (field instanceof ChronoField) {
            final ChronoField chronoField = (ChronoField) field;
            if (chronoField.isDateBased()) {
                switch (chronoField) {
//                    case DAY_OF_MONTH:
//                        return ValueRange.of(1, lengthOfMonth());
//                    case DAY_OF_YEAR:
//                        return ValueRange.of(1, lengthOfYear());
//                    case ALIGNED_WEEK_OF_MONTH:
//                        return ValueRange.of(1, getMonth() == RomaicMonth.FEBRUARY && !isLeapYear() ? 4 : 5);
//                    case YEAR_OF_ERA:
//                        return (getYear() <= 0 ? ValueRange.of(1, Year.MAX_VALUE + 1)
//                                : ValueRange.of(1, Year.MAX_VALUE));
                    default:
                        return field.range();
                }
            }
            throw new UnsupportedTemporalTypeException("Unsupported field: " + field);
        }
        return field.rangeRefinedBy(this);
    }

    @Override
    public int get(final TemporalField field) {
        if (field instanceof ChronoField) {
            return get0(field);
        }
        return ChronoLocalDate.super.get(field);
    }

    @Override
    public long getLong(final TemporalField field) {
        if (field instanceof ChronoField) {
            if (field == ChronoField.EPOCH_DAY) {
                return toEpochDay();
            }
//            if (field == ChronoField.PROLEPTIC_MONTH) {
//                return JulianUtils.getProlepticMonth(year, month);
//            }
            return get0(field);
        }
        return field.getFrom(this);
    }

    private int get0(final TemporalField field) {
        switch ((ChronoField) field) {
//            case DAY_OF_WEEK:
//                return getDayOfWeek().getValue();
//            case ALIGNED_DAY_OF_WEEK_IN_MONTH:
//                return ((day - 1) % 7) + 1;
//            case ALIGNED_DAY_OF_WEEK_IN_YEAR:
//                return ((getDayOfYear() - 1) % 7) + 1;
//            case DAY_OF_MONTH:
//                return day;
//            case DAY_OF_YEAR:
//                return getDayOfYear();
            case EPOCH_DAY:
                throw new UnsupportedTemporalTypeException(
                        "Invalid field 'EpochDay' for get() method, use getLong() instead");
//            case ALIGNED_WEEK_OF_MONTH:
//                return ((day - 1) / 7) + 1;
//            case ALIGNED_WEEK_OF_YEAR:
//                return ((getDayOfYear() - 1) / 7) + 1;
//            case MONTH_OF_YEAR:
//                return month;
//            case PROLEPTIC_MONTH:
//                throw new UnsupportedTemporalTypeException(
//                        "Invalid field 'ProlepticMonth' for get() method, use getLong() instead");
//            case YEAR_OF_ERA:
//                return (year >= 1 ? year : 1 - year);
//            case YEAR:
//                return year;
            case ERA:
                return (julianDay >= 1 ? 1 : 0);
            default:
                throw new UnsupportedTemporalTypeException("Unsupported field: " + field);
        }
    }

    @Override
    public JulianDayChronology getChronology() {
        return JulianDayChronology.INSTANCE;
    }

    @Override
    public int lengthOfMonth() {
        throw new UnsupportedTemporalTypeException("there is no month in julian day date");
    }

    @Override
    public ChronoPeriod until(final ChronoLocalDate endDateExclusive) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int compareTo(final ChronoLocalDate other) {
        if (other instanceof JulianDayDate) {
            final long diff = this.julianDay - ((JulianDayDate) other).julianDay;
            if (diff < Integer.MIN_VALUE) {
                return -1;
            } else if (diff > Integer.MAX_VALUE) {
                return 1;
            } else {
                return diff > 0 ? 1 : -1;
            }
        }
        return ChronoLocalDate.super.compareTo(other);
    }

    @Override
    public boolean isAfter(final ChronoLocalDate other) {
        if (other instanceof JulianDayDate) {
            return this.julianDay - ((JulianDayDate) other).julianDay > 0;
        }
        return ChronoLocalDate.super.isAfter(other);
    }

    @Override
    public boolean isBefore(final ChronoLocalDate other) {
        if (other instanceof JulianDayDate) {
            return this.julianDay - ((JulianDayDate) other).julianDay < 0;
        }
        return ChronoLocalDate.super.isBefore(other);
    }

    @Override
    public boolean isEqual(final ChronoLocalDate other) {
        if (other instanceof JulianDayDate) {
            return this.julianDay - ((JulianDayDate) other).julianDay == 0;
        }
        return ChronoLocalDate.super.isEqual(other);
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof JulianDayDate) {
            return this.julianDay - ((JulianDayDate) obj).julianDay == 0;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + (int) (this.julianDay ^ (this.julianDay >>> 32));
        return hash;
    }

    @Override
    public long toEpochDay() {
        return julianDay - FIRST_JULIAN_DAY_TO_1970_GREGORIAN;
    }

    @Override
    public boolean isLeapYear() {
        throw new UnsupportedTemporalTypeException("there is no year in julian day date");
    }

    @Override
    public JulianDayDate with(final TemporalAdjuster adjuster) {
        if (adjuster instanceof JulianDayDate) {
            return (JulianDayDate) adjuster;
        }
        return (JulianDayDate) adjuster.adjustInto(this);
    }

    @Override
    public JulianDayDate with(final TemporalField field, final long newValue) {
        if (field instanceof ChronoField) {
            final ChronoField f = (ChronoField) field;
            f.checkValidValue(newValue);
            switch (f) {
//                case DAY_OF_WEEK:
//                    return plusDays(newValue - getDayOfWeek().getValue());
//                case ALIGNED_DAY_OF_WEEK_IN_MONTH:
//                    return plusDays(newValue - getLong(ChronoField.ALIGNED_DAY_OF_WEEK_IN_MONTH));
//                case ALIGNED_DAY_OF_WEEK_IN_YEAR:
//                    return plusDays(newValue - getLong(ChronoField.ALIGNED_DAY_OF_WEEK_IN_YEAR));
//                case DAY_OF_MONTH:
//                    return withDayOfMonth((int) newValue);
//                case DAY_OF_YEAR:
//                    return withDayOfYear((int) newValue);
                case EPOCH_DAY:
                    return JulianDayDate.ofEpochDay(newValue);
//                case ALIGNED_WEEK_OF_MONTH:
//                    return plusWeeks(newValue - getLong(ChronoField.ALIGNED_WEEK_OF_MONTH));
//                case ALIGNED_WEEK_OF_YEAR:
//                    return plusWeeks(newValue - getLong(ChronoField.ALIGNED_WEEK_OF_YEAR));
//                case MONTH_OF_YEAR:
//                    return withMonth((int) newValue);
//                case PROLEPTIC_MONTH:
//                    return plusMonths(newValue - JulianUtils.getProlepticMonth(year, month));
//                case YEAR_OF_ERA:
//                    return withYear((int) (year >= 1 ? newValue : 1 - newValue));
//                case YEAR:
//                    return withYear((int) newValue);
                case ERA:
                    return (getLong(ChronoField.ERA) == newValue ? this : JulianDayDate.of(-julianDay));
                default:
                    throw new UnsupportedTemporalTypeException("Unsupported field: " + field);
            }
        }
        return field.adjustInto(this, newValue);
    }

//    public JulianDayDate withYear(int year) {
//        if (this.year == year) {
//            return this;
//        }
//        ChronoField.YEAR.checkValidValue(year);
//        return resolvePreviousValid(year, month, day);
//    }
//
//    public JulianDayDate withMonth(int month) {
//        if (this.month == month) {
//            return this;
//        }
//        ChronoField.MONTH_OF_YEAR.checkValidValue(month);
//        return resolvePreviousValid(year, month, day);
//    }
//
//    public JulianDayDate withDayOfMonth(int dayOfMonth) {
//        if (this.day == dayOfMonth) {
//            return this;
//        }
//        return of(year, month, dayOfMonth);
//    }
//
//    public JulianDayDate withDayOfYear(int dayOfYear) {
//        if (this.getDayOfYear() == dayOfYear) {
//            return this;
//        }
//        return ofYearDay(year, dayOfYear);
//    }
//
//    private static JulianDayDate resolvePreviousValid(int year, int month, int day) {
//        switch (month) {
//            case 2:
//                day = Math.min(day, JulianDayChronology.INSTANCE.isLeapYear(year) ? 29 : 28);
//                break;
//            case 4:
//            case 6:
//            case 9:
//            case 11:
//                day = Math.min(day, 30);
//                break;
//            default:
//        }
//        return new JulianDayDate(year, month, day);
//    }

    @Override
    public JulianDayDate plus(final TemporalAmount amountToAdd) {
//        if (amountToAdd instanceof Period) {
//            Period periodToAdd = (Period) amountToAdd;
//            return plusMonths(periodToAdd.toTotalMonths()).plusDays(periodToAdd.getDays());
//        }
        Objects.requireNonNull(amountToAdd, "amountToAdd");
        return (JulianDayDate) amountToAdd.addTo(this);
    }

    @Override
    public JulianDayDate plus(final long amountToAdd, final TemporalUnit unit) {
        if (unit instanceof ChronoUnit) {
            final ChronoUnit f = (ChronoUnit) unit;
            switch (f) {
                case DAYS:
                    return plusDays(amountToAdd);
//                case WEEKS:
//                    return plusWeeks(amountToAdd);
//                case MONTHS:
//                    return plusMonths(amountToAdd);
//                case YEARS:
//                    return plusYears(amountToAdd);
//                case DECADES:
//                    return plusYears(Math.multiplyExact(amountToAdd, 10));
//                case CENTURIES:
//                    return plusYears(Math.multiplyExact(amountToAdd, 100));
//                case MILLENNIA:
//                    return plusYears(Math.multiplyExact(amountToAdd, 1000));
                case ERAS:
                    return with(ChronoField.ERA, Math.addExact(getLong(ChronoField.ERA), amountToAdd));
                default:
                    throw new UnsupportedTemporalTypeException("Unsupported unit: " + unit);
            }
        }
        return unit.addTo(this, amountToAdd);
    }

//    public JulianDayDate plusYears(long yearsToAdd) {
//        if (yearsToAdd == 0) {
//            return this;
//        }
//        int newYear = ChronoField.YEAR.checkValidIntValue(year + yearsToAdd);  // safe overflow
//        return resolvePreviousValid(newYear, month, day);
//    }
//
//    public JulianDayDate plusMonths(long monthsToAdd) {
//        if (monthsToAdd == 0) {
//            return this;
//        }
//        long monthCount = year * 12L + (month - 1);
//        long calcMonths = monthCount + monthsToAdd;  // safe overflow
//        int newYear = ChronoField.YEAR.checkValidIntValue(Math.floorDiv(calcMonths, 12));
//        int newMonth = (int) Math.floorMod(calcMonths, 12) + 1;
//        return resolvePreviousValid(newYear, newMonth, day);
//    }
//
//    public JulianDayDate plusWeeks(long weeksToAdd) {
//        return plusDays(Math.multiplyExact(weeksToAdd, 7));
//    }

    public JulianDayDate plusDays(final long daysToAdd) {
        if (daysToAdd == 0) {
            return this;
        }
        long mjDay = Math.addExact(toEpochDay(), daysToAdd);
        return JulianDayDate.ofEpochDay(mjDay);
    }

    @Override
    public JulianDayDate minus(final TemporalAmount amountToSubtract) {
//        if (amountToSubtract instanceof Period) {
//            Period periodToSubtract = (Period) amountToSubtract;
//            return minusMonths(periodToSubtract.toTotalMonths()).minusDays(periodToSubtract.getDays());
//        }
        Objects.requireNonNull(amountToSubtract, "amountToSubtract");
        return (JulianDayDate) amountToSubtract.subtractFrom(this);
    }

    @Override
    public JulianDayDate minus(final long amountToSubtract, final TemporalUnit unit) {
        return (amountToSubtract == Long.MIN_VALUE
                ? plus(Long.MAX_VALUE, unit).plus(1, unit) : plus(-amountToSubtract, unit));
    }

//    public JulianDayDate minusYears(long yearsToSubtract) {
//        return (yearsToSubtract == Long.MIN_VALUE
//                ? plusYears(Long.MAX_VALUE).plusYears(1) : plusYears(-yearsToSubtract));
//    }

    public JulianDayDate minusDays(long daysToSubtract) {
        return (daysToSubtract == Long.MIN_VALUE ? plusDays(Long.MAX_VALUE).plusDays(1) : plusDays(-daysToSubtract));
    }

    @Override
    public <R> R query(final TemporalQuery<R> query) {
        if (query == TemporalQueries.romaicDate()) {
            return (R) this;
        }
        return ChronoLocalDate.super.query(query);
    }

    @Override
    public String toString() {
        return Long.toString(this.julianDay);
    }

    public static JulianDayDate of(final long julianDay) {
        return create(julianDay);
    }

    public static JulianDayDate ofEpochDay(final long epochDay) {
        return new JulianDayDate(epochDay + FIRST_JULIAN_DAY_TO_1970_GREGORIAN);
    }

    private static JulianDayDate create(final long julianDay) {
        return new JulianDayDate(julianDay);
    }

    public static JulianDayDate from(final TemporalAccessor temporal) {
        Objects.requireNonNull(temporal, "temporal");
        JulianDayDate date = temporal.query(TemporalQueries.julianDayDate());
        if (date == null) {
            throw new DateTimeException(
                    String.format("Unable to obtain JulianDayDate from TemporalAccessor: %s of type %s",
                            temporal, temporal.getClass().getName()));
        }
        return date;
    }

    public static JulianDayDate now(final Clock clock) {
        Objects.requireNonNull(clock, "clock");
        return JulianDayDate.ofEpochDay(TemporalUtil.instantEpoch(clock));
    }

    public static JulianDayDate now(final ZoneId zone) {
        return now(Clock.system(zone));
    }

    public static JulianDayDate now() {
        return now(Clock.systemDefaultZone());
    }

    public static JulianDayDate parse(final CharSequence text) {
        throw new UnsupportedOperationException();
//        return parse(text, DateTimeFormatter.ISO_LOCAL_DATE);
    }

    public static JulianDayDate parse(final CharSequence text, final DateTimeFormatter formatter) {
        Objects.requireNonNull(formatter, "formatter");
        return formatter.parse(text, JulianDayDate::from);
    }
}
