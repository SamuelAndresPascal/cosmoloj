package com.cosmoloj.math.operation.projection;

import com.cosmoloj.math.operation.surface.Ellipsoid;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Samuel Andrés
 */
public class Epsg9840Test {

    private final double phi = 0.939151101;
    private final double lambda = 0.037167659;
    private final double easting = -189011.711;
    private final double northing = -128640.567;

    private final Epsg9840 transform = new Epsg9840(Ellipsoid.ofInverseFlattening(6378137.0, 298.2572236),
            0.95993109, 0.08726646, 0., 0.);

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
