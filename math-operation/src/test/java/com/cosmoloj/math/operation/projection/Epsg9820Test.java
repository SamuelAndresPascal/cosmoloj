package com.cosmoloj.math.operation.projection;

import com.cosmoloj.math.operation.surface.Ellipsoid;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Samuel Andrés
 */
public class Epsg9820Test {

    private final Epsg9820 transform = new Epsg9820(Ellipsoid.ofInverseFlattening(6378137.0, 298.2572221),
            0.907571211, 0.174532925, 4321000.00, 3210000.00);

    private final double phi = 0.872664626;
    private final double lambda = 0.087266463;
    private final double easting = 3962799.45;
    private final double northing = 2999718.85;

    @Test
    public void forward() {
        Assertions.assertArrayEquals(new double[]{easting, northing},
                        transform.compute(new double[]{phi, lambda}), 1e-2);
    }

    @Test
    public void inverse() {
        Assertions.assertArrayEquals(new double[]{phi, lambda},
                transform.inverse(new double[]{easting, northing}), 1e-9);
    }
}
