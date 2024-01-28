package com.cosmoloj.language.wkt2.v2_1.lexeme.simple;

import com.cosmoloj.language.common.text.lexeme.simple.StringLiteralBuilder;
import com.cosmoloj.language.wkt.sf.lexeme.QuotedName;

/**
 *
 * @author Samuel Andrés
 */
public class QuotedLatinText extends QuotedName {

    public QuotedLatinText(final String codePoints, final int first, final int last, final int index) {
        super(codePoints, first, last, index);
    }

    public static StringLiteralBuilder builder() {
        return new StringLiteralBuilder(QuotedLatinText.class, '"') {

            @Override
            public QuotedLatinText build(final int first, final int last, final int index) {
                return new QuotedLatinText(codePoints(), first, last, index);
            }
        };
    }
}
