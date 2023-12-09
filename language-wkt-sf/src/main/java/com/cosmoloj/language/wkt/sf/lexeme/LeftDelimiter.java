package com.cosmoloj.language.wkt.sf.lexeme;

import com.cosmoloj.language.common.impl.builder.SingleCodePointLexemeBuilder;
import com.cosmoloj.language.common.impl.semantic.SingleCodePointLexeme;

/**
 *
 * @author Samuel Andrés
 */
public class LeftDelimiter extends SingleCodePointLexeme {

    public LeftDelimiter(final int delimiter, final int first, final int last, final int index) {
        super(delimiter, first, last, index);
    }

    public static SingleCodePointLexemeBuilder builder(final int delimiterReference) {
        return new SingleCodePointLexemeBuilder(delimiterReference, LeftDelimiter.class) {

            @Override
            public LeftDelimiter build(final int first, final int last, final int index) {
                return new LeftDelimiter(getCodePoint(), first, last, index);
            }
        };
    }
}
