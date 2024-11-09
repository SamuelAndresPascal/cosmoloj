package com.cosmoloj.math.operation.transformation;

import com.cosmoloj.bibliography.cosmoloj.Cosmoloj;
import com.cosmoloj.math.operation.AutoInverse;
import com.cosmoloj.math.operation.MethodParameter;
import com.cosmoloj.math.tabular.core.DoubleTabulars;
import com.cosmoloj.util.bib.Cite;
import java.util.List;
import java.util.Map;

/**
 * <div>EPSG::9624</div>
 * <div class="en">Affine Parametric Transformation</div>
 *
 * @author Samuel Andrés
 */
@Cite(Cosmoloj.IOGP_GUIDANCE_NOTE_7_2_2019)
public class Epsg9624 implements InversibleCoordinateTransformation, AutoInverse<double[]> {

    /**
     * a1, a2
     * b1, b2
     */
    private final double[][] r;

    /**
     * a0
     * b0
     */
    private final double[] vt0;

    /**
     * a'1, a'2
     * b'1, b'2
     */
    private final double[][] rr;

    /**
     * a'0
     * b'0
     */
    private final double[] vs0;

    /**
     * a0, a1, a2
     * b0, b1, b2
     *
     * @param ab
     */
    public Epsg9624(final double[][] ab) {
        this.r = new double[2][2];
        this.vt0 = new double[2];

        for (int i = 0; i < ab.length; i++) {
            this.vt0[i] = ab[i][0];
            this.r[i][0] = ab[i][1];
            this.r[i][1] = ab[i][2];
        }

        final double d = DoubleTabulars.determinant22(r);

        // ne servirait que si on utilisait une méthode non-matricielle pour calculer la transformation inverse
        this.vs0 = new double[]{
            (r[0][1] * vt0[1] - r[1][1] * vt0[0]) / d,
            (r[1][0] * vt0[0] - r[0][0] * vt0[0]) / d,
        };

        this.rr = new double[][]{
            {r[1][1] / d, -r[0][1] / d},
            {-r[1][0] / d, r[0][0] / d}
        };
    }

    public static Epsg9624 ofParams(final Map<MethodParameter, ?> params) {
        return new Epsg9624(new double[][]{{
            (double) params.get(MethodParameter.A0),
            (double) params.get(MethodParameter.A1),
            (double) params.get(MethodParameter.A2)},
            {(double) params.get(MethodParameter.B0),
            (double) params.get(MethodParameter.B1),
            (double) params.get(MethodParameter.B2)}});
    }

    @Override
    public double[] compute(final double[] input) {
        return DoubleTabulars.add(vt0, DoubleTabulars.mult(r, input));
    }

    @Override
    public double[] inverse(final double[] input) {
        return DoubleTabulars.mult(rr, DoubleTabulars.minus(input, vt0));
    }

    @Override
    public List<MethodParameter> getParameters() {
        return List.of(MethodParameter.A0,
                MethodParameter.A1,
                MethodParameter.A2,
                MethodParameter.B0,
                MethodParameter.B1,
                MethodParameter.B2);
    }

    @Override
    public Object getParameter(final MethodParameter parameter) {
        return switch (parameter) {
            case A0 -> vt0[0];
            case A1 -> r[0][0];
            case A2 -> r[0][1];
            case B0 -> vt0[1];
            case B1 -> r[1][0];
            case B2 -> r[1][0];
            default -> throw new IllegalArgumentException();
        };
    }
}
