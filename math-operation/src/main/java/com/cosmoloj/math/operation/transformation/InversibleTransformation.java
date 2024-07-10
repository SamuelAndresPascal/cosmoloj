package com.cosmoloj.math.operation.transformation;

import com.cosmoloj.math.operation.InvertibleOperation;

/**
 *
 * @author Samuel Andrés
 * @param <I>
 * @param <O>
 */
public interface InversibleTransformation<I, O> extends Transformation<I, O>, InvertibleOperation<I, O> {
}
