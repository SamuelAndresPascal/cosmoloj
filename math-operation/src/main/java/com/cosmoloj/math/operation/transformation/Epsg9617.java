package com.cosmoloj.math.operation.transformation;

import com.cosmoloj.bibliography.cosmoloj.Cosmoloj;
import com.cosmoloj.math.operation.MethodParameter;
import com.cosmoloj.math.util.MathUtil;
import com.cosmoloj.util.bib.Cite;
import java.util.List;
import java.util.Map;

/**
 * <div>EPSG::9617</div>
 * <div class="en">Polynomial transformation for Spain</div>
 *
 * @author Samuel Andrés
 */
@Cite(Cosmoloj.IOGP_GUIDANCE_NOTE_7_2_2019)
public class Epsg9617 implements CoordinateTransformation {

    private static final int PHI = 0;
    private static final int LAMBDA = 1;
    private static final int H = 2;

    private final double a0;
    private final double a1;
    private final double a2;
    private final double a3;
    private final double b00;
    private final double b0;
    private final double b1;
    private final double b2;
    private final double b3;

    public Epsg9617(final double a0, final double a1, final double a2, final double a3,
            final double b00, final double b0, final double b1, final double b2, final double b3) {
        this.a0 = a0;
        this.a1 = a1;
        this.a2 = a2;
        this.a3 = a3;
        this.b00 = b00;
        this.b0 = b0;
        this.b1 = b1;
        this.b2 = b2;
        this.b3 = b3;
    }

    public static Epsg9617 ofParams(final Map<MethodParameter, ?> params) {
        return new Epsg9617((double) params.get(MethodParameter.A0),
        (double) params.get(MethodParameter.A1),
        (double) params.get(MethodParameter.A2),
        (double) params.get(MethodParameter.A3),
        (double) params.get(MethodParameter.B00),
        (double) params.get(MethodParameter.B0),
        (double) params.get(MethodParameter.B1),
        (double) params.get(MethodParameter.B2),
        (double) params.get(MethodParameter.B3));
    }

    @Override
    public double[] compute(final double[] input) {
        final double phi = input[PHI];
        final double l = input[LAMBDA];
        final double h = input[H];

        return new double[]{
            phi + MathUtil.arcSecToDegrees(a0 + a1 * phi + a2 * l + a3 * h),
            l + MathUtil.arcSecToDegrees(b00 + b0 + b1 * phi + b2 * l + b3 * h),
            h
        };
    }

    @Override
    public List<MethodParameter> getParameters() {
        return List.of(MethodParameter.A0,
                MethodParameter.A1,
                MethodParameter.A2,
                MethodParameter.A3,
                MethodParameter.B00,
                MethodParameter.B0,
                MethodParameter.B1,
                MethodParameter.B2,
                MethodParameter.B3);
    }

    @Override
    public Object getParameter(final MethodParameter parameter) {
        return switch (parameter) {
            case A0 -> a0;
            case A1 -> a1;
            case A2 -> a2;
            case A3 -> a3;
            case B00 -> b00;
            case B0 -> b0;
            case B1 -> b1;
            case B2 -> b2;
            case B3 -> b3;
            default -> throw new IllegalArgumentException();
        };
    }
}
