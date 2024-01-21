package com.cosmoloj.language.json.lexeme.simple;

import com.cosmoloj.language.common.impl.builder.EnumLexemeBuilder;
import com.cosmoloj.language.common.impl.semantic.EnumCase;
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
    public String getCodePoints() {
        return codePoints;
    }

    public static EnumLexemeBuilder<SpecialSymbol> builder() {
        return EnumCase.SENTITIVE.builder(SpecialSymbol.class, SpecialSymbol.values());
    }
}
