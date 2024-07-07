package com.cosmoloj.math.operation.projection;

import com.cosmoloj.math.operation.surface.Ellipsoid;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Samuel Andrés
 */
public class Epsg9829Test {

    private final double a = 6378137.0;
    private final double invf = 298.2572236;

    private final double phif = -1.239183769;
    private final double lambda0 = 1.221730476;
    private final double fe = 6000000.00;
    private final double fn = 6000000.00;

    private final double phi = -1.308996939;
    private final double lambda = 2.094395102;
    private final double easting = 7255380.79;
    private final double northing = 7053389.56;

    private final Epsg9829 transform = new Epsg9829(Ellipsoid.ofInverseFlattening(a, invf), phif, lambda0, fe, fn);

    @Test
    public void forward() {

        Assertions.assertArrayEquals(new double[]{easting, northing},
                transform.compute(new double[]{phi, lambda}), 1e-2);
    }

    @Test
    public void inverse() {

        Assertions.assertArrayEquals(new double[]{phi, lambda},
                transform.inverse(new double[]{easting, northing}), 1e-9);
    }
}
