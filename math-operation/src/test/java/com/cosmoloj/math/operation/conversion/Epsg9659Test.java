package com.cosmoloj.math.operation.conversion;

import com.cosmoloj.math.operation.surface.Ellipsoid;
import com.cosmoloj.math.util.MathUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Samuel Andrés
 */
public class Epsg9659Test {

    private final double[] forward = {MathUtil.toRadians(53, 48, 33.82), MathUtil.toRadians(2, 7, 46.38), 73.};
    private final double[] inverse = {MathUtil.toRadians(53, 48, 33.82), MathUtil.toRadians(2, 7, 46.38)};

    private final Epsg9659 transform = new Epsg9659(Ellipsoid.ofInverseFlattening(6378137.0, 298.2572236));

    @Test
    public void forward() {

        Assertions.assertArrayEquals(inverse,
                transform.compute(forward));
    }

    @Test
    public void inverse() {

        Assertions.assertArrayEquals(
                new double[]{MathUtil.toRadians(53, 48, 33.82), MathUtil.toRadians(2, 7, 46.38), 0.},
                transform.inverse(inverse));
    }
}
