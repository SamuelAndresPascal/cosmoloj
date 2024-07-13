package com.cosmoloj.math.operation.projection;

import com.cosmoloj.math.operation.surface.Ellipsoid;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Samuel Andrés
 */
public class Epsg1028Test {

    private final Epsg1028 integration2dKind = new Epsg1028.Integration2dKind(
            Ellipsoid.ofInverseFlattening(6378137.0, 298.257223563), 0., 0., 0., 0.);

    private final double phi = 0.959931086;
    private final double lambda = 0.174532925;
    private final double easting = 1113194.91;
    private final double northing2dKind = 6097230.3131;

    // test du manuel

    @Test
    public void forward2dKind() {
        Assertions.assertArrayEquals(new double[]{easting, northing2dKind},
                integration2dKind.compute(new double[]{phi, lambda}), 1e-2);
    }

    @Test
    public void inverse2dKind() {
        Assertions.assertArrayEquals(new double[]{phi, lambda},
                        integration2dKind.inverse(new double[]{easting, northing2dKind}), 1e-8);
    }

    // test avec l'intégrale du troisième type (on ne trouve pas exactement la même valeur pour northing

    private final Epsg1028 integration3rdKind = new Epsg1028.Integration3rdKind(
            Ellipsoid.ofInverseFlattening(6378137.0, 298.257223563), 0., 0., 0., 0.);

    private final double northing3rdKind = 6097230.30;

    @Test
    public void forward3rdKind() {
        Assertions.assertArrayEquals(new double[]{easting, northing3rdKind},
                integration3rdKind.compute(new double[]{phi, lambda}), 1e-2);
    }

    @Test
    public void inverse3rdKind() {
        Assertions.assertArrayEquals(new double[]{phi, lambda},
                        integration3rdKind.inverse(new double[]{easting, northing3rdKind}), 1e-8);
    }

    // test avec la série (la valeur pour northing n'est pas tout à fait identique sans qu'on puisse trouver d'erreur
    // dans la série)

    private final Epsg1028 series = new Epsg1028.Series(
            Ellipsoid.ofInverseFlattening(6378137.0, 298.257223563), 0., 0., 0., 0.);

    private final double northingSeries = 6097230.29;

    @Test
    public void forwardSeries() {
        Assertions.assertArrayEquals(new double[]{easting, northingSeries},
                series.compute(new double[]{phi, lambda}), 1e-2);
    }

    @Test
    public void inverseSeries() {
        Assertions.assertArrayEquals(new double[]{phi, lambda},
                        series.inverse(new double[]{easting, northingSeries}), 1e-7);
    }
}
