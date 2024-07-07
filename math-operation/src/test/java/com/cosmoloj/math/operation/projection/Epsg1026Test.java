package com.cosmoloj.math.operation.projection;

import com.cosmoloj.math.operation.surface.Spheroid;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Samuel Andrés
 */
public class Epsg1026Test {

    private final double phi = 0.425542460;
    private final double lambda = -1.751147016;
    private final double easting = -11156569.90;
    private final double northing = 2796869.94;

    private final Epsg1026 transform = new Epsg1026(Spheroid.ofRadius(6371007.0), 0.0, 0.0, 0.00, 0.00);

    @Test
    public void forward() {

        Assertions.assertArrayEquals(new double[]{easting, northing},
                transform.compute(new double[]{phi, lambda}), 1e-1);
    }

    @Test
    public void inverse() {

        Assertions.assertArrayEquals(new double[]{phi, lambda},
                transform.inverse(new double[]{easting, northing}), 1e-8);
    }
}
