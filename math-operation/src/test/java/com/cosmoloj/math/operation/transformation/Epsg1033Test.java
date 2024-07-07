package com.cosmoloj.math.operation.transformation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Samuel Andrés
 */
public class Epsg1033Test {

    private final Epsg1033 transform = new Epsg1033(0., 0., 4.5, 0., 0., 0.000002685868, 0.219 * 1e-6);

    @Test
    public void forward() {

        Assertions.assertArrayEquals(
                new double[]{3657660.78, 255778.43, 5201387.75},
                transform.compute(new double[]{3657660.66, 255768.55, 5201382.11}), 1e-2);
    }

    @Test
    public void inverse() {

        Assertions.assertArrayEquals(
                new double[]{3657660.66, 255768.55, 5201382.11},
                transform.inverse(new double[]{3657660.78, 255778.43, 5201387.75}), 1e-2);
    }
}
