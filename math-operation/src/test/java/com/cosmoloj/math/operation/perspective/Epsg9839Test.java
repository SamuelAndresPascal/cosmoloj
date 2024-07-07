package com.cosmoloj.math.operation.perspective;

import com.cosmoloj.math.operation.surface.Ellipsoid;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Samuel Andrés
 */
public class Epsg9839Test {

    private final double phi = 0.93915110;
    private final double lambda = 0.03716765;
    private final double h = 73.0;
    private final double easting = -189013.869;
    private final double northing = -128642.040;

    private final Epsg9839 transform = new Epsg9839(Ellipsoid.ofInverseFlattening(6378137.0, 298.2572236),
            0.95993109, 0.08726646);

    @Test
    public void forward() {

        Assertions.assertArrayEquals(new double[]{easting, northing},
                transform.compute(new double[]{phi, lambda, h}), 1e-1);
    }
}
