package com.cosmoloj.language.wkt.cts.lexeme;

import com.cosmoloj.language.common.impl.builder.EnumLexemeBuilder;
import com.cosmoloj.language.common.impl.semantic.EnumCase;
import com.cosmoloj.language.common.impl.semantic.SemanticEnum;

/**
 *
 * @author Samuel Andrés
 */
public enum AxisDirectionName implements SemanticEnum<AxisDirectionName> {

    NORTH,
    SOUTH,
    EAST,
    WEST,
    UP,
    DOWN,
    OTHER;

    public static EnumLexemeBuilder<AxisDirectionName> builder() {
        return EnumCase.SENTITIVE.builder(AxisDirectionName.class, AxisDirectionName.values());
    }
}
