package com.cosmoloj.language.common.number.lexeme.compound;

import com.cosmoloj.language.common.impl.semantic.NumberLexeme;
import java.util.function.Predicate;

/**
 *
 * @author Samuel Andrés
 */
public class SignedNumericLiteral extends NumberLexeme {

    public static final Predicate<Object> INSTANCE_OF = t -> t instanceof SignedNumericLiteral;

    private final boolean decimal;

    public SignedNumericLiteral(final String codePoints, final int start, final int end, final int index,
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

    public boolean isDecimal() {
        return this.decimal;
    }
}
