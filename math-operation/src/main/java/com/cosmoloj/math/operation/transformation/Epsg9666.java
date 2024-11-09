package com.cosmoloj.math.operation.transformation;

import com.cosmoloj.bibliography.cosmoloj.Cosmoloj;
import com.cosmoloj.math.operation.MethodParameter;
import com.cosmoloj.math.tabular.core.DoubleTabulars;
import com.cosmoloj.util.bib.Cite;
import java.util.List;
import java.util.Map;

/**
 * <div>EPSG::9666</div>
 * <div class="en">P6 Right-handed Seismic Bin Grid Transformation</div>
 *
 * @author Samuel Andrés
 */
@Cite(Cosmoloj.IOGP_GUIDANCE_NOTE_7_2_2019)
public class Epsg9666 extends Epsg9622 {

    private final double[] vso;
    private final double mx;
    private final double my;
    private final double iBinIncr;
    private final double jBinIncr;

    public Epsg9666(final double xso,
            final double yso,
            final double xto,
            final double yto,
            final double k,
            final double mx,
            final double my,
            final double theta,
            final double iBinIncr,
            final double jBinIncr) {
        super(xto, yto, mx / iBinIncr, my / jBinIncr, k, theta);
        this.vso = new double[]{xso, yso};
        this.mx = mx;
        this.my = my;
        this.iBinIncr = iBinIncr;
        this.jBinIncr = jBinIncr;
    }

    public static Epsg9666 ofParams(final Map<MethodParameter, ?> params) {
        return new Epsg9666(
            (double) params.get(MethodParameter.BIN_GRID_ORIGIN_I),
            (double) params.get(MethodParameter.BIN_GRID_ORIGIN_J),
            (double) params.get(MethodParameter.BIN_GRID_ORIGIN_EASTING),
            (double) params.get(MethodParameter.BIN_GRID_ORIGIN_NORTHING),
            (double) params.get(MethodParameter.SCALE_FACTOR_OF_BIN_GRID),
            (double) params.get(MethodParameter.BIN_WIDTH_ON_I_AXIS),
            (double) params.get(MethodParameter.BIN_WIDTH_ON_J_AXIS),
            (double) params.get(MethodParameter.MAP_GRID_BEARING_OF_BIN_GRID_J_AXIS),
            (double) params.get(MethodParameter.BIN_NODE_INCREMENT_ON_I_AXIS),
            (double) params.get(MethodParameter.BIN_NODE_INCREMENT_ON_J_AXIS));
    }

    @Override
    public double[] compute(final double[] input) {
        return super.compute(DoubleTabulars.minus(input, vso));
    }

    @Override
    public double[] inverse(final double[] input) {
        return DoubleTabulars.add(super.inverse(input), vso);
    }

    @Override
    public List<MethodParameter> getParameters() {
        return List.of(MethodParameter.BIN_GRID_ORIGIN_I,
            MethodParameter.BIN_GRID_ORIGIN_J,
            MethodParameter.BIN_GRID_ORIGIN_EASTING,
            MethodParameter.BIN_GRID_ORIGIN_NORTHING,
            MethodParameter.SCALE_FACTOR_OF_BIN_GRID,
            MethodParameter.BIN_WIDTH_ON_I_AXIS,
            MethodParameter.BIN_WIDTH_ON_J_AXIS,
            MethodParameter.MAP_GRID_BEARING_OF_BIN_GRID_J_AXIS,
            MethodParameter.BIN_NODE_INCREMENT_ON_I_AXIS,
            MethodParameter.BIN_NODE_INCREMENT_ON_J_AXIS);
    }

    @Override
    public Object getParameter(final MethodParameter parameter) {
        return switch (parameter) {
            case BIN_GRID_ORIGIN_I -> vso[0];
            case BIN_GRID_ORIGIN_J -> vso[1];
            case BIN_GRID_ORIGIN_EASTING ->
                super.getParameter(MethodParameter.ORDINATE_1_OF_EVALUATION_POINT_IN_TARGET_CRS);
            case BIN_GRID_ORIGIN_NORTHING ->
                super.getParameter(MethodParameter.ORDINATE_2_OF_EVALUATION_POINT_IN_TARGET_CRS);
            case SCALE_FACTOR_OF_BIN_GRID -> super.getParameter(MethodParameter.POINT_SCALE_FACTOR);
            case BIN_WIDTH_ON_I_AXIS -> mx;
            case BIN_WIDTH_ON_J_AXIS -> my;
            case MAP_GRID_BEARING_OF_BIN_GRID_J_AXIS
                -> super.getParameter(MethodParameter.ROTATION_ANGLE_OF_SOURCE_CRS_AXES);
            case BIN_NODE_INCREMENT_ON_I_AXIS -> iBinIncr;
            case BIN_NODE_INCREMENT_ON_J_AXIS -> jBinIncr;
            default -> throw new IllegalArgumentException();
        };
    }
}
