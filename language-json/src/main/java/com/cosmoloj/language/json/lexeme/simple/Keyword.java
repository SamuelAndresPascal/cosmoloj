package com.cosmoloj.language.json.lexeme.simple;

import com.cosmoloj.language.api.semantic.Lexeme;
import com.cosmoloj.language.common.impl.builder.EnumLexemeBuilder;
import com.cosmoloj.language.common.impl.semantic.EnumCase;
import com.cosmoloj.language.common.impl.semantic.EnumLexeme;
import com.cosmoloj.language.common.impl.semantic.SemanticEnum;
import com.cosmoloj.language.json.expression.JsonValue;

/**
 *
 * @author Samuel Andrés
 */
public enum Keyword implements SemanticEnum<Keyword> {

    TRUE, FALSE, NULL;

    private static class JsonValueLex extends EnumLexeme<Keyword> implements JsonValue {

        JsonValueLex(final String chars, final int start, final int end, final int index) {
            super(chars, start, end, index, Keyword.values(), EnumCase.LOWER);
        }

    }

    public static EnumLexemeBuilder<Keyword> builder() {
        return new EnumLexemeBuilder<>(Keyword.class, Keyword.values(), EnumCase.LOWER) {
            @Override
            public Lexeme build(final int first, final int last, final int index) {
                return new JsonValueLex(codePoints(), first, last, index);
            }
        };
    }
}
