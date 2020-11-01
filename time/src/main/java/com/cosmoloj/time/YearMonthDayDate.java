package com.cosmoloj.time;

/**
 *
 * @author Samuel Andrés
 */
public class YearMonthDayDate {

    protected final int year;
    protected final int month;
    protected final int day;

    public YearMonthDayDate(final int year, final int month, final int dayOfMonth) {
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
}
