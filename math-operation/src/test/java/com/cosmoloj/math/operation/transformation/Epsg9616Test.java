package com.cosmoloj.math.operation.transformation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Samuel Andrés
 */
public class Epsg9616Test {

    private final Epsg9616 transform = new Epsg9616(.4);

    @Test
    public void forward() {

        Assertions.assertEquals(2.95, transform.compute(2.55), 1e-15);
    }

    @Test
    public void inverse() {

        Assertions.assertEquals(2.55, transform.inverse(2.95), 1e-15);
    }
}
