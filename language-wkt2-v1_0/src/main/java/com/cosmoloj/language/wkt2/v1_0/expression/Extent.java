package com.cosmoloj.language.wkt2.v1_0.expression;

import com.cosmoloj.language.common.impl.semantic.AbstractExpression;

/**
 *
 * @author Samuel Andrés
 */
public abstract class Extent extends AbstractExpression {

    protected Extent(final int first, final int last, final int index) {
        super(first, last, index);
    }
}
