package com.cosmoloj.math.operation.transformation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Samuel Andrés
 */
public class Epsg1069Test {

    private final Epsg1069 transform = new Epsg1069((1. / 1.) / (12. / 39.37));

    @Test
    public void forward() {

        Assertions.assertEquals(82.02, transform.compute(25.), 1e-3);
    }

    @Test
    public void inverse() {

        Assertions.assertEquals(25., transform.inverse(82.02), 1e-3);
    }
}
