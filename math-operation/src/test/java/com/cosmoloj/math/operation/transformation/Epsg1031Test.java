package com.cosmoloj.math.operation.transformation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Samuel Andrés
 */
public class Epsg1031Test {

    private final Epsg1031 transform = new Epsg1031(84.87, 96.49, 116.95);

    @Test
    public void forward() {

        Assertions.assertArrayEquals(
                new double[]{3771878.84, 140349.83, 5124421.30},
                transform.compute(new double[]{3771793.97, 140253.34, 5124304.35}), 1e-2);
    }

    @Test
    public void inverse() {

        Assertions.assertArrayEquals(
                new double[]{3771793.97, 140253.34, 5124304.35},
                transform.inverse(new double[]{3771878.84, 140349.83, 5124421.30}), 1e-2);
    }
}
