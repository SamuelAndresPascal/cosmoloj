package com.cosmoloj.math.operation.projection;

import com.cosmoloj.math.operation.surface.Ellipsoid;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Samuel Andrés
 */
public class Epsg9817aTest {

    private final double a =  6378249.2;
    private final double invf = 293.4660213;

    private final double phi0 = 0.604756586;
    private final double lambda0 = 0.651880476;
    private final double k0 = 0.99962560;
    private final double fe = 300000.;
    private final double fn = 300000.;

    private final double phi = 0.654874806;
    private final double lambda = 0.595793792;
    private final double easting = 15707.96;
    private final double northing = 623165.96;

    private final Epsg9817a transform
            = new Epsg9817a(Ellipsoid.ofInverseFlattening(a, invf), phi0, lambda0, k0, fe, fn);

    @Test
    public void forward() {

        Assertions.assertEquals(0.001706682563, transform.n(), 1e-12);
        Assertions.assertEquals(4.1067494e-15, transform.computeCoefA(), 1e-22);
        Assertions.assertEquals(111131.8633, transform.computeCoefAp(), 1e-4);
        Assertions.assertEquals(16300.64407, transform.computeCoefBp(), 1e-5);
        Assertions.assertEquals(17.38751, transform.computeCoefCp(), 1e-5);
        Assertions.assertEquals(0.02308, transform.computeCoefDp(), 1e-5);
        Assertions.assertEquals(0.000033, transform.computeCoefEp(), 1e-6);
        Assertions.assertEquals(3835482.233, transform.s(phi0), 1e-3);
        Assertions.assertEquals(9235264.405, transform.computeR0(), 1e-2);
        Assertions.assertEquals(4154101.458, transform.s(phi), 1e-3);
        Assertions.assertEquals(318619.225, transform.m(phi), 1e-2);
        Assertions.assertEquals(318632.72, transform.coefM(phi), 1e-2);
        Assertions.assertEquals(8916631.685, transform.r(phi), 1e-2);
        Assertions.assertEquals(-0.03188875, transform.theta(lambda), 1e-8);
        Assertions.assertEquals(easting, transform.easting(phi, lambda), 1e-2);
        Assertions.assertEquals(northing, transform.northing(phi, lambda), 1e-2);

        Assertions.assertArrayEquals(new double[]{easting, northing},
                transform.compute(new double[]{phi, lambda}), 1e-2);
    }

    @Test
    public void inverse() {

        Assertions.assertEquals(-0.031888749, transform.invTheta(easting, northing), 1e-9);
        Assertions.assertEquals(8916631.685, transform.invR(easting, northing), 1e-3);
        Assertions.assertEquals(318632.717, transform.invCoefM(easting, northing), 1e-3);
        Assertions.assertEquals(phi, transform.phi(easting, northing), 1e-9);
        Assertions.assertEquals(lambda, transform.lambda(easting, northing), 1e-9);

        Assertions.assertArrayEquals(new double[]{phi, lambda},
                transform.inverse(new double[]{easting, northing}), 1e-9);
    }
}
