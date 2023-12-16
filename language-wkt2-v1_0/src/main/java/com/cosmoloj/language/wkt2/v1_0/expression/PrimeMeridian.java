package com.cosmoloj.language.wkt2.v1_0.expression;

import com.cosmoloj.language.common.impl.semantic.AbstractExpression;
import com.cosmoloj.language.common.number.lexeme.compound.SignedNumericLiteral;
import com.cosmoloj.language.wkt2.v1_0.lexeme.simple.QuotedLatinText;
import java.util.List;
import java.util.function.Predicate;

/**
 *
 * @author Samuel Andrés
 */
public class PrimeMeridian extends AbstractExpression {

    public static final Predicate<Object> INSTANCE_OF = t -> t instanceof PrimeMeridian;

    private final QuotedLatinText name;
    private final SignedNumericLiteral irmLongitude;
    private final Unit.Angle unit;
    private final List<Identifier> identifiers;

    public PrimeMeridian(final int start, final int end, final int index, final QuotedLatinText name,
            final SignedNumericLiteral irmLongitude, final Unit.Angle unit,
            final List<Identifier> identifiers) {
        super(start, end, index);
        this.name = name;
        this.irmLongitude = irmLongitude;
        this.unit = unit;
        this.identifiers = identifiers;
    }

    public QuotedLatinText getName() {
        return name;
    }

    public SignedNumericLiteral getIrmLongitude() {
        return irmLongitude;
    }

    public Unit.Angle getUnit() {
        return unit;
    }

    public List<Identifier> getIdentifiers() {
        return identifiers;
    }
}
