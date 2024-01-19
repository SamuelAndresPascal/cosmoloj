package com.cosmoloj.language.wkt.cts.lexeme;

import com.cosmoloj.language.api.semantic.Lexeme;
import com.cosmoloj.language.common.impl.builder.EnumLexemeBuilder;
import com.cosmoloj.language.common.impl.semantic.SemanticEnum;
import java.util.function.Predicate;

/**
 *
 * @author Samuel Andrés
 */
public enum AxisDirectionName implements SemanticEnum<AxisDirectionName>, Predicate<Object> {

    NORTH,
    SOUTH,
    EAST,
    WEST,
    UP,
    DOWN,
    OTHER;

    @Override
    public boolean test(final Object token) {
        return token instanceof Lexeme a && this.equals(a.getSemantics());
    }

    public static EnumLexemeBuilder<AxisDirectionName> builder() {
        return EnumLexemeBuilder.caseSensitive(AxisDirectionName.class, AxisDirectionName.values());
    }
}
