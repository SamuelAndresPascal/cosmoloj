package com.cosmoloj.math.operation.transformation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Samuel Andrés
 */
public class Epsg1049Test {

    private final Epsg1049 transform = new Epsg1049(
            5000., 0., 871200., 10280160., 1., 82.5, 41.25, Math.toRadians(340.), 1., 1.);

    @Test
    public void forward() {

        Assertions.assertArrayEquals(new double[]{890972.63, 10298199.29},
                transform.compute(new double[]{4700., 247.}), 1e-2);
    }

    @Test
    public void inverse() {

        Assertions.assertArrayEquals(
                new double[]{4700., 247.},
                transform.inverse(new double[]{890972.63, 10298199.29}), 1e-2);
    }
}
