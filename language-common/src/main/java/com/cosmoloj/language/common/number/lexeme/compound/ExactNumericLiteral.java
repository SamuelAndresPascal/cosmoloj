package com.cosmoloj.language.common.number.lexeme.compound;

import java.util.function.Predicate;

/**
 *
 * @author Samuel Andrés
 */
public class ExactNumericLiteral extends UnsignedNumericLiteral {

    public static final Predicate<Object> EXACT_NUMERIC_LITERAL = t -> t instanceof ExactNumericLiteral;

    private final boolean decimal;

    public ExactNumericLiteral(final String codePoints, final int start, final int end, final int index,
            final boolean decimal) {
        super(codePoints, start, end, index);
        this.decimal = decimal;
    }

    @Override
    public Number parse(final String codePoints) {
        if (this.decimal) {
            return Double.parseDouble(codePoints);
        }
        return Integer.parseInt(codePoints);
    }

    @Override
    public final boolean isDecimal() {
        return this.decimal;
    }
}
