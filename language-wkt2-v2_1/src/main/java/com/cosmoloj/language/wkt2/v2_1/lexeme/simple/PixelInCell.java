package com.cosmoloj.language.wkt2.v2_1.lexeme.simple;

import com.cosmoloj.language.api.semantic.Lexeme;
import com.cosmoloj.language.common.impl.builder.EnumLexemeBuilder;
import com.cosmoloj.language.common.impl.semantic.SemanticEnum;
import java.util.function.Predicate;

/**
 *
 * @author Samuel Andrés
 */
public enum PixelInCell implements SemanticEnum<PixelInCell>, Predicate<Object> {

    CELL_CENTRE("cellCentre"), CELL_CENTER("cellCenter"), CELL_CORNER("cellCorner");

    private final String codePoints;

    PixelInCell(final String codePoints) {
        this.codePoints = codePoints;
    }

    @Override
    public String getCodePoints() {
        return this.codePoints;
    }

    @Override
    public boolean test(final Object token) {
        return token instanceof Lexeme p && this.equals(p.getSemantics());
    }

    public static EnumLexemeBuilder<PixelInCell> builder() {
        return EnumLexemeBuilder.ignoreCase(PixelInCell.class, PixelInCell.values());
    }
}
