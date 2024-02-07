package com.cosmoloj.language.wkt2.v2_1.lexeme.simple;

import com.cosmoloj.language.common.impl.builder.EnumLexemeBuilder;
import com.cosmoloj.language.common.impl.semantic.EnumCase;
import com.cosmoloj.language.common.impl.semantic.SemanticEnum;

/**
 *
 * @author Samuel Andrés
 */
public enum RangeMeaningType implements SemanticEnum<RangeMeaningType> {

    EXACT, WRAPAROUND;

    public static EnumLexemeBuilder<RangeMeaningType> builder() {
        return EnumCase.IGNORE.builder(RangeMeaningType.class, RangeMeaningType.values());
    }
}
