package com.cosmoloj.language.wkt.sf.expression;

import com.cosmoloj.language.common.number.lexeme.compound.SignedNumericLiteral;
import com.cosmoloj.language.common.impl.semantic.AbstractExpression;
import com.cosmoloj.language.wkt.sf.lexeme.QuotedName;
import java.util.function.Predicate;

/**
 *
 * @author Samuel Andrés
 */
public class Unit extends AbstractExpression {

    public static final Predicate<Object> INSTANCE_OF = t -> t instanceof Unit;

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
