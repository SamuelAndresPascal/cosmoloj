package com.cosmoloj.math.operation.projection;

import com.cosmoloj.math.operation.surface.Ellipsoid;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Samuel Andrés
 */
public class Epsg9807jhsTest {

    private final double a = 6377563.396;
    private final double invf = 299.32496;

    private final double phi0 = 0.85521133;
    private final double lambda0 = -0.03490659;
    private final double fe = 400000.00;
    private final double fn = -100000.00;
    private final double k0 = 0.9996012717;

    private final double phi = 0.88139127;
    private final double lambda = 0.00872665;
    private final double easting = 577274.99;
    private final double northing = 69740.50;

    private final Epsg9807jhs transform
            = new Epsg9807jhs(Ellipsoid.ofInverseFlattening(a, invf), phi0, lambda0, k0, fe, fn);

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
