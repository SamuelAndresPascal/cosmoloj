package com.cosmoloj.math.operation.transformation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Samuel Andrés
 */
public class Epsg9653Test {

    private final Epsg9653 transform = new Epsg9653(155000, 463000, 663395.607, 5781194.38, 1e-5, 1.,
                new double[]{-51.681, 3290.525, 20.172, 1.133, 2.075, 0.251, 0.075, -0.012});

    private final Epsg9653 inverse = new Epsg9653(663395.607, 5781194.380, 155000., 463000., 1e-5, 1.,
                new double[]{-56.619, -3290.362, -20.184, .861, -2.082, .023, -.070, .025});

    @Test
    public void forward() {

        Assertions.assertArrayEquals(
                new double[]{707155.557, 5819663.128},
                transform.compute(new double[]{200000., 500000.}), 1e-3);
    }

    @Test
    public void inverse() {

        Assertions.assertArrayEquals(
                new double[]{200000., 500000.},
                inverse.compute(new double[]{707155.557, 5819663.128}), 1e-3);
    }
}
