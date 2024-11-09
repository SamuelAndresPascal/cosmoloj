package com.cosmoloj.math.operation.transformation;

import com.cosmoloj.bibliography.cosmoloj.Cosmoloj;
import com.cosmoloj.math.operation.AutoInverse;
import com.cosmoloj.math.operation.MethodParameter;
import com.cosmoloj.math.tabular.core.DoubleTabulars;
import com.cosmoloj.util.bib.Cite;
import java.util.Arrays;
import java.util.List;

/**
 * <div class="en">Time-specific Helmert 7-parameter transformations</div>
 *
 * @author Samuel Andrés
 */
@Cite(Cosmoloj.IOGP_GUIDANCE_NOTE_7_2_2019)
abstract class AbstractTimeSpecificHelmert
        implements InversibleTransformation<double[][], double[][]>, AutoInverse<double[][]> {

    private static final int POSITION = 0;
    private static final int VELOCITY = 1;
    private static final int T = 3;

    private final AbstractHelmert kernel;
    private final double t0;

    protected AbstractTimeSpecificHelmert(final AbstractHelmert kernel, final double t0) {
        this.kernel = kernel;
        this.t0 = t0;
    }

    @Override
    public double[][] compute(final double[][] input) {

        final double[] pt1 = Arrays.copyOf(input[POSITION], 3);
        final double t1 = t0 - input[POSITION][T];

        final double[] v = Arrays.copyOf(input[VELOCITY], 3);
        final double t2 = input[VELOCITY][T] - t0;

        final double[] pt2 = kernel.compute(DoubleTabulars.add(pt1, DoubleTabulars.external(t1, v)));

        return new double[][]{
            DoubleTabulars.add(pt2, DoubleTabulars.external(t2, v)),
            v
        };
    }

    @Override
    public double[][] inverse(final double[][] input) {

        final double[] pt1 = Arrays.copyOf(input[POSITION], 3);
        final double t1 = t0 - input[POSITION][T];

        final double[] v = Arrays.copyOf(input[VELOCITY], 3);
        final double t2 = input[VELOCITY][T] - t0;

        final double[] pt2 = kernel.inverse(DoubleTabulars.add(pt1, DoubleTabulars.external(t1, v)));

        return new double[][]{
            DoubleTabulars.add(pt2, DoubleTabulars.external(t2, v)),
            v
        };
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
                MethodParameter.TRANSFORMATION_REFERENCE_EPOCH);
    }

    @Override
    public Object getParameter(final MethodParameter parameter) {
        return switch (parameter) {
            case TRANSFORMATION_REFERENCE_EPOCH -> t0;
            default -> kernel.getParameter(parameter);
        };
    }
}
