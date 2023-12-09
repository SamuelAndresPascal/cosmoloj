package com.cosmoloj.language.common.number.lexeme.compound;

import com.cosmoloj.language.common.impl.semantic.NumberLexeme;
import java.util.function.Predicate;

/**
 *
 * @author Samuel Andrés
 */
public abstract class UnsignedNumericLiteral extends NumberLexeme {

    public static final Predicate<Object> INSTANCE_OF = t -> t instanceof UnsignedNumericLiteral;

    public UnsignedNumericLiteral(final String codePoints, final int start, final int end, final int index) {
        super(codePoints, start, end, index);
    }

    public abstract boolean isDecimal();
}
