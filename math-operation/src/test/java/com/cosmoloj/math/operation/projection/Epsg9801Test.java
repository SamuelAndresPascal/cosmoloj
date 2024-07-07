package com.cosmoloj.math.operation.projection;

import com.cosmoloj.math.operation.surface.Ellipsoid;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Samuel Andrés
 */
public class Epsg9801Test {

    private final double a = 6378206.4; // 6378206.400;
    private final double e = 0.08227185;

    private final double phi0 = 0.31415927;
    private final double lambda0 = -1.34390352;
    private final double k0 = 1.;
    private final double fe = 250000.;
    private final double fn = 150000.;

    private final double phi = 0.31297535;
    private final double lambda = -1.34292061;
    private final double easting = 255966.58;
    private final double northing = 142493.51;

    private final Epsg9801 transform = new Epsg9801(Ellipsoid.ofEccentricity(a, e), phi0, lambda0, k0, fe, fn);

    @Test
    public void forward() {

        Assertions.assertEquals(0.95136402, transform.m(phi0), 1e-8);
        Assertions.assertEquals(0.72806411, transform.t(phi0), 1e-8);
        Assertions.assertEquals(3.39591092, transform.f(), 1e-7);
        Assertions.assertEquals(0.30901699, transform.n(), 1e-8);
        Assertions.assertEquals(19643955.26, transform.r(phi), 1.);
        Assertions.assertEquals(19636447.86, transform.r(phi0), 1.);
        Assertions.assertEquals(0.00030374, transform.theta(lambda), 1e-8);
        Assertions.assertEquals(0.728965259, transform.t(phi), 1e-9);
        Assertions.assertEquals(255966.58, transform.easting(phi, lambda), 1e-2);
        Assertions.assertEquals(142493.51, transform.northing(phi, lambda), 1e-1);

        Assertions.assertArrayEquals(new double[]{easting, northing},
                transform.compute(new double[]{phi, lambda}), 1e-1);
    }

    @Test
    public void inverse() {

        Assertions.assertEquals(0.000303736, transform.invTheta(easting, northing), 1e-9);
        Assertions.assertEquals(0.728965259, transform.invT(easting, northing), 1e-8);
        Assertions.assertEquals(19643955.26, transform.invR(easting, northing), 1.2);
        Assertions.assertEquals(0.31297535, transform.phi(easting, northing), 1e-8);
        Assertions.assertEquals(-1.34292061, transform.lambda(easting, northing), 1e-8);

        Assertions.assertArrayEquals(new double[]{phi, lambda},
                transform.inverse(new double[]{easting, northing}), 1e-8);
    }
}
