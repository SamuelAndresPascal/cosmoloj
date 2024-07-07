package com.cosmoloj.math.operation.conversion;

import com.cosmoloj.math.operation.surface.Ellipsoid;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Samuel Andrés
 */
public class Epsg9844Test {

    private final double phi = 0.939151101;
    private final double lambda = 0.037167659;
    private final double h = 73.0;

    private final Epsg9844 transform = new Epsg9844(Ellipsoid.ofInverseFlattening(6378137.0, 298.2572236));

    @Test
    public void forward() {

        Assertions.assertArrayEquals(new double[]{lambda, phi, h},
                transform.compute(new double[]{phi, lambda, h}));
    }

    @Test
    public void inverse() {

        Assertions.assertArrayEquals(new double[]{phi, lambda, h},
                transform.inverse(new double[]{lambda, phi, h}));
    }
}
