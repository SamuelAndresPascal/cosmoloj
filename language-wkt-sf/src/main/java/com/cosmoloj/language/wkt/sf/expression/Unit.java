package com.cosmoloj.language.wkt.sf.expression;

import com.cosmoloj.language.common.number.lexeme.compound.SignedNumericLiteral;
import com.cosmoloj.language.common.impl.semantic.AbstractExpression;
import com.cosmoloj.language.wkt.sf.lexeme.QuotedName;

/**
 *
 * @author Samuel Andrés
 */
public class Unit extends AbstractExpression {

    private final QuotedName name;
    private final SignedNumericLiteral conversionFactor;

    public Unit(final int start, final int end, final int index,
            final QuotedName name, final SignedNumericLiteral conversionFactor) {
        super(start, end, index);
        this.name = name;
        this.conversionFactor = conversionFactor;
    }

    public QuotedName getName() {
        return this.name;
    }

    public SignedNumericLiteral getConversionFactor() {
        return this.conversionFactor;
    }
}
