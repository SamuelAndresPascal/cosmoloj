package com.cosmoloj.math.operation.conversion;

import com.cosmoloj.math.operation.InversibleOperation;

/**
 *
 * @author Samuel Andrés
 * @param <I>
 * @param <O>
 */
public interface InversibleConversion<I, O> extends Conversion<I, O>, InversibleOperation<I, O> {
}
