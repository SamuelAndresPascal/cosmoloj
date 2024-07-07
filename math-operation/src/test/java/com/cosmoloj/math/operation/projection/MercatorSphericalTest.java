package com.cosmoloj.math.operation.projection;

import com.cosmoloj.bibliography.cosmoloj.Cosmoloj;
import com.cosmoloj.math.operation.surface.Spheroid;
import com.cosmoloj.util.bib.Page;
import com.cosmoloj.util.bib.Reference;
import com.cosmoloj.util.bib.SectionReference;
import com.cosmoloj.util.bib.SectionReferenceType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Samuel Andrés
 */
@Reference(Cosmoloj.MAP_PROJECTIONS)
public class MercatorSphericalTest {

    private static final double RADIUS = 1.;
    private static final double DELTA = 5e-5;
    private final double[] expectedYTab = new double[]{0.0000, 0.08738, 0.17543, 0.26484, 0.35638, 0.45088, 0.54931,
        0.65284, 0.76291, 0.88137, 1.01068, 1.15423, 1.31696, 1.50645, 1.73542, 2.02759, 2.43625, 3.13130};

    @Test
    @SectionReference(type = SectionReferenceType.TABLE, number = 7)
    @Page(45)
    public void test_equatorial() {

        final Projection projection = new MercatorSpherical(Spheroid.ofRadius(RADIUS), 0., 0.);

        for (int i = 0; i < 18; i++) {
            final double lat = 5 * i;
            final double lon = 5 * i * 2;
            final double[] proj = projection.compute(new double[]{Math.toRadians(lat), Math.toRadians(lon)});
            Assertions.assertEquals(expectedYTab[i], proj[1], DELTA, "loop nb: " + i);
            Assertions.assertEquals(lon * 0.017453, proj[0], DELTA, "loop nb: " + i);
        }
//equatorialTest(80.,
//new double[]{5.6713, 5.7588, 6.0353, 6.5486, 7.4033, 8.8229, 11.3426, 16.5817, 32.6596}, projection);
//equatorialTest(70.,
//new double[]{2.7475, 2.7899, 2.9238, 3.1725, 3.5866, 4.2743, 5.4950, 8.0331, 15.8221}, projection);
//equatorialTest(60., new double[]{1.7321, 1.7588, 1.8432, 2.0000, 2.2610, 2.6946, 3.4641, 5.0642, 9.9745}, projection);
//equatorialTest(50., new double[]{1.1918, 1.2101, 1.2682, 1.3761, 1.5557, 1.8540, 2.3835, 3.4845, 6.8630}, projection);
//equatorialTest(40., new double[]{.8391, .8520, .8930, .9689, 1.0954, 1.3054, 1.6782, 2.4534, 4.8322}, projection);
//equatorialTest(30., new double[]{.5774, .5863, .6144, .6667, .7537, .8982, 1.1547, 1.6881, 3.3248}, projection);
//equatorialTest(20., new double[]{.3640, .3696, .3873, .4203, .4751, .5662, .7279, 1.0642, 2.0960}, projection);
//equatorialTest(10., new double[]{.1763, .1790, .1876, .2036, .2302, .2743, .3527, .5155, 1.0154}, projection);
//equatorialTest(0., new double[]{.0000, .0000, .0000, .0000, .0000, .0000, .0000, .0000, .0000}, projection);
    }

    private void equatorialTest(final double latitude, final double[] expYTab, final Projection projection) {

//        for (int i = 0; i < =8; i++) {
//            Assertions.assertArrayEquals(new double[]{this.expYTab[i], expYTab[i]},
//projection.project(toRadians(latitude), toRadians(i*10.)), DELTA);
//        }
    }
}
