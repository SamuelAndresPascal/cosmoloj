package com.cosmoloj.math.operation.transformation;

import com.cosmoloj.bibliography.cosmoloj.Cosmoloj;
import com.cosmoloj.math.operation.MethodParameter;
import com.cosmoloj.util.bib.Cite;
import java.util.Map;

/**
 * <div>EPSG::1049</div>
 * <div class="en">P6 Left-handed Seismic Bin Grid Transformation</div>
 *
 * @author Samuel Andrés
 */
@Cite(Cosmoloj.IOGP_GUIDANCE_NOTE_7_2_2019)
public class Epsg1049 extends Epsg9666 {

    public Epsg1049(final double xso,
            final double yso,
            final double xto,
            final double yto,
            final double k,
            final double mx,
            final double my,
            final double theta,
            final double iBinIncr,
            final double jBinIncr) {
        super(xso, yso, xto, yto, k, -mx, my, theta, iBinIncr, jBinIncr);
    }

    public static Epsg1049 ofParams(final Map<MethodParameter, ?> params) {
        return new Epsg1049(
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
}
