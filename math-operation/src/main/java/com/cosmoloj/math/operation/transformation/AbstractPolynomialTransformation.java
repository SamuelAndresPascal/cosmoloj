package com.cosmoloj.math.operation.transformation;

import com.cosmoloj.bibliography.cosmoloj.Cosmoloj;
import com.cosmoloj.math.operation.MethodParameter;
import com.cosmoloj.math.tabular.core.DoubleTabulars;
import com.cosmoloj.math.util.set.polynomial.PolynomialUtil;
import com.cosmoloj.util.bib.Cite;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <div class="en">Polynomial transformations: general case</div>
 *
 * @author Samuel Andrés
 */
@Cite(Cosmoloj.IOGP_GUIDANCE_NOTE_7_2_2019)
abstract class AbstractPolynomialTransformation implements CoordinateTransformation {

    private final double xs;
    private final double ys;
    private final double xt;
    private final double yt;
    private final double ms;
    private final double mt;

    private final double[][] a;
    private final double[][] b;

    protected AbstractPolynomialTransformation(final double sourceEval1, final double sourceEval2,
            final double targetEval1, final double targetEval2, final double sourceScaling, final double targetScaling,
            final double[][]a, final double[][] b) {
        this.xs = sourceEval1;
        this.ys = sourceEval2;
        this.xt = targetEval1;
        this.yt = targetEval2;
        this.ms = sourceScaling;
        this.mt = targetScaling;
        this.a = DoubleTabulars.copy(a);
        this.b = DoubleTabulars.copy(b);
    }

    protected final double xs() {
        return xs;
    }

    protected final double ys() {
        return ys;
    }

    protected final double xt() {
        return xt;
    }

    protected final double yt() {
        return yt;
    }

    protected final double ms() {
        return ms;
    }

    protected final double mt() {
        return mt;
    }

    @Override
    public double[] compute(final double[] input) {
        final double u = (input[0] - xs) * ms;
        final double v = (input[1] - ys) * ms;

        final double[] al = new double[a.length];
        final double[] bl = new double[b.length];
        for (int i = 0; i < a.length; i++) {
            al[i] = PolynomialUtil.hornerAsc(a[i], v);
            bl[i] = PolynomialUtil.hornerAsc(b[i], v);
        }
        final double mtdx = PolynomialUtil.hornerAsc(al, u);
        final double mtdy = PolynomialUtil.hornerAsc(bl, u);

        return new double[]{
            input[0] - xs + xt + mtdx / mt,
            input[1] - ys + yt + mtdy / mt
        };
    }

    @Override
    public List<MethodParameter> getParameters() {
        final List<MethodParameter> sum = new ArrayList<>(coordParameters());
        sum.addAll(aParameters());
        sum.addAll(bParameters());
        return Collections.unmodifiableList(sum);
    }

    protected List<MethodParameter> coordParameters() {
        return List.of(
                MethodParameter.ORDINATE_1_OF_EVALUATION_POINT_IN_SOURCE_CRS,
                MethodParameter.ORDINATE_2_OF_EVALUATION_POINT_IN_SOURCE_CRS,
                MethodParameter.ORDINATE_1_OF_EVALUATION_POINT_IN_TARGET_CRS,
                MethodParameter.ORDINATE_2_OF_EVALUATION_POINT_IN_TARGET_CRS,
                MethodParameter.SCALING_FACTOR_FOR_SOURCE_CRS_COORD_DIFFERENCES,
                MethodParameter.SCALING_FACTOR_FOR_TARGET_CRS_COORD_DIFFERENCES);
    }

    protected abstract List<MethodParameter> aParameters();

    protected abstract List<MethodParameter> bParameters();

    @Override
    public Object getParameter(final MethodParameter parameter) {
        return switch (parameter) {
            case ORDINATE_1_OF_EVALUATION_POINT_IN_SOURCE_CRS -> xs;
            case ORDINATE_2_OF_EVALUATION_POINT_IN_SOURCE_CRS -> ys;
            case ORDINATE_1_OF_EVALUATION_POINT_IN_TARGET_CRS -> xt;
            case ORDINATE_2_OF_EVALUATION_POINT_IN_TARGET_CRS -> yt;
            case SCALING_FACTOR_FOR_SOURCE_CRS_COORD_DIFFERENCES -> ms;
            case SCALING_FACTOR_FOR_TARGET_CRS_COORD_DIFFERENCES -> mt;
            default -> {
                try {
                    parameter.extractCoef(
                            parameter.name().startsWith("A") ? a : b,
                            String.valueOf(parameter.name().charAt(0)));
                } catch (final UnsupportedOperationException uoe) {
                    //
                    throw new IllegalArgumentException();
                }
                throw new IllegalArgumentException();
            }
        };
    }
}
