package com.cosmoloj.math.operation.projection;

import com.cosmoloj.math.operation.surface.Ellipsoid;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Samuel Andrés
 */
public class Epsg9806Test {

    private final double a = 31706587.88;
    private final double e = Math.sqrt(0.006785146);

    private final double phi0 = 0.182241463;
    private final double lambda0 = -1.070468608;
    private final double fe = 430000.00;
    private final double fn = 325000.00;

    private final double phi = 0.17453293;
    private final double lambda = -1.08210414;
    private final double easting = 66644.94;
    private final double northing = 82536.22;

    private final Epsg9806 transform = new Epsg9806(Ellipsoid.ofEccentricity(a, e), phi0, lambda0, fe, fn);

    @Test
    public void forward() {

        Assertions.assertEquals(-0.01145876, transform.coefA(phi, lambda), 1e-8);
        Assertions.assertEquals(0.03109120, transform.coefT(phi), 1e-8);
        Assertions.assertEquals(0.00662550, transform.coefC(phi), 1e-8);
        // TODO
        Assertions.assertEquals(5496860.24, transform.getSurface().m(phi), 1.); // ça fait beaucoup !
        Assertions.assertEquals(31709831.92, transform.getSurface().nu(phi), 1e-2);
        Assertions.assertEquals(5739691.12, transform.getSurface().m(phi0), 1e-2);
        // TODO
        Assertions.assertArrayEquals(new double[]{easting, northing},
                transform.compute(new double[]{phi, lambda}), 1.); // ça fait beaucoup !
    }

    @Test
    public void inverse() {

        // TODO
        Assertions.assertArrayEquals(new double[]{phi, lambda},
                transform.inverse(new double[]{easting, northing}), 1e-8);
    }
}
