package com.cosmoloj.math.operation.projection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Samuel Andrés
 */
public class ProjectionUtilTest {

    @Test
    public void shift_test() {


        // (Math.PI / 2., 0.) | (0., 0.) => (Math.PI / 2., 0.)
        Assertions.assertArrayEquals(
                new double[]{Math.PI / 2., 0.}, MapProjections.shift(Math.PI / 2., 0., 0., 0.), Double.MIN_VALUE);

        // (Math.PI / 4., 0.) | (0., 0.) => (Math.PI / 4., 0.)
        Assertions.assertArrayEquals(
                new double[]{Math.PI / 4., 0.}, MapProjections.shift(Math.PI / 4., 0., 0., 0.), 1e-15);

        // (Math.PI / 4., 0.) | (Math.PI / 4., 0.) => (Math.PI / 2., 0.)
        Assertions.assertArrayEquals(
                new double[]{Math.PI / 4., 0.}, MapProjections.shift(Math.PI / 4., 0., Math.PI / 2., 0.), 1e-15);

        // (Math.PI / 4., 0.) | (3*Math.PI / 4., 0.) => (0., 0.)
        Assertions.assertArrayEquals(
                new double[]{0., 0.}, MapProjections.shift(Math.PI / 4., 0., 3 * Math.PI / 4., 0.), 1e-15);

        // (Math.PI / 4., 0.) | (-Math.PI / 2., 0.) => (-Math.PI / 4., 0.)
        Assertions.assertArrayEquals(new double[]{-Math.PI / 4., 0.},
                MapProjections.shift(Math.PI / 4., 0., -Math.PI / 2., 0.), 1e-15);


        // (Math.PI / 2., Math.PI / 2.) | (0., 0.) => (Math.PI / 2., Math.PI / 2.)
        Assertions.assertArrayEquals(
                new double[]{Math.PI / 2., Math.PI / 2.},
                MapProjections.shift(Math.PI / 2., Math.PI / 2., 0., 0.), Double.MIN_VALUE);

        // (Math.PI / 4., Math.PI / 2.) | (0., 0.) => (Math.PI / 4., Math.PI / 2.)
        Assertions.assertArrayEquals(
                new double[]{Math.PI / 4., Math.PI / 2.},
                MapProjections.shift(Math.PI / 4., Math.PI / 2., 0., 0.), 1e-15);

        // (Math.PI / 4., Math.PI / 2.) | (Math.PI / 4., 0.) => (Math.PI / 2., Math.PI / 2.)
        Assertions.assertArrayEquals(
                new double[]{Math.PI / 4., Math.PI / 2.},
                MapProjections.shift(Math.PI / 4., Math.PI / 2., Math.PI / 2., 0.), 1e-15);

        // (Math.PI / 4., Math.PI / 2.) | (3*Math.PI / 4., 0.) => (0., Math.PI / 2.)
        Assertions.assertArrayEquals(
                new double[]{0., Math.PI / 2.},
                MapProjections.shift(Math.PI / 4., Math.PI / 2., 3. * Math.PI / 4., 0.), 1e-15);

        // (Math.PI / 4., Math.PI / 2.) | (-Math.PI / 2., 0.) => (-Math.PI / 4., Math.PI / 2.)
        Assertions.assertArrayEquals(
                new double[]{-Math.PI / 4., Math.PI / 2.},
                MapProjections.shift(Math.PI / 4., Math.PI / 2., -Math.PI / 2., 0.), 1e-15);


        // (Math.PI / 2., 0.) | (0., Math.PI / 2.) => (Math.PI / 2., 0.)
        Assertions.assertArrayEquals(
                new double[]{Math.PI / 2., 0.},
                MapProjections.shift(Math.PI / 2., 0., 0., Math.PI / 2.), Double.MIN_VALUE);

        // (Math.PI / 4., 0.) | (0., Math.PI / 2.) => (Math.PI / 4., 0.)
        Assertions.assertArrayEquals(
                new double[]{Math.PI / 4., 0.}, MapProjections.shift(Math.PI / 4., 0., 0., Math.PI / 2.), 1e-15);

        // (Math.PI / 4., 0.) | (Math.PI / 4., Math.PI / 2.) => (0., Math.PI / 2.)
        Assertions.assertArrayEquals(
                new double[]{0., Math.PI / 2.},
                MapProjections.shift(Math.PI / 4., 0., Math.PI / 2., Math.PI / 2.), 1e-15);

        // (Math.PI / 4., 0.) | (3*Math.PI / 4., Math.PI / 2.) => (-Math.PI/6, -0.9553166181245093)
        Assertions.assertArrayEquals(
                new double[]{-Math.PI / 6, -Math.atan(Math.sqrt(2))},
                MapProjections.shift(Math.PI / 4, 0., 3 * Math.PI / 4, Math.PI / 2), 1e-15);

        // (Math.PI / 4., 0.) | (-Math.PI / 2., Math.PI / 2.) => (0., -Math.PI / 2..)
        Assertions.assertArrayEquals(
                new double[]{0., -Math.PI / 2},
                MapProjections.shift(Math.PI / 4, 0., -Math.PI / 2, Math.PI / 2), 1e-15);


        final double[] shift = MapProjections.shift(Math.PI / 2, 0, 0, 0);

    }


    @Test
    public void test_2() {

        //final Projection projection = new OrthographicSphericalProjection(RADIUS, Math.PI / 4., 0);

        Assertions.assertArrayEquals(new double[]{Math.PI / 2., 0.},
                MapProjections.inverseShiftTransform(Math.PI / 4., 0., Math.PI / 4., 0., 0.), Double.MIN_VALUE);
        Assertions.assertArrayEquals(new double[]{0., Math.PI},
                MapProjections.inverseShiftTransform(Math.PI / 4., 0., -Math.PI / 4., 0., 0.), 1e-15);
//
//        Assertions.assertArrayEquals(new double[]{0., 0.},
// ProjectionUtil.MapProjections.shift3(Math.PI / 4., 0., Math.PI / 4., 0., 0.), 1e-15);
//        Assertions.assertArrayEquals(new double[]{0., 0.},
// ProjectionUtil.MapProjections.shift3(Math.PI / 4., 0., -Math.PI / 4., 0., 0.), 0.);
//
//        Assertions.assertArrayEquals(new double[]{0., -1.},
// ProjectionUtil.MapProjections.shift3(Math.PI / 4., 0.,-Math.PI / 4.., 0., 0.), 0.);
//        Assertions.assertArrayEquals(new double[]{0., -1.},
// ProjectionUtil.MapProjections.shift3(Math.PI / 4.., 0.,-10.*Math.PI / 4.0., 0., 0.), 0.);
//        Assertions.assertArrayEquals(new double[]{0., -0.9969173337331281},
// ProjectionUtil.MapProjections.shift3(Math.PI / 4.., 0.,-11.*Math.PI / 4.0., 0., 0.), 0.);
//        Assertions.assertArrayEquals(new double[]{0., -0.9876883405951378},
// ProjectionUtil.MapProjections.shift3(Math.PI / 4.., 0.,-12.*Math.PI / 4.0., 0., 0.), 0.);
//        Assertions.assertArrayEquals(new double[]{0., -0.9723699203976766},
// ProjectionUtil.MapProjections.shift3(Math.PI / 4.., 0.,-13.*Math.PI / 4.0., 0., 0.), 0.);
//        Assertions.assertArrayEquals(new double[]{0., -0.9510565162951535},
// ProjectionUtil.MapProjections.shift3(Math.PI / 4.., 0.,-14.*Math.PI / 4.0., 0., 0.), 0.);
//        Assertions.assertArrayEquals(new double[]{0., -0.9238795325112867},
// ProjectionUtil.MapProjections.shift3(Math.PI / 4.., 0.,-15.*Math.PI / 4.0., 0., 0.), 0.);
//        Assertions.assertArrayEquals(new double[]{0., -0.8910065241883679},
// ProjectionUtil.MapProjections.shift3(Math.PI / 4.., 0.,-16.*Math.PI / 4.0., 0., 0.), 0.);
//        Assertions.assertArrayEquals(new double[]{0., -0.8526401643540923},
// ProjectionUtil.MapProjections.shift3(Math.PI / 4.., 0.,-17.*Math.PI / 4.0., 0., 0.), 0.);
//        Assertions.assertArrayEquals(new double[]{0., -0.8090169943749476},
// ProjectionUtil.MapProjections.shift3(Math.PI / 4.., 0.,-18.*Math.PI / 4.0., 0., 0.), 0.);
//        Assertions.assertArrayEquals(new double[]{0., -0.760405965600031},
// ProjectionUtil.MapProjections.shift3(Math.PI / 4.., 0.,-19.*Math.PI / 4.0., 0., 0.), 0.);
//        Assertions.assertArrayEquals(new double[]{0., -0.7071067811865476},
// ProjectionUtil.MapProjections.shift3(Math.PI / 4.., 0.,-Math.PI / 2.., 0., 0.), 0.);
//        Assertions.assertArrayEquals(new double[]{0., 0.},
// ProjectionUtil.MapProjections.shift3(Math.PI / 4.., 0.,-3*Math.PI / 4.., 0., 0.), 1e-15);

    }
}
