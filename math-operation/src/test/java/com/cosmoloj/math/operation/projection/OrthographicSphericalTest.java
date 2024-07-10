package com.cosmoloj.math.operation.projection;

import com.cosmoloj.bibliography.cosmoloj.Cosmoloj;
import com.cosmoloj.math.operation.surface.Spheroid;
import com.cosmoloj.util.bib.Page;
import com.cosmoloj.util.bib.Pages;
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
public class OrthographicSphericalTest {

    private static final double RADIUS = 1.;
    private static final double DELTA = 5e-5;

    @Test
    public void test_1() {

        final Projection projection = new OrthographicSpherical(Spheroid.ofRadius(RADIUS), Math.PI / 2., 0.);

        Assertions.assertArrayEquals(new double[]{0., 0.},
                projection.compute(new double[]{Math.PI / 2, 0.}), Double.MIN_VALUE);

        // Nord
        Assertions.assertArrayEquals(new double[]{0.5, -0.5},
                projection.compute(new double[]{Math.PI / 4, Math.PI / 4}), 1e-10);
        Assertions.assertArrayEquals(new double[]{-0.5, -0.5},
                projection.compute(new double[]{Math.PI / 4, -Math.PI / 4}), 1e-10);
        Assertions.assertArrayEquals(new double[]{0.5, 0.5},
                projection.compute(new double[]{Math.PI / 4, 3 * Math.PI / 4}), 1e-10);
        Assertions.assertArrayEquals(new double[]{-0.5, 0.5},
                projection.compute(new double[]{Math.PI / 4, -3 * Math.PI / 4}), 1e-10);

        // Sud
        Assertions.assertArrayEquals(new double[]{-0.5, -0.5},
                projection.compute(new double[]{-Math.PI / 4, -Math.PI / 4}), 1e-10);
        Assertions.assertArrayEquals(new double[]{0.5, -0.5},
                projection.compute(new double[]{-Math.PI / 4, Math.PI / 4}), 1e-10);

        Assertions.assertArrayEquals(new double[]{Math.cos(Math.PI / 4), 0.},
        projection.compute(new double[]{Math.PI / 4, Math.PI / 2}), 1e-30);
    }

    @Test
    public void test_2() {

        final Projection projection = new OrthographicSpherical(Spheroid.ofRadius(RADIUS), Math.PI / 4., 0.);

        Assertions.assertArrayEquals(new double[]{0., 0.},
                projection.compute(new double[]{Math.PI / 4, 0.}), Double.MIN_VALUE);

        Assertions.assertArrayEquals(new double[]{0., -1.},
                projection.compute(new double[]{-Math.PI / 4., 0.}), Double.MIN_VALUE);
        Assertions.assertArrayEquals(new double[]{0., -1.},
                projection.compute(new double[]{-10. * Math.PI / 40., 0.}), Double.MIN_VALUE);
        Assertions.assertArrayEquals(new double[]{0., -0.9969173337331281},
                projection.compute(new double[]{-11. * Math.PI / 40., 0.}), Double.MIN_VALUE);
        Assertions.assertArrayEquals(new double[]{0., -0.9876883405951378},
                projection.compute(new double[]{-12. * Math.PI / 40., 0.}), Double.MIN_VALUE);
        Assertions.assertArrayEquals(new double[]{0., -0.9723699203976766},
                projection.compute(new double[]{-13. * Math.PI / 40., 0.}), Double.MIN_VALUE);
        Assertions.assertArrayEquals(new double[]{0., -0.9510565162951534},
                projection.compute(new double[]{-14. * Math.PI / 40., 0.}), Double.MIN_VALUE);
        Assertions.assertArrayEquals(new double[]{0., -0.9238795325112867},
                projection.compute(new double[]{-15. * Math.PI / 40., 0.}), Double.MIN_VALUE);
        Assertions.assertArrayEquals(new double[]{0., -0.8910065241883679},
                projection.compute(new double[]{-16. * Math.PI / 40., 0.}), Double.MIN_VALUE);
        Assertions.assertArrayEquals(new double[]{0., -0.8526401643540923},
                projection.compute(new double[]{-17. * Math.PI / 40., 0.}), Double.MIN_VALUE);
        Assertions.assertArrayEquals(new double[]{0., -0.8090169943749476},
                projection.compute(new double[]{-18. * Math.PI / 40., 0.}), Double.MIN_VALUE);
        Assertions.assertArrayEquals(new double[]{0., -0.760405965600031},
                projection.compute(new double[]{-19. * Math.PI / 40., 0.}), Double.MIN_VALUE);
        Assertions.assertArrayEquals(new double[]{0., -0.7071067811865476},
                projection.compute(new double[]{-Math.PI / 2., 0.}), Double.MIN_VALUE);
        Assertions.assertArrayEquals(new double[]{0., 0.},
                projection.compute(new double[]{-3 * Math.PI / 4., 0.}), 1e-15);

    }

    @Test
    @SectionReference(type = SectionReferenceType.TABLE, number = 22)
    @Page(151)
    public void test_equatorial() {

        final Projection projection = new OrthographicSpherical(Spheroid.ofRadius(RADIUS), 0., 0.);

        for (int i = 0; i < EQUATORIAL.length; i++) {
            final double[][] line = EQUATORIAL[i];
            for (int j = 0; j < line[1].length; j++) {
            Assertions.assertArrayEquals(new double[]{line[1][j], line[0][0]},
                    projection.compute(new double[]{
                        Math.toRadians((EQUATORIAL.length - 1 - i) * 10.),
                        Math.toRadians(j * 10.)}),
                    DELTA);
            }
        }
    }

    private static final double[][][] EQUATORIAL = new double[][][]{
        {{1.}, {.0000, .0000, .0000, .0000, .0000, .0000, .0000, .0000, .0000, .0000}},
        {{.9848}, {.0000, .0302, .0594, .0868, .1116, .1330, .1504, .1632, .1710, .1736}},
        {{.9397}, {.0000, .0594, .1170, .1710, .2198, .2620, .2962, .3214, .3368, .3420}},
        {{.8660}, {.0000, .0868, .1710, .2500, .3214, .3830, .4330, .4698, .4924, .5000}},
        {{.7660}, {.0000, .1116, .2198, .3214, .4132, .4924, .5567, .6040, .6330, .6428}},
        {{.6428}, {.0000, .1330, .2620, .3830, .4924, .5868, .6634, .7198, .7544, .7660}},
        {{.5000}, {.0000, .1504, .2962, .4330, .5567, .6634, .7500, .8138, .8529, .8660}},
        {{.3420}, {.0000, .1632, .3214, .4698, .6040, .7198, .8138, .8830, .9254, .9397}},
        {{.1736}, {.0000, .1710, .3368, .4924, .6330, .7544, .8529, .9254, .9698, .9848}},
        {{.0000}, {.0000, .1736, .3420, .5000, .6428, .7660, .8660, .9397, .9848, 1.}}
    };

    @Test
    @SectionReference(type = SectionReferenceType.TABLE, number = 23)
    @Pages(from = 152, to = 153)
    public void test_oblique() {

        final Projection projection = new OrthographicSpherical(Spheroid.ofRadius(RADIUS), Math.toRadians(40.), 0.);

        for (int i = 0; i < OBLIQUE.length; i++) {
            final double[] expectedXTab = OBLIQUE[i][0];
            final double[] expectedYTab = OBLIQUE[i][1];

            Assertions.assertEquals(expectedYTab.length, expectedXTab.length);
            for (int j = 0; j < expectedXTab.length; j++) {
                Assertions.assertArrayEquals(new double[]{expectedXTab[j], expectedYTab[j]},
                        projection.compute(new double[]{
                            Math.toRadians((OBLIQUE.length - 1 - 5 - i) * 10.),
                            Math.toRadians(j * 10.)}),
                        DELTA);
            }
        }
    }

    private static final double[][][] OBLIQUE = new double[][][]{
        {{.0000, .0000, .0000, .0000, .0000, .0000, .0000, .0000, .0000, .0000,
            .0000, .0000, .0000, .0000, .0000, .0000, .0000, .0000, .0000},
            {.7660, .7660, .7660, .7660, .7660, .7660, .7660, .7660, .7660, .7660,
                .7660, .7660, .7660, .7660, .7660, .7660, .7660, .7660, .7660}},
        {{.0000, .0302, .0594, .0868, .1116, .1330, .1504, .1632, .1710, .1736,
            .1710, .1632, .1504, .1330, .1116, .0868, .0594, .0302, .0000},
            {.6428, .6445, .6495, .6577, .6689, .6827, .6986, .7162, .7350, .7544,
                .7738, .7926, .8102, .8262, .8399, .8511, .8593, .8643, .8660}},
        {{.0000, .0594, .1170, .1710, .2198, .2620, .2962, .3214, .3368, .3420, .3368,
            .3214, .2962, .2620, .2198, .1710, .1170, .0594, .0000},
            {.5000, .5033, .5133, .5295, .5514, .5785, .6099, .6447, .6817, .7198,
                .7580, .7950, .8298, .8612, .8883, .9102, .9264, .9364, .9397}},
        {{.0000, .0868, .1710, .2500, .3214, .3830, .4330, .4698, .4924, .5000,
            .4924, .4698, .4330, .3830, .3214, .2500, .1710, .0868, .0000},
            {.3420, .3469, .3614, .3851, .4172, .4568, .5027, .5535, .6076, .6634,
                .7192, .7733, .8241, .8700, .9096, .9417, .9654, .9799, .9848}},
        {{.0000, .1116, .2198, .3214, .4132, .4924, .5567, .6040, .6330, .6428,
            .6330, .6040, .5567, .4924, .4132, .3214, .2198, .1116, .0000},
            {.1736, .1799, .1986, .2290, .2703, .3212, .3802, .4455, .5151, .5868,
                .6586, .7281, .7934, .8524, .9033, .9446, .9751, .9937, 1.}},
        {{.0000, .1330, .2620, .3830, .4924, .5868, .6634, .7198, .7544, .7660, .7544, .7198, .6634, .5868},
            {.0000, .0075, .0297, .0660, .1152, .1759, .2462, .3240, .4069, .4924, .5779, .6608, .7386, .8089}},
        {{.0000, .1504, .2962, .4330, .5567, .6634, .7500, .8138, .8529, .8660, .8529, .8138},
            {-.1736, -.1652, -.1401, -.0991, -.0434, .0252, .1047, .1926, .2864, .3830, .4797, .5734}},
        {{.0000, .1632, .3214, .4698, .6040, .7198, .8138, .8830, .9254, .9397, .9254},
            {-.3420, -.3328, -.3056, -.2611, -.2007, -.1263, -.0400, .0554, .1571, .2620, .3669}},
        {{.0000, .1710, .3368, .4924, .6330, .7544, .8529, .9254, .9698, .9848},
            {-.5000, -.4904, -.4618, -.4152, -.3519, -.2739, -.1835, -.0835, .0231, .1330}},
        {{.0000, .1736, .3420, .5000, .6428, .7660, .8660, .9397, .9848, 1.},
            {-.6428, -.6330, -.6040, -.5567, -.4924, -.4132, -.3214, -.2198, -.1116, .0000}},
        {{.0000, .1710, .3368, .4924, .6330, .7544, .8529, .9254, .9698},
            {-.7660, -.7564, -.7279, -.6812, -.6179, -.5399, -.4495, -.3495, -.2429}},
        {{.0000, .1632, .3214, .4698, .6040, .7198, .8138, .8830},
            {-.8660, -.8568, -.8296, -.7851, -.7247, -.6503, -.5640, -.4686}},
        {{.0000, .1504, .2962, .4330, .5567, .6634, .7500},
            {-.9397, -.9312, -.9061, -.8651, -.8095, -.7408, -.6614}},
        {{.0000, .1330, .2620, .3830, .4924},
            {-.9848, -.9773, -.9551, -.9188, -.8696}},
        {{.0000},
            {-1.}}};

    @Test
    public void inverse() {

        final InvertibleProjection projection = new OrthographicSpherical(Spheroid.ofRadius(1.), Math.PI / 2., 0.);
        for (final double[][] line : INVERSE) {
            Assertions.assertArrayEquals(line[0], projection.inverse(line[1]), 1e-10);
        }
    }

    private static final double[][][] INVERSE = new double[][][]{
        // north
        {{Math.PI / 4., Math.PI / 4.}, {0.5, -0.5}},
        {{Math.PI / 4., 3. * Math.PI / 4.}, {0.5, 0.5}},

        // south
        {{Math.PI / 4., -Math.PI / 4.}, {-0.5, -0.5}},
        {{Math.PI / 4., -3. * Math.PI / 4.}, {-0.5, 0.5}}
    };
}
