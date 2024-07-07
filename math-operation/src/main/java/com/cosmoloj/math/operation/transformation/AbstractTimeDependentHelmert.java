package com.cosmoloj.math.operation.transformation;

import com.cosmoloj.bibliography.cosmoloj.Cosmoloj;
import com.cosmoloj.math.operation.AutoInverse;
import com.cosmoloj.math.operation.MethodParameter;
import com.cosmoloj.math.tabular.core.DoubleTabulars;
import com.cosmoloj.util.bib.Reference;
import java.util.Arrays;
import java.util.List;

/**
 * <div class="en">Time-dependent Helmert 7-parameter transformations</div>
 *
 * @author Samuel Andrés
 */
@Reference(Cosmoloj.IOGP_GUIDANCE_NOTE_7_2_2019)
abstract class AbstractTimeDependentHelmert
        implements InversibleCoordinateTransformation, AutoInverse<double[]> {

    private static final int T = 3;

    private final double rx;
    private final double ry;
    private final double rz;
    private final double tx;
    private final double ty;
    private final double tz;
    private final double ds;
    private final double drx;
    private final double dry;
    private final double drz;
    private final double dtx;
    private final double dty;
    private final double dtz;
    private final double dds;
    private final double t0;

    private final boolean exactMatrices;
    private final boolean exactReverse;

    protected AbstractTimeDependentHelmert(final double tx, final double ty, final double tz,
            final double rx, final double ry, final double rz,
            final double ds,
            final double dtx, final double dty, final double dtz, final double drx, final double dry, final double drz,
            final double dds, final double t0, final boolean exactMatrices, final boolean exactReverse) {

        this.rx = rx;
        this.ry = ry;
        this.rz = rz;
        this.tx = tx;
        this.ty = ty;
        this.tz = tz;
        this.ds = ds;

        this.drx = drx;
        this.dry = dry;
        this.drz = drz;
        this.dtx = dtx;
        this.dty = dty;
        this.dtz = dtz;
        this.dds = dds;

        this.t0 = t0;

        this.exactMatrices = exactMatrices;
        this.exactReverse = exactReverse;
    }

    @Override
    public double[] compute(final double[] input) {

        final double time = input[T] - t0;

        final double[][] r;
        if (exactMatrices) {
            r = DoubleTabulars.rotation3DXYZ(rx + drx * time, ry + dry * time, rz + drz * time);
        } else {
            r = DoubleTabulars.rotation3DXYZApprox(rx + drx * time, ry + dry * time, rz + drz * time);
        }

        final double[] t = new double[]{tx + dtx * time, ty + dty * time, tz + dtz * time};
        return DoubleTabulars.add(
                DoubleTabulars.external(1. + ds + dds * time, DoubleTabulars.mult(r, Arrays.copyOf(input, 3))), t);
    }

    @Override
    public double[] inverse(final double[] input) {

        final double time = input[T] - t0;

        final double[][] rr;
        if (exactMatrices) {
            rr = DoubleTabulars.rotation3DZYX(-(rx + drx * time), -(ry + dry * time), -(rz + drz * time));
        } else {
            rr = DoubleTabulars.rotation3DZYXApprox(-(rx + drx * time), -(ry + dry * time), -(rz + drz * time));
        }

        final double[] t = new double[]{tx + dtx * time, ty + dty * time, tz + dtz * time};
        if (exactReverse) {
            return DoubleTabulars.minus(
                DoubleTabulars.external(1. / (1. + ds + dds * time), DoubleTabulars.mult(rr, Arrays.copyOf(input, 3))),
                    DoubleTabulars.external(1. / (1. + ds + dds * time), DoubleTabulars.mult(rr, t)));
        } else {
            return DoubleTabulars.minus(
                DoubleTabulars.external(1. - (ds + dds * time), DoubleTabulars.mult(rr, Arrays.copyOf(input, 3))), t);
        }
    }

    @Override
    public List<MethodParameter> getParameters() {
        return List.of(MethodParameter.X_AXIS_TRANSLATION,
            MethodParameter.Y_AXIS_TRANSLATION,
            MethodParameter.Z_AXIS_TRANSLATION,
            MethodParameter.X_AXIS_ROTATION,
            MethodParameter.Y_AXIS_ROTATION,
            MethodParameter.Z_AXIS_ROTATION,
            MethodParameter.SCALE_DIFFERENCE,
            MethodParameter.RATE_OF_CHANGE_OF_X_AXIS_TRANSLATION,
            MethodParameter.RATE_OF_CHANGE_OF_Y_AXIS_TRANSLATION,
            MethodParameter.RATE_OF_CHANGE_OF_Z_AXIS_TRANSLATION,
            MethodParameter.RATE_OF_CHANGE_OF_X_AXIS_ROTATION,
            MethodParameter.RATE_OF_CHANGE_OF_Y_AXIS_ROTATION,
            MethodParameter.RATE_OF_CHANGE_OF_Z_AXIS_ROTATION,
            MethodParameter.RATE_OF_CHANGE_OF_SCALE_DIFFERENCE,
            MethodParameter.PARAMETER_REFERENCE_EPOCH);
    }

    @Override
    public Object getParameter(final MethodParameter parameter) {
        return switch (parameter) {
            case X_AXIS_TRANSLATION -> tx;
            case Y_AXIS_TRANSLATION -> ty;
            case Z_AXIS_TRANSLATION -> tz;
            case X_AXIS_ROTATION -> rx;
            case Y_AXIS_ROTATION -> ry;
            case Z_AXIS_ROTATION -> rz;
            case SCALE_DIFFERENCE -> ds;
            case RATE_OF_CHANGE_OF_X_AXIS_TRANSLATION -> dtx;
            case RATE_OF_CHANGE_OF_Y_AXIS_TRANSLATION -> dty;
            case RATE_OF_CHANGE_OF_Z_AXIS_TRANSLATION -> dtz;
            case RATE_OF_CHANGE_OF_X_AXIS_ROTATION -> drx;
            case RATE_OF_CHANGE_OF_Y_AXIS_ROTATION -> dry;
            case RATE_OF_CHANGE_OF_Z_AXIS_ROTATION -> drz;
            case RATE_OF_CHANGE_OF_SCALE_DIFFERENCE -> dds;
            case PARAMETER_REFERENCE_EPOCH -> t0;
            default -> throw new IllegalArgumentException();
        };
    }
}
