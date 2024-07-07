package com.cosmoloj.math.operation.transformation;

import com.cosmoloj.bibliography.cosmoloj.Cosmoloj;
import com.cosmoloj.math.operation.MethodParameter;
import com.cosmoloj.util.bib.Reference;
import java.util.List;
import java.util.Map;

/**
 * <div>EPSG::9651</div>
 * <div class="en">Reversible polynomial of degree 4</div>
 *
 * @author Samuel Andrés
 */
@Reference(Cosmoloj.IOGP_GUIDANCE_NOTE_7_2_2019)
public class Epsg9651 extends AbstractPolynomialInversibleTransformation {

    public Epsg9651(final double eval1, final double eval2, final double scaling, final double[][]a,
            final double[][] b) {
        super(eval1, eval2, scaling, a, b);
    }

    public static Epsg9651 ofParams(final Map<MethodParameter, ?> params) {
        final double[][] a = new double[7][7];
        final double[][] b = new double[7][7];

        A.forEach(p -> p.fillCoef(a, "A", (double) params.get(p)));
        B.forEach(p -> p.fillCoef(a, "B", (double) params.get(p)));

        return new Epsg9651(
                (double) params.get(MethodParameter.ORDINATE_1_OF_EVALUATION_POINT),
                (double) params.get(MethodParameter.ORDINATE_2_OF_EVALUATION_POINT),
                (double) params.get(MethodParameter.SCALING_FACTOR_FOR_COORD_DIFFERENCES),
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
            MethodParameter.A_U0V4);

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
            MethodParameter.B_U0V4);
}
