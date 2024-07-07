package com.cosmoloj.math.operation.projection;

import com.cosmoloj.math.operation.surface.Ellipsoid;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Samuel Andrés
 */
public class Epsg9805Test {

    private final double a = 6378245.0;
    private final double e = 0.08181333;

    private final double phi1 = 0.73303829;
    private final double lambda0 = 0.89011792;
    private final double fe = 0.00;
    private final double fn = 0.00;

    private final double phi = 0.9250245;
    private final double lambda = 0.9250245;
    private final double easting = 165704.29;
    private final double northing = 5171848.07;

    private final Epsg9805 transform = new Epsg9805(Ellipsoid.ofEccentricity(a, e), phi1, lambda0, fe, fn);

    @Test
    public void forward() {

        Assertions.assertEquals(0.744260894, transform.k0(), 1e-8);

        Assertions.assertArrayEquals(new double[]{easting, northing},
                transform.compute(new double[]{phi, lambda}), 1e-1);
    }

    @Test
    public void inverse() {

        Assertions.assertArrayEquals(new double[]{phi, lambda},
                transform.inverse(new double[]{easting, northing}), 1e-8);
    }
}
