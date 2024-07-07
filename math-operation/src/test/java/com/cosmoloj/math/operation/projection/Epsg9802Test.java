package com.cosmoloj.math.operation.projection;

import com.cosmoloj.math.operation.surface.Ellipsoid;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Samuel Andrés
 */
public class Epsg9802Test {

    private final double a = 20925832.16; // 6378206.400;
    private final double e = 0.08227185;

    private final double phif = 0.48578331;
    private final double lambdaf = -1.72787596;
    private final double phi1 = 0.49538262;
    private final double phi2 = 0.52854388;
    private final double ef = 2000000.;
    private final double nf = 0.;

    private final double phi = 0.49741884;
    private final double lambda = -1.67551608;
    private final double easting = 2963503.91;
    private final double northing = 254759.80;

    private final Epsg9802 transform = new Epsg9802(Ellipsoid.ofEccentricity(a, e), phif, lambdaf, phi1, phi2, ef, nf);

    @Test
    public void forward() {

        Assertions.assertEquals(0.88046050, transform.m(phi1), 1e-9);
        Assertions.assertEquals(0.86428642, transform.m(phi2), 1e-8);
        Assertions.assertEquals(0.59686306, transform.t(phi), 1e-9);
        Assertions.assertEquals(0.60475101, transform.t(phif), 1e-8);
        Assertions.assertEquals(0.59823957, transform.t(phi1), 1e-9);
        Assertions.assertEquals(0.57602212, transform.t(phi2), 1e-8);
        Assertions.assertEquals(0.48991263, transform.n(), 1e-8);
        Assertions.assertEquals(2.31154807, transform.f(), 1e-8);
        Assertions.assertEquals(37565039.86, transform.r(phi), 1.);
        Assertions.assertEquals(37807441.20, transform.r(phif), 1.);
        Assertions.assertEquals(0.02565177, transform.theta(lambda), 1e-8);
        Assertions.assertEquals(2963503.91, transform.easting(phi, lambda), 1e-1);
        Assertions.assertEquals(254759.80, transform.northing(phi, lambda), 1e-1);

        Assertions.assertArrayEquals(new double[]{easting, northing},
                transform.compute(new double[]{phi, lambda}), 1e-1);
    }

    @Test
    public void inverse() {

        Assertions.assertEquals(0.025651765, transform.invTheta(easting, northing), 1e-9);
        Assertions.assertEquals(0.59686306, transform.invT(easting, northing), 1e-8);
        Assertions.assertEquals(37565039.86, transform.invR(easting, northing), 1);
        Assertions.assertEquals(0.49741884, transform.phi(easting, northing), 1e-8);
        Assertions.assertEquals(-1.67551608, transform.lambda(easting, northing), 1e-8);

        Assertions.assertArrayEquals(new double[]{phi, lambda},
                transform.inverse(new double[]{easting, northing}), 1e-8);
    }
}
