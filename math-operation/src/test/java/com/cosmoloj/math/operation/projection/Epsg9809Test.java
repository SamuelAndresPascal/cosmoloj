package com.cosmoloj.math.operation.projection;

import com.cosmoloj.math.operation.surface.Ellipsoid;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Samuel Andrés
 */
public class Epsg9809Test {

    private final double a = 6377397.155;
    private final double invf = 299.15281;

    private final double phi0 = 0.910296727;
    private final double lambda0 = 0.094032038;
    private final double fe = 155000.00;
    private final double fn = 463000.00;
    private final double k0 = 0.9999079;

    private final double phi = 0.925024504;
    private final double lambda = 0.104719755;
    private final double easting = 196105.283;
    private final double northing = 557057.739;

    private final Epsg9809 transform = new Epsg9809(Ellipsoid.ofInverseFlattening(a, invf), phi0, lambda0, k0, fe, fn);

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
