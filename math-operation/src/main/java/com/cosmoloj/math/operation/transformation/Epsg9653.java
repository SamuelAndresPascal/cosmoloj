package com.cosmoloj.math.operation.transformation;

import com.cosmoloj.bibliography.cosmoloj.Cosmoloj;
import com.cosmoloj.math.operation.MethodParameter;
import com.cosmoloj.util.bib.Reference;
import java.util.List;
import java.util.Map;

/**
 * <div>EPSG::9653</div>
 * <div class="en">Complex polynomial of degree 4</div>
 *
 * @author Samuel Andrés
 */
@Reference(Cosmoloj.IOGP_GUIDANCE_NOTE_7_2_2019)
public class Epsg9653 extends AbstractComplexPolynomialTransformation {

    public Epsg9653(final double sourceEval1, final double sourceEval2, final double targetEval1,
            final double targetEval2, final double sourceScaling, final double targetScaling, final double[] a) {
        super(sourceEval1, sourceEval2, targetEval1, targetEval2, sourceScaling, targetScaling, a);
    }

    public static Epsg9653 ofParams(final Map<MethodParameter, ?> params) {
        final double[] a = new double[A.size()];

        A.forEach(p -> p.fillCoef(a, "A", (double) params.get(p)));

        return new Epsg9653(
                (double) params.get(MethodParameter.ORDINATE_1_OF_EVALUATION_POINT_IN_SOURCE_CRS),
                (double) params.get(MethodParameter.ORDINATE_2_OF_EVALUATION_POINT_IN_SOURCE_CRS),
                (double) params.get(MethodParameter.ORDINATE_1_OF_EVALUATION_POINT_IN_TARGET_CRS),
                (double) params.get(MethodParameter.ORDINATE_2_OF_EVALUATION_POINT_IN_TARGET_CRS),
                (double) params.get(MethodParameter.SCALING_FACTOR_FOR_SOURCE_CRS_COORD_DIFFERENCES),
                (double) params.get(MethodParameter.SCALING_FACTOR_FOR_TARGET_CRS_COORD_DIFFERENCES),
                a);
    }

    @Override
    protected final List<MethodParameter> aParameters() {
        return A;
    }

    private static final List<MethodParameter> A = List.of(MethodParameter.A0,
                MethodParameter.A1,
                MethodParameter.A2,
                MethodParameter.A3,
                MethodParameter.A4,
                MethodParameter.A5,
                MethodParameter.A6,
                MethodParameter.A7,
                MethodParameter.A8);
}
