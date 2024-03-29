package com.cosmoloj.time;

/**
 *
 * @author Samuel Andrés
 * @param <M>
 */
public abstract sealed class MonthDate<M> extends YearMonthDayDate permits WeekDate {

    protected MonthDate(final int year, final int month, final int dayOfMonth) {
        super(year, month, dayOfMonth);
    }

    public abstract M getMonth();
}
