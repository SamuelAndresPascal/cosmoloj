package com.cosmoloj.language.json.lexeme.compound;

import com.cosmoloj.language.common.number.lexeme.compound.SignedNumericLiteral;
import com.cosmoloj.language.common.number.lexeme.compound.SignedNumericLiteralBuilder;
import com.cosmoloj.language.json.expression.JsonValue;

/**
 *
 * @author Samuel Andrés
 */
public class JsonSignedNumericLiteral extends SignedNumericLiteral implements JsonValue {

    public JsonSignedNumericLiteral(final String codePoints, final int start, final int end, final int index,
            final boolean decimal) {
        super(codePoints, start, end, index, decimal);
    }

    public static SignedNumericLiteralBuilder builder() {
        return new SignedNumericLiteralBuilder() {

            @Override
            public JsonSignedNumericLiteral build() {
                return new JsonSignedNumericLiteral(codePoints(), first(), last(), index(), isDecimal());
            }
        };
    }
}
