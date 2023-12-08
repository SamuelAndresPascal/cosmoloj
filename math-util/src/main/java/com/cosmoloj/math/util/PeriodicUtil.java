package com.cosmoloj.math.util;

/**
 *
 * @author Samuel Andrés
 */
public final class PeriodicUtil {

    private PeriodicUtil() {
    }

    public static double periodic(final double[][] tab, final int wIndex, final int cosIndex, final int sinIndex,
            final double t) {
        return periodic(tab[wIndex], tab[cosIndex], tab[sinIndex], t);
    }

    public static double periodic(final double[] wTab, final double[] cosTab, final double[] sinTab, final double t) {

        double result = 0;

        final double w = 2 * Math.PI * t;

        double a;
        for (int i = 0; i < wTab.length; i++) {
            a = w / wTab[i];
            result += Math.cos(a) * cosTab[i] + Math.sin(a) * sinTab[i];
        }

        return result;
    }
}
