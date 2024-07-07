package com.cosmoloj.math.operation.transformation;

import com.cosmoloj.bibliography.cosmoloj.Cosmoloj;
import com.cosmoloj.math.operation.AutoInverse;
import com.cosmoloj.math.operation.MethodParameter;
import com.cosmoloj.math.tabular.core.DoubleTabulars;
import com.cosmoloj.util.bib.Reference;
import java.util.List;

/**
 * <div class="en">Molodensky-Badekas 10-parameter transformations</div>
 *
 * @author Samuel Andrés
 */
@Reference(Cosmoloj.IOGP_GUIDANCE_NOTE_7_2_2019)
abstract class AbstractMolodenskyBadekas
        implements InversibleCoordinateTransformation, AutoInverse<double[]> {

    private final double[][] r;
    private final double[][] rr;
    private final double[] t;
    private final double[] p;
    private final double ds;
    private final boolean exactReverse;

    protected AbstractMolodenskyBadekas(final double tx, final double ty, final double tz,
            final double rx, final double ry, final double rz, final double ds,
            final double epo1, final double epo2, final double epo3,
            final boolean exactMatrices, final boolean exactReverse) {

        if (exactMatrices) {
            r = DoubleTabulars.rotation3DXYZ(rx, ry, rz);
            rr = DoubleTabulars.rotation3DZYX(-rx, -ry, -rz);
        } else {
            r = DoubleTabulars.rotation3DXYZApprox(rx, ry, rz);
            rr = DoubleTabulars.rotation3DZYXApprox(-rx, -ry, -rz);
        }
        this.t = new double[]{tx, ty, tz};
        this.p = new double[]{epo1, epo2, epo3};
        this.ds = ds;
        this.exactReverse = exactReverse;
    }

    @Override
    public double[] compute(final double[] input) {
        return DoubleTabulars.add(DoubleTabulars.add(
                DoubleTabulars.external(1. + ds, DoubleTabulars.mult(r, DoubleTabulars.minus(input, p))),
                p), t);
    }

    @Override
    public double[] inverse(final double[] input) {
        if (exactReverse) {
            return DoubleTabulars.minus(DoubleTabulars.add(
                DoubleTabulars.external(1. / (1. + ds), DoubleTabulars.mult(rr, DoubleTabulars.minus(input, p))),
                p),
                    DoubleTabulars.external(1. / (1. + ds), DoubleTabulars.mult(rr, t)));
        } else {
            return DoubleTabulars.minus(DoubleTabulars.add(
                DoubleTabulars.external(1. - ds, DoubleTabulars.mult(rr, DoubleTabulars.minus(input, p))),
                p), t);
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
                MethodParameter.ORDINATE_1_OF_EVALUATION_POINT,
                MethodParameter.ORDINATE_2_OF_EVALUATION_POINT,
                MethodParameter.ORDINATE_3_OF_EVALUATION_POINT);
    }

    @Override
    public Object getParameter(final MethodParameter parameter) {
        return switch (parameter) {
            case X_AXIS_TRANSLATION -> t[0];
            case Y_AXIS_TRANSLATION -> t[1];
            case Z_AXIS_TRANSLATION -> t[2];
            case X_AXIS_ROTATION -> t[0];
            case Y_AXIS_ROTATION -> t[1];
            case Z_AXIS_ROTATION -> t[2];
            case SCALE_DIFFERENCE -> ds;
            case ORDINATE_1_OF_EVALUATION_POINT -> ds;
            case ORDINATE_2_OF_EVALUATION_POINT -> ds;
            case ORDINATE_3_OF_EVALUATION_POINT -> ds;
            default -> throw new IllegalArgumentException();
        };
    }
}
