package com.cosmoloj.math.operation.transformation;

import com.cosmoloj.math.operation.MethodParameter;
import com.cosmoloj.bibliography.cosmoloj.Cosmoloj;
import com.cosmoloj.util.bib.Cite;
import java.util.List;

/**
 *
 * @author Samuel Andrés
 */
@Cite(value = {Cosmoloj.JOACHIM_BOLJEN_2003, Cosmoloj.JOACHIM_BOLJEN_2004})
public class Epsg1036 implements CoordinateTransformation {

    @Override
    public double[] compute(final double[] input) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<MethodParameter> getParameters() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Object getParameter(final MethodParameter parameter) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
