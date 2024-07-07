package com.cosmoloj.math.operation.trials;

/**
 *
 * @author Samuel Andrés
 */
public abstract class AbstractInitTrials implements DoubleTrials {

    private final double init;

    protected AbstractInitTrials(final double init) {
        this.init = init;
    }

    @Override
    public double init() {
        return init;
    }
}
