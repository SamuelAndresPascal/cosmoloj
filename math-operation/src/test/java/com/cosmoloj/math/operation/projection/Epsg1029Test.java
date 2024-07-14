package com.cosmoloj.math.operation.projection;

import com.cosmoloj.math.operation.surface.Ellipsoid;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test de non-régression. Les valeurs ne sont pas issues de la littérature.
 *
 * @author Samuel Andrés
 */
public class Epsg1029Test {

    private final Epsg1029 projection = new Epsg1029(
            Ellipsoid.ofInverseFlattening(6378137.0, 298.257223563), 0., 0., 0., 0.);

    private final double phi = 0.959931086;
    private final double lambda = 0.174532925;
    private final double easting = 1109462.5749057303;
    private final double northing = 6102044.152446388;

    @Test
    public void forward() {
        Assertions.assertArrayEquals(new double[]{easting, northing},
                projection.compute(new double[]{phi, lambda}), 1e-2);
    }

    @Test
    public void inverse() {
        Assertions.assertArrayEquals(new double[]{phi, lambda},
                        projection.inverse(new double[]{easting, northing}), 1e-8);
    }
}
