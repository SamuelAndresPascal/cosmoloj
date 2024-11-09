package com.cosmoloj.math.operation.projection;

import com.cosmoloj.bibliography.cosmoloj.Cosmoloj;
import com.cosmoloj.math.operation.MethodParameter;
import com.cosmoloj.math.operation.surface.Ellipsoid;
import com.cosmoloj.util.bib.Cite;
import java.util.List;
import java.util.Map;

/**
 * <div>EPSG::1044</div>
 * <div class="en">Mercator variant C</div>
 *
 * @author Samuel Andrés
 */
@Cite(Cosmoloj.IOGP_GUIDANCE_NOTE_7_2_2019)
public class Epsg1044 implements InvertibleProjection {

    private static final int PHI = 0;
    private static final int LAMBDA = 1;
    private static final int EASTING = 0;
    private static final int NORTHING = 1;

    private final Ellipsoid ellipsoid;
    private final double a;
    private final double e;
    private final double phi1;
    private final double lambda0;
    private final double phif;
    private final double ef;
    private final double nf;

    private final double k0;
    private final double e2;
    private final double m;

    public Epsg1044(final Ellipsoid ellipsoid, final double phi1, final double lambda0, final double phif,
            final double ef, final double nf) {
        this.ellipsoid = ellipsoid;
        this.a = ellipsoid.a();
        this.e = ellipsoid.e();
        this.phi1 = Math.abs(phi1);
        this.lambda0 = lambda0;
        this.phif = phif;
        this.ef = ef;
        this.nf = nf;
        this.e2 = e * e;
        this.k0 = k0();
        this.m = a * k0 * Math.log(Math.tan(Math.PI / 4. + phif / 2.) * esinphi(phif));
    }

    public static Epsg1044 ofParams(final Ellipsoid ellipsoid, final Map<MethodParameter, ?> params) {
        return new Epsg1044(ellipsoid,
            (double) params.get(MethodParameter.LATITUDE_OF_1ST_STANDARD_PARALLEL),
            (double) params.get(MethodParameter.LONGITUDE_OF_NATURAL_ORIGIN),
            (double) params.get(MethodParameter.LATITUDE_OF_FALSE_ORIGIN),
            (double) params.get(MethodParameter.EASTING_AT_FALSE_ORIGIN),
            (double) params.get(MethodParameter.NORTHING_AT_FALSE_ORIGIN));
    }

    @Override
    public Ellipsoid getSurface() {
        return ellipsoid;
    }

    @Override
    public Object getParameter(final MethodParameter parameter) {
        return switch (parameter) {
            case LATITUDE_OF_1ST_STANDARD_PARALLEL -> phi1;
            case LONGITUDE_OF_NATURAL_ORIGIN -> lambda0;
            case LATITUDE_OF_FALSE_ORIGIN -> phif;
            case EASTING_AT_FALSE_ORIGIN -> ef;
            case NORTHING_AT_FALSE_ORIGIN -> nf;
            default -> throw new IllegalArgumentException();
        };
    }

    @Override
    public List<MethodParameter> getParameters() {
        return List.of(MethodParameter.LATITUDE_OF_1ST_STANDARD_PARALLEL,
                MethodParameter.LONGITUDE_OF_NATURAL_ORIGIN,
                MethodParameter.LATITUDE_OF_FALSE_ORIGIN,
                MethodParameter.EASTING_AT_FALSE_ORIGIN,
                MethodParameter.NORTHING_AT_FALSE_ORIGIN);
    }

    final double k0() {
        return Math.cos(phi1) / Math.sqrt((1. - e2 * Math.sin(phi1) * Math.sin(phi1)));
    }

    final double esinphi(final double phi) {
        final double sinphi = Math.sin(phi);
        return Math.pow((1. - e * sinphi) / (1. + e * sinphi), e / 2.);
    }

    @Override
    public double[] compute(final double[] input) {
        final double phi = input[PHI];
        return new double[]{ef + a * k0 * (input[LAMBDA] - lambda0),
            nf - m + a * k0 * Math.log(Math.tan(Math.PI / 4. + phi / 2.) * esinphi(phi))};
    }

    @Override
    public double[] inverse(final double[] input) {
        return new double[]{phi(input[NORTHING]), (input[EASTING] - ef) / (a * k0) + lambda0};
    }

    double phi(final double northing) {
        final double t = Math.exp((nf - m - northing) / (a * k0));
        final double chi = Math.PI / 2. - 2. * Math.atan(t);
        return chi + e2 * ((.5 + e2 * (5. / 24. + e2 * (1. / 12. + 13. * e2 / 360.))) * Math.sin(2. * chi)
                + e2 * ((7. / 48. + e2 * (29. / 240. + e2 * 811. / 11520.)) * Math.sin(4. * chi)
                + e2 * ((7. / 120. + e2 * 81. / 1120.) * Math.sin(6. * chi)
                + e2 * 4279. / 161280. * Math.sin(8. * chi))));
    }
}
