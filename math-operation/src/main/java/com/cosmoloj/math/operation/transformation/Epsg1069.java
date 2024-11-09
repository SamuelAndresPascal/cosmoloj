package com.cosmoloj.math.operation.transformation;

import com.cosmoloj.bibliography.cosmoloj.Cosmoloj;
import com.cosmoloj.math.operation.AutoInverse;
import com.cosmoloj.math.operation.MethodParameter;
import com.cosmoloj.util.bib.Cite;
import java.util.List;
import java.util.Map;

/**
 * <div>EPSG::1069</div>
 * <div class="en">Change of Vertical Unit</div>
 *
 * @author Samuel Andrés
 */
@Cite(Cosmoloj.IOGP_GUIDANCE_NOTE_7_2_2019)
public class Epsg1069 implements OffsetTransformation<Double>, AutoInverse<Double> {

    private final double f;

    public Epsg1069(final double f) {
        this.f = f;
    }

    public static Epsg1069 ofParams(final Map<MethodParameter, ?> params) {
        return new Epsg1069((double) params.get(MethodParameter.UNIT_CONVERSION_SCALAR));
    }

    @Override
    public Double compute(final Double input) {
        return input * f;
    }

    @Override
    public Double inverse(final Double input) {
        return input / f;
    }

    @Override
    public List<MethodParameter> getParameters() {
        return List.of(MethodParameter.UNIT_CONVERSION_SCALAR);
    }

    @Override
    public Object getParameter(final MethodParameter parameter) {
        if (MethodParameter.UNIT_CONVERSION_SCALAR.equals(parameter)) {
            return f;
        } else {
            throw new IllegalArgumentException();
        }
    }
}
