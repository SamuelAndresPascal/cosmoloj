package com.cosmoloj.math.operation.conversion;

import com.cosmoloj.math.operation.Operation;
import com.cosmoloj.math.operation.surface.Surface;

/**
 *
 * <div class="fr">
 * <p>coordinate conversion
 *
 * <p>coordinate operation that changes coordinates in a source coordinate reference system to coordinates in a target
 * coordinate reference system in which both coordinate reference systems are based on the same datum
 *
 * <p>Note 1 to entry: A coordinate conversion uses parameters which have specified values.
 *
 * <p>EXAMPLE 1           A mapping of ellipsoidal coordinates to Cartesian coordinates using a map projection.
 *
 * <p>EXAMPLE 2           Change of units such as from radians to degrees or from feet to metres.
 *
 * </div>
 *
 * @author Samuel Andrés
 * @param <I>
 * @param <O>
 */
public interface Conversion<I, O> extends Operation<I, O> {

    Surface getSurface();
}
