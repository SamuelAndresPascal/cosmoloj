package com.cosmoloj.language.wkt2.v2_1.expression;

import com.cosmoloj.language.common.impl.semantic.AbstractExpression;
import com.cosmoloj.language.common.impl.semantic.EnumLexeme;
import com.cosmoloj.language.common.number.lexeme.compound.SignedNumericLiteral;
import com.cosmoloj.language.wkt2.v2_1.lexeme.simple.QuotedLatinText;
import com.cosmoloj.language.wkt2.v2_1.lexeme.simple.WktKeyword;
import java.util.List;

/**
 *
 * @author Samuel Andrés
 */
public class Ellipsoid extends AbstractExpression {

    private final EnumLexeme<WktKeyword> label;
    private final QuotedLatinText name;
    private final SignedNumericLiteral semiMajorAxis;
    private final SignedNumericLiteral inverseFlattening;
    private final Unit.Length unit;
    private final List<Identifier> identifiers;

    public Ellipsoid(final int start, final int end, final int index, final EnumLexeme<WktKeyword> label,
            final QuotedLatinText name, final SignedNumericLiteral semiMajorAxis,
            final SignedNumericLiteral inverseFlattening, final Unit.Length unit, final List<Identifier> identifiers) {
        super(start, end, index);
        this.label = label;
        this.name = name;
        this.semiMajorAxis = semiMajorAxis;
        this.inverseFlattening = inverseFlattening;
        this.unit = unit;
        this.identifiers = identifiers;
    }

    public EnumLexeme<WktKeyword> getLabel() {
        return label;
    }

    public QuotedLatinText getName() {
        return name;
    }

    public SignedNumericLiteral getSemiMajorAxis() {
        return semiMajorAxis;
    }

    public SignedNumericLiteral getInverseFlattening() {
        return inverseFlattening;
    }

    public Unit.Length getUnit() {
        return unit;
    }

    public List<Identifier> getIdentifiers() {
        return identifiers;
    }
}
