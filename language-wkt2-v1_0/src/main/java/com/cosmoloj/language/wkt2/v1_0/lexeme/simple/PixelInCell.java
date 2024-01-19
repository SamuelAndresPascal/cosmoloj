package com.cosmoloj.language.wkt2.v1_0.lexeme.simple;

import com.cosmoloj.language.api.semantic.Token;
import com.cosmoloj.language.common.LanguageUtil;
import com.cosmoloj.language.common.impl.builder.EnumLexemeBuilder;
import com.cosmoloj.language.common.impl.semantic.EnumLexeme;
import com.cosmoloj.language.common.impl.semantic.SemanticEnum;
import java.util.Locale;
import java.util.function.Predicate;

/**
 *
 * @author Samuel Andrés
 */
public enum PixelInCell implements SemanticEnum<PixelInCell>, Predicate<Token> {

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
    public boolean test(final Token token) {
        return token instanceof PixelInCell.Lexeme p && this.equals(p.getSemantics());
    }

    public static PixelInCell toEnum(final String candidate) {
        return LanguageUtil.toEnumIgnoreCase(candidate, values());
    }

    public static final class Lexeme extends EnumLexeme<PixelInCell> {

        private Lexeme(final String codePoints, final int first, final int last, final int index) {
            super(codePoints, first, last, index);
        }

        private Lexeme(final Lexeme toMap) {
            super(toMap);
        }

        @Override
        public PixelInCell parse(final String codePoints) {
            return toEnum(codePoints);
        }
    }

    public static Lexeme map(final Lexeme toMap) {
        return new Lexeme(toMap);
    }

    public static EnumLexemeBuilder<PixelInCell> builder() {
        return new EnumLexemeBuilder<>(PixelInCell.class, PixelInCell.values(),
                EnumLexemeBuilder.Case.IGNORE, Locale.ROOT) {

            @Override
            public Lexeme build(final int first, final int last, final int index) {
                return new Lexeme(codePoints(), first, last, index);
            }
        };
    }
}
