package com.cosmoloj.math.operation.projection;

import com.cosmoloj.math.operation.surface.Ellipsoid;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Samuel Andrés
 */
public class Epsg9804Test {

    private final double a = 6377397.155;
    private final double e = 0.081696831;

    private final double phi0 = 0.0;
    private final double lambda0 = 1.91986218;
    private final double k0 = 0.997;
    private final double fe = 3900000.00;
    private final double fn = 900000.00;

    private final double phi = -0.05235988;
    private final double lambda = 2.09439510;
    private final double easting = 5009726.58;
    private final double northing = 569150.82;

    private final Epsg9804 transform = new Epsg9804(Ellipsoid.ofEccentricity(a, e), phi0, k0, lambda0, fe, fn);

    @Test
    public void forward() {

        Assertions.assertArrayEquals(new double[]{easting, northing},
                transform.compute(new double[]{phi, lambda}), 1e-1);
    }

    @Test
    public void inverse() {

        Assertions.assertArrayEquals(new double[]{phi, lambda},
                transform.inverse(new double[]{easting, northing}), 1e-8);
    }
}
