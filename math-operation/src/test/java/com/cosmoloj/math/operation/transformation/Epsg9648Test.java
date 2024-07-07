package com.cosmoloj.math.operation.transformation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Samuel Andrés
 */
public class Epsg9648Test {

// // *\d{4} \| (.*)\s*\|\s*(.*)\s
// private final double $1 = $2;\n
    private final double xs = 53.5;
    private final double ys = -7.7;
    private final double xt = 53.5;
    private final double yt = -7.7;
    private final double ms = 0.1;
    private final double mt = 3600.;
    private final double a0 = 0.763;
    private final double au1v0 = -4.487;
    private final double au0v1 = 0.123;
    private final double au2v0 = 0.215;
    private final double au1v1 = -0.515;
    private final double au0v2 = 0.183;
    private final double au3v0 = -0.265;
    private final double au2v1 = -0.57;
    private final double au1v2 = 0.414;
    private final double au0v3 = -0.374;
    private final double au3v1 = 2.852;
    private final double au2v2 = 5.703;
    private final double au1v3 = 13.11;
    private final double au3v2 = -61.678;
    private final double au2v3 = 113.743;
    private final double au3v3 = -265.898;
    private final double b0 = -2.81;
    private final double bu1v0 = -0.341;
    private final double bu0v1 = -4.68;
    private final double bu2v0 = 1.196;
    private final double bu1v1 = -0.119;
    private final double bu0v2 = 0.17;
    private final double bu3v0 = -0.887;
    private final double bu2v1 = 4.877;
    private final double bu1v2 = 3.913;
    private final double bu0v3 = 2.163;
    private final double bu3v1 = -46.666;
    private final double bu2v2 = -27.795;
    private final double bu1v3 = 18.867;
    private final double bu3v2 = -95.377;
    private final double bu2v3 = -284.294;
    private final double bu3v3 = -853.95;

    private final double[][] a = new double[6][6];
    private final double[][] b = new double[6][6];
    private final Epsg9648 transform;

    public Epsg9648Test() {
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
        a[3][2] = au3v2;
        a[2][3] = au2v3;
        a[3][3] = au3v3;
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
        b[3][2] = bu3v2;
        b[2][3] = bu2v3;
        b[3][3] = bu3v3;

        this.transform = new Epsg9648(xs, ys, xt, yt, ms, mt, a, b);
    }

    @Test
    public void forward() {

        Assertions.assertArrayEquals(new double[]{55. + .00002972, -6.5 - 0.00094913},
                transform.compute(new double[]{55., -6.5}), 1e-8);
    }

    @Test
    public void inverse() {

        Assertions.assertArrayEquals(new double[]{55., -6.5},
                transform.inverse(new double[]{55. + .00002972, -6.5 - 0.00094913}), 1e-8);
    }
}
