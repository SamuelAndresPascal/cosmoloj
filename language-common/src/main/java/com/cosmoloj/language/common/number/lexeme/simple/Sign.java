package com.cosmoloj.language.common.number.lexeme.simple;

import com.cosmoloj.language.common.impl.builder.EnumLexemeBuilder;
import com.cosmoloj.language.common.impl.semantic.EnumCase;
import com.cosmoloj.language.common.impl.semantic.EnumLexeme;
import com.cosmoloj.language.common.impl.semantic.SemanticEnum;
import java.util.function.Predicate;

/**
 *
 * @author Samuel Andrés
 */
public enum Sign implements SemanticEnum<Sign>, Predicate<Object> {

    PLUS('+'),
    MINUS('-');

    private final String codePoints;

    Sign(final int... codePoints) {
        final StringBuilder sb = new StringBuilder();
        for (final int codePoint : codePoints) {
            sb.appendCodePoint(codePoint);
        }
        this.codePoints = sb.toString();
    }

    @Override
    public boolean test(final Object token) {
        return token instanceof Sign.Lexeme && this.equals(((Sign.Lexeme) token).getSemantics());
    }

    @Override
    public String getCodePoints() {
        return codePoints;
    }

    @Override
    public int length() {
        return codePoints.length();
    }

    @Override
    public int codePointAt(final int index) {
        return codePoints.codePointAt(index);
    }

    public static Sign toEnum(final String candidate) {
        return EnumCase.SENTITIVE.parse(candidate, values());
    }

    public static boolean exists(final String candidate) {
        return toEnum(candidate) != null;
    }

    public static final class Lexeme extends EnumLexeme<Sign> {

        private Lexeme(final String chars, final int start, final int end, final int index) {
            super(chars, start, end, index, Sign.values(), EnumCase.SENTITIVE);
        }

        private Lexeme(final com.cosmoloj.language.api.semantic.Lexeme toMap) {
            super(toMap, Sign.values(), EnumCase.SENTITIVE);
        }

        @Override
        public Sign parse(final String codePoints) {
            return toEnum(codePoints);
        }
    }

    public static Lexeme map(final com.cosmoloj.language.api.semantic.Lexeme toMap) {
        return new Lexeme(toMap);
    }

    public static EnumLexemeBuilder<Sign> builder() {
        return new EnumLexemeBuilder<Sign>(Sign.class, Sign.values(), EnumCase.SENTITIVE) {

            @Override
            public Lexeme build(final int first, final int last, final int index) {
                return new Lexeme(codePoints(), first, last, index);
            }
        };
    }
}
