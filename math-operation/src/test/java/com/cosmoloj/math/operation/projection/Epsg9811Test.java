package com.cosmoloj.math.operation.projection;

import com.cosmoloj.math.operation.surface.Ellipsoid;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Disabled;

/**
 *
 * @author Samuel Andrés
 */
public class Epsg9811Test {

    private final double a = 6378137.0;
    private final double invf = 298.2572236;

    private final double phi0 = Double.NaN;
    private final double lambda0 = Double.NaN;
    private final double fe = Double.NaN;
    private final double fn = Double.NaN;

    private final double phi = Math.toRadians(39.0);
    private final double lambda = Math.toRadians(176.0);
    private final double easting = 2800000.0;
    private final double northing = 6200000.0;

    private final Epsg9811 transform = new Epsg9811(Ellipsoid.ofInverseFlattening(a, invf),
            phi0, lambda0, fe, fn);

    @Test @Disabled
    public void forward() {

        Assertions.assertArrayEquals(new double[]{easting, northing},
                transform.compute(new double[]{phi, lambda}), 1e-2);
    }

    @Test @Disabled
    public void inverse() {

        Assertions.assertArrayEquals(new double[]{phi, lambda},
                transform.inverse(new double[]{easting, northing}), 1e-9);
    }
}
