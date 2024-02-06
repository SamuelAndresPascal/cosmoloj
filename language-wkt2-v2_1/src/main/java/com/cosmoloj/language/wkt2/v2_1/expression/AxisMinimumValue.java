package com.cosmoloj.language.wkt2.v2_1.expression;

import com.cosmoloj.language.common.impl.semantic.AbstractExpression;
import com.cosmoloj.language.common.number.lexeme.compound.SignedNumericLiteral;

/**
 *
 * @author Samuel Andrés
 */
public class AxisMinimumValue extends AbstractExpression {

    private final SignedNumericLiteral value;

    protected AxisMinimumValue(final int start, final int end, final int index, final SignedNumericLiteral value) {
        super(start, end, index);
        this.value = value;
    }

    public SignedNumericLiteral getValue() {
        return value;
    }
}
