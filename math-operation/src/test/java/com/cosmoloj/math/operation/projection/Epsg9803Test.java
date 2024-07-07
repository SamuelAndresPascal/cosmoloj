package com.cosmoloj.math.operation.projection;

import com.cosmoloj.math.operation.surface.Ellipsoid;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Samuel Andrés
 */
public class Epsg9803Test {

    private final double a = 6378388; // 6378206.400;
    private final double e = 0.08199189;

    private final double phif = 1.57079633;
    private final double lambdaf = 0.07604294;
    private final double phi1 = 0.86975574;
    private final double phi2 = 0.89302680;
    private final double ef = 150000.01;
    private final double nf = 5400088.44;

    private final double phi = 0.88452540;
    private final double lambda = 0.10135773;
    private final double easting = 251763.20;
    private final double northing = 153034.13;

    private final Epsg9803 transform = new Epsg9803(Ellipsoid.ofEccentricity(a, e), phif, lambdaf, phi1, phi2, ef, nf);

    @Test
    public void forward() {

        Assertions.assertEquals(0.64628304, transform.m(phi1), 1e-8);
        Assertions.assertEquals(0.62834001, transform.m(phi2), 1e-8);
        Assertions.assertEquals(0.35913403, transform.t(phi), 1e-8);
        Assertions.assertEquals(0.0, transform.t(phif), 1e-8);
        Assertions.assertEquals(0.36750382, transform.t(phi1), 1e-8);
        Assertions.assertEquals(0.35433583, transform.t(phi2), 1e-8);
        Assertions.assertEquals(0.77164219, transform.n(), 1e-8);
        Assertions.assertEquals(1.81329763, transform.f(), 1e-8);
        Assertions.assertEquals(5248041.03, transform.r(phi), 1e-1);
        Assertions.assertEquals(0.0, transform.r(phif), 1e-2);
        Assertions.assertEquals(0.01953396, transform.theta(lambda), 1e-9);
        Assertions.assertEquals(251763.20, transform.easting(phi, lambda), 1e-2);
        Assertions.assertEquals(153034.13, transform.northing(phi, lambda), 1e-1);

        Assertions.assertEquals(0.00014204, Epsg9803.SEMI_MAJOR, 1e-8);

        Assertions.assertArrayEquals(new double[]{easting, northing},
                transform.compute(new double[]{phi, lambda}), 1e-1);
    }

    @Test
    public void inverse() {

        Assertions.assertEquals(0.01939192, transform.invTheta(easting, northing), 1e-8);
        Assertions.assertEquals(0.35913403, transform.invT(easting, northing), 1e-8);
        Assertions.assertEquals(5248041.03, transform.invR(easting, northing), 1e-2);
        Assertions.assertEquals(0.88452540, transform.phi(easting, northing), 1e-8);
        Assertions.assertEquals(0.10135773, transform.lambda(easting, northing), 1e-8);

        Assertions.assertArrayEquals(new double[]{phi, lambda},
                transform.inverse(new double[]{easting, northing}), 1e-8);
    }
}
