package com.cosmoloj.math.util.set;

/**
 *
 * @author Samuel Andrés
 * @param <E>
 * @param <R>
 */
public interface RingElement<E extends RingElement<E, R>, R extends Ring<E>> extends GroupElement<E, R> {

    default E plus(final E e) {
        return associative(e);
    }

    default E opposite() {
        return symetrical();
    }

    default E minus(final E e) {
        return associative(e.symetrical());
    }

    E mult(E e);

    E inverse();
}
