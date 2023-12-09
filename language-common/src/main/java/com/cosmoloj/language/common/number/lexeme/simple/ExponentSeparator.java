package com.cosmoloj.language.common.number.lexeme.simple;

import com.cosmoloj.language.common.impl.builder.SingleCodePointLexemeBuilder;
import com.cosmoloj.language.common.impl.semantic.SingleCodePointLexeme;
import java.util.function.Predicate;

/**
 *
 * @author Samuel Andrés
 */
public class ExponentSeparator extends SingleCodePointLexeme {

    public static final Predicate<Object> EXPONENT_SEPARATOR = t -> t instanceof ExponentSeparator;

    public ExponentSeparator(final int codePoint, final int first, final int last, final int index) {
        super(codePoint, first, last, index);
    }

    public static SingleCodePointLexemeBuilder builder(final int... exponentSeparators) {
        return new SingleCodePointLexemeBuilder(exponentSeparators, ExponentSeparator.class) {

            @Override
            public ExponentSeparator build(final int first, final int last, final int index) {
                return new ExponentSeparator(getCodePoint(), first, last, index);
            }
        };
    }
}
