package com.cosmoloj.language.json.lexeme.compound;

import com.cosmoloj.language.common.number.lexeme.compound.SignedInteger;
import com.cosmoloj.language.common.number.lexeme.compound.SignedIntegerBuilder;

/**
 *
 * @author Samuel Andrés
 */
public class JsonSignedInteger extends SignedInteger {

    public JsonSignedInteger(final String codePoints, final int start, final int end, final int index) {
        super(codePoints, start, end, index);
    }

    public static SignedIntegerBuilder builder() {
        return new SignedIntegerBuilder() {

            @Override
            public JsonSignedInteger build() {
                return new JsonSignedInteger(codePoints(), first(), last(), index());
            }
        };
    }
}
