package com.cosmoloj.math.operation.projection;

import com.cosmoloj.math.operation.surface.Ellipsoid;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Samuel Andrés
 */
public class Epsg1044Test {

    private final double phi = 0.9250245;
    private final double lambda = 0.9250245;
    private final double easting = 165704.29;
    private final double northing = 1351950.22;

    private final Epsg1044 transform = new Epsg1044(Ellipsoid.ofEccentricity(6378245.0, 0.08181333), 0.73303829,
            0.89011792, 0.73303829, 0.00, 0.00);

    @Test
    public void forward() {

        Assertions.assertEquals(0.744260894, transform.k0(), 1e-8);

        Assertions.assertArrayEquals(new double[]{easting, northing},
                transform.compute(new double[]{phi, lambda}), 1e-1);
    }

    @Test
    public void inverse() {

        Assertions.assertArrayEquals(new double[]{phi, lambda},
                transform.inverse(new double[]{easting, northing}), 1e-8);
    }
}
