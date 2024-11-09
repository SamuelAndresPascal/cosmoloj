package com.cosmoloj.math.operation.transformation;

import com.cosmoloj.bibliography.cosmoloj.Cosmoloj;
import com.cosmoloj.math.operation.MethodParameter;
import com.cosmoloj.util.bib.Cite;
import java.util.List;
import java.util.Map;

/**
 * <div>EPSG::9648</div>
 * <div class="en">General polynomial of degree 6</div>
 *
 * @author Samuel Andrés
 */
@Cite(Cosmoloj.IOGP_GUIDANCE_NOTE_7_2_2019)
public class Epsg9648 extends AbstractPolynomialNonInversibleTransformation {

    public Epsg9648(final double sourceEval1, final double sourceEval2, final double targetEval1,
            final double targetEval2, final double sourceScaling, final double targetScaling, final double[][]a,
            final double[][] b) {
        super(sourceEval1, sourceEval2, targetEval1, targetEval2, sourceScaling, targetScaling, a, b);
    }

    public static Epsg9648 ofParams(final Map<MethodParameter, ?> params) {
        final double[][] a = new double[7][7];
        final double[][] b = new double[7][7];

        A.forEach(p -> p.fillCoef(a, "A", (double) params.get(p)));
        B.forEach(p -> p.fillCoef(b, "B", (double) params.get(p)));

        return new Epsg9648(
                (double) params.get(MethodParameter.ORDINATE_1_OF_EVALUATION_POINT_IN_SOURCE_CRS),
                (double) params.get(MethodParameter.ORDINATE_2_OF_EVALUATION_POINT_IN_SOURCE_CRS),
                (double) params.get(MethodParameter.ORDINATE_1_OF_EVALUATION_POINT_IN_TARGET_CRS),
                (double) params.get(MethodParameter.ORDINATE_2_OF_EVALUATION_POINT_IN_TARGET_CRS),
                (double) params.get(MethodParameter.SCALING_FACTOR_FOR_SOURCE_CRS_COORD_DIFFERENCES),
                (double) params.get(MethodParameter.SCALING_FACTOR_FOR_TARGET_CRS_COORD_DIFFERENCES),
                a, b);
    }

    @Override
    protected final List<MethodParameter> aParameters() {
        return A;
    }

    @Override
    protected final List<MethodParameter> bParameters() {
        return B;
    }

    private static final List<MethodParameter> A = List.of(
                MethodParameter.A0,
                MethodParameter.A_U1V0,
                MethodParameter.A_U0V1,
                MethodParameter.A_U2V0,
                MethodParameter.A_U1V1,
                MethodParameter.A_U0V2,
                MethodParameter.A_U3V0,
                MethodParameter.A_U2V1,
                MethodParameter.A_U1V2,
                MethodParameter.A_U0V3,
                MethodParameter.A_U4V0,
                MethodParameter.A_U3V1,
                MethodParameter.A_U2V2,
                MethodParameter.A_U1V3,
                MethodParameter.A_U0V4,
                MethodParameter.A_U5V0,
                MethodParameter.A_U4V1,
                MethodParameter.A_U3V2,
                MethodParameter.A_U2V3,
                MethodParameter.A_U1V4,
                MethodParameter.A_U0V5,
                MethodParameter.A_U6V0,
                MethodParameter.A_U5V1,
                MethodParameter.A_U4V2,
                MethodParameter.A_U3V3,
                MethodParameter.A_U2V4,
                MethodParameter.A_U1V5,
                MethodParameter.A_U0V6);

    private static final List<MethodParameter> B = List.of(
                MethodParameter.B0,
                MethodParameter.B_U1V0,
                MethodParameter.B_U0V1,
                MethodParameter.B_U2V0,
                MethodParameter.B_U1V1,
                MethodParameter.B_U0V2,
                MethodParameter.B_U3V0,
                MethodParameter.B_U2V1,
                MethodParameter.B_U1V2,
                MethodParameter.B_U0V3,
                MethodParameter.B_U4V0,
                MethodParameter.B_U3V1,
                MethodParameter.B_U2V2,
                MethodParameter.B_U1V3,
                MethodParameter.B_U0V4,
                MethodParameter.B_U5V0,
                MethodParameter.B_U4V1,
                MethodParameter.B_U3V2,
                MethodParameter.B_U2V3,
                MethodParameter.B_U1V4,
                MethodParameter.B_U0V5,
                MethodParameter.B_U6V0,
                MethodParameter.B_U5V1,
                MethodParameter.B_U4V2,
                MethodParameter.B_U3V3,
                MethodParameter.B_U2V4,
                MethodParameter.B_U1V5,
                MethodParameter.B_U0V6);
}
