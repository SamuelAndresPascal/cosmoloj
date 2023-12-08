package com.cosmoloj.math.util.integral;

import java.util.function.DoubleUnaryOperator;

/**
 *
 * @author Samuel Andrés
 */
public final class Integral {

    private Integral() {
    }


    public static double sum(final DoubleUnaryOperator function, final double from, final double to, final int parts) {
        final double width = (to - from) / parts;
        double sum = 0.;
        for (int i = 0; i < parts; i++) {
            double x0 = from + i * width + width / 2.;
            sum += function.applyAsDouble(x0) * width;
        }
        return sum;
    }
}
