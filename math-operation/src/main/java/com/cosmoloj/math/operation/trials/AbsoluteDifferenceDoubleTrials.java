package com.cosmoloj.math.operation.trials;

/**
 *
 * @author Samuel Andrés
 */
public interface AbsoluteDifferenceDoubleTrials extends DoubleTrials {

    double precision();

    @Override
    default boolean test(final double first, final double second) {
        return Math.abs(first - second) < precision();
    }
}
