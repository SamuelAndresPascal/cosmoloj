package com.cosmoloj.language.wkt2.v1_0.expression;

import com.cosmoloj.language.common.impl.semantic.AbstractExpression;
import com.cosmoloj.language.common.number.lexeme.compound.SignedNumericLiteral;
import com.cosmoloj.language.wkt2.v1_0.lexeme.simple.QuotedLatinText;
import com.cosmoloj.language.wkt2.v1_0.lexeme.simple.WktKeyword;
import java.util.List;
import java.util.function.Predicate;

/**
 *
 * @author Samuel Andrés
 */
public class Ellipsoid extends AbstractExpression {

    public static final Predicate<Object> INSTANCE_OF = t -> t instanceof Ellipsoid;

    private final WktKeyword.Lexeme label;
    private final QuotedLatinText name;
    private final SignedNumericLiteral semiMajorAxis;
    private final SignedNumericLiteral inverseFlattening;
    private final Unit.Length unit;
    private final List<Identifier> identifiers;

    public Ellipsoid(final int start, final int end, final int index, final WktKeyword.Lexeme label,
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

    public WktKeyword.Lexeme getLabel() {
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
