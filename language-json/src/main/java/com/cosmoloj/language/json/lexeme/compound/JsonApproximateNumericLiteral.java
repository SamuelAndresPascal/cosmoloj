package com.cosmoloj.language.json.lexeme.compound;

import com.cosmoloj.language.common.number.lexeme.compound.ApproximateNumericLiteral;
import com.cosmoloj.language.common.number.lexeme.compound.ApproximateNumericLiteralBuilder;
import com.cosmoloj.language.json.expression.JsonValue;

/**
 *
 * @author Samuel Andrés
 */
public class JsonApproximateNumericLiteral extends ApproximateNumericLiteral implements JsonValue {

    public JsonApproximateNumericLiteral(final String codePoints, final int start, final int end, final int index) {
        super(codePoints, start, end, index);
    }

    public static ApproximateNumericLiteralBuilder builder() {
        return new ApproximateNumericLiteralBuilder() {

            @Override
            public JsonApproximateNumericLiteral build() {
                return new JsonApproximateNumericLiteral(codePoints(), first(), last(), index());
            }
        };
    }
}
