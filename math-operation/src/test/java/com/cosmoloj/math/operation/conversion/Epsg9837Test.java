package com.cosmoloj.math.operation.conversion;

import com.cosmoloj.math.operation.surface.Ellipsoid;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Samuel Andrés
 */
public class Epsg9837Test {

    private final double phi = 0.939151101;
    private final double lambda = 0.037167659;
    private final double h = 73.0;
    private final double u = -189013.869;
    private final double v = -128642.040;
    private final double w = -4220.171;

    private final Epsg9837 transform = new Epsg9837(Ellipsoid.ofInverseFlattening(6378137.0, 298.2572236),
            0.95993109, 0.08726646, 200.);

    @Test
    public void forward() {

        Assertions.assertArrayEquals(new double[]{u, v, w},
                transform.compute(new double[]{phi, lambda, h}), 1e-1);
    }

    @Test
    public void inverse() {

        Assertions.assertArrayEquals(new double[]{phi, lambda, h},
                transform.inverse(new double[]{u, v, w}), 1e-3);
    }
}
