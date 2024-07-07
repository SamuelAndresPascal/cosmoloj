package com.cosmoloj.math.operation.projection;

import com.cosmoloj.math.operation.surface.Ellipsoid;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Samuel Andrés
 */
public class Epsg9813Test {

    private final double a = 6378388;
    private final double invf = 297;

    private final double phic = -0.329867229;
    private final double lambdac = 0.7696902001295; // E from Paris (=0.810482544 E of Greenwich);
    private final double alphac = 0.329867229;
    private final double kc = 0.9995;
    // TODO : la documentation commence par indiquer Ec et Nc puis passe à FE et FN.
    // La base et le registre indiquent FE et FN
    private final double ec = 400000.;
    private final double nc = 800000.;

    private final double phi = -0.282565315;
    private final double lambda = 0.735138668;
    private final double easting = 188333.848;
    private final double northing = 1098841.091;

    private final Epsg9813 transform = new Epsg9813(Ellipsoid.ofInverseFlattening(a, invf),
            phic, lambdac, alphac, kc, ec, nc);

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
