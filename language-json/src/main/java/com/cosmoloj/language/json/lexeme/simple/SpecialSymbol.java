package com.cosmoloj.language.json.lexeme.simple;

import com.cosmoloj.language.api.semantic.Lexeme;
import com.cosmoloj.language.common.impl.builder.EnumLexemeBuilder;
import com.cosmoloj.language.common.impl.semantic.SemanticEnum;
import java.util.function.Predicate;

/**
 *
 * @author Samuel Andrés
 */
public enum SpecialSymbol implements SemanticEnum<SpecialSymbol>, Predicate<Object> {

    COMMA(','),
    LEFT_OBJECT_DELIMITER('{'),
    RIGHT_OBJECT_DELIMITER('}'),
    LEFT_ARRAY_DELIMITER('['),
    RIGHT_ARRAY_DELIMITER(']'),
    COLON(':');

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
        return token instanceof Lexeme l && this.equals(l.getSemantics());
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
