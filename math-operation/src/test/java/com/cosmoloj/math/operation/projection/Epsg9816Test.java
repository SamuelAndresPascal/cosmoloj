package com.cosmoloj.math.operation.projection;

import com.cosmoloj.math.operation.surface.Ellipsoid;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Samuel Andrés
 */
public class Epsg9816Test {

    private final double a = 6378137.0;
    private final double invf = 298.2572236;

    private final double phif = 36.5964;
    private final double lambdaf = 7.83445;
    private final double ef = 270.;
    private final double nf = 360.;

    private final double phi = 38.97997;
    private final double lambda = 8.22437;
    private final double easting = 302.;
    private final double northing = 598.;

    private final Epsg9816 transform = new Epsg9816(Ellipsoid.ofInverseFlattening(a, invf), phif, lambdaf, ef, nf);

    @Test
    public void forward() {

        Assertions.assertArrayEquals(new double[]{phi, lambda},
                transform.compute(new double[]{easting, northing}), 1e-5);
    }

    @Test
    public void inverse() {

        Assertions.assertArrayEquals(new double[]{easting, northing},
                transform.inverse(new double[]{phi, lambda}), 1e-1);
    }
}
