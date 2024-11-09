package com.cosmoloj.math.operation.transformation;

import com.cosmoloj.bibliography.cosmoloj.Cosmoloj;
import com.cosmoloj.math.operation.AutoInverse;
import com.cosmoloj.math.operation.MethodParameter;
import com.cosmoloj.math.tabular.core.DoubleTabulars;
import com.cosmoloj.util.bib.Cite;
import java.util.List;
import java.util.Map;

/**
 * <div>EPSG::9623</div>
 * <div class="en">Affine Geometric Transformation</div>
 *
 * @author Samuel Andrés
 */
@Cite(Cosmoloj.IOGP_GUIDANCE_NOTE_7_2_2019)
public class Epsg9623 implements InversibleCoordinateTransformation, AutoInverse<double[]> {

    private final double k;

    /**
     * cosThetax, sinThetay
     * -sinThetax, cosThetay
     */
    private final double[][] r;

    /**
     * mx 0
     * 0 my
     */
    private final double[][] s;

    /**
     * xt0
     * yt0
     */
    private final double[] vt0;

    /**
     * cosThetay, -sinThetay
     * sinThetax, cosThetax
     */
    private final double[][] rr;

    /**
     * 1/mx 0
     * 0 1/my
     */
    private final double[][] sr;

    private final double z;
    private final double thetax;
    private final double thetay;

    public Epsg9623(final double xt0, final double yt0, final double mx, final double my, final double k,
            final double thetax, final double thetay) {

        this.k = k;

        this.r = new double[][]{
            {Math.cos(thetax), Math.sin(thetay)},
            {-Math.sin(thetax), Math.cos(thetay)}
        };

        this.s = DoubleTabulars.diagonal(new double[]{mx, my});

        this.vt0 = new double[]{xt0, yt0};

        this.rr = new double[][]{
            {Math.cos(thetay), -Math.sin(thetay)},
            {Math.sin(thetax), Math.cos(thetax)}
        };

        this.sr = DoubleTabulars.diagonal(new double[]{1. / mx, 1. / my});

        this.z = Math.cos(thetax - thetay);
        this.thetax = thetax;
        this.thetay = thetay;
    }

    public static Epsg9623 ofParams(final Map<MethodParameter, ?> params) {
        return new Epsg9623(
            (double) params.get(MethodParameter.ORDINATE_1_OF_EVALUATION_POINT_IN_TARGET_CRS),
            (double) params.get(MethodParameter.ORDINATE_2_OF_EVALUATION_POINT_IN_TARGET_CRS),
            (double) params.get(MethodParameter.SCALE_FACTOR_FOR_SOURCE_CRS_FIRST_AXIS),
            (double) params.get(MethodParameter.SCALE_FACTOR_FOR_SOURCE_CRS_SECOND_AXIS),
            (double) params.get(MethodParameter.POINT_SCALE_FACTOR),
            (double) params.get(MethodParameter.ROTATION_ANGLE_OF_SOURCE_CRS_FIRST_AXIS),
            (double) params.get(MethodParameter.ROTATION_ANGLE_OF_SOURCE_CRS_SECOND_AXIS));
    }

    @Override
    public double[] compute(final double[] input) {
        return DoubleTabulars.add(vt0,
                DoubleTabulars.mult(DoubleTabulars.mult(DoubleTabulars.external(k, r), s), input));
    }

    @Override
    public double[] inverse(final double[] input) {
        return DoubleTabulars.mult(DoubleTabulars.mult(
                DoubleTabulars.external(1. / k / z, sr), rr), DoubleTabulars.minus(input, vt0));
    }

    @Override
    public List<MethodParameter> getParameters() {
        return List.of(MethodParameter.ORDINATE_1_OF_EVALUATION_POINT_IN_TARGET_CRS,
            MethodParameter.ORDINATE_2_OF_EVALUATION_POINT_IN_TARGET_CRS,
            MethodParameter.SCALE_FACTOR_FOR_SOURCE_CRS_FIRST_AXIS,
            MethodParameter.SCALE_FACTOR_FOR_SOURCE_CRS_SECOND_AXIS,
            MethodParameter.POINT_SCALE_FACTOR,
            MethodParameter.ROTATION_ANGLE_OF_SOURCE_CRS_FIRST_AXIS,
            MethodParameter.ROTATION_ANGLE_OF_SOURCE_CRS_SECOND_AXIS);
    }

    @Override
    public Object getParameter(final MethodParameter parameter) {
        return switch (parameter) {
            case ORDINATE_1_OF_EVALUATION_POINT_IN_TARGET_CRS -> vt0[0];
            case ORDINATE_2_OF_EVALUATION_POINT_IN_TARGET_CRS -> vt0[1];
            case SCALE_FACTOR_FOR_SOURCE_CRS_FIRST_AXIS -> s[0][0];
            case SCALE_FACTOR_FOR_SOURCE_CRS_SECOND_AXIS -> s[1][1];
            case POINT_SCALE_FACTOR -> k;
            case ROTATION_ANGLE_OF_SOURCE_CRS_FIRST_AXIS -> thetax;
            case ROTATION_ANGLE_OF_SOURCE_CRS_SECOND_AXIS -> thetay;
            default -> throw new IllegalArgumentException();
        };
    }
}
