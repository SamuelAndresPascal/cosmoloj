package com.cosmoloj.time;

import java.time.temporal.ChronoField;

/**
 *
 * @author Samuel Andrés
 */
public final class JulianUtil {

    private JulianUtil() {
    }

    public static final int MONTHS_PER_YEAR = 12;

    public static final int YEARS_PER_CYCLE = 4;

    // Nombre de jours par cycle de 4 ans :
    public static final int DAYS_PER_CYCLE = 1461;

    /**
     * Nombre de jour dans un siècle pétrinien.
     *
     * Ainsi, 1 siècle =  75 années normales    = 75*365 = 27375
     *                  + 25 années bissextiles = 25*366 =  9150
     *                  -----------------------------------------
     *                                                     36525
     *
     */
    public static final int DAYS_PER_CENTURY = 36525;

    /**
     * Cette méthode est valable pour tout système calendaire dont l'année est
     * divisée en {@link JulianUtil#MONTHS_PER_YEAR} mois (en particulier le
     * calendrier grégorien).
     *
     * @param year
     * @param month
     * @see ChronoField#PROLEPTIC_MONTH
     * @return
     */
    public static long getProlepticMonth(final int year, final int month) {
        return (year * (long) MONTHS_PER_YEAR + month - 1);
    }
}
