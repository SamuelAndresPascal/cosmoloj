package com.cosmoloj.math.operation.projection;

import com.cosmoloj.math.operation.conversion.CoordinateConversion;

/**
 *
 * @author Samuel Andrés
 */
public interface Projection extends CoordinateConversion {

    default boolean canProject(final double[] input) {
        return true;
    }
}
