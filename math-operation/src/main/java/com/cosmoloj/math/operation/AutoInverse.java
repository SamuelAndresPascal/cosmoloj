package com.cosmoloj.math.operation;

import java.util.List;

/**
 *
 * @author Samuel Andrés
 * @param <I>
 */
public interface AutoInverse<I> extends InvertibleOperation<I, I> {

    @Override
    default AutoInverse<I> inverse() {
        final AutoInverse<I> parent = this;
        return new AutoInverse<I>() {
            @Override
            public I inverse(final I input) {
                return parent.compute(input);
            }

            @Override
            public I compute(final I input) {
                return parent.inverse(input);
            }

            @Override
            public List<MethodParameter> getParameters() {
                return parent.getParameters();
            }

            @Override
            public Object getParameter(final MethodParameter parameter) {
                return parent.getParameter(parameter);
            }
        };
    }
}
