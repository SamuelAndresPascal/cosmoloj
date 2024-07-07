package com.cosmoloj.math.operation.transformation;

import com.cosmoloj.math.operation.InversibleOperation;

/**
 *
 * @author Samuel Andrés
 * @param <I>
 * @param <O>
 */
public interface InversibleTransformation<I, O> extends Transformation<I, O>, InversibleOperation<I, O> {
}
