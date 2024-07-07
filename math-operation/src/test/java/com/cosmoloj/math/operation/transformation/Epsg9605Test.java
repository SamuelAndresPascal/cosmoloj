package com.cosmoloj.math.operation.transformation;

import com.cosmoloj.math.operation.surface.Ellipsoid;
import com.cosmoloj.math.util.MathUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Samuel Andrés
 */
public class Epsg9605Test {

    private final Epsg9605 transform = new Epsg9605(Ellipsoid.ofInverseFlattening(6378137.0, 298.2572236),
            Ellipsoid.INTERNATIONAL, 84.87, 96.49, 116.95, 251, 1.419227 * 1e-5);

    @Test
    public void forward() {

        Assertions.assertArrayEquals(
            new double[]{MathUtil.toRadians(53, 48, 36.563), MathUtil.toRadians(2, 07, 51.477), 28.089032044440557},
            transform.compute(new double[]{MathUtil.toRadians(53, 48, 33.82), MathUtil.toRadians(2, 07, 46.38), 73.0}),
            1e-8);
    }

    @Test
    public void inverse() {

        Assertions.assertArrayEquals(
        new double[]{MathUtil.toRadians(53, 48, 33.82), MathUtil.toRadians(2, 07, 46.38), 72.99483886340806},
        transform.inverse(new double[]{MathUtil.toRadians(53, 48, 36.563), MathUtil.toRadians(2, 07, 51.477), 28.091}),
        1e-8);
    }
}
