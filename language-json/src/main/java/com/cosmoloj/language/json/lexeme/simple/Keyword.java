package com.cosmoloj.language.json.lexeme.simple;

import com.cosmoloj.language.api.semantic.Lexeme;
import com.cosmoloj.language.common.impl.builder.EnumLexemeBuilder;
import com.cosmoloj.language.common.impl.semantic.EnumLexeme;
import com.cosmoloj.language.common.impl.semantic.SemanticEnum;
import com.cosmoloj.language.json.expression.JsonValue;
import java.util.function.Predicate;

/**
 *
 * @author Samuel Andrés
 */
public enum Keyword implements SemanticEnum<Keyword>, Predicate<Object> {

    TRUE, FALSE, NULL;

    @Override
    public boolean test(final Object token) {
        return token instanceof Lexeme l && this.equals(l.getSemantics());
    }

    private static class JsonLex extends EnumLexeme.LowerCase<Keyword> implements JsonValue {

        JsonLex(final String chars, final int start, final int end, final int index) {
            super(chars, start, end, index, Keyword.values());
        }

    }

    public static EnumLexemeBuilder<Keyword> builder() {
        return new EnumLexemeBuilder<>(Keyword.class, Keyword.values(), EnumLexemeBuilder.Case.LOWER) {
            @Override
            public Lexeme build(final int first, final int last, final int index) {
                return new JsonLex(codePoints(), first, last, index);
            }
        };
    }
}
