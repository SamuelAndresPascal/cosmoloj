package com.cosmoloj.math.operation.projection;

import com.cosmoloj.math.operation.surface.Ellipsoid;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Samuel Andrés
 */
public class Epsg1052Test {

    private final double phi = 0.083775804;
    private final double lambda = -1.295906970;
    private final double easting = 80859.033;
    private final double northing = 122543.174;

    private final Epsg1052 transform = new Epsg1052(Ellipsoid.ofInverseFlattening(6378137.0, 298.2572221),
            0.081689893, -1.294102154, 2550.000, 92334.879, 109320.965);

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
