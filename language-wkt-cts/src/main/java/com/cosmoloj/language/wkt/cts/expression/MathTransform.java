package com.cosmoloj.language.wkt.cts.expression;

import com.cosmoloj.language.common.impl.semantic.AbstractExpression;

/**
 *
 * @author Samuel Andrés
 */
public abstract class MathTransform extends AbstractExpression {

    protected MathTransform(final int start, final int end, final int index) {
        super(start, end, index);
    }

}
