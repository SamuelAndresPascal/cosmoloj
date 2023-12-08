package com.cosmoloj.math.util.set;

/**
 *
 * @author Samuel Andrés
 * @param <E>
 */
public interface Group<E> extends AlgebricSet {

    E associative(E a, E b);

    E symetrical(E e);

    E neutral();
}
