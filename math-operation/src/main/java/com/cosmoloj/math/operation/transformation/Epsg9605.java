package com.cosmoloj.math.operation.transformation;

import com.cosmoloj.bibliography.cosmoloj.Cosmoloj;
import com.cosmoloj.math.operation.AutoInverse;
import com.cosmoloj.math.operation.MethodParameter;
import com.cosmoloj.math.operation.surface.Ellipsoid;
import com.cosmoloj.math.util.MathUtil;
import com.cosmoloj.util.bib.Cite;
import java.util.List;
import java.util.Map;

/**
 * <div>EPSG::9605</div>
 * <div class="en">Abridged Molodensky transformation</div>
 *
 * @author Samuel Andrés
 */
@Cite(Cosmoloj.IOGP_GUIDANCE_NOTE_7_2_2019)
public class Epsg9605 implements InversibleCoordinateTransformation, AutoInverse<double[]> {

    private static final int PHI = 0;
    private static final int LAMBDA = 1;
    private static final int H = 2;
    private static final double SIN_1_SEC = Math.sin(MathUtil.toRadians(0, 0, 1.));

    private final double tx;
    private final double ty;
    private final double tz;
    private final double da;
    private final double df;

    private final Ellipsoid source;
    private final Ellipsoid target;
    private final double as;
    private final double at;
    private final double fs;
    private final double ft;

    public Epsg9605(final Ellipsoid source, final Ellipsoid target,
            final double tx, final double ty, final double tz, final double da, final double df) {
        this.tx = tx;
        this.ty = ty;
        this.tz = tz;
        this.da = da;
        this.df = df;

        this.source = source;
        this.target = target;
        this.as = source.a();
        this.at = target.a();
        this.fs = source.f();
        this.ft = target.f();
    }

    public static Epsg9605 ofParams(final Ellipsoid source, final Ellipsoid target,
            final Map<MethodParameter, ?> params) {
        return new Epsg9605(source, target,
            (double) params.get(MethodParameter.X_AXIS_TRANSLATION),
            (double) params.get(MethodParameter.Y_AXIS_TRANSLATION),
            (double) params.get(MethodParameter.Z_AXIS_TRANSLATION),
            (double) params.get(MethodParameter.SEMI_MAJOR_AXIS_LENGTH_DIFFERENCE),
            (double) params.get(MethodParameter.FLATTENING_DIFFERENCE));
    }

    @Override
    public double[] compute(final double[] input) {
        final double phi = input[PHI];
        final double lambda = input[LAMBDA];
        final double cosphi = Math.cos(phi);
        final double sinphi = Math.sin(phi);
        final double coslambda = Math.cos(lambda);
        final double sinlambda = Math.sin(lambda);
        final double rho = source.rho(phi);
        final double nu = source.nu(phi);
        final double dphi = MathUtil.arcSecToRadians((-tx * sinphi * coslambda - ty * sinphi * sinlambda + tz * cosphi
                + (as * df + fs * da) * Math.sin(2. * phi)) / (rho * SIN_1_SEC));
        final double dlambda = MathUtil.arcSecToRadians((-tx * sinlambda + ty * coslambda) / (nu * cosphi * SIN_1_SEC));
        final double dh = tx * cosphi * coslambda + ty * cosphi * sinlambda + tz * sinphi
                + (as * df + fs * da) * sinphi * sinphi - da;
        return new double[]{
            phi + dphi,
            lambda + dlambda,
            input[H] + dh
        };
    }

    @Override
    public double[] inverse(final double[] input) {
        final double phi = input[PHI];
        final double lambda = input[LAMBDA];
        final double cosphi = Math.cos(phi);
        final double sinphi = Math.sin(phi);
        final double coslambda = Math.cos(lambda);
        final double sinlambda = Math.sin(lambda);
        final double rho = target.rho(phi);
        final double nu = target.nu(phi);
        final double dphi = MathUtil.arcSecToRadians((-tx * sinphi * coslambda - ty * sinphi * sinlambda + tz * cosphi
                + (at * df + ft * da) * Math.sin(2. * phi)) / (rho * SIN_1_SEC));
        final double dlambda = MathUtil.arcSecToRadians((-tx * sinlambda + ty * coslambda) / (nu * cosphi * SIN_1_SEC));
        final double dh = tx * cosphi * coslambda + ty * cosphi * sinlambda + tz * sinphi
                + (at * df + ft * da) * sinphi * sinphi - da;
        return new double[]{
            phi - dphi,
            lambda - dlambda,
            input[H] - dh
        };
    }

    @Override
    public List<MethodParameter> getParameters() {
        return List.of(MethodParameter.X_AXIS_TRANSLATION,
                MethodParameter.Y_AXIS_TRANSLATION,
                MethodParameter.Z_AXIS_TRANSLATION,
                MethodParameter.SEMI_MAJOR_AXIS_LENGTH_DIFFERENCE,
                MethodParameter.FLATTENING_DIFFERENCE);
    }

    @Override
    public Object getParameter(final MethodParameter parameter) {
        return switch (parameter) {
            case X_AXIS_TRANSLATION -> tx;
            case Y_AXIS_TRANSLATION -> ty;
            case Z_AXIS_TRANSLATION -> tz;
            case SEMI_MAJOR_AXIS_LENGTH_DIFFERENCE -> da;
            case FLATTENING_DIFFERENCE -> df;
            default -> throw new IllegalArgumentException();
        };
    }
}
