package com.cosmoloj.math.operation.transformation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Samuel Andrés
 */
public class Epsg1034Test {

    private final Epsg1034 transform = new Epsg1034(-270.933, 115.599, -360.226,
            -0.000025530288, -0.000006001993, 0.000011543414, -5.109 * 1e-6,
            2464351.59, -5783466.61, 974809.81);

    @Test
    public void forward() {

        Assertions.assertArrayEquals(
                new double[]{2550138.46, -5749799.87, 1054530.82},
                transform.compute(new double[]{2550408.96, -5749912.26, 1054891.11}), 1e-2);
    }

    @Test
    public void inverse() {

        Assertions.assertArrayEquals(
                new double[]{2550408.96, -5749912.26, 1054891.11},
                transform.inverse(new double[]{2550138.46, -5749799.87, 1054530.82}), 1e-1);
    }
}
