package com.cosmoloj.language.wkt.sf.expression;

import com.cosmoloj.language.common.number.lexeme.compound.SignedNumericLiteral;
import com.cosmoloj.language.common.impl.semantic.AbstractExpression;
import com.cosmoloj.language.wkt.sf.lexeme.QuotedName;

/**
 *
 * @author Samuel Andrés
 */
public class Parameter extends AbstractExpression {

    private final QuotedName name;
    private final SignedNumericLiteral value;

    public Parameter(final int start, final int end, final int index,
            final QuotedName name, final SignedNumericLiteral value) {
        super(start, end, index);
        this.name = name;
        this.value = value;
    }

    public QuotedName getName() {
        return this.name;
    }

    public SignedNumericLiteral getValue() {
        return this.value;
    }
}
