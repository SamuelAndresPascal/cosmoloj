package com.cosmoloj.math.operation.transformation;

import com.cosmoloj.math.operation.Operation;

/**
 * <div class="en">
 * <p>coordinate transformation
 *
 * <p>coordinate operation that changes coordinates in a source coordinate reference system to coordinates in a target
 * coordinate reference system in which the source and target coordinate reference systems are based on different datums
 *
 * <p>Note 1 to entry: A coordinate transformation uses parameters which are derived empirically. Any error in those
 * coordinates will be embedded in the coordinate transformation and when the coordinate transformation is applied the
 * embedded errors are transmitted to output coordinates.
 *
 * <p>Note 2 to entry: A coordinate transformation is colloquially sometimes referred to as a ‘datum transformation’.
 * This is erroneous. A coordinate transformation changes coordinate values. It does not change the definition of the
 * datum. In this document coordinates are referenced to a coordinate reference system. A coordinate transformation
 * operates between two coordinate reference systems, not between two datums.
 * </div>
 *
 * @author Samuel Andrés
 * @param <I>
 * @param <O>
 */
public interface Transformation<I, O> extends Operation<I, O> {
}
