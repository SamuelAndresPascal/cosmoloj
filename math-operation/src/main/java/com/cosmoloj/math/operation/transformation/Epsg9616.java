package com.cosmoloj.math.operation.transformation;

import com.cosmoloj.bibliography.cosmoloj.Cosmoloj;
import com.cosmoloj.math.operation.AutoInverse;
import com.cosmoloj.math.operation.MethodParameter;
import com.cosmoloj.util.bib.Reference;
import java.util.List;
import java.util.Map;

/**
 * <div>EPSG::9616</div>
 * <div class="en">Vertical Offset</div>
 *
 * @author Samuel Andrés
 */
@Reference(Cosmoloj.IOGP_GUIDANCE_NOTE_7_2_2019)
public class Epsg9616 implements OffsetTransformation<Double>, AutoInverse<Double> {

    private final double a;

    public Epsg9616(final double a) {
        this.a = a;
    }

    public static Epsg9616 ofParams(final Map<MethodParameter, ?> params) {
        return new Epsg9616((double) params.get(MethodParameter.VERTICAL_OFFSET));
    }

    @Override
    public Double compute(final Double input) {
        return input + a;
    }

    @Override
    public Double inverse(final Double input) {
        return input - a;
    }

    @Override
    public List<MethodParameter> getParameters() {
        return List.of(MethodParameter.VERTICAL_OFFSET);
    }

    @Override
    public Object getParameter(final MethodParameter parameter) {
        if (MethodParameter.VERTICAL_OFFSET.equals(parameter)) {
            return a;
        } else {
            throw new IllegalArgumentException();
        }
    }
}
