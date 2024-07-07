package com.cosmoloj.math.operation.transformation;

import com.cosmoloj.math.util.MathUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Samuel Andrés
 */
public class Epsg9660Test {

    private final Epsg9660 transform
            = new Epsg9660(MathUtil.arcSecToRadians(-5.86), MathUtil.arcSecToRadians(0.28), 31.2);

    @Test
    public void forward() {

        Assertions.assertArrayEquals(
            new double[]{MathUtil.toRadians(38, 8, 30.705), MathUtil.toRadians(23, 48, 16.515), 35.5},
            transform.compute(new double[]{MathUtil.toRadians(38, 8, 36.565), MathUtil.toRadians(23, 48, 16.235), 4.3}),
            1e-9);
    }

    @Test
    public void inverse() {

        Assertions.assertArrayEquals(
        new double[]{MathUtil.toRadians(38, 8, 36.565), MathUtil.toRadians(23, 48, 16.235), 4.3},
        transform.inverse(new double[]{MathUtil.toRadians(38, 8, 30.705), MathUtil.toRadians(23, 48, 16.515), 35.5}),
        1e-9);
    }
}
