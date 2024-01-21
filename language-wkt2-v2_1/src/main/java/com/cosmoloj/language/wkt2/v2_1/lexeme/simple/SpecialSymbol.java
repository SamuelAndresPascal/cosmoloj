package com.cosmoloj.language.wkt2.v2_1.lexeme.simple;

import com.cosmoloj.language.common.impl.builder.EnumLexemeBuilder;
import com.cosmoloj.language.common.impl.semantic.EnumCase;
import com.cosmoloj.language.common.impl.semantic.SemanticEnum;

/**
 *
 * @author Samuel Andrés
 */
public enum SpecialSymbol implements SemanticEnum<SpecialSymbol> {

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
    public String getCodePoints() {
        return codePoints;
    }

    public static EnumLexemeBuilder<SpecialSymbol> builder() {
        return EnumCase.SENTITIVE.builder(SpecialSymbol.class, SpecialSymbol.values());
    }
}
