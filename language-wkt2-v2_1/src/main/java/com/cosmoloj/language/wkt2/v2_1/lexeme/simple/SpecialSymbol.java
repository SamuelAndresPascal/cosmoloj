package com.cosmoloj.language.wkt2.v2_1.lexeme.simple;

import com.cosmoloj.language.api.semantic.Lexeme;
import com.cosmoloj.language.common.impl.builder.EnumLexemeBuilder;
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
        return token instanceof Lexeme s && this.equals(s.getSemantics());
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

    public static EnumLexemeBuilder<SpecialSymbol> builder() {
        return EnumLexemeBuilder.caseSensitive(SpecialSymbol.class, SpecialSymbol.values());
    }
}
