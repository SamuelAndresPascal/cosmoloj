package com.cosmoloj.math.operation.trials;

/**
 *
 * @author Samuel Andrés
 */
public interface DoubleTrials {

    double trial(double input);

    double init();

    boolean test(double first, double second);

    default double loop() {
        double it = trial(init());

        while (true) {
            final double tmp = trial(it);
            if (test(tmp, it)) {
                it = tmp;
                break;
            }
            it = tmp;
        }
        return it;
    }

}
