package com.cosmoloj.math.operation.projection;

import com.cosmoloj.math.operation.surface.Ellipsoid;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Samuel Andrés
 */
public class Epsg9832Test {

    private final double phi0 = 0.166621493;
    private final double lambda0 = 2.411499514;
    private final double ef = 40000.00;
    private final double nf = 60000.00;

    private final double phi = 0.167490973;
    private final double lambda = 2.411923377;
    private final double easting = 42665.90;
    private final double northing = 65509.82;

    private final Epsg9832 transform = new Epsg9832(Ellipsoid.CLARKE_1866, phi0, lambda0, ef, nf);

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
