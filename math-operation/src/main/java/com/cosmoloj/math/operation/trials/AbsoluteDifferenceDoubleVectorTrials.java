package com.cosmoloj.math.operation.trials;

import com.cosmoloj.math.tabular.core.DoubleTabulars;
import java.util.stream.DoubleStream;

/**
 *
 * @author Samuel Andrés
 */
public interface AbsoluteDifferenceDoubleVectorTrials extends DoubleVectorTrials {

    double precision();

    @Override
    default boolean test(final double[] first, final double[] second) {
        return DoubleTabulars.max(DoubleStream.of(DoubleTabulars.minus(first, second))
                .map(Math::abs)
                .toArray()) < precision();
    }
}
