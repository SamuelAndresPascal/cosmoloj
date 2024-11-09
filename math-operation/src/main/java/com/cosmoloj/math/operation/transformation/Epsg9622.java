package com.cosmoloj.math.operation.transformation;

import com.cosmoloj.bibliography.cosmoloj.Cosmoloj;
import com.cosmoloj.math.operation.MethodParameter;
import com.cosmoloj.util.bib.Cite;
import java.util.List;
import java.util.Map;

/**
 * <div>EPSG::9622</div>
 * <div class="en">Affine orthogonal geometric transformation</div>
 *
 * @author Samuel Andrés
 */
@Cite(Cosmoloj.IOGP_GUIDANCE_NOTE_7_2_2019)
public class Epsg9622 extends Epsg9623 {

    public Epsg9622(final double xt0, final double yt0, final double mx, final double my, final double k,
            final double theta) {
        super(xt0, yt0, mx, my, k, theta, theta);
    }

    public static Epsg9622 ofParams(final Map<MethodParameter, ?> params) {
        return new Epsg9622(
            (double) params.get(MethodParameter.ORDINATE_1_OF_EVALUATION_POINT_IN_TARGET_CRS),
            (double) params.get(MethodParameter.ORDINATE_2_OF_EVALUATION_POINT_IN_TARGET_CRS),
            (double) params.get(MethodParameter.SCALE_FACTOR_FOR_SOURCE_CRS_FIRST_AXIS),
            (double) params.get(MethodParameter.SCALE_FACTOR_FOR_SOURCE_CRS_SECOND_AXIS),
            (double) params.get(MethodParameter.POINT_SCALE_FACTOR),
            (double) params.get(MethodParameter.ROTATION_ANGLE_OF_SOURCE_CRS_AXES));
    }

    @Override
    public List<MethodParameter> getParameters() {
        return List.of(MethodParameter.ORDINATE_1_OF_EVALUATION_POINT_IN_TARGET_CRS,
            MethodParameter.ORDINATE_2_OF_EVALUATION_POINT_IN_TARGET_CRS,
            MethodParameter.SCALE_FACTOR_FOR_SOURCE_CRS_FIRST_AXIS,
            MethodParameter.SCALE_FACTOR_FOR_SOURCE_CRS_SECOND_AXIS,
            MethodParameter.POINT_SCALE_FACTOR,
            MethodParameter.ROTATION_ANGLE_OF_SOURCE_CRS_AXES);
    }

    @Override
    public Object getParameter(final MethodParameter parameter) {
        return switch (parameter) {
            case ROTATION_ANGLE_OF_SOURCE_CRS_FIRST_AXIS, ROTATION_ANGLE_OF_SOURCE_CRS_SECOND_AXIS ->
                throw new IllegalArgumentException();
            case ROTATION_ANGLE_OF_SOURCE_CRS_AXES ->
                super.getParameter(MethodParameter.ROTATION_ANGLE_OF_SOURCE_CRS_FIRST_AXIS);
            default -> super.getParameter(parameter);
        };
    }
}
