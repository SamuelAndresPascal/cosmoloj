package com.cosmoloj.math.operation.transformation;

import com.cosmoloj.math.util.MathUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Samuel Andrés
 */
public class Epsg9617Test {

    private final Epsg9617 transform = new Epsg9617(11.328779, -.1674, -.03852, .0000359,
            -13276.58, 2.5079425, .08352, -.00854, -.0000038);

    @Test
    public void forward() {

        Assertions.assertArrayEquals(
                new double[]{MathUtil.toDegrees(42, 38, 56.82), MathUtil.toDegrees(0, -1, 35.97), 0.},
                transform.compute(new double[]{42.647992, 3.659603, 0.}), 1e-6);
    }
}
