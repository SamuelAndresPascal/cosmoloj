package com.cosmoloj.math.operation.transformation.concat;

import com.cosmoloj.math.operation.surface.Ellipsoid;
import com.cosmoloj.math.util.MathUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Samuel Andrés
 */
public class Epsg1062Test {

    private final Epsg1062 transform = new Epsg1062(Ellipsoid.ofInverseFlattening(6378388.0, 297.0),
            Ellipsoid.ofInverseFlattening(6378137.0, 298.2572236),
            -270.933, 115.599, -360.226,
            0.000025530288, 0.000006001993, -0.000011543414,
            -5.109 * 1e-6,
            2464351.59, -5783466.61, 974809.81);

    @Test
    public void forward() {

        Assertions.assertArrayEquals(
                new double[]{MathUtil.toRadians(9, 34, 49.001),
                    MathUtil.toRadians(-66, 4, 54.705), 180.51405381970108},
                transform.compute(new double[]{MathUtil.toRadians(9, 35, 0.386),
                    MathUtil.toRadians(-66, 4, 48.091), 201.46}), 1e-8);
    }

    @Test
    public void inverse() {

        Assertions.assertArrayEquals(
                new double[]{MathUtil.toRadians(9, 35, 0.386),
                    MathUtil.toRadians(-66, 4, 48.091), 201.46524150762707},
                transform.inverse(new double[]{MathUtil.toRadians(9, 34, 49.001),
                    MathUtil.toRadians(-66, 4, 54.705), 180.51}), 1e-9);
    }
}
