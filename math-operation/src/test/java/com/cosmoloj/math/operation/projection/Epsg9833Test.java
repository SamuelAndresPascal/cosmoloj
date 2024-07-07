package com.cosmoloj.math.operation.projection;

import com.cosmoloj.math.operation.surface.Ellipsoid;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Samuel Andrés
 */
public class Epsg9833Test {

    private final double phi0 = -0.283616003;

    private final double phi = -0.293938867;
    private final double lambda = 3.141493807;
    private final double easting = 16015.2890;
    private final double northing = 13369.6601;

    private final Epsg9833 transform = new Epsg9833(Ellipsoid.ofSquareEccentricity(317063.667, 0.006803481),
            phi0, 3.129957125, 12513.318, 16628.885);

    @Test
    public void forward() {

        Assertions.assertEquals(0.011041875, transform.coefA(phi, lambda), 1e-9);
        Assertions.assertEquals(0.091631819, transform.coefT(phi), 1e-9);
        Assertions.assertEquals(0.006275088, transform.coefC(phi), 1e-9);
        Assertions.assertEquals(-92590.02, transform.getSurface().m(phi), 1e-2);
        Assertions.assertEquals(317154.24, transform.getSurface().nu(phi), 1e-2);
        Assertions.assertEquals(-89336.59, transform.getSurface().m(phi0), 1e-2);
        Assertions.assertEquals(315176.48, transform.getSurface().rho(phi), 1e-2);
        Assertions.assertEquals(-3259.28, transform.x(phi, lambda), 1e-2);

        Assertions.assertArrayEquals(new double[]{easting, northing},
                transform.compute(new double[]{phi, lambda}), 1e-3);
    }

    @Test
    public void inverse() {

        final double phi1p = transform.invPhi1p(northing);
        final double invM1 = transform.invM1(northing);
        final double phi1 = transform.getSurface().phi1(transform.getSurface().rectifyingLatitude(invM1));
        final double nu1p = transform.getSurface().nu(phi1p);
        final double rho1p = transform.getSurface().rho(phi1p);
        //Assertions.assertEquals(0.293952249, phi1p, 1e-9);
        Assertions.assertEquals(317154.25, nu1p, 1e-2);
        Assertions.assertEquals(315176.50, rho1p, 1e-2);
        Assertions.assertEquals(-0.058, transform.invQp(northing, nu1p, rho1p), 1e-3);
        Assertions.assertEquals(-0.058, transform.invQ(northing), 1e-3);
        Assertions.assertEquals(0.091644092, transform.coefT(phi1), 1e-9);

        Assertions.assertEquals(-92595.87, invM1, 1e-2);
        Assertions.assertEquals(317154.25, transform.getSurface().nu(phi1), 1e-2);
        Assertions.assertEquals(315176.51, transform.getSurface().rho(phi1), 1e-2);
        Assertions.assertEquals(-0.292540098, transform.getSurface().rectifyingLatitude(invM1), 1e-9);
        Assertions.assertEquals(-0.293957437, phi1, 1e-9);

        Assertions.assertArrayEquals(new double[]{phi, lambda},
                transform.inverse(new double[]{easting, northing}), 1e-9);
    }
}
