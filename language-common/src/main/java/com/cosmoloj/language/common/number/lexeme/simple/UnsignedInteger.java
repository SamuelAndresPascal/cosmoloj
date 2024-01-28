package com.cosmoloj.language.common.number.lexeme.simple;

import com.cosmoloj.language.common.impl.semantic.IntegerLexeme;
import java.util.function.IntPredicate;

/**
 *
 * @author Samuel Andrés
 */
public class UnsignedInteger extends IntegerLexeme {

    public UnsignedInteger(final String codePoints, final int first, final int last, final int index) {
        super(codePoints, first, last, index);
    }

    public static UnsignedIntegerBuilder builder(final IntPredicate isDigit) {
        return new UnsignedIntegerBuilder(isDigit);
    }
}
