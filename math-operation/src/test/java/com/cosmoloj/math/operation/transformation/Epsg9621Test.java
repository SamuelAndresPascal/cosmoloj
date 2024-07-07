package com.cosmoloj.math.operation.transformation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Samuel Andrés
 */
public class Epsg9621Test {

    private final Epsg9621 transform = new Epsg9621(-129.549, -208.185, 1.00000155, 0.000007588);

    private final double[] s = {300000., 4500000.};
    private final double[] t = {299905.060, 4499796.515};

    @Test
    public void forward() {

        Assertions.assertArrayEquals(t, transform.compute(s), 1e-2);
    }

    @Test
    public void inverse() {

        Assertions.assertArrayEquals(s, transform.inverse(t), 1e-2);
    }
}
