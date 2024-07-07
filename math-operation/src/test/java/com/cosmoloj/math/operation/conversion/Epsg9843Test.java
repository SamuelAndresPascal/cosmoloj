package com.cosmoloj.math.operation.conversion;

import com.cosmoloj.math.operation.surface.Ellipsoid;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Samuel Andrés
 */
public class Epsg9843Test {

    private final double phi = 0.939151101;
    private final double lambda = 0.037167659;

    private final Epsg9843 transform = new Epsg9843(Ellipsoid.ofInverseFlattening(6378137.0, 298.2572236));

    @Test
    public void forward() {

        Assertions.assertArrayEquals(new double[]{lambda, phi},
                transform.compute(new double[]{phi, lambda}));
    }

    @Test
    public void inverse() {

        Assertions.assertArrayEquals(new double[]{phi, lambda},
                transform.inverse(new double[]{lambda, phi}));
    }
}
