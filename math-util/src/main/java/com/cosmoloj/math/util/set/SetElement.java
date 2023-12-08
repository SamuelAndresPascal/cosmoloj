package com.cosmoloj.math.util.set;

/**
 *
 * @author Samuel Andrés
 * @param <E>
 * @param <S>
 */
public interface SetElement<E extends SetElement<E, S>, S extends AlgebricSet> {

    S set();
}
