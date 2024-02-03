package com.cosmoloj.language.json.lexeme.simple;

import com.cosmoloj.language.common.text.lexeme.simple.StringLiteral;
import com.cosmoloj.language.common.text.lexeme.simple.StringLiteralBuilder;
import com.cosmoloj.language.json.expression.JsonValue;

/**
 *
 * @author Samuel Andrés
 */
public class QuotedString extends StringLiteral implements JsonValue {

    private static final int[][] MAP = new int[][]{{'n', '\n'},
        {'/', '/'},
        {'b', '\b'},
        {'f', '\f'},
        {'n', '\n'},
        {'r', '\r'},
        {'t', '\t'}};

    public QuotedString(final String codePoints, final int first, final int last, final int index) {
        super(codePoints, first, last, index, '"', '\\', StringLiteral.buildEscapeMap(MAP));
    }

    public static StringLiteralBuilder builder() {
        return new StringLiteralBuilder(
                QuotedString.class, '"', '\\', StringLiteralBuilder.buildEscapeMap(QuotedString.MAP)) {

            @Override
            public QuotedString build(final int first, final int last, final int index) {
                return new QuotedString(codePoints(), first, last, index);
            }
        };
    }
}
