package com.cosmoloj.language.wkt2.v1_0.expression;

import com.cosmoloj.language.common.impl.semantic.AbstractExpression;
import com.cosmoloj.language.common.number.lexeme.simple.UnsignedInteger;

/**
 *
 * @author Samuel Andrés
 */
public class AxisOrder extends AbstractExpression {

    private final UnsignedInteger value;

    public AxisOrder(final int start, final int end, final int index, final UnsignedInteger value) {
        super(start, end, index);
        this.value = value;
    }

    public UnsignedInteger getValue() {
        return value;
    }
}
