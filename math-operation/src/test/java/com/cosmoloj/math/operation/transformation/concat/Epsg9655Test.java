package com.cosmoloj.math.operation.transformation.concat;

import com.cosmoloj.math.operation.surface.Ellipsoid;
import com.cosmoloj.math.util.MathUtil;
import java.io.IOException;
import java.nio.file.Paths;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

/**
 *
 * @author Samuel Andrés
 */
public class Epsg9655Test {

    private final Epsg9655 transform;

    public Epsg9655Test() throws IOException {
        this.transform = new Epsg9655(Ellipsoid.CLARKE_1880, Ellipsoid.GRS_80,
            Paths.get(Epsg9655.class.getResource("gr3df97a.txt").getPath()));
    }

    @Test
    public void forward() {

        Assertions.assertArrayEquals(
            new double[]{MathUtil.toRadians(48, 50, 40.0050), MathUtil.toRadians(2, 25, 29.8960), 42.811511241830885},
            transform.compute(new double[]{MathUtil.toRadians(48, 50, 40.2441), MathUtil.toRadians(2, 25, 32.4187)}),
            1e-7);
    }

    @Test
    public void inverse() {

        Assertions.assertArrayEquals(
        new double[]{MathUtil.toRadians(48, 50, 40.2441), MathUtil.toRadians(2, 25, 32.4187)},
        transform.inverse(new double[]{MathUtil.toRadians(48, 50, 40.00502), MathUtil.toRadians(2, 25, 29.89599), 0.}),
        1e-7);
    }
}
