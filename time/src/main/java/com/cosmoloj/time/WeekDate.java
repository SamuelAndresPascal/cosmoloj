package com.cosmoloj.time;

/**
 *
 * @author Samuel Andrés
 * @param <M> The type of month.
 * @param <W>
 */
public abstract class WeekDate<M, W> extends MonthDate<M> {

    protected WeekDate(final int year, final int month, final int dayOfMonth) {
        super(year, month, dayOfMonth);
    }

    public abstract W getDayOfWeek();
}
