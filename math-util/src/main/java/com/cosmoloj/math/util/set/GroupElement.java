package com.cosmoloj.math.util.set;

/**
 *
 * @author Samuel Andrés
 * @param <E>
 * @param <G>
 */
public interface GroupElement<E extends GroupElement<E, G>, G extends Group<E>> extends SetElement<E, G> {

    E associative(E e);

    E symetrical();
}
