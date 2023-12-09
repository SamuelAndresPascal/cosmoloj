package com.cosmoloj.language.wkt.sf.expression;

import com.cosmoloj.language.common.impl.semantic.AbstractExpression;

/**
 *
 * @author Samuel Andrés
 */
public abstract class SpatialReferenceSystem extends AbstractExpression {

    public SpatialReferenceSystem(final int start, final int end, final int index) {
        super(start, end, index);
    }
}
