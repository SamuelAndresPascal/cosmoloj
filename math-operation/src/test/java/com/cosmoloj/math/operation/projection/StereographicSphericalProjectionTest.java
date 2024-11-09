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
public class StereographicSphericalProjectionTest {

    private static final double RADIUS = 1.;
    private static final double SCALE_FACTOR = 1.;
    private static final double DELTA = 5e-6;

    @Test
    @SectionReference(type = SectionReferenceType.TABLE, number = 24)
    @Page(159)
    public void test_equatorial() {

        final Projection projection = new StereographicSpherical(Spheroid.ofRadius(RADIUS), 0., 0., SCALE_FACTOR);

        equatorialTest(90.,
                new double[]{.00000, .00000, .00000, .00000, .00000, .00000, .00000, .00000, .00000, .00000},
                new double[]{2.00000, 2.00000, 2.00000, 2.00000, 2.00000, 2.00000, 2.00000, 2.00000, 2.00000, 2.00000},
                projection);
        equatorialTest(80.,
                new double[]{.00000, .05150, .10212, .15095, .19703, .23933, .27674, .30806, .33201, .34730},
                new double[]{1.67820, 1.68198, 1.69331, 1.71214, 1.73837, 1.77184, 1.81227, 1.85920, 1.91196, 1.96962},
                projection);
        equatorialTest(70.,
                new double[]{.00000, .08885, .17705, .26386, .34841, .42957, .50588, .57547, .63588, .68404},
                new double[]{1.40042, 1.40586, 1.42227, 1.44992, 1.48921, 1.54067, 1.60493, 1.68256, 1.77402, 1.87939},
                projection);
        equatorialTest(60.,
                new double[]{.00000, .11635, .23269, .34892, .46477, .57972, .69282, .80246, .90613, 1.00000},
                new double[]{1.15470, 1.16058, 1.17839, 1.20868, 1.25237, 1.31078, 1.38564, 1.47911, 1.59368, 1.73205},
                projection);
        equatorialTest(50.,
                new double[]{.00000, .13670, .27412, .41292, .55371, .69688, .84255, .99033, 1.13892, 1.28558},
                new double[]{.93262, .93819, .95515, .98421, 1.02659, 1.08415, 1.15945, 1.25597, 1.37825, 1.53209},
                projection);
        equatorialTest(40.,
                new double[]{.00000, .15164, .30468, .46053, .62062, .78641, .95937, 1.14080, 1.33167, 1.53209},
                new double[]{.72794, .73277, .74749, .77285, .81016, .86141, .92954, 1.01868, 1.13464, 1.28558},
                projection);
        equatorialTest(30.,
                new double[]{.00000, .16233, .32661, .49487, .66931, .85235, 1.04675, 1.25567, 1.48275, 1.73205},
                new double[]{.53590, .53970, .55133, .57143, .60117, .64240, .69783, .77149, .86928, 1.00000},
                projection);
        equatorialTest(20.,
                new double[]{.00000, .16950, .34136, .51808, .70241, .89755, 1.10732, 1.33650, 1.59119, 1.87939},
                new double[]{.35265, .35527, .36327, .37713, .39773, .42645, .46538, .51767, .58808, .68404},
                projection);
        equatorialTest(10.,
                new double[]{.00000, .17363, .34987, .53150, .72164, .92394, 1.14295, 1.38450, 1.65643, 1.96962},
                new double[]{.17498, .17631, .18037, .18744, .19796, .21267, .23271, .25979, .29658, .34730},
                projection);
        equatorialTest(0.,
                new double[]{.00000, .17498, .35265, .53590, .72794, .93262, 1.15470, 1.40042, 1.67820, 2.00000},
                new double[]{.00000, .00000, .00000, .00000, .00000, .00000, .00000, .00000, .00000, .00000},
                projection);
    }

    private void equatorialTest(
            final double latitude,
            final double[] expectedXTab,
            final double[] expectedYTab,
            final Projection projection) {

        for (int i = 0; i <= 9; i++) {
            Assertions.assertArrayEquals(new double[]{expectedXTab[i], expectedYTab[i]},
                    projection.compute(new double[]{Math.toRadians(latitude), Math.toRadians(i * 10.)}), DELTA);
        }
    }
}
