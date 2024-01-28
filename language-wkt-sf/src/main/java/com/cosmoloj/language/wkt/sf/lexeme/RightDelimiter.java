package com.cosmoloj.language.wkt.sf.lexeme;

import com.cosmoloj.language.common.impl.builder.SingleCodePointLexemeBuilder;
import com.cosmoloj.language.common.impl.semantic.SingleCodePointLexeme;

/**
 *
 * @author Samuel Andrés
 */
public class RightDelimiter extends SingleCodePointLexeme {

    public RightDelimiter(final int delimiter, final int first, final int last, final int index) {
        super(delimiter, first, last, index);
    }

    public static SingleCodePointLexemeBuilder<RightDelimiter> builder(final int delimiterReference) {
        return new SingleCodePointLexemeBuilder<>(delimiterReference, RightDelimiter.class) {

            @Override
            public RightDelimiter build(final int first, final int last, final int index) {
                return new RightDelimiter(getCodePoint(), first, last, index);
            }
        };
    }
}
