package com.cosmoloj.math.operation.conversion;

import com.cosmoloj.math.operation.InvertibleOperation;

/**
 *
 * @author Samuel Andrés
 * @param <I>
 * @param <O>
 */
public interface InversibleConversion<I, O> extends Conversion<I, O>, InvertibleOperation<I, O> {
}
