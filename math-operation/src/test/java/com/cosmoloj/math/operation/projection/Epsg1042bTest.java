package com.cosmoloj.math.operation.projection;

import com.cosmoloj.math.operation.surface.Ellipsoid;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Samuel Andrés
 */
public class Epsg1042bTest {

    private final double c1 = 2.946529277e-2;
    private final double c2 = 2.515965696e-2;
    private final double c3 = 1.193845912e-7;
    private final double c4 = -4.668270147e-7;
    private final double c5 = 9.233980362e-12;
    private final double c6 = 1.523735715e-12;
    private final double c7 = 1.696780024e-18;
    private final double c8 = 4.408314235e-18;
    private final double c9 = -8.331083518e-24;
    private final double c10 = -3.689471323e-24;

    private final double phi = 0.876312568;
    private final double lambda = 0.602425500;
    private final double southing = 6050538.71;
    private final double westing = 5568990.91;

    private final Epsg1042b transform = new Epsg1042b(Ellipsoid.ofEccentricity(6377397.155, 0.081696831),
            0.863937979, 0.741764932, 0.528627763, 1.370083463, 0.9999, 5000000.00, 5000000.00, 1089000.00, 654000.00,
            c1, c2, c3, c4, c5, c6, c7, c8, c9, c10);

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

        final double invXp = transform.invXp(new double[]{southing, westing});
        final double invYp = transform.invYp(new double[]{southing, westing});

        final double invD = transform.invD(invXp, invYp);
        final double invT = transform.invT(invXp, invYp);
        final double invU = transform.invU(invT, invD);

        Assertions.assertEquals(1050538.631, invXp, 1e-2);
        Assertions.assertEquals(568990.995, invYp, 1e-2);
        Assertions.assertEquals(1194731.002, transform.invR(invXp, invYp), 1e-2);
        Assertions.assertEquals(0.496385393, transform.invTheta(invXp, invYp), 1e-8);
        Assertions.assertEquals(0.506554627, invD, 1e-8);
        Assertions.assertEquals(1.386275051, invT, 1e-9);
        Assertions.assertEquals(0.875596951, invU, 1e-9);
        Assertions.assertEquals(0.139422687, transform.invV(invT, invD, invU), 1e-9);

        Assertions.assertArrayEquals(new double[]{phi, lambda},
                transform.inverse(new double[]{southing, westing}), 1e-9);
    }
}
