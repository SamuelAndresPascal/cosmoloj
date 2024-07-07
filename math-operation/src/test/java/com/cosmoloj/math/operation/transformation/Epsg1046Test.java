package com.cosmoloj.math.operation.transformation;

import com.cosmoloj.math.operation.surface.Ellipsoid;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Samuel Andrés
 */
public class Epsg1046Test {

    private final Epsg1046 transform = new Epsg1046(Ellipsoid.ofInverseFlattening(6378137., 298.25722221),
            .818850307, .142826110, -.245, -.000001018, -.000000155, "");

    private final double lat = .826122513;
    private final double lon = .168715161;

    @Test
    public void forward() {

        Assertions.assertArrayEquals(new double[]{lat, lon, 472.69},
                transform.compute(new double[]{lat, lon, 473.0}), 1e-2);
    }

    @Test
    public void inverse() {

        Assertions.assertArrayEquals(new double[]{lat, lon, 473.0},
                transform.inverse(new double[]{lat, lon, 472.69}), 1e-2);
    }
}
