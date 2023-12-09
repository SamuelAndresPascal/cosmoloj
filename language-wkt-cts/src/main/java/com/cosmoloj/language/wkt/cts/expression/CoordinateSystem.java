package com.cosmoloj.language.wkt.cts.expression;

import com.cosmoloj.language.common.impl.semantic.AbstractExpression;
import com.cosmoloj.language.wkt.sf.lexeme.QuotedName;

/**
 *
 * @author Samuel Andrés
 */
public abstract class CoordinateSystem extends AbstractExpression {

    protected CoordinateSystem(final int start, final int end, final int index) {
        super(start, end, index);
    }

    public abstract QuotedName getName();
}
