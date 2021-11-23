package com.cosmoloj.time;

/**
 *
 * @author Samuel Andrés
 */
public sealed class YearMonthDayDate permits MonthDate {

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
