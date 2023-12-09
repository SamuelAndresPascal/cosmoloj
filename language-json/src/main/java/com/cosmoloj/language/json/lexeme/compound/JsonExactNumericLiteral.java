package com.cosmoloj.language.json.lexeme.compound;

import com.cosmoloj.language.common.number.lexeme.compound.ExactNumericLiteral;
import com.cosmoloj.language.common.number.lexeme.compound.ExactNumericLiteralBuilder;
import com.cosmoloj.language.json.expression.JsonValue;

/**
 *
 * @author Samuel Andrés
 */
public class JsonExactNumericLiteral extends ExactNumericLiteral implements JsonValue {

    public JsonExactNumericLiteral(final String codePoints, final int start, final int end, final int index,
            final boolean decimal) {
        super(codePoints, start, end, index, decimal);
    }

    public static ExactNumericLiteralBuilder builder() {
        return new ExactNumericLiteralBuilder() {

            @Override
            public JsonExactNumericLiteral build() {
                return new JsonExactNumericLiteral(codePoints(), first(), last(), index(), isDecimal());
            }
        };
    }
}
