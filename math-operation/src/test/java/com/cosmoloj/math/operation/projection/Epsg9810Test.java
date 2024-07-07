package com.cosmoloj.math.operation.projection;

import com.cosmoloj.math.operation.surface.Ellipsoid;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Samuel Andrés
 */
public class Epsg9810Test {

    private final double a = 6378137.0;
    private final double invf = 298.2572236;

    private final double lambda0 = 0.0;
    private final double fe = 2000000.00;
    private final double fn = 2000000.00;
    private final double k0 = 0.994;

    private final double phi = 1.274090354;
    private final double lambda = 0.767944871;
    private final double easting = 3320416.75;
    private final double northing = 632668.43;

    private final Epsg9810 transform = new Epsg9810(Ellipsoid.ofInverseFlattening(a, invf),
            Aspect.NORTH_POLE, lambda0, k0, fe, fn);

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
