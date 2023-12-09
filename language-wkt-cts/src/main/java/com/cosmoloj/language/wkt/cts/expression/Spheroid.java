package com.cosmoloj.language.wkt.cts.expression;

import com.cosmoloj.language.common.impl.semantic.AbstractExpression;
import com.cosmoloj.language.common.number.lexeme.compound.SignedNumericLiteral;
import com.cosmoloj.language.wkt.cts.lexeme.WktName;
import com.cosmoloj.language.wkt.sf.lexeme.QuotedName;
import java.util.function.Predicate;

/**
 *
 * @author Samuel Andrés
 */
public class Spheroid extends AbstractExpression {

    public static final Predicate<Object> INSTANCE_OF = t -> t instanceof Spheroid;

    private final WktName.Lexeme label;
    private final QuotedName name;
    private final SignedNumericLiteral semiMajorAxis;
    private final SignedNumericLiteral inverseFlattening;
    private final Authority authority;

    public Spheroid(final int start, final int end, final int index, final WktName.Lexeme label, final QuotedName name,
            final SignedNumericLiteral semiMajorAxis, final SignedNumericLiteral inverseFlattening,
            final Authority authority) {
        super(start, end, index);
        this.label = label;
        this.name = name;
        this.semiMajorAxis = semiMajorAxis;
        this.inverseFlattening = inverseFlattening;
        this.authority = authority;
    }

    public WktName.Lexeme getLabel() {
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

    public Authority getAuthority() {
        return this.authority;
    }
}
