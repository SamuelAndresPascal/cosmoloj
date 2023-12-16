package com.cosmoloj.language.wkt2.v1_0.lexeme.simple;

import com.cosmoloj.language.common.text.lexeme.simple.StringLiteralBuilder;
import com.cosmoloj.language.wkt.sf.lexeme.QuotedName;
import java.util.function.Predicate;

/**
 *
 * @author Samuel Andrés
 */
public class QuotedUnicodeText extends QuotedName {

    public static final Predicate<Object> INSTANCE_OF_QUOTED_UNICODE_TEXT = t -> t instanceof QuotedUnicodeText;

    public QuotedUnicodeText(final String codePoints, final int first, final int last, final int index) {
        super(codePoints, first, last, index);
    }

    public static StringLiteralBuilder builder() {
        return new StringLiteralBuilder(QuotedUnicodeText.class, '"') {

            @Override
            public QuotedUnicodeText build(final int first, final int last, final int index) {
                return new QuotedUnicodeText(codePoints(), first, last, index);
            }
        };
    }
}
