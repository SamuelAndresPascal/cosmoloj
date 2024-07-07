package com.cosmoloj.math.operation.projection;

import com.cosmoloj.math.operation.surface.Ellipsoid;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Samuel Andrés
 */
public class Epsg9815Test {

    private final double a = 6377298.556;
    private final double invf = 300.8017;

    private final double phic = 0.069813170;
    private final double lambdac = 2.007128640;
    private final double alphac = 0.930536611;
    private final double gammac = 0.927295218;
    private final double ec = 590476.87;
    private final double nc = 442857.65;
    private final double kc = 0.99984;

    private final double phi = 0.094025313;
    private final double lambda = 2.021187362;
    private final double easting = 679245.73;
    private final double northing = 596562.78;

    private final Epsg9815 transform = new Epsg9815(Ellipsoid.ofInverseFlattening(a, invf),
            phic, lambdac, alphac, gammac, kc, ec, nc);

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
