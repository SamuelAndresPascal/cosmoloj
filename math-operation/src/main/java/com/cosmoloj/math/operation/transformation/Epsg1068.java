package com.cosmoloj.math.operation.transformation;

import com.cosmoloj.bibliography.cosmoloj.Cosmoloj;
import com.cosmoloj.math.operation.AutoInverse;
import com.cosmoloj.math.operation.MethodParameter;
import com.cosmoloj.util.bib.Reference;
import java.util.List;

/**
 * <div>EPSG::1068</div>
 * <div class="en">Height Depth Reversal</div>
 *
 * @author Samuel Andrés
 */
@Reference(Cosmoloj.IOGP_GUIDANCE_NOTE_7_2_2019)
public class Epsg1068 implements OffsetTransformation<Double>, AutoInverse<Double> {

    private static final Epsg1068 INSTANCE = new Epsg1068();

    public static Epsg1068 instance() {
        return INSTANCE;
    }

    @Override
    public Double compute(final Double input) {
        return -input;
    }

    @Override
    public Double inverse(final Double input) {
        return -input;
    }

    @Override
    public List<MethodParameter> getParameters() {
        return List.of();
    }

    @Override
    public Object getParameter(final MethodParameter parameter) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
