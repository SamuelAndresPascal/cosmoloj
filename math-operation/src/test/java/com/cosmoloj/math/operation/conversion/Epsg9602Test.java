package com.cosmoloj.math.operation.conversion;

import com.cosmoloj.math.operation.surface.Ellipsoid;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Samuel Andrés
 */
public class Epsg9602Test {

    private final double phi = 0.939151101;
    private final double lambda = 0.037167659;
    private final double h = 73.0;
    private final double x = 3771793.968;
    private final double y = 140253.342;
    private final double z = 5124304.349;

    private final Epsg9602 transform = new Epsg9602(Ellipsoid.ofInverseFlattening(6378137.0, 298.2572236));

    @Test
    public void forward() {

        Assertions.assertArrayEquals(new double[]{x, y, z},
                transform.compute(new double[]{phi, lambda, h}), 1e-1);
    }

    @Test
    public void inverse() {

        Assertions.assertArrayEquals(new double[]{phi, lambda, h},
                transform.inverse(new double[]{x, y, z}), 1e-3);
    }
}
