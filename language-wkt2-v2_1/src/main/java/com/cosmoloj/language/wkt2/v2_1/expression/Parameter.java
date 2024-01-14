package com.cosmoloj.language.wkt2.v2_1.expression;

import com.cosmoloj.language.common.number.lexeme.compound.SignedNumericLiteral;
import com.cosmoloj.language.wkt2.v2_1.lexeme.simple.QuotedLatinText;
import java.util.List;
import java.util.function.Predicate;

/**
 *
 * @author Samuel Andrés
 */
public class Parameter extends AbstractParam {

    public static final Predicate<Object> INSTANCE_OF = t -> t instanceof Parameter;

    private final SignedNumericLiteral value;
    private final Unit unit;

    public Parameter(final int start, final int end, final int index, final QuotedLatinText name,
            final SignedNumericLiteral value, final Unit unit, final List<Identifier> identifiers) {
        super(start, end, index, name, identifiers);
        this.value = value;
        this.unit = unit;
    }

    public SignedNumericLiteral getValue() {
        return value;
    }

    public Unit getUnit() {
        return unit;
    }
}
