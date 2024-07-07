package com.cosmoloj.math.operation.projection;

import com.cosmoloj.math.operation.surface.Ellipsoid;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Samuel Andrés
 */
public class Epsg9819bTest {

    private final double a = 6377397.155;
    private final double e = 0.081696831;

    private final double phic = 0.863937979;
    private final double lambda0 = 0.741764932;
    private final double alphac = 0.528627763;
    private final double phip = 1.370083463;
    private final double kp = 0.9999;
    private final double ef = 0.00;
    private final double nf = 0.00;

    private final double phi = 0.876312568;
    private final double lambda = 0.602425500;
    private final double southing = 1050538.643;
    private final double westing = 568990.997;

    private final Epsg9819b transform = new Epsg9819b(Ellipsoid.ofEccentricity(a, e),
            phic, lambda0, alphac, phip, kp, ef, nf);

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

        Assertions.assertArrayEquals(new double[]{southing, westing},
                transform.compute(new double[]{phi, lambda}), 1e-1);
    }

    @Test
    public void inverse() {

        final double invXp = transform.invXp(southing);
        final double invYp = transform.invYp(westing);

        final double invD = transform.invD(invXp, invYp);
        final double invT = transform.invT(invXp, invYp);
        final double invU = transform.invU(invT, invD);

        Assertions.assertEquals(1050538.643, invXp, 1e-3);
        Assertions.assertEquals(568990.997, invYp, 1e-3);
        Assertions.assertEquals(1194731.014, transform.invR(invXp, invYp), 1e-3);
        Assertions.assertEquals(0.496385389, transform.invTheta(invXp, invYp), 1e-9);
        Assertions.assertEquals(0.506554623, invD, 1e-9);
        Assertions.assertEquals(1.386275049, invT, 1e-9);
        Assertions.assertEquals(0.875596949, invU, 1e-9);
        Assertions.assertEquals(0.139422687, transform.invV(invT, invD, invU), 1e-9);

        Assertions.assertArrayEquals(new double[]{phi, lambda},
                transform.inverse(new double[]{southing, westing}), 1e-8);
    }
}
