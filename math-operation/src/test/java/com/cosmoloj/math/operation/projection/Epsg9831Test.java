package com.cosmoloj.math.operation.projection;

import com.cosmoloj.math.operation.surface.Ellipsoid;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Samuel Andrés
 */
public class Epsg9831Test {

    private final double phi0 = 0.235138896;
    private final double lambda0 = 2.526342288;
    private final double ef = 50000.00;
    private final double nf = 50000.00;

    private final double phi = 0.232810140;
    private final double lambda = 2.524362746;
    private final double easting = 37712.48;
    private final double northing = 35242.00;

    private final Epsg9831 transform = new Epsg9831(Ellipsoid.CLARKE_1866, phi0, lambda0, ef, nf);

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
