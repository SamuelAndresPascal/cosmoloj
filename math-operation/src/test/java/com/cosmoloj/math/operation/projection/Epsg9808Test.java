package com.cosmoloj.math.operation.projection;

import com.cosmoloj.math.operation.surface.Ellipsoid;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Samuel Andrés
 */
public class Epsg9808Test {

    private final double a = 6378137.;
    private final double invf = 298.25722356;

    private final double phi0 = 0.0;
    private final double lambda0 = 0.506145483;
    private final double fe = 0.00;
    private final double fn = 0.00;
    private final double k0 = 1.0;

    private final double phi = -0.449108618;
    private final double lambda = 0.493625066;
    private final double westing = 71984.49;
    private final double southing = 2847342.74;

    private final Epsg9808 transform = new Epsg9808(Ellipsoid.ofInverseFlattening(a, invf), phi0, lambda0, k0, fe, fn);

    @Test
    public void forward() {

        Assertions.assertArrayEquals(new double[]{westing, southing},
                transform.compute(new double[]{phi, lambda}), 1e-2);
    }

    @Test
    public void inverse() {

        Assertions.assertArrayEquals(new double[]{phi, lambda},
                transform.inverse(new double[]{westing, southing}), 1e-9);
    }
}
