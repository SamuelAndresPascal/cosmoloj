package com.cosmoloj.math.util.integral;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Samuel Andrés
 */
public class IntegralTest {

    @Test
    @DisplayName("sum")
    public void sum() {
        final double phip = 1.;

        // non-régression
        Assertions.assertEquals(2.804496876931063, Integral.sum(l -> f(phip, l), 0., Math.PI / 2., 100));

        // non-régression
        Assertions.assertEquals(4.405293392791076, Integral.sum(p -> Integral.sum(l ->
                f(phip, l), 0., Math.PI / 2., 100), 0., Math.PI / 2., 100));
    }

    private static double f(final double phi, final double lambda) {
        return phi + lambda;
    }
}
