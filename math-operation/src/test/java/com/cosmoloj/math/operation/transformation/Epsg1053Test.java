package com.cosmoloj.math.operation.transformation;

import com.cosmoloj.math.util.MathUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Samuel Andrés
 */
public class Epsg1053Test {

    private final Epsg1053 transform = new Epsg1053(
            -84.68 * 1e-3, -19.42 * 1e-3, 32.01 * 1e-3,
            MathUtil.arcSecToRadians(0.4254 * 1e-3),
            MathUtil.arcSecToRadians(-2.2578 * 1e-3),
            MathUtil.arcSecToRadians(-2.4015 * 1e-3),
            0.00971 * 1e-6,
            1.42 * 1e-3, 1.34 * 1e-3, 0.9 * 1e-3,
            MathUtil.arcSecToRadians(-1.5461 * 1e-3),
            MathUtil.arcSecToRadians(-1.1820 * 1e-3),
            MathUtil.arcSecToRadians(-1.1551 * 1e-3),
            0.000109 * 1e-6, 1994.00);

    @Test
    public void forward() {

        Assertions.assertArrayEquals(
                new double[]{-3789470.004, 4841770.686, -1690895.108},
                transform.compute(new double[]{-3789470.710, 4841770.404, -1690893.952, 2013.90}), 1e-3);
    }

    @Test
    public void inverse() {

        Assertions.assertArrayEquals(
                new double[]{-3789470.710, 4841770.404, -1690893.952},
                transform.inverse(new double[]{-3789470.004, 4841770.686, -1690895.108, 2013.90}), 1e-3);
    }
}
