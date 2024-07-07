package com.cosmoloj.math.operation.projection;

import com.cosmoloj.math.operation.surface.Ellipsoid;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Samuel Andrés
 */
public class Epsg9830Test {

    private final double a = 6378388.0;
    private final double invf = 297.0;

    private final double phif = -1.169370599;
    private final double lambda0 = 2.443460953;
    private final double ef = 300000.00;
    private final double nf = 200000.00;

    private final double phi = -1.162480524;
    private final double lambda = 2.444707118;
    private final double easting = 303169.52;
    private final double northing = 244055.72;

    private final Epsg9830 transform = new Epsg9830(Ellipsoid.ofInverseFlattening(a, invf), phif, lambda0, ef, nf);

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
