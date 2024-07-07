package com.cosmoloj.math.operation.transformation;

import com.cosmoloj.math.operation.AutoInverse;
import com.cosmoloj.math.operation.MethodParameter;
import com.cosmoloj.math.tabular.core.DoubleTabulars;
import com.cosmoloj.math.util.set.polynomial.PolynomialUtil;
import java.util.List;

/**
 *
 * @author Samuel Andrés
 */
abstract class AbstractPolynomialInversibleTransformation extends AbstractPolynomialTransformation
        implements InversibleCoordinateTransformation, AutoInverse<double[]> {

    private final double[][] ar;
    private final double[][] br;

    protected AbstractPolynomialInversibleTransformation(final double eval1, final double eval2, final double scaling,
            final double[][]a, final double[][] b) {
        super(eval1, eval2, eval1, eval2, scaling, scaling, a, b);
        this.ar = DoubleTabulars.external(-1., a);
        this.br = DoubleTabulars.external(-1., b);
    }

    @Override
    public double[] inverse(final double[] input) {
        final double u = (input[0] - xs()) * ms();
        final double v = (input[1] - ys()) * ms();

        final double[] al = new double[ar.length];
        final double[] bl = new double[br.length];
        for (int i = 0; i < ar.length; i++) {
            al[i] = PolynomialUtil.hornerAsc(ar[i], v);
            bl[i] = PolynomialUtil.hornerAsc(br[i], v);
        }
        final double mtdx = PolynomialUtil.hornerAsc(al, u);
        final double mtdy = PolynomialUtil.hornerAsc(bl, u);

        return new double[]{
            input[0] - xs() + xt() + mtdx / mt(),
            input[1] - ys() + yt() + mtdy / mt(),
        };
    }

    @Override
    protected final List<MethodParameter> coordParameters() {
        return List.of(
                MethodParameter.ORDINATE_1_OF_EVALUATION_POINT,
                MethodParameter.ORDINATE_2_OF_EVALUATION_POINT,
                MethodParameter.SCALING_FACTOR_FOR_COORD_DIFFERENCES);
    }

    @Override
    public final Object getParameter(final MethodParameter parameter) {
        if (super.coordParameters().contains(parameter)) {
            throw new IllegalArgumentException();
        }
        return switch (parameter) {
            case ORDINATE_1_OF_EVALUATION_POINT
                -> super.getParameter(MethodParameter.ORDINATE_1_OF_EVALUATION_POINT_IN_SOURCE_CRS);
            case ORDINATE_2_OF_EVALUATION_POINT
                -> super.getParameter(MethodParameter.ORDINATE_2_OF_EVALUATION_POINT_IN_SOURCE_CRS);
            case SCALING_FACTOR_FOR_COORD_DIFFERENCES
                -> super.getParameter(MethodParameter.SCALING_FACTOR_FOR_TARGET_CRS_COORD_DIFFERENCES);
            default -> super.getParameter(parameter);
        };
    }
}
