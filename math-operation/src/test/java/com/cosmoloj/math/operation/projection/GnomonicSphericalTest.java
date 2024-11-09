package com.cosmoloj.math.operation.projection;

import com.cosmoloj.bibliography.cosmoloj.Cosmoloj;
import com.cosmoloj.math.operation.surface.Spheroid;
import com.cosmoloj.util.bib.Page;
import com.cosmoloj.util.bib.Cite;
import com.cosmoloj.util.bib.SectionReference;
import com.cosmoloj.util.bib.SectionReferenceType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Samuel Andrés
 */
@Cite(Cosmoloj.MAP_PROJECTIONS)
public class GnomonicSphericalTest {

    private final Projection projection = new GnomonicSpherical(Spheroid.ofRadius(1.), 0., 0.);

    private static final double[] X = new double[]{
        0.0000, 0.1763, 0.3640, 0.5774, 0.8391, 1.1918, 1.7321, 2.7475, 5.6713};
    private static final double[][] Y = new double[][]{
        {5.6713, 5.7588, 6.0353, 6.5486, 7.4033, 8.8229, 11.3426, 16.5817, 32.6596},
        {2.7475, 2.7899, 2.9238, 3.1725, 3.5866, 4.2743, 5.4950, 8.0331, 15.8221},
        {1.7321, 1.7588, 1.8432, 2.0000, 2.2610, 2.6946, 3.4641, 5.0642, 9.9745},
        {1.1918, 1.2101, 1.2682, 1.3761, 1.5557, 1.8540, 2.3835, 3.4845, 6.8630},
        {.8391, .8520, .8930, .9689, 1.0954, 1.3054, 1.6782, 2.4534, 4.8322},
        {.5774, .5863, .6144, .6667, .7537, .8982, 1.1547, 1.6881, 3.3248},
        {.3640, .3696, .3873, .4203, .4751, .5662, .7279, 1.0642, 2.0960},
        {.1763, .1790, .1876, .2036, .2302, .2743, .3527, .5155, 1.0154},
        {.0000, .0000, .0000, .0000, .0000, .0000, .0000, .0000, .0000}};

    @Test
    @SectionReference(type = SectionReferenceType.TABLE, number = 26)
    @Page(168)
    public void test_equatorial() {
        for (int i = 0; i < Y.length; i++) {
            for (int j = 0; j <= 8; j++) {
                Assertions.assertArrayEquals(new double[]{X[j], Y[i][j]},
                        projection.compute(new double[]{Math.toRadians(80. - i * 10), Math.toRadians(j * 10.)}), 5e-5);
            }
        }
    }
}
