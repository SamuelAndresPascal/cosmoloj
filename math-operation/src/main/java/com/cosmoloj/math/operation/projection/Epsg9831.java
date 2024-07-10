package com.cosmoloj.math.operation.projection;

import com.cosmoloj.bibliography.cosmoloj.Cosmoloj;
import com.cosmoloj.math.operation.MethodParameter;
import com.cosmoloj.math.operation.surface.Ellipsoid;
import com.cosmoloj.math.operation.trials.AbstractInitTrials;
import java.util.List;
import java.util.Map;
import com.cosmoloj.math.operation.trials.AbsoluteDifferenceDoubleTrials;
import com.cosmoloj.util.bib.Reference;

/**
 * <div>EPSG::9831</div>
 * <div class="en">Guam Projection</div>
 *
 * @author Samuel Andrés
 *
 */
@Reference(Cosmoloj.IOGP_GUIDANCE_NOTE_7_2_2019)
public class Epsg9831 implements InvertibleProjection {

    private static final int PHI = 0;
    private static final int LAMBDA = 1;
    private static final int EASTING = 0;
    private static final int NORTHING = 1;

    private final Ellipsoid ellipsoid;
    private final double a;
    private final double phi0;
    private final double lambda0;
    private final double fe;
    private final double fn;

    private final double m0;

    public Epsg9831(final Ellipsoid ellipsoid, final double phi0, final double lambda0, final double fe,
            final double fn) {
        this.ellipsoid = ellipsoid;
        this.a = ellipsoid.a();
        this.phi0 = phi0;
        this.lambda0 = lambda0;
        this.fe = fe;
        this.fn = fn;

        this.m0 = ellipsoid.m(phi0);
    }

    public static Epsg9831 ofParams(final Ellipsoid ellipsoid, final Map<MethodParameter, ?> params) {
        return new Epsg9831(ellipsoid,
            (double) params.get(MethodParameter.LATITUDE_OF_NATURAL_ORIGIN),
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
            case LATITUDE_OF_NATURAL_ORIGIN -> phi0;
            case LONGITUDE_OF_NATURAL_ORIGIN -> lambda0;
            case FALSE_EASTING -> fe;
            case FALSE_NORTHING -> fn;
            default -> throw new IllegalArgumentException();
        };
    }

    @Override
    public List<MethodParameter> getParameters() {
        return List.of(MethodParameter.LATITUDE_OF_NATURAL_ORIGIN,
                MethodParameter.LONGITUDE_OF_NATURAL_ORIGIN,
                MethodParameter.FALSE_EASTING,
                MethodParameter.FALSE_NORTHING);
    }

    @Override
    public double[] compute(final double[] input) {
        final double phi = input[PHI];

        final double eSinSqrt = ellipsoid.eSinSqrt(phi);
        final double x = a * (input[LAMBDA] - lambda0) * Math.cos(phi) / eSinSqrt;

        return new double[]{
            fe + x,
            fn + ellipsoid.m(phi) - m0 + (x * x * Math.tan(phi) * eSinSqrt / (2. * a))
        };
    }

    @Override
    public double[] inverse(final double[] input) {

        final double x = input[EASTING] - fe;
        final double phi = this.new Trials(x, input[NORTHING] - fn).loop();

        return new double[]{
            phi,
            lambda0 + x * ellipsoid.eSinSqrt(phi) / (a * Math.cos(phi))
        };
    }

    private class Trials extends AbstractInitTrials implements AbsoluteDifferenceDoubleTrials {

        private final double x;
        private final double y;

        Trials(final double x, final double y) {
            super(phi0);
            this.x = x;
            this.y = y;
        }

        @Override
        public double trial(final double phi) {

            return ellipsoid.phi1(ellipsoid.mu(m0 + y
                    - (x * x * Math.tan(phi) * Math.sqrt(ellipsoid.eSinSqrt(phi)) / (2. * a))));
        }

        @Override
        public double precision() {
            return 1e-9;
        }
    }
}
