package com.cosmoloj.language.wkt.sf.lexeme;

import com.cosmoloj.language.common.text.lexeme.simple.StringLiteral;
import com.cosmoloj.language.common.text.lexeme.simple.StringLiteralBuilder;

/**
 *
 * @author Samuel Andrés
 */
public class QuotedName extends StringLiteral {

    public QuotedName(final String codePoints, final int first, final int last, final int index) {
        super(codePoints, first, last, index, '"');
    }

    public static StringLiteralBuilder builder() {
        return new StringLiteralBuilder(QuotedName.class, '"') {

            @Override
            public QuotedName build(final int first, final int last, final int index) {
                return new QuotedName(codePoints(), first, last, index);
            }
        };
    }
}
