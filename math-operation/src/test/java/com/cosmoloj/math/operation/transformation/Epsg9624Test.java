package com.cosmoloj.math.operation.transformation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Samuel Andrés
 */
public class Epsg9624Test {

    private final Epsg9624 transform = new Epsg9624(new double[][]{
        {82357.457, .304794369, .000015417425},
        {28091.324, -.000015417425, .304794369}
    });

    private final double[] s = {553900., 482500.};
    private final double[] t = {251190.497, 175146.067};

    @Test
    public void forward() {

        Assertions.assertArrayEquals(t, transform.compute(s), 1e-3);
    }

    @Test
    public void inverse() {

        Assertions.assertArrayEquals(s, transform.inverse(t), 1e-2);
    }
}
