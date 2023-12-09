package com.cosmoloj.language.common.number.lexeme.simple;

import com.cosmoloj.language.common.impl.builder.SingleCodePointLexemeBuilder;
import com.cosmoloj.language.common.impl.semantic.SingleCodePointLexeme;
import java.util.function.Predicate;

/**
 *
 * @author Samuel Andrés
 */
public class DecimalSeparator extends SingleCodePointLexeme {

    public static final Predicate<Object> DECIMAL_SEPARATOR = t -> t instanceof DecimalSeparator;

    public DecimalSeparator(final int codePoint, final int first, final int last, final int index) {
        super(codePoint, first, last, index);
    }

    public static SingleCodePointLexemeBuilder builder(final int delimiterReference) {
        return new SingleCodePointLexemeBuilder(delimiterReference, DecimalSeparator.class) {

            @Override
            public DecimalSeparator build(final int first, final int last, final int index) {
                return new DecimalSeparator(getCodePoint(), first, last, index);
            }
        };
    }
}
