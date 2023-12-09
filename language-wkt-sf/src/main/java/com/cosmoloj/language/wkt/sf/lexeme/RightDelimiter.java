package com.cosmoloj.language.wkt.sf.lexeme;

import com.cosmoloj.language.common.impl.builder.SingleCodePointLexemeBuilder;
import com.cosmoloj.language.common.impl.semantic.SingleCodePointLexeme;
import java.util.function.Predicate;

/**
 *
 * @author Samuel Andrés
 */
public class RightDelimiter extends SingleCodePointLexeme {

    public static final Predicate<Object> INSTANCE_OF = t -> t instanceof RightDelimiter;

    public RightDelimiter(final int delimiter, final int first, final int last, final int index) {
        super(delimiter, first, last, index);
    }

    public static SingleCodePointLexemeBuilder builder(final int delimiterReference) {
        return new SingleCodePointLexemeBuilder(delimiterReference, RightDelimiter.class) {

            @Override
            public RightDelimiter build(final int first, final int last, final int index) {
                return new RightDelimiter(getCodePoint(), first, last, index);
            }
        };
    }
}
