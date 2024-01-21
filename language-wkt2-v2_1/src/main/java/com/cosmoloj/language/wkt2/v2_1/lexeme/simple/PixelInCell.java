package com.cosmoloj.language.wkt2.v2_1.lexeme.simple;

import com.cosmoloj.language.common.impl.builder.EnumLexemeBuilder;
import com.cosmoloj.language.common.impl.semantic.EnumCase;
import com.cosmoloj.language.common.impl.semantic.SemanticEnum;

/**
 *
 * @author Samuel Andrés
 */
public enum PixelInCell implements SemanticEnum<PixelInCell> {

    CELL_CENTRE("cellCentre"), CELL_CENTER("cellCenter"), CELL_CORNER("cellCorner");

    private final String codePoints;

    PixelInCell(final String codePoints) {
        this.codePoints = codePoints;
    }

    @Override
    public String getCodePoints() {
        return this.codePoints;
    }

    public static EnumLexemeBuilder<PixelInCell> builder() {
        return EnumCase.IGNORE.builder(PixelInCell.class, PixelInCell.values());
    }
}
