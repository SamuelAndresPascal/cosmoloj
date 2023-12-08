package com.cosmoloj.math.util.set;

/**
 *
 * @author Samuel Andrés
 * @param <E>
 */
public interface Ring<E> extends Group<E> {

    default E addition(final E a, final E b) {
        return associative(a, b);
    }

    default E opposite(final E e) {
        return symetrical(e);
    }

    default E substraction(final E a, final E b) {
        return addition(a, opposite(b));
    }

    E multiplication(E a, E b);

    E inverse(E e);

    E identity();
}
