package com.cosmoloj.time;

import com.cosmoloj.time.chrono.RomaicChronology;
import java.time.DateTimeException;
import java.time.chrono.Chronology;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.TextStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalQueries;
import java.time.temporal.TemporalQuery;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.time.temporal.ValueRange;
import java.util.Locale;

/**
 *
 * @author Samuel Andrés
 */
public enum RomaicMonth implements TemporalAccessor, TemporalAdjuster {

    /**
     * The singleton instance for the month of September with 30 days.
     * This has the numeric value of {@code 1}.
     */
    SEPTEMBER,

    /**
     * The singleton instance for the month of October with 31 days.
     * This has the numeric value of {@code 2}.
     */
    OCTOBER,

    /**
     * The singleton instance for the month of November with 30 days.
     * This has the numeric value of {@code 3}.
     */
    NOVEMBER,

    /**
     * The singleton instance for the month of December with 31 days.
     * This has the numeric value of {@code 4}.
     */
    DECEMBER,

    /**
     * The singleton instance for the month of January with 31 days.
     * This has the numeric value of {@code 5}.
     */
    JANUARY,

    /**
     * The singleton instance for the month of February with 28 days, or 29 in a leap year.
     * This has the numeric value of {@code 6}.
     */
    FEBRUARY,

    /**
     * The singleton instance for the month of March with 31 days.
     * This has the numeric value of {@code 7}.
     */
    MARCH,

    /**
     * The singleton instance for the month of April with 30 days.
     * This has the numeric value of {@code 8}.
     */
    APRIL,

    /**
     * The singleton instance for the month of May with 31 days.
     * This has the numeric value of {@code 9}.
     */
    MAY,
    /**
     * The singleton instance for the month of June with 30 days.
     * This has the numeric value of {@code 10}.
     */
    JUNE,

    /**
     * The singleton instance for the month of July with 31 days.
     * This has the numeric value of {@code 11}.
     */
    JULY,

    /**
     * The singleton instance for the month of August with 31 days.
     * This has the numeric value of {@code 12}.
     */
    AUGUST;

    /**
     * Private cache of all the constants.
     */
    private static final RomaicMonth[] ENUMS = RomaicMonth.values();


    public static RomaicMonth of(final int month) {
        if (month < 1 || month > 12) {
            throw new DateTimeException("Invalid value for MonthOfYear: " + month);
        }
        return ENUMS[month - 1];
    }

    public static RomaicMonth from(final TemporalAccessor temporal) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int getValue() {
        return ordinal() + 1;
    }

    public String getDisplayName(final TextStyle style, final Locale locale) {
        return new DateTimeFormatterBuilder()
                .appendText(ChronoField.MONTH_OF_YEAR, style)
                .toFormatter(locale)
                .format(this);
    }

    @Override
    public boolean isSupported(final TemporalField field) {
        if (field instanceof ChronoField) {
            return field == ChronoField.MONTH_OF_YEAR;
        }
        return field != null && field.isSupportedBy(this);
    }

    @Override
    public ValueRange range(final TemporalField field) {
        if (field == ChronoField.MONTH_OF_YEAR) {
            return field.range();
        }
        return TemporalAccessor.super.range(field);
    }

    @Override
    public int get(final TemporalField field) {
        if (field == ChronoField.MONTH_OF_YEAR) {
            return getValue();
        }
        return TemporalAccessor.super.get(field);
    }

    @Override
    public long getLong(final TemporalField field) {
        if (field == ChronoField.MONTH_OF_YEAR) {
            return getValue();
        } else if (field instanceof ChronoField) {
            throw new UnsupportedTemporalTypeException("Unsupported field: " + field);
        }
        return field.getFrom(this);
    }

    public RomaicMonth plus(final long months) {
        int amount = (int) (months % 12);
        return ENUMS[(ordinal() + (amount + 12)) % 12];
    }

    public RomaicMonth minus(final long months) {
        return plus(-(months % 12));
    }

    public int length(boolean leapYear) {
        switch (this) {
            case FEBRUARY: return (leapYear ? 29 : 28);
            case APRIL:
            case JUNE:
            case SEPTEMBER:
            case NOVEMBER: return 30;
            default: return 31;
        }
    }

    public int minLength() {
        switch (this) {
            case FEBRUARY: return 28;
            case APRIL:
            case JUNE:
            case SEPTEMBER:
            case NOVEMBER: return 30;
            default: return 31;
        }
    }

    public int maxLength() {
        switch (this) {
            case FEBRUARY: return 29;
            case APRIL:
            case JUNE:
            case SEPTEMBER:
            case NOVEMBER: return 30;
            default: return 31;
        }
    }

    public int firstDayOfYear(boolean leapYear) {
        int leap = leapYear ? 1 : 0;
        switch (this) {
            case SEPTEMBER: return 1;
            case OCTOBER: return 31;
            case NOVEMBER: return 62;
            case DECEMBER: return 92;
            case JANUARY: return 123;
            case FEBRUARY: return 154;
            case MARCH: return 182 + leap;
            case APRIL: return 213 + leap;
            case MAY: return 243 + leap;
            case JUNE: return 274 + leap;
            case JULY: return 304 + leap;
            case AUGUST:
            default: return 335 + leap;
        }
    }

    public RomaicMonth firstMonthOfQuarter() {
        return ENUMS[(ordinal() / 3) * 3];
    }

    @SuppressWarnings("unchecked")
    @Override
    public <R> R query(final TemporalQuery<R> query) {
        if (query == TemporalQueries.chronology()) {
            return (R) RomaicChronology.INSTANCE;
        } else if (query == TemporalQueries.precision()) {
            return (R) ChronoUnit.MONTHS;
        }
        return TemporalAccessor.super.query(query);
    }

    @Override
    public Temporal adjustInto(final Temporal temporal) {
        if (!Chronology.from(temporal).equals(RomaicChronology.INSTANCE)) {
            throw new DateTimeException("Adjustment only supported on ISO date-time");
        }
        return temporal.with(ChronoField.MONTH_OF_YEAR, getValue());
    }

}
