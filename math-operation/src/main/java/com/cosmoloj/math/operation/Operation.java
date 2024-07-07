package com.cosmoloj.math.operation;

import java.util.List;

/**
 *
 * <div class="en">
 * <p>coordinate operation
 *
 * <p>process using a mathematical model, based on a one-to-one relationship, that changes coordinates in a source
 * coordinate reference system to coordinates in a target coordinate reference system, or that changes coordinates at a
 * source coordinate epoch to coordinates at a target coordinate epoch within the same coordinate reference system
 *
 * </div>
 *
 * @author Samuel Andrés
 * @param <I>
 * @param <O>
 */
public interface Operation<I, O> {

    O compute(I input);

    List<MethodParameter> getParameters();

    Object getParameter(MethodParameter parameter);

    static String toString(final Operation<?, ?> op) {
        final StringBuilder sb = new StringBuilder();

        for (final MethodParameter p : op.getParameters()) {
            sb.append(p.name()).append(": ").append(op.getParameter(p)).append(System.lineSeparator());
        }

        return sb.toString();
    }
}
