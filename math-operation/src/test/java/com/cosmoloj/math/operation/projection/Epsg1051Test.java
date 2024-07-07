package com.cosmoloj.math.operation.projection;

import com.cosmoloj.math.operation.surface.Ellipsoid;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Samuel Andrés
 */
public class Epsg1051Test {

    private final double phif = 0.756018454;
    private final double lambdaf = -1.471894336;
    private final double phi1 = 0.771144641;
    private final double phi2 = 0.797615468;
    private final double ef = 2000000.;
    private final double nf = 0.;
    private final double k = 1.0000382;

    private final double phi = 0.763581548;
    private final double lambda = -1.451532161;
    private final double easting = 2308335.75;
    private final double northing = 160210.48;

    private final Epsg1051 transform = new Epsg1051(Ellipsoid.ofEccentricity(20925832.164, 0.08227185),
            phif, lambdaf, phi1, phi2, ef, nf, k);

    @Test
    public void forward() {

        Assertions.assertEquals(0.718295175, transform.m(phi1), 1e-9);
        Assertions.assertEquals(0.699629151, transform.m(phi2), 1e-9);
        Assertions.assertEquals(0.429057680, transform.t(phi), 1e-9);
        Assertions.assertEquals(0.433541026, transform.t(phif), 1e-9);
        Assertions.assertEquals(0.424588396, transform.t(phi1), 1e-9);
        Assertions.assertEquals(0.409053868, transform.t(phi2), 1e-9);
        Assertions.assertEquals(0.706407410, transform.n(), 1e-9);
        Assertions.assertEquals(1.862317735, transform.f(), 1e-9);
        Assertions.assertEquals(21436775.51, transform.r(phi), 1e-2);
        Assertions.assertEquals(21594768.40, transform.r(phif), 1e-2);
        Assertions.assertEquals(0.014383991, transform.theta(lambda), 1e-9);
        Assertions.assertEquals(easting, transform.easting(phi, lambda), 1e-2);
        Assertions.assertEquals(northing, transform.northing(phi, lambda), 1e-1);

        Assertions.assertArrayEquals(new double[]{easting, northing},
                transform.compute(new double[]{phi, lambda}), 1e-1);
    }

    @Test
    public void inverse() {

        Assertions.assertEquals(0.014383991, transform.invTheta(easting, northing), 1e-9);
        Assertions.assertEquals(0.429057680, transform.invT(easting, northing), 1e-9);
        Assertions.assertEquals(21436775.51, transform.invR(easting, northing), 1e-2);
        Assertions.assertEquals(phi, transform.phi(easting, northing), 1e-9);
        Assertions.assertEquals(lambda, transform.lambda(easting, northing), 1e-9);

        Assertions.assertArrayEquals(new double[]{phi, lambda},
                transform.inverse(new double[]{easting, northing}), 1e-9);
    }
}
