package com.cosmoloj.math.operation.projection;

import com.cosmoloj.bibliography.cosmoloj.Cosmoloj;
import com.cosmoloj.math.operation.MethodParameter;
import com.cosmoloj.math.operation.surface.Ellipsoid;
import com.cosmoloj.util.bib.Reference;
import java.util.List;
import java.util.Map;

/**
 * <div>EPSG::9805</div>
 * <div class="en">Mercator - Variant B (2SP)</div>
 *
 * @author Samuel Andrés
 */
@Reference(Cosmoloj.IOGP_GUIDANCE_NOTE_7_2_2019)
public class Epsg9805 implements InvertibleProjection {

    private static final int PHI = 0;
    private static final int LAMBDA = 1;
    private static final int EASTING = 0;
    private static final int NORTHING = 1;

    private final Ellipsoid ellipsoid;
    private final double a;
    private final double e;
    private final double phi1;
    private final double lambda0;
    private final double fe;
    private final double fn;

    private final double k0;
    private final double e2;

    public Epsg9805(final Ellipsoid ellipsoid, final double phi1, final double lambda0, final double fe,
            final double fn) {
        this.ellipsoid = ellipsoid;
        this.a = ellipsoid.a();
        this.e = ellipsoid.e();
        this.phi1 = Math.abs(phi1);
        this.lambda0 = lambda0;
        this.fe = fe;
        this.fn = fn;
        this.e2 = e * e;
        this.k0 = k0();
    }

    public static Epsg9805 ofParams(final Ellipsoid ellipsoid, final Map<MethodParameter, ?> params) {
        return new Epsg9805(ellipsoid,
            (double) params.get(MethodParameter.LATITUDE_OF_1ST_STANDARD_PARALLEL),
            (double) params.get(MethodParameter.LONGITUDE_OF_NATURAL_ORIGIN),
            (double) params.get(MethodParameter.FALSE_EASTING),
            (double) params.get(MethodParameter.FALSE_NORTHING));
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
            case FALSE_EASTING -> fe;
            case FALSE_NORTHING -> fn;
            default -> throw new IllegalArgumentException();
        };
    }

    @Override
    public List<MethodParameter> getParameters() {
        return List.of(MethodParameter.LATITUDE_OF_1ST_STANDARD_PARALLEL,
                MethodParameter.LONGITUDE_OF_NATURAL_ORIGIN,
                MethodParameter.FALSE_EASTING,
                MethodParameter.FALSE_NORTHING);
    }

    final double k0() {
        return Math.cos(phi1) / Math.sqrt((1. - e * e * Math.sin(phi1) * Math.sin(phi1)));
    }

    @Override
    public double[] compute(final double[] input) {
        final double phi = input[PHI];
        final double esinphi = e * Math.sin(phi);
        return new double[]{fe + a * k0 * (input[LAMBDA] - lambda0),
            fn + a * k0 * Math.log(Math.tan(Math.PI / 4. + phi / 2.)
                    * Math.pow((1. - esinphi) / (1. + esinphi), e / 2.))};
    }

    @Override
    public double[] inverse(final double[] input) {
        return new double[]{phi(input[NORTHING]), (input[EASTING] - fe) / (a * k0) + lambda0};
    }

    double phi(final double northing) {
        final double t = Math.exp((fn - northing) / (a * k0));
        final double chi = Math.PI / 2. - 2 * Math.atan(t);
        return chi + e2 * ((.5 + e2 * (5. / 24. + e2 * (1. / 12. + 13. * e2 / 360.))) * Math.sin(2. * chi)
                + e2 * ((7. / 48. + e2 * (29. / 240. + e2 * 811. / 11520.)) * Math.sin(4. * chi)
                + e2 * ((7. / 120. + e2 * 81. / 1120.) * Math.sin(6. * chi)
                + e2 * 4279. / 161280. * Math.sin(8. * chi))));
    }
}
