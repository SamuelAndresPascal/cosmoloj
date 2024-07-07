package com.cosmoloj.math.operation.trials;

/**
 *
 * @author Samuel Andrés
 * @param <T>
 */
public interface Trials<T> {

    T trial(T input);

    T init();

    boolean test(T first, T second);

    default T loop() {
        T it = trial(init());

        while (true) {
            final T tmp = trial(it);
            if (test(tmp, it)) {
                it = tmp;
                break;
            }
            it = tmp;
        }
        return it;
    }

}
