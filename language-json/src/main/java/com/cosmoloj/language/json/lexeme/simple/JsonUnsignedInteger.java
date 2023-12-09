package com.cosmoloj.language.json.lexeme.simple;

import com.cosmoloj.language.common.number.lexeme.simple.UnsignedInteger;
import com.cosmoloj.language.common.number.lexeme.simple.UnsignedIntegerBuilder;
import com.cosmoloj.language.json.expression.JsonValue;

/**
 *
 * @author Samuel Andrés
 */
public class JsonUnsignedInteger extends UnsignedInteger implements JsonValue {

    public JsonUnsignedInteger(final String codePoints, final int first, final int last, final int index) {
        super(codePoints, first, last, index);
    }

    public static UnsignedIntegerBuilder builder() {
        return new UnsignedIntegerBuilder(Character::isDigit) {

            @Override
            public JsonUnsignedInteger build(final int first, final int last, final int index) {
                return new JsonUnsignedInteger(codePoints(), first, last, index);
            }
        };
    }
}
