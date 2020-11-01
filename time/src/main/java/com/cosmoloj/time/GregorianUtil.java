package com.cosmoloj.time;

/**
 *
 * @author Samuel Andrés
 */
public final class GregorianUtil {

    private GregorianUtil() {
    }

    public static final int YEARS_PER_CYCLE = 400;

    /**
     * Nombre de jour dans un cycle grégorien de 400 ans dont trois des
     * premières années de siècles sur 4 ne sont pas bissextile.
     *
     * Ainsi, 1 siècle =  75 années normales    = 75*365 = 27375
     *                  + 25 années bissextiles = 25*366 =  9150
     *                  -----------------------------------------
     *                                                     36525
     *
     * Donc, 4 siècles juliens font 36525*4 = 146100 jours.
     *
     * Pour prendre en compte la réforme grégorienne, il faut retirer 3 jours à
     * ce total, ce qui donne 146097 jours.
     *
     */
    public static final int DAYS_PER_CYCLE = 146097;

    // Nombre de jours par année grégorienne en moyenne : 365,2425
    public static final float DAYS_PER_GREGORIAN_YEAR = ((float) DAYS_PER_CYCLE) / YEARS_PER_CYCLE;
}
