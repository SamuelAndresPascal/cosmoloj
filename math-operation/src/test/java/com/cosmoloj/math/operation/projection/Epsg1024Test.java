package com.cosmoloj.math.operation.projection;

import com.cosmoloj.math.operation.surface.Ellipsoid;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Samuel Andrés
 */
public class Epsg1024Test {

    private final double phi = 0.425542460;
    private final double lambda = -1.751147016;
    private final double easting = -11169055.58;
    private final double northing = 2800000.00;

    private final Epsg1024 transform = new Epsg1024(Ellipsoid.ofEccentricity(6378137.0, 0.), 0.0, 0.00, 0.00);

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
