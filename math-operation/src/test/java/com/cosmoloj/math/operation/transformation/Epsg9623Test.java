package com.cosmoloj.math.operation.transformation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Samuel Andrés
 */
public class Epsg9623Test {

    // valeurs arbitraires pour test de non régression + cohérence forward/inverse
    private final Epsg9623 transform1 = new Epsg9623(
            82357.457, 28091.324, 1., 1., 1., Math.toRadians(30.), Math.toRadians(20.));

    private final double[] s1 = {553900., 482500.};
    private final double[] t1 = {727073.6473108358, 204543.0135292009};

    @Test
    public void forward1() {

        Assertions.assertArrayEquals(t1, transform1.compute(s1), 1e-3);
    }

    @Test
    public void inverse1() {

        Assertions.assertArrayEquals(s1, transform1.inverse(t1), 1e-2);
    }

    // valeurs arbitraires pour test de non régression + cohérence forward/inverse
    private final Epsg9623 transform2 = new Epsg9623(
            82357.457, 28091.324, 2., 3., 4., Math.toRadians(30.), Math.toRadians(20.));

    private final double[] s2 = {553900., 482500.};
    private final double[] t2 = {5900185.856105227, 3253311.59835041};

    @Test
    public void forward2() {

        Assertions.assertArrayEquals(t2, transform2.compute(s2), 1e-3);
    }

    @Test
    public void inverse2() {

        Assertions.assertArrayEquals(s2, transform2.inverse(t2), 1e-2);
    }
}
