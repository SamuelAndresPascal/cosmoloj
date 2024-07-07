package com.cosmoloj.math.operation.transformation;

import com.cosmoloj.bibliography.cosmoloj.Cosmoloj;
import com.cosmoloj.math.operation.MethodParameter;
import com.cosmoloj.math.util.complex.Complex;
import com.cosmoloj.math.util.set.polynomial.PolynomialUtil;
import com.cosmoloj.util.bib.Reference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <div class="en">Polynomial transformation with complex numbers</div>
 *
 * @author Samuel Andrés
 */
@Reference(Cosmoloj.IOGP_GUIDANCE_NOTE_7_2_2019)
abstract class AbstractComplexPolynomialTransformation implements CoordinateTransformation {

    private final double xs;
    private final double ys;
    private final double xt;
    private final double yt;
    private final double ms;
    private final double mt;

    private final Complex[] a;

    protected AbstractComplexPolynomialTransformation(final double sourceEval1, final double sourceEval2,
            final double targetEval1, final double targetEval2, final double sourceScaling, final double targetScaling,
            final double[] a) {
        this.xs = sourceEval1;
        this.ys = sourceEval2;
        this.xt = targetEval1;
        this.yt = targetEval2;
        this.ms = sourceScaling;
        this.mt = targetScaling;
        this.a = new Complex[a.length / 2 + 1]; // +1 to include order 0
        this.a[0] = Complex.ZERO;
        for (int i = 0; i < this.a.length - 1; i++) {
            this.a[i + 1] = Complex.of(a[2 * i], a[2 * i + 1]);
        }
    }

    @Override
    public double[] compute(final double[] input) {
        final double u = (input[0] - xs) * ms;
        final double v = (input[1] - ys) * ms;

        final Complex mtdx = PolynomialUtil.hornerAsc(a, Complex.of(u, v));

        return new double[]{
            input[0] - xs + xt + mtdx.getReal() / mt,
            input[1] - ys + yt + mtdx.getImaginary() / mt
        };
    }

    @Override
    public final List<MethodParameter> getParameters() {
        final List<MethodParameter> sum = new ArrayList<>(coordParameters());
        sum.addAll(aParameters());
        return Collections.unmodifiableList(sum);
    }

    private List<MethodParameter> coordParameters() {
        return List.of(
                MethodParameter.ORDINATE_1_OF_EVALUATION_POINT_IN_SOURCE_CRS,
                MethodParameter.ORDINATE_2_OF_EVALUATION_POINT_IN_SOURCE_CRS,
                MethodParameter.ORDINATE_1_OF_EVALUATION_POINT_IN_TARGET_CRS,
                MethodParameter.ORDINATE_2_OF_EVALUATION_POINT_IN_TARGET_CRS,
                MethodParameter.SCALING_FACTOR_FOR_SOURCE_CRS_COORD_DIFFERENCES,
                MethodParameter.SCALING_FACTOR_FOR_TARGET_CRS_COORD_DIFFERENCES);
    }

    protected abstract List<MethodParameter> aParameters();

    @Override
    public final Object getParameter(final MethodParameter parameter) {
        return switch (parameter) {
            case ORDINATE_1_OF_EVALUATION_POINT_IN_SOURCE_CRS -> xs;
            case ORDINATE_2_OF_EVALUATION_POINT_IN_SOURCE_CRS -> ys;
            case ORDINATE_1_OF_EVALUATION_POINT_IN_TARGET_CRS -> xt;
            case ORDINATE_2_OF_EVALUATION_POINT_IN_TARGET_CRS -> yt;
            case SCALING_FACTOR_FOR_SOURCE_CRS_COORD_DIFFERENCES -> ms;
            case SCALING_FACTOR_FOR_TARGET_CRS_COORD_DIFFERENCES -> mt;
            default -> {
                try {
                    parameter.extractCoef(a, "A");
                } catch (final UnsupportedOperationException uoe) {
                    //
                    throw new IllegalArgumentException();
                }
                throw new IllegalArgumentException();
            }
        };
    }
}
