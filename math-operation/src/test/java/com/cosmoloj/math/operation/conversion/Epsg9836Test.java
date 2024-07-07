package com.cosmoloj.math.operation.conversion;

import com.cosmoloj.math.operation.surface.Ellipsoid;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Samuel Andrés
 */
public class Epsg9836Test {

    private final double x = 3771_793.968;
    private final double y = 140_253.342;
    private final double z = 5124_304.349;
    private final double u = -189013.869;
    private final double v = -128642.040;
    private final double w = -4220.171;

    private final Epsg9836 transform = new Epsg9836(Ellipsoid.ofInverseFlattening(6378137.0, 298.2572236),
            3652_755.3058, 319_574.6799, 5201_547.3536);

    @Test
    public void forward() {

        Assertions.assertArrayEquals(new double[]{u, v, w},
                transform.compute(new double[]{x, y, z}), 1e-3);
    }

    @Test
    public void inverse() {

        Assertions.assertArrayEquals(new double[]{x, y, z},
                transform.inverse(new double[]{u, v, w}), 1e-3);
    }
}
