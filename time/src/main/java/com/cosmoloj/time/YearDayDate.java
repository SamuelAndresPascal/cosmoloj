package com.cosmoloj.time;

import java.io.Serializable;

/**
 * Classe conteneur d'un jour dans une année donnée.
 *
 * @author Samuel Andrés
 */
public class YearDayDate implements Serializable {

    private final int dayOfYear;
    private final long year;

    public YearDayDate(final int dayOfYear, final long year) {
        this.dayOfYear = dayOfYear;
        this.year = year;
    }

    public int getDayOfYear() {
        return dayOfYear;
    }

    public long getYear() {
        return year;
    }
}
