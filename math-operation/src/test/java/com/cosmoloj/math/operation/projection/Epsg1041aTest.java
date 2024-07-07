package com.cosmoloj.math.operation.projection;

import com.cosmoloj.math.operation.surface.Ellipsoid;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Samuel Andrés
 */
public class Epsg1041aTest {

    private final double phi = 0.876312568;
    private final double lambda = 0.602425500;
    private final double easting = -568991.00;
    private final double northing = -1050538.64;

    private final Epsg1041a transform = new Epsg1041a(Ellipsoid.ofEccentricity(6377397.155, 0.081696831),
            0.863937979, 0.741764932, 0.528627763, 1.370083463, 0.9999, 0.00, 0.00);

    @Test
    public void forward() {
        final double coefU = transform.coefU(phi);
        final double coefV = transform.coefV(lambda);
        final double coefT = transform.coefT(coefU, coefV);

        Assertions.assertEquals(0.875596949, coefU, 1e-8);
        Assertions.assertEquals(0.139422687, coefV, 1e-9);
        Assertions.assertEquals(1.386275049, coefT, 1e-8);
        Assertions.assertEquals(0.506554623, transform.coefD(coefU, coefV, coefT), 1e-8);
        Assertions.assertEquals(0.496385389, transform.theta(coefU, coefV, coefT), 1e-8);
        Assertions.assertEquals(1194731.014, transform.r(coefU, coefV, coefT), 1e-1);

        Assertions.assertArrayEquals(new double[]{easting, northing},
                transform.compute(new double[]{phi, lambda}), 1e-1);
    }

    @Test
    public void inverse() {

        final double invXp = transform.invXp(-northing);
        final double invYp = transform.invYp(-easting);

        final double invD = transform.invD(invXp, invYp);
        final double invT = transform.invT(invXp, invYp);
        final double invU = transform.invU(invT, invD);

        Assertions.assertEquals(1050538.643, invXp, 1e-2);
        Assertions.assertEquals(568990.997, invYp, 1e-2);
        Assertions.assertEquals(1194731.014, transform.invR(invXp, invYp), 1e-2);
        Assertions.assertEquals(0.496385389, transform.invTheta(invXp, invYp), 1e-8);
        Assertions.assertEquals(0.506554623, invD, 1e-8);
        Assertions.assertEquals(1.386275049, invT, 1e-9);
        Assertions.assertEquals(0.875596949, invU, 1e-9);
        Assertions.assertEquals(0.139422687, transform.invV(invT, invD, invU), 1e-8);

        Assertions.assertArrayEquals(new double[]{phi, lambda},
                transform.inverse(new double[]{easting, northing}), 1e-8);
    }
}
