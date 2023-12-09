package com.cosmoloj.language.json.lexeme.simple;

import com.cosmoloj.language.api.semantic.Token;
import com.cosmoloj.language.common.LanguageUtil;
import com.cosmoloj.language.common.impl.builder.EnumLexemeBuilder;
import com.cosmoloj.language.common.impl.semantic.EnumLexeme;
import com.cosmoloj.language.common.impl.semantic.SemanticEnum;
import com.cosmoloj.language.json.expression.JsonValue;
import java.util.Locale;
import java.util.function.Predicate;

/**
 *
 * @author Samuel Andrés
 */
public enum Keyword implements SemanticEnum<Keyword>, Predicate<Token> {

    TRUE, FALSE, NULL;

    @Override
    public boolean test(final Token token) {
        return token instanceof Keyword.Lexeme && this.equals(((Keyword.Lexeme) token).getSemantics());
    }

    public static Keyword toEnum(final String candidate) {
        return LanguageUtil.toEnumLowerCase(candidate, values());
    }

    public static boolean exists(final String candidate) {
        return toEnum(candidate) != null;
    }

    public static final class Lexeme extends EnumLexeme<Keyword> implements JsonValue {

        private Lexeme(final String chars, final int start, final int end, final int index) {
            super(chars, start, end, index);
        }

        private Lexeme(final Lexeme toMap) {
            super(toMap);
        }

        @Override
        public Keyword parse(final String codePoints) {
            return toEnum(codePoints);
        }
    }

    public static Lexeme map(final Lexeme toMap) {
        return new Lexeme(toMap);
    }

    public static EnumLexemeBuilder<Keyword> builder() {
        return new EnumLexemeBuilder<Keyword>(Keyword.class, Keyword.values(), EnumLexemeBuilder.Case.LOWER,
                Locale.ROOT) {

            @Override
            public Lexeme build(final int first, final int last, final int index) {
                return new Lexeme(codePoints(), first, last, index);
            }
        };
    }
}
