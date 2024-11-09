package com.cosmoloj.math.operation.transformation;

import com.cosmoloj.bibliography.cosmoloj.Cosmoloj;
import com.cosmoloj.math.operation.AutoInverse;
import com.cosmoloj.math.operation.MethodParameter;
import com.cosmoloj.math.tabular.core.DoubleTabulars;
import com.cosmoloj.util.bib.Cite;
import java.util.List;
import java.util.Map;

/**
 * <div>EPSG::9621</div>
 * <div class="en">Similarity Transformation</div>
 *
 * @author Samuel Andrés
 */
@Cite(Cosmoloj.IOGP_GUIDANCE_NOTE_7_2_2019)
public class Epsg9621 implements InversibleCoordinateTransformation, AutoInverse<double[]> {

    private final double m;

    /**
     * cosTheta, sinTheta
     * -sinTheta, cosTheta
     */
    private final double[][] r;

    /**
     * xt0
     * yt0
     */
    private final double[] vt0;

    /**
     * cosTheta, -sinTheta
     * sinTheta, cosTheta
     */
    private final double[][] rr;
    private final double theta;

    public Epsg9621(final double epo1, final double epo2, final double m, final double theta) {

        this.m = m;

        this.r = DoubleTabulars.rotation2D(-theta);

        this.vt0 = new double[]{epo1, epo2};

        this.rr = DoubleTabulars.rotation2D(theta);
        this.theta = theta;
    }

    public static Epsg9621 ofParams(final Map<MethodParameter, ?> params) {
        return new Epsg9621(
            (double) params.get(MethodParameter.ORDINATE_1_OF_EVALUATION_POINT_IN_TARGET_CRS),
            (double) params.get(MethodParameter.ORDINATE_2_OF_EVALUATION_POINT_IN_TARGET_CRS),
            (double) params.get(MethodParameter.SCALE_DIFFERENCE),
            (double) params.get(MethodParameter.ROTATION_ANGLE_OF_SOURCE_CRS_AXES));
    }

    @Override
    public double[] compute(final double[] input) {
        return DoubleTabulars.add(vt0, DoubleTabulars.mult(DoubleTabulars.external(m, r), input));
    }

    @Override
    public double[] inverse(final double[] input) {
        return DoubleTabulars.mult(DoubleTabulars.external(1. / m, rr), DoubleTabulars.minus(input, vt0));
    }

    @Override
    public List<MethodParameter> getParameters() {
        return List.of(MethodParameter.ORDINATE_1_OF_EVALUATION_POINT_IN_TARGET_CRS,
                MethodParameter.ORDINATE_2_OF_EVALUATION_POINT_IN_TARGET_CRS,
                MethodParameter.SCALE_DIFFERENCE,
                MethodParameter.ROTATION_ANGLE_OF_SOURCE_CRS_AXES);
    }

    @Override
    public Object getParameter(final MethodParameter parameter) {
        return switch (parameter) {
            case ORDINATE_1_OF_EVALUATION_POINT_IN_TARGET_CRS -> vt0[0];
            case ORDINATE_2_OF_EVALUATION_POINT_IN_TARGET_CRS -> vt0[1];
            case SCALE_DIFFERENCE -> m;
            case ROTATION_ANGLE_OF_SOURCE_CRS_AXES -> theta;
            default -> throw new IllegalArgumentException();
        };
    }

}
