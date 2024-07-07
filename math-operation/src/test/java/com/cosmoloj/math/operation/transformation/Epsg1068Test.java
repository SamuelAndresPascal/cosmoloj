package com.cosmoloj.math.operation.transformation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Samuel Andrés
 */
public class Epsg1068Test {

    private final Epsg1068 transform = new Epsg1068();

    @Test
    public void forward() {

        Assertions.assertEquals(300., transform.compute(-300.), 1e-15);
        Assertions.assertEquals(500., transform.compute(-500.), 1e-15);
    }

    @Test
    public void inverse() {

        Assertions.assertEquals(-300, transform.inverse(300.), 1e-15);
        Assertions.assertEquals(-500, transform.inverse(500.), 1e-15);
    }
}
