package com.cosmoloj.math.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Samuel Andrés
 */
public class MathUtilTest {

    @Test
    @DisplayName("gauss coefficients")
    public void testGaussCoeff() {
        Assertions.assertEquals(1, MathUtil.gaussCoeff(0, 0.3));
        Assertions.assertEquals(.3, MathUtil.gaussCoeff(1, 0.3));
        Assertions.assertEquals(1.3 * .3 / 2, MathUtil.gaussCoeff(2, 0.3));
        Assertions.assertEquals(1.3 * .3 * -.7 / (3 * 2), MathUtil.gaussCoeff(3, 0.3));
        Assertions.assertEquals(2.3 * 1.3 * .3 * -.7 / (4 * 3 * 2), MathUtil.gaussCoeff(4, 0.3));
        Assertions.assertEquals(2.3 * 1.3 * .3 * -.7 * -1.7 / (5 * 4 * 3 * 2), MathUtil.gaussCoeff(5, 0.3));
        Assertions.assertEquals(3.3 * 2.3 * 1.3 * .3 * -.7 * -1.7 / (6 * 5 * 4 * 3 * 2), MathUtil.gaussCoeff(6, 0.3));
    }

    @Test
    @DisplayName("even gauss coefficients")
    public void testGaussCoeffEven() {
        // n = 0 => 2n = 0
        Assertions.assertEquals(1, MathUtil.gaussCoeffEven(0, 0.3));
        // n = 1 => 2n = 2
        Assertions.assertEquals(1.3 * .3 / 2, MathUtil.gaussCoeffEven(1, 0.3));
        // n = 2 => 2n = 4
        Assertions.assertEquals(2.3 * 1.3 * .3 * -.7 / (4 * 3 * 2), MathUtil.gaussCoeffEven(2, 0.3));
        // n = 3 => 2n = 6
        Assertions.assertEquals(3.3 * 2.3 * 1.3 * .3 * -.7 * -1.7 / (6 * 5 * 4 * 3 * 2),
                MathUtil.gaussCoeffEven(3, 0.3));
    }

    @Test
    @DisplayName("odd gauss coefficients")
    public void testGaussCoeffOdd() {
        // n = 0 => 2n+1 = 1
        Assertions.assertEquals(.3, MathUtil.gaussCoeffOdd(0, .3));
        // n = 1 => 2n+1 = 3
        Assertions.assertEquals(1.3 * .3 * -.7 / (3 * 2), MathUtil.gaussCoeffOdd(1, .3));
        // n = 2 => 2n+1 = 5
        Assertions.assertEquals(2.3 * 1.3 * .3 * -.7 * -1.7 / (5 * 4 * 3 * 2), MathUtil.gaussCoeffOdd(2, .3));
    }

    @Test
    @DisplayName("bessel coefficients")
    public void testBesselCoeff() {
        Assertions.assertEquals(.5, MathUtil.besselCoeff(0, 0.3));
        Assertions.assertEquals(-.2, MathUtil.besselCoeff(1, 0.3));
        Assertions.assertEquals(.0975, MathUtil.besselCoeff(2, 0.3));
        Assertions.assertEquals(-.143, MathUtil.besselCoeff(3, 0.3));
        Assertions.assertEquals(-.013081249999999997, MathUtil.besselCoeff(4, 0.3));
        Assertions.assertEquals(.021976499999999996, MathUtil.besselCoeff(5, 0.3));
        Assertions.assertEquals(.0024461937499999993, MathUtil.besselCoeff(6, 0.3));
    }

    @Test
    @DisplayName("Bessel coefficients")
    @Disabled("Bessel coefficients do not work")
    public void testBesselCoeff_1() {
        final double[] d = new double[]{.1, .2, .23, .54, .4, .7};
        for (int i = 0; i < d.length; i++) {
            Assertions.assertEquals(0.25 * d[i] * (d[i] - 1.0), MathUtil.besselCoeff(2, d[i]));
        }
    }

    @Test
    @DisplayName("factorial")
    public void testFactorial() {
        Assertions.assertEquals(1, MathUtil.factorial(0));
        Assertions.assertEquals(1, MathUtil.factorial(1));
        Assertions.assertEquals(2, MathUtil.factorial(2));
        Assertions.assertEquals(6, MathUtil.factorial(3));
        Assertions.assertEquals(24, MathUtil.factorial(4));
        Assertions.assertEquals(120, MathUtil.factorial(5));
        Assertions.assertEquals(720, MathUtil.factorial(6));
    }

    @Test
    @DisplayName("factorial quotient")
    public void testFactorialQuotient() {
        Assertions.assertEquals(1, MathUtil.factorialQuotient(0, 1), Double.MIN_VALUE);
        Assertions.assertEquals(1, MathUtil.factorialQuotient(1, 1), Double.MIN_VALUE);
        Assertions.assertEquals(2, MathUtil.factorialQuotient(2, 1), Double.MIN_VALUE);
        Assertions.assertEquals(6, MathUtil.factorialQuotient(3, 1), Double.MIN_VALUE);
        Assertions.assertEquals(24, MathUtil.factorialQuotient(4, 1), Double.MIN_VALUE);
        Assertions.assertEquals(120, MathUtil.factorialQuotient(5, 1), Double.MIN_VALUE);
        Assertions.assertEquals(720, MathUtil.factorialQuotient(6, 1), Double.MIN_VALUE);

        Assertions.assertEquals(1, MathUtil.factorialQuotient(0, 0), Double.MIN_VALUE);
        Assertions.assertEquals(1, MathUtil.factorialQuotient(1, 0), Double.MIN_VALUE);
        Assertions.assertEquals(2, MathUtil.factorialQuotient(2, 0), Double.MIN_VALUE);
        Assertions.assertEquals(6, MathUtil.factorialQuotient(3, 0), Double.MIN_VALUE);
        Assertions.assertEquals(24, MathUtil.factorialQuotient(4, 0), Double.MIN_VALUE);
        Assertions.assertEquals(120, MathUtil.factorialQuotient(5, 0), Double.MIN_VALUE);
        Assertions.assertEquals(720, MathUtil.factorialQuotient(6, 0), Double.MIN_VALUE);

        Assertions.assertEquals(20, MathUtil.factorialQuotient(5, 3), Double.MIN_VALUE);
        Assertions.assertEquals(0.05, MathUtil.factorialQuotient(3, 5), Double.MIN_VALUE);
    }

    @Test
    public void mod() {
        Assertions.assertEquals(59.5, MathUtil.mod(59.5, MathUtil.DEGREES_IN_CIRCLE));
        Assertions.assertEquals(358.5, MathUtil.mod(-1.5, MathUtil.DEGREES_IN_CIRCLE));
        Assertions.assertEquals(-300.5, MathUtil.mod(59.5, -MathUtil.DEGREES_IN_CIRCLE));
        Assertions.assertEquals(-1.5, MathUtil.mod(-1.5, -MathUtil.DEGREES_IN_CIRCLE));
    }

    @Test
    public void modDegree() {
        Assertions.assertEquals(59.5, MathUtil.modDegree(59.5));
        Assertions.assertEquals(358.5, MathUtil.modDegree(-1.5));

        Assertions.assertEquals(0., MathUtil.modDegree(0.));
        Assertions.assertEquals(90., MathUtil.modDegree(90.));
        Assertions.assertEquals(180., MathUtil.modDegree(180.));
        Assertions.assertEquals(45f, MathUtil.modDegree(45.));
        Assertions.assertEquals(270., MathUtil.modDegree(270.));
        Assertions.assertEquals(180f, MathUtil.modDegree(-180.));
        Assertions.assertEquals(0., MathUtil.modDegree(360.));
        Assertions.assertEquals(1, MathUtil.modDegree(361));
        Assertions.assertEquals(0., MathUtil.modDegree(720));
    }

    @Test
    public void modRadian() {

        Assertions.assertEquals(0., MathUtil.modRadian(0.));
        Assertions.assertEquals(Math.PI / 2., MathUtil.modRadian(Math.PI / 2.));
        Assertions.assertEquals(Math.PI, MathUtil.modRadian(Math.PI));
        Assertions.assertEquals(Math.PI / 4., MathUtil.modRadian(Math.PI / 4.));
        Assertions.assertEquals(3 * Math.PI / 2., MathUtil.modRadian(3 * Math.PI / 2.));
        Assertions.assertEquals(Math.PI, MathUtil.modRadian(-Math.PI));
        Assertions.assertEquals(0., MathUtil.modRadian(2 * Math.PI));
        Assertions.assertEquals(1, MathUtil.modRadian(2 * Math.PI + 1.));
        Assertions.assertEquals(0., MathUtil.modRadian(4 * Math.PI));
    }

    @Test
    public void toRadians() {
        Assertions.assertEquals(0.93915110, MathUtil.toRadians(53, 48, 33.82), 1e-8);
        Assertions.assertEquals(0.03716765, MathUtil.toRadians(2, 07, 46.38), 1e-8);
    }

    @Test
    public void toDegrees() {
        Assertions.assertEquals(-0.026658333333333332, MathUtil.toDegrees(0, -1, 35.97));
        Assertions.assertEquals(0.026658333333333332, MathUtil.toDegrees(0, 1, 35.97));
        Assertions.assertEquals(-35.97 / 3600., MathUtil.toDegrees(0, 0, -35.97));
        Assertions.assertEquals(35.97 / 3600., MathUtil.toDegrees(0, 0, 35.97));
        Assertions.assertEquals(-(1 + 35.97 / 3600.), MathUtil.toDegrees(-1, 0, 35.97));
        Assertions.assertEquals(1 + 35.97 / 3600., MathUtil.toDegrees(1, 0, 35.97));

        Assertions.assertEquals("seconds >= 60 or minutes > 59 or degrees > 359",
                Assertions.assertThrows(IllegalArgumentException.class, () -> MathUtil.toDegrees(360, 1, 1.))
                        .getMessage());
        Assertions.assertEquals("seconds < 0 or minutes < 0 or degrees < -359",
                Assertions.assertThrows(IllegalArgumentException.class, () -> MathUtil.toDegrees(-360, 1, 1.))
                        .getMessage());
        Assertions.assertEquals("minutes > 59 or seconds out of range ]0;60]",
                Assertions.assertThrows(IllegalArgumentException.class, () -> MathUtil.toDegrees(0, 60, 1.))
                        .getMessage());
        Assertions.assertEquals("seconds < 0 or minutes < -59",
                Assertions.assertThrows(IllegalArgumentException.class, () -> MathUtil.toDegrees(0, -60, 1.))
                        .getMessage());
        Assertions.assertEquals("seconds out of range ]-60;60[",
                Assertions.assertThrows(IllegalArgumentException.class, () -> MathUtil.toDegrees(0, 0, 60.))
                        .getMessage());
        Assertions.assertEquals("seconds out of range ]-60;60[",
                Assertions.assertThrows(IllegalArgumentException.class, () -> MathUtil.toDegrees(0, 0, -60))
                        .getMessage());

        MathUtil.toDegrees(359, 1, 1.);
        MathUtil.toDegrees(-359, 1, 1.);
        MathUtil.toDegrees(0, 59, 1.);
        MathUtil.toDegrees(0, -59, 1.);
        MathUtil.toDegrees(0, 0, 59.9999999999);
        MathUtil.toDegrees(0, 0, -59.9999999999);
    }

    @Test
    @DisplayName("pgcd")
    public void pgcd() {
        Assertions.assertEquals(5, MathUtil.pgcd(15, 25));
    }

    @Test
    @DisplayName("ppcm")
    public void ppcm() {
        Assertions.assertEquals(48, MathUtil.ppcm(16, 6));
        Assertions.assertEquals(48, MathUtil.ppcm(16, 3));
        Assertions.assertEquals(16, MathUtil.ppcm(16, 2));
        Assertions.assertEquals(8, MathUtil.ppcm(8, 2));
        Assertions.assertEquals(8, MathUtil.ppcm(8, 4));
        Assertions.assertEquals(8, MathUtil.ppcm(8, 1));
    }
}
