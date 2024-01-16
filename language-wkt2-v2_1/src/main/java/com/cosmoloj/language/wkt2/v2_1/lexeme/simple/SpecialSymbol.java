package com.cosmoloj.language.wkt2.v2_1.lexeme.simple;

import com.cosmoloj.language.common.LanguageUtil;
import com.cosmoloj.language.common.impl.builder.EnumLexemeBuilder;
import com.cosmoloj.language.common.impl.semantic.EnumLexeme;
import com.cosmoloj.language.common.impl.semantic.SemanticEnum;
import java.util.function.Predicate;

/**
 *
 * @author Samuel Andrés
 */
public enum SpecialSymbol implements SemanticEnum<SpecialSymbol>, Predicate<Object> {

    DOUBLEQUOTE('"', '"'),
    COLON(':'),
    COMMA(','),
    Z('Z'), // Séparateur de l'heure et du fuseau horaire
    T('T'), // Séparateur du jour et de l'heure au format ISO
    PLUS_SIGN('+'),
    MINUS_SIGN('-'),
    SPACE(' ');

    private final String codePoints;

    SpecialSymbol(final int... codePoints) {
        final StringBuilder sb = new StringBuilder();
        for (final int codePoint : codePoints) {
            sb.appendCodePoint(codePoint);
        }
        this.codePoints = sb.toString();
    }

    @Override
    public boolean test(final Object token) {
        return token instanceof SpecialSymbol.Lexeme && this.equals(((SpecialSymbol.Lexeme) token).getSemantics());
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

    public static SpecialSymbol toEnum(final String candidate) {
        return LanguageUtil.toEnum(candidate, values());
    }

    public static boolean exists(final String candidate) {
        return toEnum(candidate) != null;
    }

    public static final class Lexeme extends EnumLexeme<SpecialSymbol> {

        private Lexeme(final String chars, final int start, final int end, final int index) {
            super(chars, start, end, index);
        }

        private Lexeme(final Lexeme toMap) {
            super(toMap);
        }

        @Override
        public SpecialSymbol parse(final String codePoints) {
            return toEnum(codePoints);
        }
    }

    public static Lexeme map(final Lexeme toMap) {
        return new Lexeme(toMap);
    }

    public static EnumLexemeBuilder<SpecialSymbol> builder() {
        return new EnumLexemeBuilder<SpecialSymbol>(SpecialSymbol.class, SpecialSymbol.values()) {

            @Override
            public Lexeme build(final int first, final int last, final int index) {
                return new Lexeme(codePoints(), first, last, index);
            }
        };
    }
}
