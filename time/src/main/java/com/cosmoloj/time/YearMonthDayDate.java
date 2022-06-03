package com.cosmoloj.time;

import java.io.Serializable;
import java.time.chrono.ChronoLocalDate;
import java.time.temporal.ChronoField;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalQuery;
import java.time.temporal.TemporalUnit;

/**
 *
 * @author Samuel Andrés
 */
public abstract sealed class YearMonthDayDate
        implements Temporal, TemporalAdjuster, ChronoLocalDate, Serializable
        permits MonthDate {

    private final int year;
    private final int month;
    private final int day;

    protected YearMonthDayDate(final int year, final int month, final int dayOfMonth) {
        this.year = year;
        this.month = month;
        this.day = dayOfMonth;
    }

    public int getYear() {
        return year;
    }

    public int getMonthValue() {
        return month;
    }

    public int getDayOfMonth() {
        return day;
    }

    public int getDayOfYear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isSupported(final TemporalUnit unit) {
        return ChronoLocalDate.super.isSupported(unit);
    }

    @Override
    public boolean isSupported(final TemporalField field) {
        return ChronoLocalDate.super.isSupported(field);
    }

    @Override
    public int get(final TemporalField field) {
        if (field instanceof ChronoField) {
            return get0(field);
        }
        return ChronoLocalDate.super.get(field);
    }

    protected abstract int get0(TemporalField field);

    @Override
    public int compareTo(final ChronoLocalDate other) {
        if (other instanceof GregorianDate date) {
            return TemporalUtil.compare(this, date);
        }
        return ChronoLocalDate.super.compareTo(other);
    }

    @Override
    public boolean isAfter(final ChronoLocalDate other) {
        if (other instanceof GregorianDate date) {
            return TemporalUtil.compare(this, date) > 0;
        }
        return ChronoLocalDate.super.isAfter(other);
    }

    @Override
    public boolean isBefore(final ChronoLocalDate other) {
        if (other instanceof GregorianDate date) {
            return TemporalUtil.compare(this, date) < 0;
        }
        return ChronoLocalDate.super.isBefore(other);
    }

    @Override
    public boolean isEqual(final ChronoLocalDate other) {
        if (other instanceof GregorianDate date) {
            return TemporalUtil.compare(this, date) == 0;
        }
        return ChronoLocalDate.super.isEqual(other);
    }

    @Override
    public <R> R query(final TemporalQuery<R> query) {
        return ChronoLocalDate.super.query(query);
    }

    @Override
    public int hashCode() {
        int yearValue = year;
        int monthValue = month;
        int dayValue = day;
        return (yearValue & 0xFFFFF800) ^ ((yearValue << 11) + (monthValue << 6) + (dayValue));
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final YearMonthDayDate other = (YearMonthDayDate) obj;
        if (this.year != other.year) {
            return false;
        }
        if (this.month != other.month) {
            return false;
        }
        if (this.day != other.day) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "YearMonthDayDate{" + "year=" + year + ", month=" + month + ", day=" + day + '}';
    }
}
