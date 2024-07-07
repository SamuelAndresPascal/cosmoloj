package com.cosmoloj.math.operation.transformation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Samuel Andrés
 */
public class Epsg1066Test {

    private final Epsg1066 transform = new Epsg1066(
            0.003, 0.001, 0.,
            -9.21145994108117e-11, 2.03621746066005e-10, -9.69627362219071e-12,
            0., 2010.0);

    @Test
    public void forward() {

        final double[][] forward = transform.compute(new double[][]{
                    {2845456.0813, 2160954.2453, 5265993.2296, 2005.0},
                    {-0.0212, 0.0124, 0.0072, 2013.90}
                });

        Assertions.assertArrayEquals(new double[]{2845455.8945, 2160954.3562, 5265993.2945}, forward[0], 1e-4);
        Assertions.assertArrayEquals(new double[]{-0.0212, 0.0124, 0.0072}, forward[1], 1e-4);
    }

    @Test
    public void inverse() {

        final double[][] inverse = transform.inverse(new double[][]{
                    {2845455.8945, 2160954.3562, 5265993.2945, 2013.90},
                    {-0.0212, 0.0124, 0.0072, 2005.00}
                });

        Assertions.assertArrayEquals(new double[]{2845456.0813, 2160954.2453, 5265993.2296}, inverse[0], 1e-4);
        Assertions.assertArrayEquals(new double[]{-0.0212, 0.0124, 0.0072}, inverse[1], 1e-4);
    }
}
