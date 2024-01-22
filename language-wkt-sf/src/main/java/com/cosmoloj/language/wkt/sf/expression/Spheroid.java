package com.cosmoloj.language.wkt.sf.expression;

import com.cosmoloj.language.common.number.lexeme.compound.SignedNumericLiteral;
import com.cosmoloj.language.common.impl.semantic.AbstractExpression;
import com.cosmoloj.language.common.impl.semantic.EnumLexeme;
import com.cosmoloj.language.wkt.sf.lexeme.QuotedName;
import com.cosmoloj.language.wkt.sf.lexeme.WktName;

/**
 *
 * @author Samuel Andrés
 */
public class Spheroid extends AbstractExpression {

    private final EnumLexeme<WktName> label;
    private final QuotedName name;
    private final SignedNumericLiteral semiMajorAxis;
    private final SignedNumericLiteral inverseFlattening;

    public Spheroid(final int start, final int end, final int index, final EnumLexeme<WktName> label,
            final QuotedName name, final SignedNumericLiteral semiMajorAxis,
            final SignedNumericLiteral inverseFlattening) {
        super(start, end, index);
        this.label = label;
        this.name = name;
        this.semiMajorAxis = semiMajorAxis;
        this.inverseFlattening = inverseFlattening;
    }

    public EnumLexeme<WktName> getLabel() {
        return this.label;
    }

    public QuotedName getName() {
        return this.name;
    }

    public SignedNumericLiteral getSemiMajorAxis() {
        return this.semiMajorAxis;
    }

    public SignedNumericLiteral getInverseFlattening() {
        return this.inverseFlattening;
    }
}
