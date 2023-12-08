package com.cosmoloj.math.util;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 *
 * @author Samuel Andrés
 */
public final class TaylorUtil {

    private TaylorUtil() {
    }

    public static double asin(final double x, final int k) {

        if (k == 0) {
            return 0;
        }

        double result = x;
        for (int i = 3; i <= k; i++) {
            result += asinMemberN(x, i);
        }
        return result;
    }

    private static double asinMemberN(final double x, final int k) {

        if (k % 2 == 0) {
            return 0.;
        }

        final int n = (k - 1) /  2;
        double result = Math.pow(x, (double) (2 * n + 1));

        for (int i = 1; i <= 2 * n - 1; i += 2) {
          result *= i / (i + 1.);
        }

        return result / (2 * n + 1);
    }

    public static BigDecimal asin(final BigDecimal x, final int k) {

        if (k == 0) {
            return BigDecimal.ZERO;
        }

        BigDecimal result = x;
        for (int i = 3; i <= k; i++) {
            result = result.add(asinMemberN(x, i));
        }
        return result;
    }

    private static BigDecimal asinMemberN(final BigDecimal x, final int k) {

        if (k % 2 == 0) {
            return BigDecimal.ZERO;
        }

        final int n = (k - 1) /  2;
        BigDecimal result = x.pow(2 * n + 1);

        for (int i = 1; i <= 2 * n - 1; i += 2) {
            MathContext mc = new MathContext(100000, RoundingMode.CEILING);
            result = result.multiply(BigDecimal.valueOf(i).divide(BigDecimal.valueOf(i).add(BigDecimal.ONE), mc));
        }

        return result.divide(BigDecimal.valueOf(2).multiply(BigDecimal.valueOf(n).add(BigDecimal.ONE)));
    }
}
