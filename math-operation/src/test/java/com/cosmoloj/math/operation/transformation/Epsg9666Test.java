package com.cosmoloj.math.operation.transformation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Samuel Andrés
 */
public class Epsg9666Test {

    private final Epsg9666 transform = new Epsg9666(
            1., 1., 456781.00, 5836723.00, .99984, 25., 12.5, Math.toRadians(20.), 1., 1.);

    @Test
    public void forward() {

        Assertions.assertArrayEquals(new double[]{464855.62, 5837055.90},
                transform.compute(new double[]{300., 247.}), 1e-2);
    }

    @Test
    public void inverse() {

        Assertions.assertArrayEquals(
                new double[]{300., 247.},
                transform.inverse(new double[]{464855.62, 5837055.90}), 1e-2);
    }
}
