package com.cosmoloj.math.operation;

/**
 *
 * @author Samuel Andrés
 * @param <I>
 * @param <O>
 */
public interface InversibleOperation<I, O> extends Operation<I, O> {

    O inverse(I input);

    Operation<O, I> inverse();
}
