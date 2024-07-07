package com.cosmoloj.math.operation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Samuel Andrés
 */
public class MethodParameterTest {

    private final double a0 = -5.56098e-06;
    private final double au1v0 = -1.55391e-06;
    private final double au0v1 = -4.0262e-07;
    private final double au2v0 = -5.09693e-07;
    private final double au1v1 = -8.19775e-07;
    private final double au0v2 = -2.47592e-07;
    private final double au3v0 = 1.36682e-07;
    private final double au2v1 = 1.86198e-07;
    private final double au1v2 = 1.2335e-07;
    private final double au0v3 = 5.68797e-08;
    private final double au4v0 = -2.32217e-09;
    private final double au3v1 = -7.69931e-09;
    private final double au2v2 = -7.86953e-09;
    private final double au1v3 = -6.12216e-09;
    private final double au0v4 = -4.01382e-09;
    private final double b0 = 1.48944e-05;
    private final double bu1v0 = 2.68191e-06;
    private final double bu0v1 = 2.4529e-06;
    private final double bu2v0 = 2.944e-07;
    private final double bu1v1 = 1.5226e-06;
    private final double bu0v2 = 9.10592e-07;
    private final double bu3v0 = -3.68241e-07;
    private final double bu2v1 = -8.51732e-07;
    private final double bu1v2 = -5.66713e-07;
    private final double bu0v3 = -1.85188e-07;
    private final double bu4v0 = 2.84312e-08;
    private final double bu3v1 = 6.84853e-08;
    private final double bu2v2 = 5.00828e-08;
    private final double bu1v3 = 4.15937e-08;
    private final double bu0v4 = 7.62236e-09;



    private final double[][] a = new double[6][6];
    private final double[][] b = new double[6][6];

    public MethodParameterTest() {
        a[0][0] = a0;
        a[1][0] = au1v0;
        a[0][1] = au0v1;
        a[2][0] = au2v0;
        a[1][1] = au1v1;
        a[0][2] = au0v2;
        a[3][0] = au3v0;
        a[2][1] = au2v1;
        a[1][2] = au1v2;
        a[0][3] = au0v3;
        a[3][1] = au3v1;
        a[2][2] = au2v2;
        a[1][3] = au1v3;
        a[4][0] = au4v0;
        a[0][4] = au0v4;
        b[0][0] = b0;
        b[1][0] = bu1v0;
        b[0][1] = bu0v1;
        b[2][0] = bu2v0;
        b[1][1] = bu1v1;
        b[0][2] = bu0v2;
        b[3][0] = bu3v0;
        b[2][1] = bu2v1;
        b[1][2] = bu1v2;
        b[0][3] = bu0v3;
        b[3][1] = bu3v1;
        b[2][2] = bu2v2;
        b[1][3] = bu1v3;
        b[4][0] = bu4v0;
        b[0][4] = bu0v4;
    }

    @Test
    public void test0() {
        Assertions.assertEquals(-5.56098e-06, MethodParameter.A0.extractCoef(a, "A"));
    }

    @Test
    public void testN() {
        Assertions.assertEquals(6.84853e-08, MethodParameter.B_U3V1.extractCoef(b, "B"));
    }

    @Test
    public void testUnsupported() {
        Assertions.assertEquals("this method only works for A/B matricial coefficients",
                Assertions.assertThrows(UnsupportedOperationException.class,
                () -> MethodParameter.AZIMUTH_OF_THE_INITIAL_LINE.extractCoef(a, "A")).getMessage());
        Assertions.assertEquals("this method only works for A/B matricial coefficients",
                Assertions.assertThrows(UnsupportedOperationException.class,
                () -> MethodParameter.C1.extractCoef(b, "B")).getMessage());
    }
}
